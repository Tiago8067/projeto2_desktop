package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.Fornecedor;
import org.example.models.Roupa;
import org.example.models.enums.TamanhoRoupa;
import org.example.models.enums.TipoRoupa;
import org.hibernate.Session;

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
            this.entityManager.merge(roupa);
            //this.entityManager.merge(entityManager.getReference(Roupa.class, roupa.getIdRoupa()));
            this.entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }

    public void atualizarStock(Integer id, Integer quantidade) {
        Roupa roupa = entityManager.find(Roupa.class, id);
        roupa.setStock(roupa.getStock() + quantidade);
        //entityManager.merge(roupa);
        entityManager.persist(roupa);
        //Session session = Hibernate
        //Roupa roupa = session.get()
    }


    public List<Roupa> buscarTodas() {
        String jpql = "SELECT r FROM Roupa r";
        return entityManager.createQuery(jpql, Roupa.class).getResultList();
    }

    public List<Roupa> buscarPorTipoRoupa(TipoRoupa tipoRoupa) {
        String jpql = "SELECT r FROM Roupa r WHERE r.tipoRoupa = :tipoRoupa";
        return entityManager.createQuery(jpql, Roupa.class).setParameter("tipoRoupa", tipoRoupa).getResultList();
    }

    public List<Roupa> buscarPorTamanhoRoupa(TamanhoRoupa tamanhoRoupa) {
        String jpql = "SELECT r FROM Roupa r WHERE r.tamanhoRoupa = :tamanhoRoupa";
        return entityManager.createQuery(jpql, Roupa.class).setParameter("tamanhoRoupa", tamanhoRoupa).getResultList();
    }
}
