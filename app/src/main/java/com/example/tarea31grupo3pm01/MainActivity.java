package com.example.tarea31grupo3pm01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tarea31grupo3pm01.RespuestaLogin;
import com.example.tarea31grupo3pm01.SolicitudLogin;
import com.example.tarea31grupo3pm01.ClienteRetrofit;
import com.example.tarea31grupo3pm01.InterfazApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etUsuario, etContrasena;
    private Button btnIngresar;
    private InterfazApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        btnIngresar = findViewById(R.id.btnIngresar);

        api = ClienteRetrofit.obtenerCliente().create(InterfazApi.class);

        btnIngresar.setOnClickListener(v -> {
            String usuario = etUsuario.getText().toString();
            String pass = etContrasena.getText().toString();

            if (!usuario.isEmpty() && !pass.isEmpty()) {
                realizarLogin(usuario, pass);
            } else {
                Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void realizarLogin(String usuario, String contrasena) {
        SolicitudLogin solicitud = new SolicitudLogin(usuario, contrasena);
        Call<RespuestaLogin> llamada = api.iniciarSesion(solicitud);

        llamada.enqueue(new Callback<RespuestaLogin>() {
            @Override
            public void onResponse(Call<RespuestaLogin> call, Response<RespuestaLogin> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getTokenAcceso();

                    SharedPreferences preferencias = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
                    preferencias.edit().putString("TOKEN_ACCESO", "Bearer " + token).apply();

                    Toast.makeText(MainActivity.this, "Acceso Concedido", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(MainActivity.this, ActividadListaProductos.class));
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaLogin> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}