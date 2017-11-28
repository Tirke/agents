package fr.m2.miage.pharma.behaviors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.m2.miage.pharma.discuss.Proposition;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ResponderBehaviourProduction extends CyclicBehaviour {

  final Gson gson = new GsonBuilder().create();

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
          System.out.println("Production : " + offer.getContent());
          myAgent.send(offer);
          break;

        // Association respond agree
        case ACLMessage.AGREE:
          System.out.println();
          System.out.println("new agree : " + aclMessage.getContent());
          //TODO Begin production and then send

          break;


      }
    }
  }

  private ACLMessage getRespondMessage(ACLMessage demand) {

    // Create new messages from demand
    ACLMessage offerWithTime = demand.createReply();

    // Set type of respond : propose to propose
    offerWithTime.setPerformative(ACLMessage.PROPOSE);
    //TODO en fonction de la BDD créer la proposition
    Proposition propositionWithTime = new Proposition(15, 2, 150);

    offerWithTime.setContent(gson.toJson(propositionWithTime));

    return offerWithTime;

  }
}
