package org.example.models;

import lombok.*;
import org.example.models.enums.CategoriaRoupa;
import org.example.models.enums.TamanhoRoupa;
import org.example.models.enums.TipoRoupa;
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
@Table(name = "tb_roupa")
public class Roupa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
