package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.models.Utilizador;

import javax.persistence.EntityManager;
import java.util.List;

@AllArgsConstructor
public class UtilizadorDao {

    private EntityManager entityManager;

    /*
    public UtilizadorDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    */

    public void registrar(Utilizador utilizador) {
        try {
            entityManager.getTransaction().begin();
            this.entityManager.persist(utilizador);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }

    public void atualizar(Utilizador utilizador) {
        this.entityManager.merge(utilizador);
    }

    public void remover(Utilizador utilizador) {
        utilizador = entityManager.merge(utilizador);
        this.entityManager.remove(utilizador);
    }

    public Utilizador buscarPorId(Integer id) {
        return entityManager.find(Utilizador.class, id);
    }

    public List<Utilizador> buscarTodos() {
        String jpql = "SELECT u FROM Utilizador u";
        return entityManager.createQuery(jpql, Utilizador.class).getResultList();
    }
}
