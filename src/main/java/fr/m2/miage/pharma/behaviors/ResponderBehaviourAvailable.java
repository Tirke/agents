package fr.m2.miage.pharma.behaviors;

import static fr.m2.miage.pharma.services.DatabaseService.getAllNotEmptyLotFromMaladie;
import static fr.m2.miage.pharma.services.DatabaseService.getAvailableUnits;
import static fr.m2.miage.pharma.services.DatabaseService.getMaladieByName;
import static fr.m2.miage.pharma.services.DatabaseService.getMinDatePremption;
import static fr.m2.miage.pharma.services.DatabaseService.saveCollectionInDB;
import static fr.m2.miage.pharma.services.DatabaseService.saveVente;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.m2.miage.pharma.discuss.Proposition;
import fr.m2.miage.pharma.discuss.Request;
import fr.m2.miage.pharma.models.Lot;
import fr.m2.miage.pharma.models.Maladie;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponderBehaviourAvailable extends CyclicBehaviour {

  private final Logger logger = LoggerFactory.getLogger(ResponderBehaviourAvailable.class);
  private final Gson gson = new GsonBuilder().create();
  private double reduction;


  public ResponderBehaviourAvailable(Agent agent, double reduction) {
    super(agent);
    this.reduction = reduction;
  }

  @Override
  public void action() {
    ACLMessage aclMessage = myAgent.receive();

    if (aclMessage != null) {
      switch (aclMessage.getPerformative()) {
        // Association demands
        case ACLMessage.CFP:
          ACLMessage offer = getRespondMessage(aclMessage);
          logger.info("Sending offer" + offer.getPerformative());
          myAgent.send(offer);
          break;

        // Association respond agree
        case ACLMessage.ACCEPT_PROPOSAL:
          logger.info("The proposition done by " + myAgent.getName() + " was accepted :D");

          if (registerSale(aclMessage)) {
            adjustStock(aclMessage);
            ACLMessage reponseValidation = aclMessage.createReply();
            reponseValidation.setPerformative(ACLMessage.INFORM);
            myAgent.send(reponseValidation);
          } else {
            ACLMessage reponseRefus = aclMessage.createReply();
            reponseRefus.setPerformative(ACLMessage.FAILURE);
            myAgent.send(reponseRefus);
          }
          break;

        case ACLMessage.REJECT_PROPOSAL:
          logger.info("The proposition done by " + myAgent.getName() + " was refused ...");
          break;
      }
    }
  }

  private void adjustStock(ACLMessage aclMessage) {
    Proposition proposition = (Proposition) getDataStore().get(aclMessage.getConversationId());
    int unitsToRemove = proposition.getNombre();
    Maladie maladie = (Maladie) getDataStore().get(aclMessage.getConversationId() + ":maladie");

    List<Lot> listeLot = getAllNotEmptyLotFromMaladie(maladie.getNom());

    int i = 0;
    while (unitsToRemove > 0 && i < listeLot.size()) {

      int removeOnThisLot = Integer.min(unitsToRemove, listeLot.get(i).getStockActuel());
      listeLot.get(i).setStockActuel(listeLot.get(i).getStockActuel() - removeOnThisLot);

      unitsToRemove -= removeOnThisLot;
      i += 1;
    }

    saveCollectionInDB(listeLot);
  }

  private boolean registerSale(ACLMessage aclMessage) {
    Proposition proposition = (Proposition) getDataStore().get(aclMessage.getConversationId());

    Maladie maladie = (Maladie) getDataStore().get(aclMessage.getConversationId() + ":maladie");
    Date datePeremption = (Date) getDataStore()
        .get(aclMessage.getConversationId() + ":datePeremption");
    int availableUnits = getAvailableUnits(maladie.getNom(), datePeremption);
    boolean weHaveEnough = availableUnits >= proposition.getNombre();

    if (weHaveEnough) {
      saveVente(myAgent.getName(), aclMessage.getSender().getName(), proposition.getDateLivraison(),
          new Date(), proposition.getNombre(), proposition.getPrix(), maladie);
    }

    return weHaveEnough;
  }

  private ACLMessage getRespondMessage(ACLMessage demand) {
    Request request = gson.fromJson(demand.getContent(), Request.class);
    Maladie maladie = getMaladieByName(request.getMaladie());

    ACLMessage offerWithoutTime = demand.createReply();

    int proposeUnit = Integer
        .min(request.getNb(), getAvailableUnits(request.getMaladie(), request.getDate()));
    Date datePeremption = getMinDatePremption(request.getMaladie(), request.getDate());
    double prix = maladie.getPrixInitial() * (1 - reduction);

    Proposition propositionWithoutTime = new Proposition(prix, new Date(), datePeremption,
        proposeUnit, maladie.getVolume());

    getDataStore().put(demand.getConversationId(), propositionWithoutTime);
    getDataStore().put(demand.getConversationId() + ":maladie", maladie);
    getDataStore().put(demand.getConversationId() + ":datePeremption", request.getDate());

    offerWithoutTime.setContent(gson.toJson(propositionWithoutTime));

    if (proposeUnit == 0) {
      // On ne fait pas d'offre si on n'a pas les vaccins en DB
      offerWithoutTime.setPerformative(ACLMessage.REFUSE);
      logger.info("*** Je refuse " + proposeUnit);
    } else {
      offerWithoutTime.setPerformative(ACLMessage.PROPOSE);
    }

    return offerWithoutTime;
  }
}
