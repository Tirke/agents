package fr.m2.miage.pharma.agents;

import fr.m2.miage.pharma.behaviors.RegisterService;
import jade.core.Agent;

public class PharmacyAgent extends Agent {

  @Override
  protected void setup() {
    RegisterService rs = new RegisterService(this, "Pharmacy");
    this.addBehaviour(rs);
  }
}
