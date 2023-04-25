package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//serve para so criar umja unica vez a factory
public class JPAUtil {
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("projeto2_desktop");

    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }
}
