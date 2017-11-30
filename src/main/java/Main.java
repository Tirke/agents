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

    session.getTransaction().commit();
    session.close();

    getSessionFactory().close();
  }
}
