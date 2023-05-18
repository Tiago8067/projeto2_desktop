package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.Roupa;
import org.example.models.enums.TamanhoRoupa;
import org.example.models.enums.TipoRoupa;
import org.example.util.ConnectionUtil;
import org.example.util.GoToUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
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
//            this.entityManager.refresh(roupa);
//            this.entityManager.merge(roupa);
            this.entityManager.merge(entityManager.getReference(Roupa.class, roupa.getIdRoupa()));
            this.entityManager.getTransaction().commit();

//            Session session = entityManager.unwrap(org.hibernate.Session.class);
//            SessionFactory sessionFactory = session.getSessionFactory();
//            Session newSession = sessionFactory.openSession();
//            Transaction transaction = newSession.beginTransaction();
//            newSession.saveOrUpdate(roupa);
//            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        }
    }

    public void atualizarStock(Roupa roupa, Integer stock) {
        // Parametros Integer id, Integer stock ª=> TipoRoupa tipoRoupa, TamanhoRoupa tamanhoRoupa
        //Roupa roupa = entityManager.find(Roupa.class, id);
        //roupa.setStock(roupa.getStock() + quantidade);
        //entityManager.merge(roupa);
        //entityManager.persist(roupa);
        //Session session = Hibernate
        //Roupa roupa = session.get()
        //entityManager.getTransaction().begin();
        //entityManager.createNativeQuery("UPDATE Roupa r SET r.Stock = :stock WHERE r.idRoupa = :id")
                //.setParameter("id", id)
                //.executeUpdate();
        //String jpql = "UPDATE Roupa r SET r.Stock = :stock WHERE r.idRoupa = :id";
        //entityManager.getTransaction().commit();

        //String jpql = "UPDATE Roupa r SET r.Stock = :stock WHERE r.idRoupa = :id";
        //entityManager.createQuery(jpql, Roupa.class).setParameter("id", id).executeUpdate();
        //entityManager.createQuery("UPDATE Roupa r SET r.Stock = :stock WHERE r.idRoupa = :id").setParameter("id", id); //.executeUpdate();


        /*
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        SessionFactory sessionFactory = session.getSessionFactory();
        Session newSession = sessionFactory.openSession();
        Transaction transaction = newSession.beginTransaction();
        Query query = newSession.createQuery("UPDATE Roupa r SET r.Stock = :stock WHERE r.idRoupa = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        transaction.commit();

         */

        // todo - realizar com prepared statment
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection conn = connectionUtil.criarConexao();

        String sql = "UPDATE Roupa SET Stock = ? WHERE tipoRoupa = ? AND tamanhoRoupa = ?"; //tipoRoupa = ? AND tamanhoRoupa = ?   idRoupa = ?

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, stock);
            statement.setString(2, String.valueOf(roupa.getTipoRoupa()));
            statement.setString(3, String.valueOf(roupa.getTamanhoRoupa()));
            //statement.setString(2, String.valueOf(tipoRoupa));
            //statement.setString(3, String.valueOf(tamanhoRoupa));

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

//    public Integer buscarPorTipoTamanhoRoupa(Roupa roupa) { //TipoRoupa tipoRoupa, TamanhoRoupa tamanhoRoupa
//        String jpql = "SELECT r.stock FROM Roupa r WHERE r.tipoRoupa=:tipoRoupa AND r.tamanhoRoupa=:tamanhoRoupa ";
//        return  entityManager.createQuery(jpql, Integer.class)
//                .setParameter("tipoRoupa", roupa.getTipoRoupa())
//                .setParameter("tamanhoRoupa", roupa.getTamanhoRoupa())
//                .getSingleResult();
//    }

    public Roupa buscarPorTipoTamanhoRoupa(Roupa roupa) { //TipoRoupa tipoRoupa, TamanhoRoupa tamanhoRoupa
        String jpql = "SELECT r.stock FROM Roupa r WHERE r.tipoRoupa=:tipoRoupa AND r.tamanhoRoupa=:tamanhoRoupa ";
        return  entityManager.createQuery(jpql, Roupa.class)
                .setParameter("tipoRoupa", roupa.getTipoRoupa())
                .setParameter("tamanhoRoupa", roupa.getTamanhoRoupa())
                .getSingleResult();
    }
    public Roupa buscarPorId(Integer id){
        return entityManager.find(Roupa.class, id);
    }

}
