package fr.m2.miage.pharma.agents;

import fr.m2.miage.pharma.behaviors.RegisterService;
import fr.m2.miage.pharma.behaviors.ResponderBehaviourBoiron;
import jade.core.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LaboAgentBoiron extends Agent {

  private final Logger logger = LoggerFactory.getLogger(LaboAgentBoiron.class);


  @Override
  protected void setup() {
    RegisterService rs = new RegisterService(this, "boiron", "labo");
    this.addBehaviour(rs);

    ResponderBehaviourBoiron rb = new ResponderBehaviourBoiron(this);
    this.addBehaviour(rb);
  }

  @Override
  protected void takeDown() {
    logger.info("Taking down " + this.getName() + " gracefully");
  }
}
