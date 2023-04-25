package org.example.models;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Doacao implements Serializable {
    private Integer Id;
    private Instant data;
}
