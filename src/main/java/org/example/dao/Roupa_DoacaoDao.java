package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.Doacao;
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
//                "FROM tb_doacao d " +
//                "JOIN tb_utilizador u ON u.idutilizador = d.utilizador_id " +
                "WHERE id_roupa_doacao = ? ";

        //todo atualizar queries update do dao com as ligacoes da tabela ou mandar para a class LinhaDoacoes os outros ids da classes associadas

        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, quantidade);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }
}
