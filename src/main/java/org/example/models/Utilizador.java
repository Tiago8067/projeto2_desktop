package org.example.models;

import lombok.*;
import org.example.models.enums.EstadoUtilizador;
import org.example.models.enums.TipoUtilizador;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
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

    // TODO => Acrescentar data no registro
    private Instant dataNascimento;
    private Integer contacto;

    @Enumerated(EnumType.STRING)
    private TipoUtilizador tipoUtilizador;

    @Enumerated(EnumType.STRING)
    private EstadoUtilizador estadoUtilizador;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Localizacao localizacao;

}
