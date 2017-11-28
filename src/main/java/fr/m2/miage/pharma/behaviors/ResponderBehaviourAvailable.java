package fr.m2.miage.pharma.behaviors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.m2.miage.pharma.discuss.Proposition;
import fr.m2.miage.pharma.discuss.Request;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.List;

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
        case ACLMessage.AGREE:
          System.out.println("ok");
          //TODO register sale
          //TODO adjust stock
          break;
      }
    }
  }

  private ACLMessage getRespondMessage(ACLMessage demand) {
    List<ACLMessage> offers = new ArrayList<>();

    // Create new messages from demand
    ACLMessage offerWithoutTime = demand.createReply();

    Request request = gson.fromJson(demand.getContent(), Request.class);
    System.out.println(request.getDate());

    // Set type of respond : propose to propose
    offerWithoutTime.setPerformative(ACLMessage.PROPOSE);
    Proposition propositionWithoutTime = new Proposition(10, 0, 100, 1);

    offerWithoutTime.setContent(gson.toJson(propositionWithoutTime));

    return offerWithoutTime;

  }
}
