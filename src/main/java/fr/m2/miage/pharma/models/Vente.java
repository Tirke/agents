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
  private String agent;
  private int nbUnite;
  private float prixUnitaire;
  @ManyToOne
  private Maladie maladie;
  private String client;


  public Date getDateLivraison() {
    return dateLivraison;
  }

  public void setDateLivraison(Date dateLivraison) {
    this.dateLivraison = dateLivraison;
  }

  public Date getDateVente() {
    return dateVente;
  }

  public void setDateVente(Date dateVente) {
    this.dateVente = dateVente;
  }

  public String getAgent() {
    return agent;
  }

  public void setAgent(String agent) {
    this.agent = agent;
  }

  public int getNbUnite() {
    return nbUnite;
  }

  public void setNbUnite(int nbUnite) {
    this.nbUnite = nbUnite;
  }

  public float getPrixUnitaire() {
    return prixUnitaire;
  }

  public void setPrixUnitaire(float prixUnitaire) {
    this.prixUnitaire = prixUnitaire;
  }

  public Maladie getMaladie() {
    return maladie;
  }

  public void setMaladie(Maladie maladie) {
    this.maladie = maladie;
  }

  public String getClient() {
    return client;
  }

  public void setClient(String client) {
    this.client = client;
  }
}
