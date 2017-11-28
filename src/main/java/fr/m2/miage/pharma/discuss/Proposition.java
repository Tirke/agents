package fr.m2.miage.pharma.discuss;

import java.io.Serializable;
import java.util.Date;

public class Proposition implements Serializable{
  private double prix;
  private Date dateLivraison;
  private Date datePeremption;
  private int nombre;
  private double volume;


  public Proposition(double prix, Date dateLivraison, Date datePeremption, int nombre,
      double volume) {
    this.prix = prix;
    this.dateLivraison = dateLivraison;
    this.datePeremption = datePeremption;
    this.nombre = nombre;
    this.volume = volume;
  }

  public double getPrix() {
    return prix;
  }

  public void setPrix(double prix) {
    this.prix = prix;
  }

  public Date getDateLivraison() {
    return dateLivraison;
  }

  public void setDateLivraison(Date dateLivraison) {
    this.dateLivraison = dateLivraison;
  }

  public Date getDatePeremption() {
    return datePeremption;
  }

  public void setDatePeremption(Date datePeremption) {
    this.datePeremption = datePeremption;
  }

  public int getNombre() {
    return nombre;
  }

  public void setNombre(int nombre) {
    this.nombre = nombre;
  }

  public double getVolume() {
    return volume;
  }

  public void setVolume(double volume) {
    this.volume = volume;
  }
}
