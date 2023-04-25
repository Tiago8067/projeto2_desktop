package org.example.models;

import lombok.*;

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
public class Fornecedor implements Serializable {
    private Integer id;
    private String nome;
    private Integer contacto;

    private List<Localizacao> localizacaos = new ArrayList<>();

}
