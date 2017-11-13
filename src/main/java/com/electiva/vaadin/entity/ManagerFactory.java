package com.electiva.vaadin.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ManagerFactory {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenUnitElectiva");
    private static final EntityManager em = emf.createEntityManager();

    public static EntityManager getEntityManager() {
        return em;
    }

}
