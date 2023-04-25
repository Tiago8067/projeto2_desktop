package org.example.models;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Localizacao implements Serializable {
    private Integer id;
    private String codigoPostal;
    private String localidade;
    private String rua;
    private Integer numeroPorta;
    private String cidade;
    private String distrito;
}
