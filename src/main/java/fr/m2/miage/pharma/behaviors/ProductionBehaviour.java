package fr.m2.miage.pharma.behaviors;

import static fr.m2.miage.pharma.services.HibernateSessionProvider.getSessionFactory;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import org.hibernate.Session;

public class ProductionBehaviour extends TickerBehaviour {

  public ProductionBehaviour(Agent a, long period) {
    super(a, period);
  }

  @Override
  protected void onTick() {

    //TODO Create lots where stock < 10 or 20 or more (

    Session session = getSessionFactory().openSession();


    session.close();
  }
}
