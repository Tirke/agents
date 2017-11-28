package fr.m2.miage.pharma.models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.hibernate.annotations.GenericGenerator;

@NamedQueries({
    @NamedQuery(
        name = "getStock",
        query = "select sum(l.stockActuel) from Lot l "
            + "where l.maladie.maladie = :maladieName and "
            + "l.datePeremption > :datePeremption and "
            + "l.dateFabrication < current_date()"
    ),
    @NamedQuery(
        name = "getAllNotEmptyLotFromMaladie",
        query = "select l from Lot l "
            + "where l.maladie.maladie = :maladieName and "
            + "l.stockActuel > 0 and "
            + "l.dateFabrication < current_date() "
            + "order by l.datePeremption"
    )
})

@Entity
public class Lot {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private String id;
  private Date datePeremption;
  private Date dateFabrication;
  private int stockInitial;
  private int stockActuel;
  @ManyToOne
  private Maladie maladie;

  public Date getDatePeremption() {
    return datePeremption;
  }

  public void setDatePeremption(Date datePeremption) {
    this.datePeremption = datePeremption;
  }

  public Date getDateFabrication() {
    return dateFabrication;
  }

  public void setDateFabrication(Date dateFabrication) {
    this.dateFabrication = dateFabrication;
  }

  public int getStockInitial() {
    return stockInitial;
  }

  public void setStockInitial(int stockInitial) {
    this.stockInitial = stockInitial;
  }

  public int getStockActuel() {
    return stockActuel;
  }

  public void setStockActuel(int stockActuel) {
    this.stockActuel = stockActuel;
  }

  public Maladie getMaladie() {
    return maladie;
  }

  public void setMaladie(Maladie maladie) {
    this.maladie = maladie;
  }
}
