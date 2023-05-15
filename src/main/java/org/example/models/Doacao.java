package org.example.models;

import lombok.*;
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
@Table(name = "tb_doacao")
public class Doacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDoacao;
    private Instant data;
    @ManyToOne
    private Utilizador utilizador;
}
