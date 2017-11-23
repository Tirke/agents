package fr.m2.miage.pharma.behaviors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.m2.miage.pharma.Discuss.Proposition;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

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
        case ACLMessage.AGREE:
          System.out.println("new agree : " + aclMessage.getContent());
          break;
        case ACLMessage.CFP:
          System.out.println("new cfp : " + aclMessage.getContent());

          ACLMessage message = aclMessage.createReply();
          ACLMessage message2 = aclMessage.createReply();
          message.setPerformative(ACLMessage.PROPOSE);
          message2.setPerformative(ACLMessage.PROPOSE);
          Proposition proposition = new Proposition(10, 0, 100);
          Proposition proposition2 = new Proposition(15, 2, 150);

          message.setContent(gson.toJson(proposition));
          message2.setContent(gson.toJson(proposition2));

          myAgent.send(message);
          myAgent.send(message2);

          System.out.println("Réponse envoyée : " + message.getPerformative());

          break;
      }
    }
  }
}
