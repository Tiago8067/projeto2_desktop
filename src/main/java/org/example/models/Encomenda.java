package org.example.models;

import lombok.*;
import org.example.models.enums.EstadoEncomenda;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_encomenda")
public class Encomenda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEncomenda;
    private LocalDate dataDePedido;
    @Enumerated(EnumType.STRING)
    private EstadoEncomenda estadoEncomenda;
    @ManyToOne
    @JoinColumn(name = "fornecedor_id", referencedColumnName = "idFornecedor")
    private Fornecedor fornecedor;
    @ManyToOne
    @JoinColumn(name = "utilizador_id", referencedColumnName = "idUtilizador")
    private Utilizador utilizador;

    @ManyToOne
    @JoinColumn(name = "linha_encomenda_id", referencedColumnName = "idLinhaEncomenda")
    private LinhaEncomenda linha_encomenda;
}
