package fr.m2.miage.pharma.models;


import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Vente {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  String id;
  private Date dateLivraison;
  private Date dateVente;
  private int nbUnite;
  private int prixUnitaire;
  @ManyToOne
  private Maladie maladie;
  @ManyToOne
  private Client client;


}
