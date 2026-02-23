package org.example.dataProvider;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {

        try{
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Exception e){
            throw new RuntimeException("Error al crear SessionFactory",e);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }


}