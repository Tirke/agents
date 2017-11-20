package fr.m2.miage.pharma.behaviors;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ResponderBehaviour extends CyclicBehaviour {

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
      }
    }
  }
}
