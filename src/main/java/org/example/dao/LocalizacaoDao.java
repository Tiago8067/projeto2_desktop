package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.Localizacao;

import javax.persistence.EntityManager;

@NoArgsConstructor
@AllArgsConstructor
public class LocalizacaoDao {

    private EntityManager entityManager;

    public void registrar(Localizacao localizacao) {
        try {
            entityManager.getTransaction().begin();
            this.entityManager.persist(localizacao);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }
}
