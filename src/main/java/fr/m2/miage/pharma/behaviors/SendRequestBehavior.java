package fr.m2.miage.pharma.behaviors;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class SendRequestBehavior extends TickerBehaviour {

  public SendRequestBehavior(Agent a, long period) {
    super(a, period);
  }

  @Override
  protected void onTick() {
    sendMessage("bonjour", new AID("pharmacy", AID.ISLOCALNAME), ACLMessage.REQUEST);
  }

  private void sendMessage(String msg, AID id, int type) {
    ACLMessage aclMessage = new ACLMessage(type);
    aclMessage.addReceiver(id);
    aclMessage.setContent(msg);
    super.getAgent().send(aclMessage);
  }
}
