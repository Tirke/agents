package fr.m2.miage.pharma.agents;

import fr.m2.miage.pharma.behaviors.RegisterService;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class BuyerAgent extends Agent {

  @Override
  protected void setup() {
    System.out.println("Bonjour, je suis l'acheteur");
    RegisterService rs = new RegisterService(this, "Buyer");
    this.addBehaviour(rs);

//    while (true){
//      try {
//        sendMessage("bonjour", new AID("pharmacy", AID.ISLOCALNAME));
//        sendMessageAgree("bonjour, je suis ok", new AID("pharmacy", AID.ISLOCALNAME));
//        Thread.sleep(3000);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//    }

  }

  private void sendMessage(String msg, AID id){
    ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
    aclMessage.addReceiver(id);
    aclMessage.setContent(msg);
    this.send(aclMessage);
  }

  private void sendMessageAgree(String msg, AID id){
    ACLMessage aclMessage = new ACLMessage(ACLMessage.AGREE);
    aclMessage.addReceiver(id);
    aclMessage.setContent(msg);
    this.send(aclMessage);
  }
}
