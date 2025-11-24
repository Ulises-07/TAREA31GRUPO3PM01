package com.example.tarea31grupo3pm01;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {
    private RecyclerView rv;
    private Button btnNuevo, btnRefrescar;
    private ClienteApi clienteApi;
    private AdaptadorProductos adaptador;
    private List<ModeloProducto> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        clienteApi = new ClienteApi(this);

        rv = findViewById(R.id.recyclerProductos);
        btnNuevo = findViewById(R.id.btnNuevo);
        btnRefrescar = findViewById(R.id.btnRefrescar);

        rv.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new AdaptadorProductos(this, lista);
        rv.setAdapter(adaptador);

        btnNuevo.setOnClickListener(v -> {
            startActivity(new Intent(ListaActivity.this, CrearActivity.class));
            finish();
        });

        btnRefrescar.setOnClickListener(v -> cargarProductos());

        cargarProductos();
    }

    private void cargarProductos() {
        new AsyncTask<Void, Void, JSONObject>(){
            Exception error;
            @Override
            protected JSONObject doInBackground(Void... voids) {
                try {
                    return clienteApi.get("api/leer_productos.php", true);
                } catch (Exception e) { error = e; return null; }
            }
            @Override
            protected void onPostExecute(JSONObject res) {
                if (error != null) { Toast.makeText(ListaActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show(); return; }
                try {
                    lista.clear();
                    JSONArray arr = res.getJSONArray("productos");
                    for (int i=0;i<arr.length();i++){
                        JSONObject o = arr.getJSONObject(i);
                        ModeloProducto p = new ModeloProducto(o.getInt("id"), o.getString("nombre"), o.optString("descripcion",""), o.optDouble("precio",0.0));
                        lista.add(p);
                    }
                    adaptador.notifyDataSetChanged();
                } catch (Exception e) { Toast.makeText(ListaActivity.this, "Parse error", Toast.LENGTH_SHORT).show(); }
            }
        }.execute();
    }
}
