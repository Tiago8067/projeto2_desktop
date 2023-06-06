package org.example.modelsHelp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LinhaRoupaDiferenca {
    private String tipoRoupa;
    private String tamanhoRoupa;
    private Integer quantidade;
    private Integer stock;
}
