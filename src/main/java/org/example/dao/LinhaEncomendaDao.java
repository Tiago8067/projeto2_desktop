package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.models.LinhaEncomenda;

import javax.persistence.EntityManager;

@NoArgsConstructor
@AllArgsConstructor
public class LinhaEncomendaDao {
    private EntityManager entityManager;

    public void registar(LinhaEncomenda linhaEncomenda) {
        this.entityManager.persist(linhaEncomenda);
    }
}
