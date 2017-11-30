package fr.m2.miage.pharma.agents;

import static fr.m2.miage.pharma.services.HibernateSessionProvider.getSessionFactory;

import fr.m2.miage.pharma.behaviors.ProductionBehaviour;
import fr.m2.miage.pharma.behaviors.RegisterService;
import fr.m2.miage.pharma.behaviors.ResponderBehaviourProduction;
import jade.core.Agent;

public class LaboAgentProduction extends Agent {


  @Override
  protected void setup() {
    RegisterService rs = new RegisterService(this, "laboProduction", "labo");
    this.addBehaviour(rs);

    ResponderBehaviourProduction rb = new ResponderBehaviourProduction(this);
    this.addBehaviour(rb);

    ProductionBehaviour pb = new ProductionBehaviour(this, 1000);
    this.addBehaviour(pb);
  }


  @Override
  protected void takeDown() {
    getSessionFactory().close();
  }
}
