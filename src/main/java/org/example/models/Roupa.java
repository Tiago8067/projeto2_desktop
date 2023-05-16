package org.example.models;

import lombok.*;
import org.example.models.enums.CategoriaRoupa;
import org.example.models.enums.TamanhoRoupa;
import org.example.models.enums.TipoRoupa;
import org.hibernate.annotations.DynamicUpdate;

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
//@DynamicUpdate
@Table(name = "tb_roupa")
public class Roupa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(unique = true, updatable = false)
    private Integer idRoupa;
    private String nome;
    private String imageSrc;
    private Integer Stock;
    private Instant data;
    @Enumerated(EnumType.STRING)
    private CategoriaRoupa categoriaRoupa;
    @Enumerated(EnumType.STRING)
    private TamanhoRoupa tamanhoRoupa;
    @Enumerated(EnumType.STRING)
    private TipoRoupa tipoRoupa;

    // todo - testar com a lista de doacao
}
