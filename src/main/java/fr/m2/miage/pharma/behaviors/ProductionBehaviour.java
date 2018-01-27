package fr.m2.miage.pharma.behaviors;

import static fr.m2.miage.pharma.services.DatabaseService.addStockToRandomMaladie;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;


public class ProductionBehaviour extends TickerBehaviour {

  private int minStockTrigger;

  public ProductionBehaviour(Agent a, long period, int minStockTrigger) {
    super(a, period);
    this.minStockTrigger = minStockTrigger;
  }

  @Override
  protected void onTick() {
    addStockToRandomMaladie(minStockTrigger, this.myAgent.getName());
  }
}
