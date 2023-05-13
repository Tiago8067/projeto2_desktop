package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.Fornecedor;
import org.example.models.Roupa;

import javax.persistence.EntityManager;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class RoupaDao {
    private EntityManager entityManager;

    public void registar(Roupa roupa) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(roupa);
            this.entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }

    public void atualizar(Roupa roupa) {
        try {
            this.entityManager.getTransaction().begin();
            //this.entityManager.merge(roupa);
            this.entityManager.merge(entityManager.getReference(Roupa.class, roupa.getIdRoupa()));
            this.entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }


    public List<Roupa> buscarTodas() {
        String jpql = "SELECT r FROM Roupa r";
        return entityManager.createQuery(jpql, Roupa.class).getResultList();
    }
}
