package fr.m2.miage.pharma.agents;

import fr.m2.miage.pharma.behaviors.AskerBehavior;
import fr.m2.miage.pharma.behaviors.RegisterService;
import jade.core.Agent;

public class BuyerAgent extends Agent {

  @Override
  protected void setup() {
    System.out.println("Bonjour, je suis l'acheteur");
    RegisterService rs = new RegisterService(this, "Buyer", "");
    this.addBehaviour(rs);
    AskerBehavior ab = new AskerBehavior(this);
    this.addBehaviour(ab);
  }
}
