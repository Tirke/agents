package fr.m2.miage.pharma.services;

import static fr.m2.miage.pharma.services.HibernateSessionProvider.getSessionFactory;

import fr.m2.miage.pharma.models.Lot;
import fr.m2.miage.pharma.models.Maladie;
import fr.m2.miage.pharma.models.Vente;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseService {

  private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);

  public static int getAvailableUnits(String maladieName, Date peremption, String agentName) {
    Session session = getSessionFactory().openSession();
    Integer availableUnits;

    try {
      Long getResult = session
          .createNamedQuery("getStock", Long.class)
          .setParameter("maladieName", maladieName)
          .setParameter("datePeremption", peremption)
          .setParameter("agentName", agentName.split("-")[0])
          .getSingleResult();

      availableUnits = (getResult == null) ? 0 : Math.toIntExact(getResult);
      //int overflow is possible so we catch
    } catch (ArithmeticException e) {
      availableUnits = Integer.MAX_VALUE;
    }
    session.close();

    return availableUnits;
  }

  public static Date getMinDatePremption(String maladieName, Date peremption, String agentName) {
    Session session = getSessionFactory().openSession();
    Date minPeremption = (Date) session
        .getNamedQuery("getMinPeremptionForMaladie")
        .setParameter("maladieName", maladieName)
        .setParameter("datePeremption", peremption)
        .setParameter("agentName", agentName.split("-")[0])
        .getSingleResult();
    session.close();
    return minPeremption;
  }

  public static void saveVente(
      String agentName,
      String client,
      Date dateLivraison,
      Date dateVente,
      int nbUnite,
      double prixUnitaire,
      Maladie maladie) {
    Vente vente = new Vente();
    vente.setAgent(agentName);
    vente.setClient(client);
    vente.setDateLivraison(dateLivraison);
    vente.setDateVente(dateVente);
    vente.setNbUnite(nbUnite);
    vente.setPrixUnitaire(prixUnitaire);
    vente.setMaladie(maladie);

    Session session = getSessionFactory().openSession();
    session.beginTransaction();
    session.save(vente);
    session.getTransaction().commit();
    session.close();
  }

  public static Maladie getMaladieByName(String name) {
    Session session = getSessionFactory().openSession();
    Maladie maladie = (Maladie) session
        .getNamedQuery("getMaladieByName")
        .setParameter("maladieName", name)
        .getResultList()
        .get(0);
    session.close();
    return maladie;
  }

  public static List<Lot> getAllNotEmptyLotFromMaladie(String maladieName, String agentName) {
    Session session = getSessionFactory().openSession();
    List<Lot> listeLot = session
        .createNamedQuery("getAllNotEmptyLotFromMaladie", Lot.class)
        .setParameter("maladieName", maladieName)
        .setParameter("agentName", agentName.split("-")[0])
        .getResultList();
    session.close();
    return listeLot;
  }

  public static void addStockToRandomMaladie(int minStockTrigger, String agentName) {
    Session session = getSessionFactory().openSession();

    List<Maladie> maladies = session
        .createNamedQuery("getAllMaladie", Maladie.class)
        .getResultList();

    // Choose one random maladie
    Random randomizer = new Random();
    Maladie maladie = maladies.get(randomizer.nextInt(maladies.size()));

    // Get current stock
    Long getResult = session
        .createNamedQuery("getStockNoDate", Long.class)
        .setParameter("maladieName", maladie.getNom())
        .setParameter("agentName", agentName.split("-")[0])
        .getSingleResult();

    Integer stock = (getResult == null) ? 0 : Math.toIntExact(getResult);

    if (stock <= minStockTrigger) {
      // On met au hasard le nombre de vaccin à créer (entre 50 et 100)
      int stockToSet = randomizer.nextInt(50) + 50;

      // On calcule les date (fabrication et péremption)
      Date fabrication = new Date(
          Instant.now().toEpochMilli() + maladie.getProductionTime() * stockToSet);
      Date permeption = new Date(fabrication.getTime() + maladie.getDelaiPeremption());

      // On crée et on ajoute le lot dans la base de données
      Lot futureLot = new Lot();
      futureLot.setAgentName(agentName.split("-")[0]);
      futureLot.setStockActuel(stockToSet);
      futureLot.setStockInitial(stockToSet);
      futureLot.setMaladie(maladie);
      futureLot.setDatePeremption(permeption);
      futureLot.setDateFabrication(fabrication);
      saveObjectInDB(futureLot);
      logger.info(agentName + " : Création de " +
          stockToSet + " vaccins pour " +
          maladie.getNom());
    }
    session.close();
  }

  public static void saveCollectionInDB(Collection collection) {
    Session session = getSessionFactory().openSession();
    session.beginTransaction();

    for (Object o : collection) {
      session.save(o);
    }

    session.getTransaction().commit();
    session.close();
  }

  public static void saveObjectInDB(Object o) {
    Session session = getSessionFactory().openSession();
    session.beginTransaction();
    session.save(o);
    session.getTransaction().commit();
    session.close();
  }
}
