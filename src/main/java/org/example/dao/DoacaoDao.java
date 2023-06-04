package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.modelsHelp.LinhaDoacoes;
import org.example.models.Doacao;
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
public class DoacaoDao {
    private EntityManager entityManager;

    public void registar(Doacao doacao) {
        this.entityManager.persist(doacao);
    }

    public List<Doacao> buscarTodas() {
        String jpql = "SELECT d FROM Doacao d";
        return entityManager.createQuery(jpql, Doacao.class).getResultList();
    }

    public List<LinhaDoacoes> buscarTodasDoacaoRoupa() {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = "SELECT d.idDoacao, u.username, r.tipoRoupa, r.tamanhoRoupa, rd.quantidade, d.datadadoacao " +
                "FROM tb_roupa_doacao rd " +
                "INNER JOIN tb_doacao d ON d.roupa_doacao_id = rd.id_roupa_doacao " +
                "INNER JOIN tb_roupa r ON r.roupa_doacao_id = rd.id_roupa_doacao " +
                "INNER JOIN tb_utilizador u ON u.idutilizador = d.utilizador_id ";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<LinhaDoacoes> linhaDoacoesList = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                linhaDoacoesList.add(new LinhaDoacoes(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getInt(5), resultSet.getString(6)));
            }
        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        return linhaDoacoesList;
    }

    public Doacao buscarPorId(Integer id) {
        return entityManager.find(Doacao.class, id);
    }

    public void apagarDoacaoPorId(Integer id) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = " DELETE FROM tb_doacao WHERE iddoacao = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }

    public void atualizarDoacao(Integer id, String data) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = "UPDATE tb_roupa_doacao " +
                "SET datadadoacao = ? " +
                "WHERE id_doacao = ? ";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, data);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }
}
