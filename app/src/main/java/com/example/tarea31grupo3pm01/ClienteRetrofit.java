package com.example.tarea31grupo3pm01;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteRetrofit {
    private static final String URL_BASE = "http://192.168.1.43/crud_oauth_db/";
    private static Retrofit retrofit = null;

    public static Retrofit obtenerCliente() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
