package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.Localizacao;
import org.example.models.Utilizador;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class UtilizadorDao {
    private EntityManager entityManager;

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
        LocalizacaoDao localizacaoDao = new LocalizacaoDao(entityManager);
        Localizacao localizacaoRemover;
        try {
            entityManager.getTransaction().begin();
            if (utilizador.getLocalizacao() == null) {
                this.entityManager.remove(utilizador);
            } else {
                localizacaoRemover = localizacaoDao.buscarPorId(utilizador.getLocalizacao().getIdLocalizacao());
                localizacaoDao.removerPeloUtilizador(localizacaoRemover);
                this.entityManager.remove(utilizador);
            }
            entityManager.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }

    public Utilizador buscarPorId(Integer id) {
        return entityManager.find(Utilizador.class, id);
    }

    public List<Utilizador> buscarTodos() {
        String jpql = "SELECT u FROM Utilizador u";
        return entityManager.createQuery(jpql, Utilizador.class).getResultList();
    }

    public Utilizador buscarUtilizadorPorUsername(String username) {
        try {
            String jpql = "SELECT u FROM Utilizador u WHERE u.username = :username";
            return entityManager.createQuery(jpql, Utilizador.class).setParameter("username", username).getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }

    public Utilizador buscarUtilizadorPorEmail(String email) {
        try {
            String jpql = "SELECT u FROM Utilizador u WHERE u.email = :email";
            return entityManager.createQuery(jpql, Utilizador.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }
}
