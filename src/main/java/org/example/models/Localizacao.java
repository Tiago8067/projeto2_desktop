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
@Table(name = "tb_localizacao")
public class Localizacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLocalizacao;
    private String codigoPostal;
    private String localidade;
    private String rua;
    private Integer numeroPorta;
    private String cidade;
}
