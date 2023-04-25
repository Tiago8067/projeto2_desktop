package org.example.models;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LinhaEncomenda implements Serializable {
    private Integer quantidade;
}
