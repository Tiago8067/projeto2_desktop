package org.example.models;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString
@Entity
@Table(name = "tb_roupa_doacao")
public class Roupa_Doacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_roupa_doacao;
    private Integer quantidade;

    @OneToMany(mappedBy = "roupa_doacao")
    private List<Doacao> doacoes;

    @OneToMany(mappedBy = "roupa_doacao")
    private List<Roupa> roupas;
}
