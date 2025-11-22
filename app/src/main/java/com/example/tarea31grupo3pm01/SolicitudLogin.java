package com.example.tarea31grupo3pm01;

import com.google.gson.annotations.SerializedName;

public class SolicitudLogin {
    @SerializedName("nombre_usuario")
    private String nombreUsuario;

    @SerializedName("contrasena")
    private String contrasena;

    public SolicitudLogin(String nombreUsuario, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }
}
