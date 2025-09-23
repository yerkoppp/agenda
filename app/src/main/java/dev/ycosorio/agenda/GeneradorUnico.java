package dev.ycosorio.agenda;

import java.util.UUID;

public class GeneradorUnico {

    private UUID identificador;

    // Convertir el UUID a String para mostrarlo
    private String identificadorString = identificador.toString();

    public GeneradorUnico(){
        this.identificador = UUID.randomUUID();
    }

    public String getIdentificadorString() {
        return identificadorString;
    }

}



