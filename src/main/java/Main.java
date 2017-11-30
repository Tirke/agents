import static fr.m2.miage.pharma.services.HibernateSessionProvider.getSessionFactory;

import fr.m2.miage.pharma.models.Maladie;
import org.hibernate.Session;

public class Main {

  public static void main(String[] args) {

    // On peut récupérer globalement des sessions
    Session session = getSessionFactory().openSession();
    session.beginTransaction(); // Ouvre la tx

    //Create Maladies
    Maladie maladie = new Maladie();

    maladie.setNom("grippe");
    maladie.setPrixInitial(15);
    maladie.setProductionTime(100);
    maladie.setVolume(0.2);
    maladie.setDelaiPeremption(1000*60*2);//2 minutes in ms
    session.save(maladie);

    maladie = new Maladie();
    maladie.setNom("bronchite");
    maladie.setPrixInitial(10);
    maladie.setProductionTime(80);
    maladie.setVolume(0.5);
    maladie.setDelaiPeremption(1000*60*2);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setNom("rage");
    maladie.setPrixInitial(10);
    maladie.setProductionTime(110);
    maladie.setVolume(0.8);
    maladie.setDelaiPeremption(1000*60 + 1000*30);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setNom("variole");
    maladie.setPrixInitial(17);
    maladie.setProductionTime(90);
    maladie.setVolume(0.2);
    maladie.setDelaiPeremption(1000*60 + 1000*60 + 1000*10);
    session.save(maladie);

    maladie = new Maladie();
    maladie.setNom("sida");
    maladie.setPrixInitial(25);
    maladie.setProductionTime(105);
    maladie.setVolume(0.1);
    maladie.setDelaiPeremption(1000*60 + 1000*60 + 1000*60);
    session.save(maladie);

//    maladie = new Maladie();
//    maladie.setNom("choléra");
//    maladie.setPrixInitial(5);
//    maladie.setProductionTime(2);
//    maladie.setVolume(0.2);
//    maladie.setDelaiPeremption(10);
//    session.save(maladie);
//
//    maladie = new Maladie();
//    maladie.setNom("coqueluche");
//    maladie.setPrixInitial(5);
//    maladie.setProductionTime(2);
//    maladie.setVolume(0.1);
//    maladie.setDelaiPeremption(10);
//    session.save(maladie);
//
//    maladie = new Maladie();
//    maladie.setNom("diphtérie");
//    maladie.setPrixInitial(5);
//    maladie.setProductionTime(2);
//    maladie.setVolume(0.5);
//    maladie.setDelaiPeremption(10);
//    session.save(maladie);
//
//    maladie = new Maladie();
//    maladie.setNom("encéphalite");
//    maladie.setPrixInitial(5);
//    maladie.setProductionTime(2);
//    maladie.setVolume(0.9);
//    maladie.setDelaiPeremption(10);
//    session.save(maladie);
//
//    maladie = new Maladie();
//    maladie.setNom("fièvre");
//    maladie.setPrixInitial(5);
//    maladie.setProductionTime(2);
//    maladie.setVolume(0.4);
//    maladie.setDelaiPeremption(10);
//    session.save(maladie);
//
//    maladie = new Maladie();
//    maladie.setNom("hépatite A");
//    maladie.setPrixInitial(5);
//    maladie.setProductionTime(2);
//    maladie.setVolume(0.2);
//    maladie.setDelaiPeremption(10);
//    session.save(maladie);
//
//    maladie = new Maladie();
//    maladie.setNom("hépatite B");
//    maladie.setPrixInitial(5);
//    maladie.setProductionTime(2);
//    maladie.setVolume(0.3);
//    maladie.setDelaiPeremption(10);
//    session.save(maladie);
//
//    maladie = new Maladie();
//    maladie.setNom("rubéole");
//    maladie.setPrixInitial(5);
//    maladie.setProductionTime(2);
//    maladie.setVolume(0.2);
//    maladie.setDelaiPeremption(10);
//    session.save(maladie);
//
//    maladie = new Maladie();
//    maladie.setNom("varicelle");
//    maladie.setPrixInitial(5);
//    maladie.setProductionTime(2);
//    maladie.setVolume(0.1);
//    maladie.setDelaiPeremption(10);
//    session.save(maladie);
//
//    maladie = new Maladie();
//    maladie.setNom("tétanos");
//    maladie.setPrixInitial(5);
//    maladie.setProductionTime(2);
//    maladie.setVolume(0.6);
//    maladie.setDelaiPeremption(10);
//    session.save(maladie);
//
//    maladie = new Maladie();
//    maladie.setNom("oreillons");
//    maladie.setPrixInitial(5);
//    maladie.setProductionTime(2);
//    maladie.setVolume(0.5);
//    maladie.setDelaiPeremption(10);
//    session.save(maladie);
//
//    maladie = new Maladie();
//    maladie.setNom("zona");
//    maladie.setPrixInitial(5);
//    maladie.setProductionTime(2);
//    maladie.setVolume(0.1);
//    maladie.setDelaiPeremption(10);
//    session.save(maladie);
//
//    maladie = new Maladie();
//    maladie.setNom("fièvre jaune");
//    maladie.setPrixInitial(5);
//    maladie.setProductionTime(2);
//    maladie.setVolume(0.2);
//    maladie.setDelaiPeremption(10);
//    session.save(maladie);
//
//    maladie = new Maladie();
//    maladie.setNom("rotavirus");
//    maladie.setPrixInitial(5);
//    maladie.setProductionTime(2);
//    maladie.setVolume(1.1);
//    maladie.setDelaiPeremption(10);
//    session.save(maladie);
//
//    Lot lot = new Lot();
//    lot.setDateFabrication(new Date(1262304000));
//    lot.setDatePeremption(new Date(1577836800));
//    lot.setStockInitial(350);
//    lot.setStockActuel(300);
//    lot.setMaladie(maladie);
//    session.save(lot);
//
//    Query query = session.getNamedQuery("getStockFromMaladie");
//    query.setParameter("maladieName", "rotavirus");
//    int stock = ((Long) query.getResultList().get(0)).intValue();
//    System.out.println(stock);

    session.getTransaction().commit(); // On commit
    session.close(); // On oublie pas de fermer la session

    // On peut faire des named queries et tout et tout comme dab.

    // A utiliser que si l'application a fini de tourner
    getSessionFactory().close();
  }
}
