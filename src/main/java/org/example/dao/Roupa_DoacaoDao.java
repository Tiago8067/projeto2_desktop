package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.Doacao;
import org.example.models.Fornecedor;
import org.example.models.Roupa;
import org.example.models.Roupa_Doacao;

import javax.persistence.EntityManager;
import java.util.List;

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

    public void atualizar(Roupa_Doacao roupa_doacao) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.merge(roupa_doacao);
            this.entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }

    public List<Roupa_Doacao> buscarTodas() {
        String jpql = "SELECT r_d FROM Roupa_Doacao r_d";
        return entityManager.createQuery(jpql, Roupa_Doacao.class).getResultList();
    }
}
