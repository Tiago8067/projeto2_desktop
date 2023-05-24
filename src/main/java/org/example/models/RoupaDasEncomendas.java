package org.example.models;

import lombok.*;
import org.example.models.enums.CategoriaRoupa;
import org.example.models.enums.TamanhoRoupa;
import org.example.models.enums.TipoRoupa;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_roupa_das_encomendas")
public class RoupaDasEncomendas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRoupaDasEncomendas;
    private Instant dataDeEntrega;
    @Enumerated(EnumType.STRING)
    private CategoriaRoupa categoriaRoupa;
    @Enumerated(EnumType.STRING)
    private TamanhoRoupa tamanhoRoupa;
    @Enumerated(EnumType.STRING)
    private TipoRoupa tipoRoupa;

    @ManyToOne
    @JoinColumn(name = "linha_encomenda_id", referencedColumnName = "idLinhaEncomenda")
    private LinhaEncomenda linha_encomenda;

}
