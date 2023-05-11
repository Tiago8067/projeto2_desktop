package org.example.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("projeto2_desktop");

    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }
}
