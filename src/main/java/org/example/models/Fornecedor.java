package org.example.models;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
    @JoinColumn(name = "localizacao_id", referencedColumnName = "idLocalizacao")
    private Localizacao localizacao;

    @OneToMany(mappedBy = "fornecedor")
    private List<Encomenda> encomendas;
}
