package fr.m2.miage.pharma.agents;

import fr.m2.miage.pharma.behaviors.RegisterService;
import fr.m2.miage.pharma.behaviors.ResponderBehaviourProduction;
import jade.core.Agent;

public class LaboAgentProduction extends Agent{

  @Override
  protected void setup() {
    RegisterService rs = new RegisterService(this, "laboProduction", "labo");
    this.addBehaviour(rs);

    ResponderBehaviourProduction rb = new ResponderBehaviourProduction(this);
    this.addBehaviour(rb);
  }



}
