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
    )
})

@Entity
public class Maladie {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private String id;
  private int productionTime;
  private float prixInitial;
  private String maladie;
  private float volume;

  public Maladie() {
  }

  public float getVolume() {
    return volume;
  }

  public void setVolume(float volume) {
    this.volume = volume;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getProductionTime() {
    return productionTime;
  }

  public void setProductionTime(int productionTime) {
    this.productionTime = productionTime;
  }

  public float getPrixInitial() {
    return prixInitial;
  }

  public void setPrixInitial(float prixInitial) {
    this.prixInitial = prixInitial;
  }

  public String getMaladie() {
    return maladie;
  }

  public void setMaladie(String maladie) {
    this.maladie = maladie;
  }
}
