package fr.m2.miage.pharma.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Client {
  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;
  private String name;
}
