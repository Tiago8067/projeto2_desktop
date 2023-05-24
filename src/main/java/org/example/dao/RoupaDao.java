package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.modelsHelp.LinhaRoupa;
import org.example.models.Roupa;
import org.example.models.enums.CategoriaRoupa;
import org.example.models.enums.TamanhoRoupa;
import org.example.models.enums.TipoRoupa;
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
            this.entityManager.merge(roupa);
//            this.entityManager.merge(entityManager.getReference(Roupa.class, roupa.getIdRoupa()));
            this.entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }

    public void atualizarStock(Roupa roupa, Integer stock) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = "UPDATE Roupa SET Stock = ? WHERE tipoRoupa = ? AND tamanhoRoupa = ?";

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

    public List<Roupa> buscarPorTipoTamanhoRoupa(Roupa roupa) {
        String jpql = " SELECT r FROM Roupa r WHERE r.tipoRoupa = :tipoRoupa AND r.tamanhoRoupa = :tamanhoRoupa ";
        return entityManager.createQuery(jpql, Roupa.class)
                .setParameter("tipoRoupa", roupa.getTipoRoupa())
                .setParameter("tamanhoRoupa", roupa.getTamanhoRoupa())
                .getResultList();
    }

    public Roupa buscarPorId(Integer id) {
        return entityManager.find(Roupa.class, id);
    }

    public void atualizarRoupa(Integer id, String tipoRoupa, String tamanhoRoupa, String categoriaRopupa, String imgsrc) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = " UPDATE tb_roupa " +
                " SET tiporoupa = ? " +
                " , tamanhoroupa = ? " +
                " , categoriaroupa = ? " +
                " , imagesrc = ? " +
                " WHERE idroupa = ? ";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, tipoRoupa);
            preparedStatement.setString(2, tamanhoRoupa);
            preparedStatement.setString(3, categoriaRopupa);
            preparedStatement.setString(4, imgsrc);
            preparedStatement.setInt(5, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }

    public void atualizarRoupaEmPedidos(Integer id, Integer stock) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = "UPDATE tb_roupa " +
                "SET stock = ? " +
                "WHERE idroupa = ? ";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, stock);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }

    public void atualizarRoupaEncomendas(Integer idLinhaEncomenda, Integer id) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = "UPDATE tb_roupa " +
                "SET linha_encomenda_id = ? " +
                "WHERE idencomenda = ? ";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, idLinhaEncomenda);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }

    public void apagarRoupa(Integer id) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = " DELETE FROM tb_roupa WHERE idroupa = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }

    public List<Roupa> buscarTipoTamanhoUnico() {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = " SELECT DISTINCT categoriaroupa, imagesrc, tiporoupa, tamanhoroupa FROM tb_roupa; ";
        List<Roupa> listaRoupas = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.execute();
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Roupa roupa = new Roupa();
                roupa.setTipoRoupa(TipoRoupa.valueOf(rs.getString("tiporoupa")));
                roupa.setTamanhoRoupa(TamanhoRoupa.valueOf(rs.getString("tamanhoroupa")));
                roupa.setCategoriaRoupa(CategoriaRoupa.valueOf(rs.getString("categoriaroupa")));
                roupa.setImageSrc(rs.getString("imagesrc"));
                listaRoupas.add(roupa);
            }
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
        return listaRoupas;
    }

    public List<LinhaRoupa> buscarDadosParaStock() {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = "SELECT r.tipoRoupa, r.tamanhoRoupa, rd.quantidade " +
                "FROM tb_roupa_doacao rd " +
                "INNER JOIN tb_roupa r ON r.roupa_doacao_id = rd.id_roupa_doacao ";

        List<LinhaRoupa> linhaRoupaList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LinhaRoupa lr = new LinhaRoupa();
                lr.setTipoRoupa(resultSet.getString("tiporoupa"));
                lr.setTamanhoRoupa(resultSet.getString("tamanhoroupa"));
                lr.setQuantidade(resultSet.getInt("quantidade"));
                linhaRoupaList.add(lr);
            }
        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        return linhaRoupaList;
    }

    public void inserirRoupaEmPedidos(Roupa roupa) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        /*insert into tb_roupa (idroupa, categoriaroupa, data, imagesrc, nome, stock, tamanhoroupa, tiporoupa, linha_encomenda_id,
                roupa_doacao_id)
        values ();

        todo falta a data
        */

        String sql = "INSERT INTO tb_roupa (categoriaroupa, imagesrc, stock, tamanhoroupa, tiporoupa, roupa_doacao_id) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(roupa.getCategoriaRoupa()));
            preparedStatement.setString(2, roupa.getImageSrc());
            preparedStatement.setInt(3, roupa.getStock());
            preparedStatement.setString(4, String.valueOf(roupa.getTamanhoRoupa()));
            preparedStatement.setString(5, String.valueOf(roupa.getTipoRoupa()));
            preparedStatement.setInt(6, roupa.getRoupa_doacao().getId_roupa_doacao());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println("ERRO: " + sqlException.getMessage());
        }
    }

    public List<Roupa> buscarTamanhoBtnEspecifico(TamanhoRoupa tamanhoRoupa) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = " SELECT DISTINCT categoriaroupa, imagesrc, tiporoupa, tamanhoroupa " +
                "FROM tb_roupa " +
                " WHERE tamanhoroupa = ? ";

        List<Roupa> listaRoupas = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(tamanhoRoupa));

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Roupa roupa = new Roupa();
                roupa.setTipoRoupa(TipoRoupa.valueOf(rs.getString("tiporoupa")));
                roupa.setTamanhoRoupa(TamanhoRoupa.valueOf(rs.getString("tamanhoroupa")));
                roupa.setCategoriaRoupa(CategoriaRoupa.valueOf(rs.getString("categoriaroupa")));
                roupa.setImageSrc(rs.getString("imagesrc"));
                listaRoupas.add(roupa);
            }
        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        return listaRoupas;
    }

    public List<Roupa> buscarCategoriaBtnEspecifico(CategoriaRoupa categoriaRoupa) {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = " SELECT DISTINCT categoriaroupa, imagesrc, tiporoupa, tamanhoroupa " +
                "FROM tb_roupa " +
                " WHERE categoriaroupa = ? ";

        List<Roupa> listaRoupas = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(categoriaRoupa));

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Roupa roupa = new Roupa();
                roupa.setTipoRoupa(TipoRoupa.valueOf(rs.getString("tiporoupa")));
                roupa.setTamanhoRoupa(TamanhoRoupa.valueOf(rs.getString("tamanhoroupa")));
                roupa.setCategoriaRoupa(CategoriaRoupa.valueOf(rs.getString("categoriaroupa")));
                roupa.setImageSrc(rs.getString("imagesrc"));
                listaRoupas.add(roupa);
            }
        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        return listaRoupas;
    }
}
