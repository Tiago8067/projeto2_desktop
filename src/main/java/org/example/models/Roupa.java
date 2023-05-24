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
@Table(name = "tb_roupa")
public class Roupa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(unique = true, updatable = false)
    private Integer idRoupa;
    private String imageSrc;
    private Integer stock;
    @Enumerated(EnumType.STRING)
    private CategoriaRoupa categoriaRoupa;
    @Enumerated(EnumType.STRING)
    private TamanhoRoupa tamanhoRoupa;
    @Enumerated(EnumType.STRING)
    private TipoRoupa tipoRoupa;

    @ManyToOne
    @JoinColumn(name = "roupa_doacao_id", referencedColumnName = "id_roupa_doacao")
    private Roupa_Doacao roupa_doacao;
}
