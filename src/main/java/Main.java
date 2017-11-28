import static fr.m2.miage.pharma.services.HibernateSessionProvider.getSessionFactory;

import fr.m2.miage.pharma.models.Lot;
import fr.m2.miage.pharma.models.Maladie;
import java.util.Date;
import javax.persistence.Query;
import org.hibernate.Session;

public class Main {

  public static void main(String[] args) {

    // On peut récupérer globalement des sessions
    Session session = getSessionFactory().openSession();
    session.beginTransaction(); // Ouvre la tx

    //Create Maladies
    Maladie maladie = new Maladie();

    maladie.setMaladie("grippe");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(0.2);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setMaladie("sida");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(0.1);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setMaladie("bronchite");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(0.5);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setMaladie("choléra");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(0.2);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setMaladie("coqueluche");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(0.1);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setMaladie("diphtérie");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(0.5);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setMaladie("encéphalite");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(0.9);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setMaladie("fièvre");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(0.4);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setMaladie("hépatite A");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(0.2);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setMaladie("hépatite B");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(0.3);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setMaladie("rage");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(0.8);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setMaladie("rubéole");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(0.2);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setMaladie("varicelle");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(0.1);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setMaladie("variole");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(0.2);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setMaladie("tétanos");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(0.6);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setMaladie("oreillons");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(0.5);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setMaladie("zona");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(0.1);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setMaladie("fièvre jaune");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(0.2);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setMaladie("rotavirus");
    maladie.setPrixInitial(5);
    maladie.setProductionTime(2);
    maladie.setVolume(1.1);
    session.save(maladie);

    Lot lot = new Lot();
    lot.setDateFabrication(new Date(1262304000));
    lot.setDatePeremption(new Date(1577836800));
    lot.setStockInitial(350);
    lot.setStockActuel(300);
    lot.setMaladie(maladie);
    session.save(lot);

    Query query = session.getNamedQuery("getStockFromMaladie");
    query.setParameter("maladieName", "rotavirus");
    int stock = ((Long) query.getResultList().get(0)).intValue();
    System.out.println(stock);

    session.getTransaction().commit(); // On commit
    session.close(); // On oublie pas de fermer la session

    // On peut faire des named queries et tout et tout comme dab.

    // A utiliser que si l'application a fini de tourner
    // C'est assez compliqué dans notre cas vu qu'on utilise pas de Main
    // L'idéal serait de fermer la sessionFactory dans la méthode onDie
    // d'un agent qui ne meurt qu'à la fin de l'application.
    getSessionFactory().close();
  }
}
