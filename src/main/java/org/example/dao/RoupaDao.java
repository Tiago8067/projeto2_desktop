package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.Roupa;
import org.example.models.Roupa_Doacao;
import org.example.models.enums.TamanhoRoupa;
import org.example.models.enums.TipoRoupa;
import org.example.util.ConnectionUtil;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
            this.entityManager.merge(entityManager.getReference(Roupa.class, roupa.getIdRoupa()));
            this.entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }

    public void atualizarStock(Roupa roupa, Integer stock) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = "UPDATE Roupa SET Stock = ? WHERE tipoRoupa = ? AND tamanhoRoupa = ?"; //tipoRoupa = ? AND tamanhoRoupa = ?   idRoupa = ?

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, stock);
            statement.setString(2, String.valueOf(roupa.getTipoRoupa()));
            statement.setString(3, String.valueOf(roupa.getTamanhoRoupa()));

            statement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
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

    public Roupa buscarPorTipoTamanhoRoupa(Roupa roupa) { //TipoRoupa tipoRoupa, TamanhoRoupa tamanhoRoupa
        String jpql = "SELECT r.stock FROM Roupa r WHERE r.tipoRoupa=:tipoRoupa AND r.tamanhoRoupa=:tamanhoRoupa ";
        return entityManager.createQuery(jpql, Roupa.class)
                .setParameter("tipoRoupa", roupa.getTipoRoupa())
                .setParameter("tamanhoRoupa", roupa.getTamanhoRoupa())
                .getSingleResult();
    }

    public Roupa buscarPorId(Integer id) {
        return entityManager.find(Roupa.class, id);
    }

    public void atualizarRoupa(Integer id, String tipoRoupa, String tamanhoRoupa) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = " UPDATE tb_roupa " +
                " SET tiporoupa = ? " +
                " , tamanhoroupa = ? " +
                " WHERE idroupa = ? " ;

        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, tipoRoupa);
            preparedStatement.setString(2, tamanhoRoupa);
            preparedStatement.setInt(3, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }

}
