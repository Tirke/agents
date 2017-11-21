package fr.m2.miage.pharma.agents;

import fr.m2.miage.pharma.behaviors.AskerBehavior;
import fr.m2.miage.pharma.behaviors.RegisterService;
import fr.m2.miage.pharma.behaviors.SendAgreeBehavior;
import fr.m2.miage.pharma.behaviors.SendRequestBehavior;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;

public class BuyerAgent extends Agent {

  @Override
  protected void setup() {
    System.out.println("Bonjour, je suis l'acheteur");
    RegisterService rs = new RegisterService(this, "Buyer", "");
    this.addBehaviour(rs);
    AskerBehavior ab = new AskerBehavior(this);
    this.addBehaviour(new SendRequestBehavior(this, 1000));
    this.addBehaviour(new SendAgreeBehavior(this, 3000));

    SequentialBehaviour sb = new SequentialBehaviour(this);
    sb.reset();
  }
}
