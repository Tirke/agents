package fr.m2.miage.pharma.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.hibernate.annotations.GenericGenerator;

@NamedQueries({
    @NamedQuery(
        name = "getMaladieByName",
        query = "select m from Maladie m where m.maladie = :maladieName"
    ),
    @NamedQuery(
        name = "getAllMaldie",
        query = "select m from Maladie m"
    )
})

@Entity
public class Maladie {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private String id;
  private int productionTime;
  private double prixInitial;
  private String maladie;
  private double volume;

  public Maladie() {
  }


  public int getProductionTime() {
    return productionTime;
  }

  public void setProductionTime(int productionTime) {
    this.productionTime = productionTime;
  }

  public double getPrixInitial() {
    return prixInitial;
  }

  public void setPrixInitial(double prixInitial) {
    this.prixInitial = prixInitial;
  }

  public String getMaladie() {
    return maladie;
  }

  public void setMaladie(String maladie) {
    this.maladie = maladie;
  }

  public double getVolume() {
    return volume;
  }

  public void setVolume(double volume) {
    this.volume = volume;
  }
}
