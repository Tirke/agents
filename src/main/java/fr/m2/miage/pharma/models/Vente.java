package fr.m2.miage.pharma.models;


import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Vente {

  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  String id;


}
