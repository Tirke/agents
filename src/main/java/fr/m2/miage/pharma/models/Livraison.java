package fr.m2.miage.pharma.models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

// Uniquement si il y a un délai

@Entity
public class Livraison {

  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @OneToOne
  private Vente vente;

  private Date dateLivraison;

  @OneToOne
  private Maladie maladie;

  private int resteAProduire;

  public Livraison(Vente vente, Date dateLivraison, Maladie maladie, int resteAProduire) {
    this.vente = vente;
    this.dateLivraison = dateLivraison;
    this.maladie = maladie;
    this.resteAProduire = resteAProduire;
  }
}
