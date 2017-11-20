package fr.m2.miage.pharma.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class ClientRequest {

  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  // Historique
  private String client;

  private Maladie maladie;

  private int nbVaccinsVoulus;

  private boolean hasSold;

  private boolean hadStock;
}
