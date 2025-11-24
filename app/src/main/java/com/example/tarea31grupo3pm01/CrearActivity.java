package com.example.tarea31grupo3pm01;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class CrearActivity extends AppCompatActivity {
    private EditText edtNombre, edtDescripcion, edtPrecio;
    private Button btnGuardar;
    private ClienteApi clienteApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);
        clienteApi = new ClienteApi(this);

        edtNombre = findViewById(R.id.edtNombre);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        edtPrecio = findViewById(R.id.edtPrecio);
        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(v -> {
            String nombre = edtNombre.getText().toString().trim();
            String descripcion = edtDescripcion.getText().toString().trim();
            String precio = edtPrecio.getText().toString().trim();
            if (nombre.isEmpty()) { Toast.makeText(this, "Nombre requerido", Toast.LENGTH_SHORT).show(); return; }
            new GuardarTask(nombre, descripcion, precio).execute();
        });
    }

    private class GuardarTask extends AsyncTask<Void, Void, JSONObject> {
        String nombre, descripcion, precio;
        Exception error;
        GuardarTask(String nombre, String descripcion, String precio) { this.nombre=nombre; this.descripcion=descripcion; this.precio=precio; }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                JSONObject body = new JSONObject();
                body.put("nombre", nombre);
                body.put("descripcion", descripcion);
                body.put("precio", Double.parseDouble(precio.isEmpty() ? "0" : precio));
                return clienteApi.post("api/crear_producto.php", body, true);
            } catch (Exception e) { error = e; return null; }
        }

        @Override
        protected void onPostExecute(JSONObject res) {
            if (error != null) { Toast.makeText(CrearActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show(); return; }
            Toast.makeText(CrearActivity.this, "Creado", Toast.LENGTH_SHORT).show();
            startActivity(new android.content.Intent(CrearActivity.this, ListaActivity.class));
            finish();
        }
    }
}
