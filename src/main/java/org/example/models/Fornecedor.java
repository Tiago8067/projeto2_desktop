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
@Table(name = "tb_fornecedor")
public class Fornecedor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFornecedor;
    private String nome;
    private Integer contacto;
    @ManyToOne
    private Localizacao localizacao;

    //todo tambem teria de uma lista
    // em todas as relacoes com uma * 1 no 1 teria nde ter uma lista do *
}
