package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.*;
import org.example.modelsHelp.LinhaEncomendas;
import org.example.util.ConnectionUtil;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class EncomendaDao {
    private EntityManager entityManager;

    public void registar(Encomenda encomenda) {
        this.entityManager.persist(encomenda);
    }

    public List<Encomenda> buscarTodas() {
        String jpql = "SELECT e FROM Encomenda e";
        return entityManager.createQuery(jpql, Encomenda.class).getResultList();
    }

    public Encomenda buscarPorId(Integer id) {
        return entityManager.find(Encomenda.class, id);
    }

    //todo falta a data
    public void atualizarEncomenda(String estado, Integer idEncomenda) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = "UPDATE tb_encomenda " +
                "SET estadoencomenda = ?  " +
                "WHERE idencomenda = ? ";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, estado);
            preparedStatement.setInt(2, idEncomenda);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }

    public void atualizarFornecedorEncomenda(Integer idFornecedor, Integer idEncomenda) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = "UPDATE tb_encomenda " +
                "SET fornecedor_id = ? " +
                "WHERE idencomenda = ? ";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, idFornecedor);
            preparedStatement.setInt(2, idEncomenda);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }

    public void atualizarUtilizadorEncomenda(Integer idUtilizador, Integer idEncomenda) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = "UPDATE tb_encomenda " +
                "SET utilizador_id = ? " +
                "WHERE idencomenda = ? ";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, idUtilizador);
            preparedStatement.setInt(2, idEncomenda);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }

    public List<LinhaEncomendas> buscarTodasEncomendas() {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = "SELECT u.username, r.tiporoupa, r.tamanhoroupa, le.quantidade, f.nome, e.estadoencomenda, e.idencomenda, le.idlinhaencomenda, r.idroupadasencomendas, e.datadepedido, r.datadeentrega  " +
                "FROM tb_linha_encomenda le " +
                "INNER JOIN tb_encomenda e ON e.linha_encomenda_id = le.idlinhaencomenda " +
                "INNER JOIN tb_roupa_das_encomendas r ON r.linha_encomenda_id = le.idlinhaencomenda " +
                "INNER JOIN tb_fornecedor f ON f.idfornecedor = e.fornecedor_id " +
                "INNER JOIN tb_utilizador u ON u.idutilizador = e.utilizador_id ";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<LinhaEncomendas> linhaEncomendaList = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                linhaEncomendaList.add(new LinhaEncomendas(resultSet.getInt(7), resultSet.getInt(8), resultSet.getInt(9), resultSet.getString(1),
                        resultSet.getString(5), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(6),
                        resultSet.getString(10), resultSet.getString(11)));
            }
        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        return linhaEncomendaList;
    }

    public void apagarEncomenda(Integer id) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = " DELETE FROM tb_encomenda WHERE idencomenda = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }

    public List<LinhaEncomendas> buscarTodasEncomendasFiltradasPeloEstado(String estado) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = "SELECT u.username, r.tiporoupa, r.tamanhoroupa, le.quantidade, f.nome, e.estadoencomenda, e.idencomenda, le.idlinhaencomenda, r.idroupadasencomendas, e.datadepedido, r.datadeentrega " +
                "FROM tb_linha_encomenda le " +
                "INNER JOIN tb_encomenda e ON e.linha_encomenda_id = le.idlinhaencomenda " +
                "INNER JOIN tb_roupa_das_encomendas r ON r.linha_encomenda_id = le.idlinhaencomenda " +
                "INNER JOIN tb_fornecedor f ON f.idfornecedor = e.fornecedor_id " +
                "INNER JOIN tb_utilizador u ON u.idutilizador = e.utilizador_id " +
                "WHERE e.estadoencomenda = ? ";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<LinhaEncomendas> linhaEncomendaList = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, estado);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                linhaEncomendaList.add(new LinhaEncomendas(resultSet.getInt(7), resultSet.getInt(8), resultSet.getInt(9), resultSet.getString(1),
                        resultSet.getString(5), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(6),
                        resultSet.getString(7), resultSet.getString(8)));
            }
        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        return linhaEncomendaList;
    }
}
