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
public class Utilizador implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private Integer numeroCc;
    private Integer nif;
    private String nome;
    private Instant dataNascimento;
    private Integer contacto;

    private List<Doacao> doacoes= new ArrayList<>();

    private List<Localizacao> localizacaos= new ArrayList<>();
}
