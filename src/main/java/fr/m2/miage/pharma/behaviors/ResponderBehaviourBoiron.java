package fr.m2.miage.pharma.behaviors;

import static fr.m2.miage.pharma.services.HibernateSessionProvider.getSessionFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.m2.miage.pharma.discuss.Proposition;
import fr.m2.miage.pharma.discuss.Request;
import fr.m2.miage.pharma.models.Maladie;
import fr.m2.miage.pharma.models.Vente;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;

public class ResponderBehaviourBoiron extends CyclicBehaviour{


  final Gson gson = new GsonBuilder().create();


  public ResponderBehaviourBoiron(Agent agent) {
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
        case ACLMessage.AGREE:
          registerSale(aclMessage);
          break;
      }
    }
  }

  private void registerSale(ACLMessage aclMessage) {

    Proposition proposition = (Proposition) getDataStore().get(aclMessage.getConversationId());
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

    session.getTransaction().commit(); // On commit
    session.close(); // On oublie pas de fermer la session

  }

  private ACLMessage getRespondMessage(ACLMessage demand) {
    List<ACLMessage> offers = new ArrayList<>();

    // Create new messages from demand
    ACLMessage offerWithoutTime = demand.createReply();

    Request request = gson.fromJson(demand.getContent(), Request.class);

    // Set type of respond : propose to propose
    offerWithoutTime.setPerformative(ACLMessage.PROPOSE);

    Session session = getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.getNamedQuery("getMaladieByName");
    query.setParameter("maladieName", request.getMaladie());

    Maladie maladie = (Maladie) query.getResultList().get(0);

    session.getTransaction().commit();
    session.close();

    float prix = maladie.getPrixInitial();
    Proposition propositionWithoutTime = new Proposition(prix, 0, request.getNb(), maladie.getVolume());

    getDataStore().put(demand.getConversationId(), propositionWithoutTime);
    getDataStore().put(demand.getConversationId()+":maladie", maladie);

    offerWithoutTime.setContent(gson.toJson(propositionWithoutTime));

    return offerWithoutTime;

  }

}
