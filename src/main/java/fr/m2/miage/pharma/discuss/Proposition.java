package fr.m2.miage.pharma.discuss;

import java.io.Serializable;

public class Proposition implements Serializable{
  private float prix;
  private int delai;
  private int nombre;
  private float volume;

  public Proposition(float prix, int delai, int nombre, float volume) {
    this.prix = prix;
    this.delai = delai;
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

  public int getDelai() {
    return delai;
  }

  public int getNombre() {
    return nombre;
  }
}
