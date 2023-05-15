package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.Doacao;
import org.example.models.Fornecedor;

import javax.persistence.EntityManager;

@NoArgsConstructor
@AllArgsConstructor
public class DoacaoDao {
    private EntityManager entityManager;

    public void registar(Doacao doacao) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(doacao);
            this.entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }

    public void atualizar(Doacao doacao) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.merge(doacao);
            this.entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }
}
