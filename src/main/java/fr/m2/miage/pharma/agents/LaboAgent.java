package fr.m2.miage.pharma.agents;

import fr.m2.miage.pharma.behaviors.RegisterService;
import fr.m2.miage.pharma.behaviors.ResponderBehaviour;
import jade.core.Agent;

public class LaboAgent extends Agent {

  @Override
  protected void setup() {
    System.out.println("Bonjour, je suis le vendeur");
    RegisterService rs = new RegisterService(this, "Pharmacy", "labo");
    this.addBehaviour(rs);
    ResponderBehaviour rb = new ResponderBehaviour(this);
    this.addBehaviour(rb);
//    Idée de ContractNet maybe pas utile
//    addBehaviour(new ContractNetResponder());
  }
}
