package com.example.tarea31grupo3pm01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.tarea31grupo3pm01.ModeloProducto;
import com.example.tarea31grupo3pm01.ClienteRetrofit;
import com.example.tarea31grupo3pm01.InterfazApi;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActividadListaProductos extends AppCompatActivity implements AdaptadorProductos.OnProductoListener {

    private RecyclerView recyclerProductos;
    private FloatingActionButton fabAgregar;
    private InterfazApi api;
    private String tokenAlmacenado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_lista_productos);

        recyclerProductos = findViewById(R.id.recyclerProductos);
        fabAgregar = findViewById(R.id.fabAgregar);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(this));

        api = ClienteRetrofit.obtenerCliente().create(InterfazApi.class);

        SharedPreferences preferencias = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        tokenAlmacenado = preferencias.getString("TOKEN_ACCESO", "");

        if (tokenAlmacenado.isEmpty()) {
            finish();
        }

        fabAgregar.setOnClickListener(v -> {
            startActivity(new Intent(this, ActividadFormularioProducto.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarProductos();
    }

    private void cargarProductos() {
        Call<List<ModeloProducto>> llamada = api.obtenerProductos(tokenAlmacenado);
        llamada.enqueue(new Callback<List<ModeloProducto>>() {
            @Override
            public void onResponse(Call<List<ModeloProducto>> call, Response<List<ModeloProducto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AdaptadorProductos adaptador = new AdaptadorProductos(ActividadListaProductos.this, response.body(), ActividadListaProductos.this);
                    recyclerProductos.setAdapter(adaptador);
                } else {
                    if(response.code() == 401) {
                        Toast.makeText(ActividadListaProductos.this, "Sesión expirada", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ModeloProducto>> call, Throwable t) {
                Toast.makeText(ActividadListaProductos.this, "Error al cargar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onEliminarClick(int idProducto) {
        Call<Void> llamada = api.eliminarProducto(tokenAlmacenado, idProducto);
        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ActividadListaProductos.this, "Eliminado", Toast.LENGTH_SHORT).show();
                    cargarProductos();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ActividadListaProductos.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
