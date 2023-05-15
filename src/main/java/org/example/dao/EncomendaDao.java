package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.Encomenda;

import javax.persistence.EntityManager;

@NoArgsConstructor
@AllArgsConstructor
public class EncomendaDao {
    private EntityManager entityManager;
    public void registar(Encomenda encomenda) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(encomenda);
            this.entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }
}
