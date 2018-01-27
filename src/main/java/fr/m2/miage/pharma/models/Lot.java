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
            + "where l.maladie.nom = :maladieName and "
            + "l.agentName = :agentName and "
            + "l.datePeremption > :datePeremption and "
            + "l.dateFabrication < current_timestamp "
    ),
    @NamedQuery(
        name = "getAllNotEmptyLotFromMaladie",
        query = "select l from Lot l "
            + "where l.maladie.nom = :maladieName and "
            + "l.agentName = :agentName and "
            + "l.stockActuel > 0 and "
            + "l.dateFabrication < current_timestamp "
            + "order by l.datePeremption"
    ),
    @NamedQuery(
        name = "getStockNoDate",
        query = "select sum(l.stockActuel) from Lot l "
            + "where l.maladie.nom = :maladieName and "
            + "l.agentName = :agentName and "
            + "l.datePeremption >= current_timestamp "
    ),
    @NamedQuery(
        name = "getMinPeremptionForMaladie",
        query = "select min(l.datePeremption) from Lot l where "
            + "l.maladie.nom = :maladieName and "
            + "l.agentName = :agentName and "
            + "l.datePeremption > :datePeremption and "
            + "l.dateFabrication < current_timestamp "
    )
})

@Entity
public class Lot {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private String id;
  private String agentName;
  private Date datePeremption;
  private Date dateFabrication;
  private int stockInitial;
  private int stockActuel;
  @ManyToOne
  private Maladie maladie;

  public Date getDatePeremption() {
    return datePeremption;
  }

  public String getAgentName() {
    return agentName;
  }

  public void setAgentName(String agentName) {
    this.agentName = agentName;
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
