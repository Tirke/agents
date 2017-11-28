package fr.m2.miage.pharma.models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Lot {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private String id;
  private Date datePeremption;
  private Date dateFabrication;
  private int stockInitial;
  private int stockActuel;
  @ManyToOne
  private Maladie maladie;
}
