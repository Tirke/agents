package fr.m2.miage.pharma.agents;

import static fr.m2.miage.pharma.services.HibernateSessionProvider.getSessionFactory;

import fr.m2.miage.pharma.behaviors.RegisterService;
import fr.m2.miage.pharma.behaviors.ResponderBehaviourAvailable;
import jade.core.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LaboAgentAvailable extends Agent {

  private final Logger logger = LoggerFactory.getLogger(LaboAgentAvailable.class);

  @Override
  protected void setup() {
    Object[] args = getArguments();
    double reduction = (Double) args[0];

    RegisterService rs = new RegisterService(this,  this.getLocalName(), "labo");
    this.addBehaviour(rs);

    // Behaviour to respond to association
    ResponderBehaviourAvailable rb = new ResponderBehaviourAvailable(this, reduction);
    this.addBehaviour(rb);
  }

  @Override
  protected void takeDown() {
    logger.info("Taking down " + this.getName() + " gracefully");
    getSessionFactory().close();
  }
}
