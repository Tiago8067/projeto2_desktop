package org.example.models;

import lombok.*;
import org.example.models.enums.EstadoUtilizador;
import org.example.models.enums.TipoUtilizador;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_utilizador")
public class Utilizador implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUtilizador;
    private String email;
    private String username;
    private String password;
    private Integer numeroCc;
    private Integer nif;
    private String nome;

    // TODO => Acrescentar data no registo

    private Instant dataNascimento;
    private Integer contacto;
    @Enumerated(EnumType.STRING)
    private TipoUtilizador tipoUtilizador;
    @Enumerated(EnumType.STRING)
    private EstadoUtilizador estadoUtilizador;
    @ManyToOne
    @JoinColumn(name = "localizacao_id", referencedColumnName = "idLocalizacao")
    private Localizacao localizacao;

    @OneToMany(mappedBy = "utilizador")   //Faz ser bidirecional
    private List<Doacao> doacoes;

    @OneToMany(mappedBy = "utilizador")
    private List<Encomenda> encomendas;
}
