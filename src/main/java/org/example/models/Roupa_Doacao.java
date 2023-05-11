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
@Table(name = "tb_roupa_doacao")
public class Roupa_Doacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_roupa_doacao;

    @ManyToOne
    private Doacao doacao;

    @ManyToOne
    private Roupa roupa;

    private Integer quantidade;

}
