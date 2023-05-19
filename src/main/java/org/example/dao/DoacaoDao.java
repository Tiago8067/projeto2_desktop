package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.Doacao;
import org.example.models.Fornecedor;
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
//        try {
//            this.entityManager.getTransaction().begin();
//            this.entityManager.persist(doacao);
//            this.entityManager.getTransaction().commit();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            this.entityManager.getTransaction().rollback();
//        }
        this.entityManager.persist(doacao);
    }

    public void atualizar(Doacao doacao) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.merge(doacao);
            this.entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }

    public List<Doacao> buscarTodas() {
        String jpql = "SELECT d FROM Doacao d";
        return entityManager.createQuery(jpql, Doacao.class).getResultList();
    }

    //todo falta data
    public List<LinhaDoacoes> buscarTodasDoacaoRoupa() {
        //String jpql = "SELECT d.idDoacao, d.utilizador.username, r.tipoRoupa, r.tamanhoRoupa, rd.quantidade FROM Doacao d JOIN Roupa_Doacao rd ON d.idDoacao = rd.id_roupa_doacao, Roupa r JOIN Roupa_Doacao r_d ON r.idRoupa = r_d.id_roupa_doacao";
//        String jpql = "SELECT d.idDoacao, d.utilizador.username, r.tipoRoupa, r.tamanhoRoupa, rd.quantidade " +
//                "FROM Roupa_Doacao rd " +
//                "JOIN rd.doacoes d " +
//                "JOIN rd.roupas r ";
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = "SELECT d.idDoacao, u.username, r.tipoRoupa, r.tamanhoRoupa, rd.quantidade " +
                "FROM tb_roupa_doacao rd " +
                "INNER JOIN tb_doacao d ON d.roupa_doacao_id = rd.id_roupa_doacao " +
                "INNER JOIN tb_roupa r ON r.roupa_doacao_id = rd.id_roupa_doacao " +
                "INNER JOIN tb_utilizador u ON u.idutilizador = d.utilizador_id ";

        //return entityManager.createQuery(jpql, Object[].class).getResultList();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<LinhaDoacoes> linhaDoacoesList = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                linhaDoacoesList.add(new LinhaDoacoes(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getInt(5)));
            }
        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        return linhaDoacoesList;

//        for (Object x : entityManager.createQuery(sql, Object[].class).getResultList()) {
//            linhaDoacoesList.add(new LinhaDoacoes(x))
//        }
    }
}
