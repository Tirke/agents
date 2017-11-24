package fr.m2.miage.pharma.behaviors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.m2.miage.pharma.discuss.Proposition;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.List;

public class ResponderBehaviour extends CyclicBehaviour {

  final Gson gson = new GsonBuilder().create();


  public ResponderBehaviour(Agent agent) {
    super(agent);
  }

  @Override
  public void action() {
    ACLMessage aclMessage = myAgent.receive();

    if (aclMessage != null) {
      switch (aclMessage.getPerformative()) {
        case ACLMessage.REQUEST:
          System.out.println("new request : " + aclMessage.getContent());
          break;

        // Association respond agree
        case ACLMessage.AGREE:
          System.out.println();
          System.out.println("new agree : " + aclMessage.getContent());
          System.out.println(aclMessage.getConversationId());

          break;

        // Association demands
        case ACLMessage.CFP:

          List<ACLMessage> offers = getRespondMessage(aclMessage);

          for (ACLMessage offer : offers) {
            myAgent.send(offers.get(0));
          }

          break;
      }
    }
  }

  private List<ACLMessage> getRespondMessage(ACLMessage demand) {
    List<ACLMessage> offers = new ArrayList<>();

    // Create new messages from demand
    ACLMessage offerWithoutTime = demand.createReply();
    ACLMessage offerWithTime = demand.createReply();

    // Set type of respond : propose to propose
    offerWithoutTime.setPerformative(ACLMessage.PROPOSE);
    offerWithTime.setPerformative(ACLMessage.PROPOSE);
    Proposition propositionWithoutTime = new Proposition(10, 0, 100);
    Proposition propositionWithTime = new Proposition(15, 2, 150);

    offerWithoutTime.setContent(gson.toJson(propositionWithoutTime));
    offerWithTime.setContent(gson.toJson(propositionWithTime));

    offers.add(offerWithoutTime);
    offers.add(offerWithTime);

    return offers;
  }
}
