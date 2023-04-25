package org.example.models;

import lombok.*;

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
@Table(name = "tb_encomenda")
public class Encomenda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEncomenda;
    private Instant data;

    @ManyToOne
    private Fornecedor fornecedor;

    @OneToMany(mappedBy = "encomenda")
    private List<LinhaEncomenda> linhas = new ArrayList<>();
}
