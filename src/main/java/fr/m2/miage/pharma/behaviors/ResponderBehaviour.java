package fr.m2.miage.pharma.behaviors;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ResponderBehaviour extends SimpleBehaviour {

  private final static MessageTemplate templateRequest = MessageTemplate
      .MatchPerformative(ACLMessage.REQUEST);
  private final static MessageTemplate templateAgree = MessageTemplate
      .MatchPerformative(ACLMessage.AGREE);

  public ResponderBehaviour(Agent agent) {
    super(agent);
  }

  @Override
  public void action() {
    while (true) {
      ACLMessage aclMessage = myAgent.receive();
      if (aclMessage != null) {
        switch (aclMessage.getPerformative()) {
          case ACLMessage.REQUEST:
            System.out.println("new request : " + aclMessage.getContent());
            break;
          case ACLMessage.AGREE:
            System.out.println("new agree : " + aclMessage.getContent());
            break;
        }
      }
    }
  }

  @Override
  public boolean done() {
    return true;
  }
}
