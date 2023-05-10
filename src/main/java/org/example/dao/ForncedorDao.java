package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.Fornecedor;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@NoArgsConstructor
@AllArgsConstructor
public class ForncedorDao {
    private EntityManager entityManager;

    public void registrar(Fornecedor fornecedor) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(fornecedor);
            this.entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }

    public Fornecedor buscarFornecedorPorNome(String nome) {
        try {
            String jpql = "SELECT f FROM Fornecedor f WHERE f.nome = :nome";
            return entityManager.createQuery(jpql, Fornecedor.class).setParameter("nome", nome).getSingleResult();
        }  catch (NoResultException noResultException) {
            return null;
        }
    }
}
