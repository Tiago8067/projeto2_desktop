package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.Localizacao;

import javax.persistence.EntityManager;
import java.util.List;

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

    public void atualizar(Localizacao localizacao) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.merge(localizacao);
            this.entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }

    public void remover(Localizacao localizacao) {
        try {
            entityManager.getTransaction().begin();
            this.entityManager.remove(entityManager.getReference(Localizacao.class, localizacao.getIdLocalizacao()));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }

    public void removerPeloUtilizador(Localizacao localizacao) {
        this.entityManager.remove(localizacao);
    }

    public void removerPeloFornecedor(Localizacao localizacao) {
        try {
            entityManager.getTransaction().begin();
            this.entityManager.remove(entityManager.getReference(Localizacao.class, localizacao.getIdLocalizacao()));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }

    public List<Localizacao> buscarTodos() {
        String jpql = "SELECT l FROM Localizacao l";
        return entityManager.createQuery(jpql, Localizacao.class).getResultList();
    }

    public Localizacao buscarPorId(Integer id) {
        return entityManager.find(Localizacao.class, id);
    }
}
