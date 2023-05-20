package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.Roupa_Doacao;
import org.example.util.ConnectionUtil;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Roupa_DoacaoDao {
    private EntityManager entityManager;

    public void registar(Roupa_Doacao roupa_doacao) {
        this.entityManager.persist(roupa_doacao);
    }

    public void atualizar(Roupa_Doacao roupa_doacao) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.merge(roupa_doacao);
            this.entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }

    public List<Roupa_Doacao> buscarTodas() {
        String jpql = "SELECT r_d FROM Roupa_Doacao r_d";
        return entityManager.createQuery(jpql, Roupa_Doacao.class).getResultList();
    }

    public void atualizarRoupa_Doacao(Integer id, Integer quantidade) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = "UPDATE tb_roupa_doacao " +
                "SET quantidade = ? " +
                "WHERE id_roupa_doacao = ? ";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, quantidade);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }

    public void apagarRoupa_DoacaoPorId(Integer id) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = " DELETE FROM tb_roupa_doacao WHERE id_roupa_doacao = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }
}
