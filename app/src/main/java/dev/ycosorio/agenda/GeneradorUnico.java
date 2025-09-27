package dev.ycosorio.agenda;

import java.util.UUID;

public class GeneradorUnico {

    private UUID identificador;
    private String identificadorString;

    public GeneradorUnico(){
        this.identificador = UUID.randomUUID();
        this.identificadorString = identificador.toString();
    }

    public String getIdentificadorString() {
        return identificadorString;
    }

    public UUID getIdentificador() {
        return identificador;
    }
}