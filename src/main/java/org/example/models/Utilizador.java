package org.example.models;

import lombok.*;
import org.example.models.enums.TipoUtilizador;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
    private Instant dataNascimento;
    private Integer contacto;

    @Enumerated(EnumType.STRING)
    private TipoUtilizador tipoUtilizador;

    @ManyToOne
    private Localizacao localizacao;

}
