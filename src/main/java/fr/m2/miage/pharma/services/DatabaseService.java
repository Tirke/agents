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

/**
 * Created by Antoine on 29/11/2017.
 */
public class DatabaseService {

  public static int getAvailableUnits(String maladieName, Date peremption) {
    Session session = getSessionFactory().openSession();
    int availableUnits;
    //int overflow is possible
    try {
      availableUnits = (int) (long) session
          .getNamedQuery("getStock")
          .setParameter("maladieName", maladieName)
          .setParameter("datePeremption", peremption)
          .getSingleResult();
    } catch (ArithmeticException e) {
      availableUnits = Integer.MAX_VALUE;
    }
    session.close();

    return availableUnits;
  }

  public static Date getMinDatePremption(String maladieName, Date peremption) {
    Session session = getSessionFactory().openSession();
    Date minPeremption = (Date) session
        .getNamedQuery("getMinPeremptionForMaladie")
        .setParameter("maladieName", maladieName)
        .setParameter("datePeremption", peremption)
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

  public static List<Lot> getAllNotEmptyLotFromMaladie(String maladieName) {
    Session session = getSessionFactory().openSession();
    List<Lot> listeLot = session
        .createNamedQuery("getAllNotEmptyLotFromMaladie", Lot.class)
        .setParameter("maladieName", maladieName)
        .getResultList();
    session.close();
    return listeLot;
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


  public static void addStockToRandomMaladie() {
    Session session = getSessionFactory().openSession();

    List<Maladie> maladies = session
        .createNamedQuery("getAllMaladie", Maladie.class)
        .getResultList();

    Random randomizer = new Random();
    Maladie maladie = maladies.get(randomizer.nextInt(maladies.size()));

    Long getResult = session
        .createNamedQuery("getStockNoDate", Long.class)
        .setParameter("maladieName", maladie.getNom())
        .getResultList().get(0);

    Integer stock = (getResult == null) ? 0 : Math.toIntExact(getResult);

    if (stock <= 20) {
      // On met au hasard le nombre de vaccin à créer
      int stockToSet = randomizer.nextInt(50) + 50;
      Date permeption = new Date(Instant.now().toEpochMilli() + maladie.getDelaiPeremption());
      Date fabrication = new Date(
          Instant.now().toEpochMilli() + maladie.getProductionTime() * stockToSet);

      session.beginTransaction();
      Lot futureLot = new Lot();
      futureLot.setStockActuel(stockToSet);
      futureLot.setStockInitial(stockToSet);
      futureLot.setMaladie(maladie);
      futureLot.setDatePeremption(permeption);
      futureLot.setDateFabrication(fabrication);
      session.save(futureLot);
      session.getTransaction().commit();
    }
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
