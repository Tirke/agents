package fr.m2.miage.pharma.behaviors;

import static fr.m2.miage.pharma.services.DatabaseService.addStockToRandomMaladie;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;


public class ProductionBehaviour extends TickerBehaviour {

  public ProductionBehaviour(Agent a, long period) {
    super(a, period);
  }

  @Override
  protected void onTick() {
    addStockToRandomMaladie();
  }
}
