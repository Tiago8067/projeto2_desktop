package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.RoupaDasEncomendas;
import org.example.util.ConnectionUtil;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class RoupaDasEncomendasDao {
    private EntityManager entityManager;

    public void registar(RoupaDasEncomendas roupaDasEncomendas) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(roupaDasEncomendas);
            this.entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }

    public List<RoupaDasEncomendas> buscarTodas() {
        String jpql = "SELECT r FROM RoupaDasEncomendas r";
        return entityManager.createQuery(jpql, RoupaDasEncomendas.class).getResultList();
    }

    public void atualizarRoupaDasEncomendas(String tipoRoupa, String tamanhoRoupa, Integer id) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = "UPDATE tb_roupa_das_encomendas " +
                "SET tiporoupa = ?, tamanhoroupa = ? " +
                "WHERE idroupadasencomendas = ? ";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, tipoRoupa);
            preparedStatement.setString(2, tamanhoRoupa);
            preparedStatement.setInt(3, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }

    public void atualizarDataEntrega(LocalDate data, Integer id) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = "UPDATE tb_roupa_das_encomendas " +
                "SET datadeentrega = ? " +
                "WHERE linha_encomenda_id = ? ";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(data));
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }

    public void apagarRoupaDasEncomendas(Integer id) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = " DELETE FROM tb_roupa_das_encomendas WHERE idroupadasencomendas = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }
}
