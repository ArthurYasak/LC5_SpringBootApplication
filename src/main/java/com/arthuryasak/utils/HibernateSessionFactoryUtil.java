package application.utils;

import application.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;
    // private static Session session;

    public static Session getSession() {
        sessionFactory = getSessionFactory();
        // try (Session session = sessionFactory.openSession()) {
            Session session = sessionFactory.openSession();
            return session;
        // } catch(Throwable t) {
        //     t.printStackTrace();
        //     return null;
        // }
    }
    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration()
                        .configure("hibernate.cfg.xml") // use hibernate.cfg.xml
                        .addAnnotatedClass(User.class)
                        .addAnnotatedClass(Lot.class)
                        .addAnnotatedClass(LotProperty.class)
                        .addAnnotatedClass(Bet.class)
                        .addAnnotatedClass(UserData.class)
                        .addAnnotatedClass(AuthorizationData.class)
                        .addAnnotatedClass(Commission.class)
                        .buildSessionFactory();
            } catch (Exception e) {
                System.out.println("EXCEPTION! ");
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }
}
