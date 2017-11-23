package fr.m2.miage.pharma.behaviors;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class SendAgreeBehavior extends TickerBehaviour {

  public SendAgreeBehavior(Agent a, long period) {
    super(a, period);
  }

  @Override
  protected void onTick() {
    sendMessage("bonjour, je suis ok", new AID("pharmacy", AID.ISLOCALNAME),
        ACLMessage.CFP);
  }

  private void sendMessage(String msg, AID id, int type) {
    ACLMessage aclMessage = new ACLMessage(type);
    aclMessage.addReceiver(id);
    aclMessage.setContent(msg);
    super.getAgent().send(aclMessage);
  }
}
