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
public class Encomenda implements Serializable {
    private Integer id;
    private Instant data;


}
