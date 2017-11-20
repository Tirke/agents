package fr.m2.miage.pharma.models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Lot {

  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  private float prixInitial;

  private Date datePeremption;

  private Date dateFabrication;

  private int stockInitial;

  private Maladie maladie;
}
