package fr.m2.miage.pharma.agents;

import static fr.m2.miage.pharma.services.HibernateSessionProvider.getSessionFactory;

import fr.m2.miage.pharma.behaviors.ProductionBehaviour;
import fr.m2.miage.pharma.behaviors.RegisterService;
import fr.m2.miage.pharma.behaviors.ResponderBehaviourProduction;
import jade.core.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LaboAgentProduction extends Agent {

  private final Logger logger = LoggerFactory.getLogger(LaboAgentProduction.class);

  @Override
  protected void setup() {
    RegisterService rs = new RegisterService(this, "laboProduction", "labo");
    this.addBehaviour(rs);

    ResponderBehaviourProduction rb = new ResponderBehaviourProduction(this);
    this.addBehaviour(rb);

    ProductionBehaviour pb = new ProductionBehaviour(this, 50000);
    this.addBehaviour(pb);
  }


  @Override
  protected void takeDown() {
    logger.info("Taking down " + this.getName() + " gracefully");
    getSessionFactory().close();
  }
}
