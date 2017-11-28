package fr.m2.miage.pharma.agents;

import fr.m2.miage.pharma.behaviors.RegisterService;
import fr.m2.miage.pharma.behaviors.ResponderBehaviourAvailable;
import jade.core.Agent;

public class LaboAgentAvailable extends Agent {

  @Override
  protected void setup() {
    RegisterService rs = new RegisterService(this, "laboAvailable", "labo");
    this.addBehaviour(rs);

    // Behaviour to respond to association
    ResponderBehaviourAvailable rb = new ResponderBehaviourAvailable(this);
    this.addBehaviour(rb);
  }
}
