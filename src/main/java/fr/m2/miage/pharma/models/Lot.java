package fr.m2.miage.pharma.models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Lot {

  @Id
  String id;

  float prixInitial;

  Date datePeremption;

  Date dateFabrication;

  int stockInitial;

  Maladie maladie;

}
