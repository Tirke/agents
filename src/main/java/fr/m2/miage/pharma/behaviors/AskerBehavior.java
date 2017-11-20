package fr.m2.miage.pharma.behaviors;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AskerBehavior extends CyclicBehaviour {

  public AskerBehavior(Agent a) {
    super(a);
  }

  @Override
  public void action() {
    try {
      sendMessage("bonjour", new AID("pharmacy", AID.ISLOCALNAME), ACLMessage.REQUEST);
      sendMessage("bonjour, je suis ok", new AID("pharmacy", AID.ISLOCALNAME),
          ACLMessage.AGREE);
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


  private void sendMessage(String msg, AID id, int type) {
    ACLMessage aclMessage = new ACLMessage(type);
    aclMessage.addReceiver(id);
    aclMessage.setContent(msg);
    super.getAgent().send(aclMessage);
  }
}
