package com.example.tarea31grupo3pm01;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class EditarActivity extends AppCompatActivity {
    private EditText edtNombre, edtDescripcion, edtPrecio;
    private Button btnActualizar, btnEliminar;
    private ClienteApi clienteApi;
    private int idProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        clienteApi = new ClienteApi(this);

        edtNombre = findViewById(R.id.edtNombreE);
        edtDescripcion = findViewById(R.id.edtDescripcionE);
        edtPrecio = findViewById(R.id.edtPrecioE);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        idProducto = getIntent().getIntExtra("id", 0);
        edtNombre.setText(getIntent().getStringExtra("nombre"));
        edtDescripcion.setText(getIntent().getStringExtra("descripcion"));
        edtPrecio.setText(String.valueOf(getIntent().getDoubleExtra("precio", 0.0)));

        btnActualizar.setOnClickListener(v -> {
            new ActualizarTask().execute();
        });

        btnEliminar.setOnClickListener(v -> {
            new EliminarTask().execute();
        });
    }

    private class ActualizarTask extends AsyncTask<Void, Void, JSONObject> {
        Exception error;
        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                JSONObject body = new JSONObject();
                body.put("id", idProducto);
                body.put("nombre", edtNombre.getText().toString());
                body.put("descripcion", edtDescripcion.getText().toString());
                body.put("precio", Double.parseDouble(edtPrecio.getText().toString()));
                return clienteApi.post("api/actualizar_producto.php", body, true);
            } catch (Exception e) { error = e; return null; }
        }
        @Override
        protected void onPostExecute(JSONObject res) {
            if (error != null) { Toast.makeText(EditarActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show(); return; }
            Toast.makeText(EditarActivity.this, "Actualizado", Toast.LENGTH_SHORT).show();
            startActivity(new android.content.Intent(EditarActivity.this, ListaActivity.class));
            finish();
        }
    }

    private class EliminarTask extends AsyncTask<Void, Void, JSONObject> {
        Exception error;
        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                JSONObject body = new JSONObject();
                body.put("id", idProducto);
                return clienteApi.post("api/eliminar_producto.php", body, true);
            } catch (Exception e) { error = e; return null; }
        }
        @Override
        protected void onPostExecute(JSONObject res) {
            if (error != null) { Toast.makeText(EditarActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show(); return; }
            Toast.makeText(EditarActivity.this, "Eliminado", Toast.LENGTH_SHORT).show();
            startActivity(new android.content.Intent(EditarActivity.this, ListaActivity.class));
            finish();
        }
    }
}