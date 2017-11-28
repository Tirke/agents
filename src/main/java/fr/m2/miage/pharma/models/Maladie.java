package fr.m2.miage.pharma.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.hibernate.annotations.GenericGenerator;

@NamedQueries({
    @NamedQuery(
        name = "getMaladieId",
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
}
