package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.Doacao;
import org.example.models.Fornecedor;
import javax.persistence.EntityManager;
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
    public List<Object[]> buscarTodasDoacaoRoupa() {
        //String jpql = "SELECT d.idDoacao, d.utilizador.username, r.tipoRoupa, r.tamanhoRoupa, rd.quantidade FROM Doacao d JOIN Roupa_Doacao rd ON d.idDoacao = rd.id_roupa_doacao, Roupa r JOIN Roupa_Doacao r_d ON r.idRoupa = r_d.id_roupa_doacao";
        String jpql = "SELECT d.idDoacao, d.utilizador.username, r.tipoRoupa, r.tamanhoRoupa, rd.quantidade " +
                "FROM Roupa_Doacao rd " +
                "JOIN rd.doacoes d " +
                "JOIN rd.roupas r ";
        return entityManager.createQuery(jpql, Object[].class).getResultList();
    }
}
