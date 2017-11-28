package fr.m2.miage.pharma.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Maladie {

  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;
  private int productionTime;
  private float prixInitial;
  private String maladie;
}
