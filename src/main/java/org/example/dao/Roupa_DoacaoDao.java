package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.Fornecedor;
import org.example.models.Roupa;
import org.example.models.Roupa_Doacao;

import javax.persistence.EntityManager;

@NoArgsConstructor
@AllArgsConstructor
public class Roupa_DoacaoDao {
    private EntityManager entityManager;

    public void registar(Roupa_Doacao roupa_doacao) {
        this.entityManager.persist(roupa_doacao);
//        try {
//            this.entityManager.getTransaction().begin();
//            this.entityManager.persist(roupa_doacao);
//            this.entityManager.getTransaction().commit();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            this.entityManager.getTransaction().rollback();
//        }
    }

    public void atulizar(Roupa_Doacao roupa_doacao) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.merge(roupa_doacao);
            this.entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }
}
