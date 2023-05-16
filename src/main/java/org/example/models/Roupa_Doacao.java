package org.example.models;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_roupa_doacao")
public class Roupa_Doacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_roupa_doacao;
    @ManyToOne//(cascade = CascadeType.ALL)
    private Doacao doacao;
    @ManyToOne
    private Roupa roupa;
    private Integer quantidade;

    //todo - aqui e que teria de ter as listas
}
