package fr.m2.miage.pharma.behaviors;

import static fr.m2.miage.pharma.services.DatabaseService.getAllNotEmptyLotFromMaladie;
import static fr.m2.miage.pharma.services.DatabaseService.getAvailableUnits;
import static fr.m2.miage.pharma.services.DatabaseService.getMaladieByName;
import static fr.m2.miage.pharma.services.DatabaseService.getMinDatePremption;
import static fr.m2.miage.pharma.services.DatabaseService.saveCollectionInDB;
import static fr.m2.miage.pharma.services.DatabaseService.saveObjectInDB;
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
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class ResponderBehaviourProduction extends CyclicBehaviour {

  private final Gson gson = new GsonBuilder().create();

  public ResponderBehaviourProduction(Agent a) {
    super(a);
  }

  @Override
  public void action() {
    ACLMessage aclMessage = myAgent.receive();

    if (aclMessage != null) {
      switch (aclMessage.getPerformative()) {
        // Association demands
        case ACLMessage.CFP:
          ACLMessage offer = getRespondMessage(aclMessage);
          myAgent.send(offer);
          break;

        case ACLMessage.ACCEPT_PROPOSAL:
          System.out.println("The proposition done by " + myAgent.getName() + " was accepted :D");

          if(registerSale(aclMessage)){
            adjustStock(aclMessage);
            createFutureLot(aclMessage);
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
          System.out.println("The proposition done by " + myAgent.getName() + " was refused ...");
          break;
      }
    }
  }

  private void createFutureLot(ACLMessage aclMessage) {
    Proposition proposition = (Proposition) getDataStore().get(aclMessage.getConversationId());
    Maladie maladie = (Maladie) getDataStore().get(aclMessage.getConversationId() + ":maladie");
    int prodcutionPrevue = (int) getDataStore().get(aclMessage.getConversationId()+":productionPrevue");

    Lot futureLot = new Lot();
    futureLot.setStockActuel(0);
    futureLot.setStockInitial(prodcutionPrevue);
    futureLot.setMaladie(maladie);
    futureLot.setDatePeremption(proposition.getDatePeremption());
    futureLot.setDateFabrication(proposition.getDateLivraison());

    saveObjectInDB(futureLot);
  }

  private void adjustStock(ACLMessage aclMessage) {
    Proposition proposition = (Proposition) getDataStore().get(aclMessage.getConversationId());
    Maladie maladie = (Maladie) getDataStore().get(aclMessage.getConversationId() + ":maladie");
    int unitsToRemove = getAvailableUnits(maladie.getNom(), proposition.getDatePeremption());

    List<Lot> listeLot = getAllNotEmptyLotFromMaladie(maladie.getNom());

    int i = 0;
    while (unitsToRemove > 0 && i < listeLot.size()){

      int removeOnThisLot = Integer.min(unitsToRemove, listeLot.get(i).getStockActuel());
      listeLot.get(i).setStockActuel(listeLot.get(i).getStockActuel() - removeOnThisLot);

      unitsToRemove -= removeOnThisLot;
      i += 1;
    }
    if (listeLot != null) {
      saveCollectionInDB(listeLot);
    }
  }

  private boolean registerSale(ACLMessage aclMessage) {
    Proposition proposition = (Proposition) getDataStore().get(aclMessage.getConversationId());

    Maladie maladie = (Maladie) getDataStore().get(aclMessage.getConversationId() + ":maladie");
    Date datePeremption = (Date) getDataStore().get(aclMessage.getConversationId() + ":datePeremption");
    int productionPrevue = (int) getDataStore().get(aclMessage.getConversationId()+":productionPrevue");
    int availableUnits = getAvailableUnits(maladie.getNom(), datePeremption);

    if (availableUnits + productionPrevue >= proposition.getNombre()){
      saveVente(myAgent.getName(), aclMessage.getSender().getName(), proposition.getDateLivraison(),
          new Date(), proposition.getNombre(), proposition.getPrix(), maladie);
      return true;
    } else {
      return false;
    }
  }

  private ACLMessage getRespondMessage(ACLMessage demand) {
    Request request = gson.fromJson(demand.getContent(), Request.class);
    Maladie maladie = getMaladieByName(request.getMaladie());

    ACLMessage offerWithTime = demand.createReply();
    offerWithTime.setPerformative(ACLMessage.PROPOSE);

    int availableUnits = getAvailableUnits(maladie.getNom(), request.getDate());

    // Price and Delevery date if we have enough units
    double prix = maladie.getPrixInitial()*0.9;
    Date dateLivraison = new Date();

    // If we don't, some math
    if(availableUnits < request.getNb()){
      prix = maladie.getPrixInitial()*0.85;
      dateLivraison = new Date(Instant.now().toEpochMilli() + maladie.getProductionTime()*(request.getNb() - availableUnits));
    }

    Date datePeremption = getMinDatePremption(request.getMaladie(), request.getDate());
    // This date may be null if there is no units available in database
    // Which means we have to calculate this date
    if(datePeremption == null){datePeremption = new Date(dateLivraison.getTime() + maladie.getDelaiPeremption());}

    Proposition propositionWithTime = new Proposition(prix, dateLivraison, datePeremption, request.getNb(), maladie.getVolume());

    offerWithTime.setContent(gson.toJson(propositionWithTime));

    getDataStore().put(demand.getConversationId(), propositionWithTime);
    getDataStore().put(demand.getConversationId()+":maladie", maladie);
    getDataStore().put(demand.getConversationId()+":datePeremption", request.getDate());
    getDataStore().put(demand.getConversationId()+":productionPrevue", request.getNb() - availableUnits);

    return offerWithTime;
  }
}
