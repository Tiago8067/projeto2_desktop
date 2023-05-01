package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.Utilizador;

import javax.persistence.EntityManager;
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

    /*public List<Utilizador> buscarDadosParaLogin(String username, String email, String password,
                                                 TipoUtilizador tipoUtilizador,
                                                 EstadoUtilizador estadoUtilizador) {
        String jpql = "SELECT u FROM Utilizador u WHERE 1=1 ";
        if (username != null) {
            jpql = " AND u.username = :username ";
        }
        if (email != null) {
            jpql = " AND u.email = :email ";
        }
        if (password != null) {
            jpql = " AND u.password = :password ";
        }
        if (tipoUtilizador != null) {
            jpql = " AND u.tipoUtilizador = :tipoUtilizador ";
        }
        if (estadoUtilizador != null) {
            jpql = " AND u.estadoUtilizador = :estadoUtilizador ";
        }
        TypedQuery<Utilizador> query = entityManager.createQuery(jpql, Utilizador.class);
        if (username != null){
            query.setParameter("username", username);
        }
        if (email != null){
            query.setParameter("email", email);
        }
        if (password != null){
            query.setParameter("password", password);
        }
        if (tipoUtilizador != null){
            query.setParameter("tipoUtilizador", tipoUtilizador);
        }
        if (estadoUtilizador != null){
            query.setParameter("estadoUtilizador", estadoUtilizador);
        }
        return query.getResultList();
    }*/

    public String buscarUsernameDoUtilizador(String username) {
        String jpql = "SELECT u FROM Utilizador u WHERE u.username = :username";
        entityManager.createQuery(jpql, Utilizador.class).setParameter("username", username).getResultList();
        return username;
    }

    public List<Utilizador> buscarUtilizadorPorUsername(String username) {
        String jpql = "SELECT u FROM Utilizador u WHERE u.username = :username";
        return entityManager.createQuery(jpql, Utilizador.class).setParameter("username", username).getResultList();
    }

    public List<Utilizador> buscarUtilizadorPorEmail(String email) {
        String jpql = "SELECT u FROM Utilizador u WHERE u.email = :email";
        return entityManager.createQuery(jpql, Utilizador.class).setParameter("email", email).getResultList();
    }

    public List<Utilizador> buscarUtilizadorPorPassword(String password) {
        String jpql = "SELECT u FROM Utilizador u WHERE u.password = :password";
        return entityManager.createQuery(jpql, Utilizador.class).setParameter("password", password).getResultList();
    }
}
