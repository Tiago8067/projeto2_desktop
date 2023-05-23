package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.Encomenda;
import org.example.models.LinhaEncomenda;
import org.example.modelsHelp.LinhaEncomendas;
import org.example.util.ConnectionUtil;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class LinhaEncomendaDao {
    private EntityManager entityManager;

    public void registar(LinhaEncomenda linhaEncomenda) {
        this.entityManager.persist(linhaEncomenda);
    }

    public List<LinhaEncomenda> buscarTodas() {
        String jpql = "SELECT le FROM LinhaEncomenda le";
        return entityManager.createQuery(jpql, LinhaEncomenda.class).getResultList();
    }

    public LinhaEncomenda buscarPorId(Integer id) {
        return entityManager.find(LinhaEncomenda.class, id);
    }

    public void atualizarLinha_Encomenda(Integer quantidade, Integer id) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = "UPDATE tb_linha_encomenda " +
                "SET quantidade = ? " +
                "WHERE idlinhaencomenda = ? ";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, quantidade);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }

    public void apagarLinhaEncomenda(Integer id) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = " DELETE FROM tb_linha_encomenda WHERE idlinhaencomenda = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }
}
