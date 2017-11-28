package fr.m2.miage.pharma.discuss;

import java.io.Serializable;
import java.util.Date;

public class Proposition implements Serializable{
  private float prix;
  private Date dateLivraison;
  private int nombre;
  private float volume;

  public Proposition(float prix, Date dateLivraison, int nombre, float volume) {
    this.prix = prix;
    this.dateLivraison = dateLivraison;
    this.nombre = nombre;
    this.volume = volume;
  }

  public float getVolume() {
    return volume;
  }

  public void setVolume(float volume) {
    this.volume = volume;
  }

  public float getPrix() {
    return prix;
  }

  public Date getDateLivraison() {
    return dateLivraison;
  }

  public int getNombre() {
    return nombre;
  }
}
