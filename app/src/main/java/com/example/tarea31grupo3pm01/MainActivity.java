package com.example.tarea31grupo3pm01;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnEntrar;
    private ClienteApi clienteApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clienteApi = new ClienteApi(this);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa correo y contraseña", Toast.LENGTH_SHORT).show();
                return;
            }
            new LoginTask(email, password).execute();
        });
    }

    private class LoginTask extends AsyncTask<Void, Void, JSONObject> {
        private String email, password;
        private Exception error;

        LoginTask(String email, String password) {
            this.email = email; this.password = password;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                JSONObject body = new JSONObject();
                body.put("grant_type", "password");
                body.put("username", email);
                body.put("password", password);
                return clienteApi.post("oauth/token.php", body, false);
            } catch (Exception e) {
                error = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject res) {
            if (error != null) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
            if (res == null) {
                Toast.makeText(MainActivity.this, "Respuesta vacía", Toast.LENGTH_SHORT).show();
                return;
            }
            if (res.has("access_token")) {
                try {
                    String token = res.getString("access_token");
                    clienteApi.guardarToken(token);
                    Toast.makeText(MainActivity.this, "Login OK", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, ListaActivity.class));
                    finish();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error al procesar token", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, res.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}