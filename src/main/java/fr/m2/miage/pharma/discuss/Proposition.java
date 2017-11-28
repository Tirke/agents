package fr.m2.miage.pharma.discuss;

import java.io.Serializable;
import java.util.Date;

public class Proposition implements Serializable{
  private double prix;
  private Date dateLivraison;
  private int nombre;
  private double volume;

  public Proposition(double prix, Date dateLivraison, int nombre, double volume) {
    this.prix = prix;
    this.dateLivraison = dateLivraison;
    this.nombre = nombre;
    this.volume = volume;
  }

  public double getVolume() {
    return volume;
  }

  public void setVolume(double volume) {
    this.volume = volume;
  }

  public double getPrix() {
    return prix;
  }

  public Date getDateLivraison() {
    return dateLivraison;
  }

  public int getNombre() {
    return nombre;
  }
}
