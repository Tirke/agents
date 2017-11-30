package fr.m2.miage.pharma.behaviors;

import static fr.m2.miage.pharma.services.HibernateSessionProvider.getSessionFactory;

import fr.m2.miage.pharma.models.Maladie;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import java.util.List;
import java.util.Random;
import org.hibernate.Session;

public class ProductionBehaviour extends TickerBehaviour {

  public ProductionBehaviour(Agent a, long period) {
    super(a, period);
  }

  @Override
  protected void onTick() {

    Session session = getSessionFactory().openSession();

    List<Maladie> maladies = session
        .createNamedQuery("getAllMaladie", Maladie.class)
        .getResultList();

    Random randomizer = new Random();
    Maladie maladie = maladies.get(randomizer.nextInt(maladies.size()));

    Object result = session
        .getNamedQuery("getStockNoDate")
        .setParameter("maladieName", maladie.getNom())
        .getResultList().get(0);
    int stock = (result == null) ? 0 : ((Long) result).intValue();

    System.out.println(stock);

    System.out.println(maladie.getNom());
    System.out.println(stock);

    //TODO Créer un lot

    session.close();
  }
}
