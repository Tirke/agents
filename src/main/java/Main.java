import fr.m2.miage.pharma.models.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main {

//  private static final SessionFactory ourSessionFactory;
//
//  static {
//    try {
//      ourSessionFactory = new Configuration().
//          configure("hibernate.cfg.xml").
//          buildSessionFactory();
//    } catch (Throwable ex) {
//      throw new ExceptionInInitializerError(ex);
//    }
//  }
//
//  public static Session getSession() throws HibernateException {
//    return ourSessionFactory.openSession();
//  }
//
//  public static void main(final String[] args) throws Exception {
//    final Session session = getSession();
//    try {
//      System.out.println("querying all the managed entities...");
//      final Map metadataMap = session.getSessionFactory().getAllClassMetadata();
//      for (Object key : metadataMap.keySet()) {
//        final ClassMetadata classMetadata = (ClassMetadata) metadataMap.get(key);
//        final String entityName = classMetadata.getEntityName();
//        final Query query = session.createQuery("from " + entityName);
//        System.out.println("executing: " + query.getQueryString());
//        for (Object o : query.list()) {
//          System.out.println("  " + o);
//        }
//      }
//    } finally {
//      session.close();
//    }
//  }

  public static void main(String[] args) {
    final StandardServiceRegistry registry =
        new StandardServiceRegistryBuilder()
            .configure("hibernate.cfg.xml")
            .build();
    SessionFactory sessionFactory = new MetadataSources(registry)
        .buildMetadata()
        .buildSessionFactory();

    Session session = sessionFactory.openSession();
    Transaction tx = session.beginTransaction();

    Client client = new Client();
    client.setName("Arnaud");

    session.persist(client);
    tx.commit();
    session.close();
    sessionFactory.close();
  }
}
