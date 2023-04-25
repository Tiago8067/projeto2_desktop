package org.example.models;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Roupa implements Serializable {
    private Integer id;
    private String nome;
}
