package fr.m2.miage.pharma.services;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateSessionProvider {

  private static SessionFactory sessionFactory;

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
          .configure()
          .build();
      try {
        sessionFactory = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();
      } catch (Exception e) {
        StandardServiceRegistryBuilder.destroy(registry);
      }
    }

    return sessionFactory;
  }
}
