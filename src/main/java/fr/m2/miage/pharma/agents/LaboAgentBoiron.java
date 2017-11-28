package fr.m2.miage.pharma.agents;

import fr.m2.miage.pharma.behaviors.RegisterService;
import fr.m2.miage.pharma.behaviors.ResponderBehaviourBoiron;
import fr.m2.miage.pharma.behaviors.ResponderBehaviourProduction;
import jade.core.Agent;

public class LaboAgentBoiron extends Agent {

  @Override
  protected void setup() {
    RegisterService rs = new RegisterService(this, "boiron", "labo");
    this.addBehaviour(rs);

    ResponderBehaviourBoiron rb = new ResponderBehaviourBoiron(this);
    this.addBehaviour(rb);
  }
}
