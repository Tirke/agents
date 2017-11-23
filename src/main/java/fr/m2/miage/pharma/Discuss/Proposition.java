package fr.m2.miage.pharma.Discuss;

import java.io.Serializable;

public class Proposition implements Serializable{
  private int prix;
  private int delai;
  private int nombre;

  public Proposition(int prix, int delai, int nombre) {
    this.prix = prix;
    this.delai = delai;
    this.nombre = nombre;
  }
}
