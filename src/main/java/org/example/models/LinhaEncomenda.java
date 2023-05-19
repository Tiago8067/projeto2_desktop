package org.example.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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

    @OneToMany(mappedBy = "linha_encomenda")
    private List<Encomenda> encomendas;

    @OneToMany(mappedBy = "linha_encomenda")
    private List<Roupa> roupas;
}
