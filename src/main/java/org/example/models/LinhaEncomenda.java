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
@Table(name = "tb_linha_encomenda")
public class LinhaEncomenda implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLinhaEncomenda;
    private Integer quantidade;
    @ManyToOne
    private Roupa roupa;
    @ManyToOne
    private Encomenda encomenda;
}
