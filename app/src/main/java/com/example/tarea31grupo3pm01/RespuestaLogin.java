package com.example.tarea31grupo3pm01;

import com.google.gson.annotations.SerializedName;

public class RespuestaLogin {
    @SerializedName("mensaje")
    private String mensaje;

    @SerializedName("token_acceso")
    private String tokenAcceso;

    @SerializedName("tipo_token")
    private String tipoToken;

    public String getTokenAcceso() {
        return tokenAcceso;
    }

    public String getMensaje() {
        return mensaje;
    }
}
