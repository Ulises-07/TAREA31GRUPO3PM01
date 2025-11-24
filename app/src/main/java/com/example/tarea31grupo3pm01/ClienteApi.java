package com.example.tarea31grupo3pm01;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class ClienteApi {
    private static final String BASE_URL = "http://192.168.1.35/mi_api/";
    private static final String PREFS = "mis_prefs";
    private SharedPreferences prefs;
    private Context contexto;

    public ClienteApi(Context contexto) {
        this.contexto = contexto;
        this.prefs = contexto.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public void guardarToken(String token) {
        prefs.edit().putString("access_token", token).apply();
    }

    public String obtenerToken() {
        return prefs.getString("access_token", null);
    }

    private String leerRespuesta(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String linea;
        while ((linea = br.readLine()) != null) {
            sb.append(linea);
        }
        return sb.toString();
    }

    public JSONObject post(String ruta, JSONObject body, boolean conAuth) throws Exception {
        URL url = new URL(BASE_URL + ruta);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        conn.setRequestProperty("Content-Type", "application/json");
        if (conAuth) {
            String token = obtenerToken();
            if (token != null) conn.setRequestProperty("Authorization", "Bearer " + token);
        }
        conn.setDoOutput(true);
        if (body != null) {
            OutputStream os = conn.getOutputStream();
            os.write(body.toString().getBytes("UTF-8"));
            os.close();
        }
        int code = conn.getResponseCode();
        InputStream is = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream();
        String resp = leerRespuesta(is);
        return new JSONObject(resp);
    }


    public JSONObject get(String ruta, boolean conAuth) throws Exception {
        URL url = new URL(BASE_URL + ruta);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        if (conAuth) {
            String token = obtenerToken();
            if (token != null) conn.setRequestProperty("Authorization", "Bearer " + token);
        }
        int code = conn.getResponseCode();
        InputStream is = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream();
        String resp = leerRespuesta(is);
        return new JSONObject(resp);
    }
}
