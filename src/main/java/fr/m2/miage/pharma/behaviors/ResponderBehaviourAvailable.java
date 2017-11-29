package fr.m2.miage.pharma.behaviors;

import static fr.m2.miage.pharma.services.HibernateSessionProvider.getSessionFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.m2.miage.pharma.discuss.Proposition;
import fr.m2.miage.pharma.discuss.Request;
import fr.m2.miage.pharma.models.Lot;
import fr.m2.miage.pharma.models.Maladie;
import fr.m2.miage.pharma.models.Vente;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder.In;
import org.hibernate.Session;

public class ResponderBehaviourAvailable extends CyclicBehaviour {

  final Gson gson = new GsonBuilder().create();


  public ResponderBehaviourAvailable(Agent agent) {
    super(agent);
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

        // Association respond agree
        case ACLMessage.ACCEPT_PROPOSAL:
          if(registerSale(aclMessage)){
            adjustStock(aclMessage);
            ACLMessage reponseValidation = aclMessage.createReply();
            reponseValidation.setPerformative(ACLMessage.INFORM);
            myAgent.send(reponseValidation);
          } else {
            ACLMessage reponseValidation = aclMessage.createReply();
            reponseValidation.setPerformative(ACLMessage.FAILURE);
            myAgent.send(reponseValidation);
          }
          break;
        case ACLMessage.REJECT_PROPOSAL:
          System.out.println("The proposition done by " + myAgent.getName() + " was refused ...");
          break;
      }
    }
  }

  private void adjustStock(ACLMessage aclMessage) {
    Proposition proposition = (Proposition) getDataStore().get(aclMessage.getConversationId());
    int unitsToRemove = proposition.getNombre();
    Maladie maladie = (Maladie) getDataStore().get(aclMessage.getConversationId() + ":maladie");

    Session session = getSessionFactory().openSession();
    List<Lot> listeLot = session
        .getNamedQuery("getAllNotEmptyLotFromMaladie")
        .setParameter("maladieName", maladie.getMaladie())
        .getResultList();

    int i = 0;
    while (unitsToRemove > 0 && i < listeLot.size()){

      int removeOnThisLot = Integer.min(unitsToRemove, listeLot.get(i).getStockActuel());
      listeLot.get(i).setStockActuel(listeLot.get(i).getStockActuel() - removeOnThisLot);

      unitsToRemove -= removeOnThisLot;
      i += 1;
    }

    session = getSessionFactory().openSession();
    session.beginTransaction();

    for (Lot lot: listeLot) {
      session.save(lot);
    }

    session.getTransaction().commit();
    session.close();
  }

  private boolean registerSale(ACLMessage aclMessage) {
    Proposition proposition = (Proposition) getDataStore().get(aclMessage.getConversationId());

    Maladie maladie = (Maladie) getDataStore().get(aclMessage.getConversationId() + ":maladie");
    Date datePeremption = (Date) getDataStore().get(aclMessage.getConversationId() + ":datePeremption");
    int availableUnits = getAvailableUnits(maladie.getMaladie(), datePeremption);

    if (availableUnits >= proposition.getNombre()){
      Vente vente = new Vente();
      vente.setAgent(myAgent.getName());
      vente.setClient(aclMessage.getSender().getName());
      vente.setDateLivraison(new Date());
      vente.setDateVente(new Date());
      vente.setNbUnite(proposition.getNombre());
      vente.setPrixUnitaire(proposition.getPrix());
      vente.setMaladie((Maladie) getDataStore().get(aclMessage.getConversationId() + ":maladie"));

      Session session = getSessionFactory().openSession();
      session.beginTransaction();
      session.save(vente);
      session.getTransaction().commit();
      session.close();

      return true;
    } else {
      return false;
    }
  }

  private ACLMessage getRespondMessage(ACLMessage demand) {

    // Create new messages from demand
    ACLMessage offerWithoutTime = demand.createReply();

    Request request = gson.fromJson(demand.getContent(), Request.class);

    Session session = getSessionFactory().openSession();
    Maladie maladie = (Maladie) session
        .getNamedQuery("getMaladieByName")
        .setParameter("maladieName", request.getMaladie())
        .getResultList()
        .get(0);
    session.close();

    // Set type of respond : propose to propose
    offerWithoutTime.setPerformative(ACLMessage.PROPOSE);

    int proposeUnit = Integer.min(request.getNb(), getAvailableUnits(request.getMaladie(), request.getDate()));
    Date datePeremption = getMinDatePremption(request.getMaladie(), request.getDate());
    double prix = maladie.getPrixInitial()*0.90;

    Proposition propositionWithoutTime = new Proposition(prix, new Date(), datePeremption, proposeUnit, maladie.getVolume());

    getDataStore().put(demand.getConversationId(), propositionWithoutTime);
    getDataStore().put(demand.getConversationId()+":maladie", maladie);
    getDataStore().put(demand.getConversationId()+":datePeremption", request.getDate());

    offerWithoutTime.setContent(gson.toJson(propositionWithoutTime));

    return offerWithoutTime;
  }

  private Date getMinDatePremption(String maladieName, Date peremption){
    Session session = getSessionFactory().openSession();
    Date minPeremption = (Date) session
        .getNamedQuery("getMinPeremptionForMaladie")
        .setParameter("maladieName", maladieName)
        .setParameter("datePeremption", peremption)
        .getSingleResult();
    session.close();
    return minPeremption;
  }

  private int getAvailableUnits(String maladieName, Date peremption){
    Session session = getSessionFactory().openSession();
    int availableUnits;
    //int overflow is possible
    try {
      availableUnits = (int) (long) session
          .getNamedQuery("getStock")
          .setParameter("maladieName", maladieName)
          .setParameter("datePeremption", peremption)
          .getSingleResult();
    } catch (ArithmeticException e){
      availableUnits = Integer.MAX_VALUE;
    }
    session.close();

    return availableUnits;
  }
}
