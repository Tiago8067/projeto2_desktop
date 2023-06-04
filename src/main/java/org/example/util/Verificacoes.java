package org.example.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Verificacoes {
    public Integer verficaInteiro(int verificaNumero, String verifica) {
        try {
            verificaNumero = Integer.parseInt(verifica);
            return verificaNumero;
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
            return 0;
        }
    }
}
