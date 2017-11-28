import static fr.m2.miage.pharma.services.HibernateSessionProvider.getSessionFactory;

import fr.m2.miage.pharma.models.Client;
import org.hibernate.Session;

public class Main {

  public static void main(String[] args) {

    // On peut récupérer globalement des sessions
    Session session = getSessionFactory().openSession();
    session.beginTransaction(); // Ouvre la tx

    Client client = new Client();
    client.setName("Arnaud");

    session.save(client); // On utilise save et non persist
    session.getTransaction().commit(); // On commit
    session.close(); // On oublie pas de fermer la session

    // On peut faire des named queries et tout et tout comme dab.

    // A utiliser que si l'application a fini de tourner
    getSessionFactory().close();
  }
}
