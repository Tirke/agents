package fr.m2.miage.pharma.behaviors;

import static fr.m2.miage.pharma.services.DatabaseService.getMaladieByName;
import static fr.m2.miage.pharma.services.DatabaseService.saveVente;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.m2.miage.pharma.discuss.Proposition;
import fr.m2.miage.pharma.discuss.Request;
import fr.m2.miage.pharma.models.Maladie;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponderBehaviourBoiron extends CyclicBehaviour {

  private final Logger logger = LoggerFactory.getLogger(ResponderBehaviourBoiron.class);
  private final Gson gson = new GsonBuilder().create();


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
          logger.info("Sending offer");
          myAgent.send(offer);
          break;

        // Association respond agree
        case ACLMessage.ACCEPT_PROPOSAL:
          logger.info("The proposition done by " + myAgent.getName() + " was accepted :D");

          registerSale(aclMessage);
          ACLMessage inform = aclMessage.createReply();
          aclMessage.setPerformative(ACLMessage.INFORM);
          myAgent.send(inform);
          logger.info("inform sent");
          break;
        case ACLMessage.REJECT_PROPOSAL:
          logger.info("The proposition done by " + myAgent.getName() + " was refused ...");
          break;
      }
    }
  }

  private void registerSale(ACLMessage aclMessage) {
    Proposition proposition = (Proposition) getDataStore().get(aclMessage.getConversationId());
    saveVente(myAgent.getName(), aclMessage.getSender().getName(), new Date(), new Date(),
        proposition.getNombre(), proposition.getPrix(),
        (Maladie) getDataStore().get(aclMessage.getConversationId() + ":maladie"));
  }

  private ACLMessage getRespondMessage(ACLMessage demand) {
    // Create new messages from demand
    ACLMessage offerWithoutTime = demand.createReply();

    Request request = gson.fromJson(demand.getContent(), Request.class);
    logger.info(
        "New demand from " + demand.getSender().getName() + " (vaccin : " + request.getMaladie()
            + ", quantity : " + request.getNb()
            + ", date : " + request.getDate() + ")"
    );

    // Set type of respond : propose to propose
    offerWithoutTime.setPerformative(ACLMessage.PROPOSE);

    Maladie maladie = getMaladieByName(request.getMaladie());

    double prix = maladie.getPrixInitial();
    Date today = new Date();
    Proposition proposition = new Proposition(prix, new Date(),
        new Date(today.getTime() + (1000 * 60 * 60 * 24)), request.getNb(),
        maladie.getVolume());

    getDataStore().put(demand.getConversationId(), proposition);
    getDataStore().put(demand.getConversationId() + ":maladie", maladie);

    offerWithoutTime.setContent(gson.toJson(proposition));

    return offerWithoutTime;
  }

}
