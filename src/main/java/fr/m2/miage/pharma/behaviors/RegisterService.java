package fr.m2.miage.pharma.behaviors;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class RegisterService extends OneShotBehaviour {

  private String name;
  private String type;

  public RegisterService(Agent a, String name, String type) {
    super(a);
    this.name = name;
    this.type = type;
  }

  @Override
  public void action() {
    DFAgentDescription dfd = new DFAgentDescription();
    dfd.setName(super.myAgent.getAID());
    ServiceDescription sd = new ServiceDescription();
    sd.setType(this.type);
    sd.setName(this.name);
    dfd.addServices(sd);
    try {
      DFService.register(super.myAgent, dfd);
    } catch (FIPAException e) {
      System.err.println(super.myAgent.getLocalName() + " error during service register");
      super.myAgent.doDelete();
    }
  }
}
