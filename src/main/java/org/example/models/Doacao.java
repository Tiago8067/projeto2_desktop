package org.example.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_doacao")
public class Doacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDoacao;
    private Instant data;
    @ManyToOne
    @JoinColumn(name = "utilizador_id", referencedColumnName = "idUtilizador")
    private Utilizador utilizador;

    @ManyToOne
    @JoinColumn(name = "roupa_doacao_id", referencedColumnName = "id_roupa_doacao")
    private Roupa_Doacao roupa_doacao;

//    private IntegerProperty id;
//
//    public IntegerProperty idProperty() {
//        if (id == null) id = new SimpleIntegerProperty(this, "id");
//        return id;
//    }
}
