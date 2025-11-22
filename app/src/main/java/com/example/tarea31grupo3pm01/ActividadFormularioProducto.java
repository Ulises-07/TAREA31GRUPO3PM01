package com.example.tarea31grupo3pm01;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tarea31grupo3pm01.ModeloProducto;
import com.example.tarea31grupo3pm01.ClienteRetrofit;
import com.example.tarea31grupo3pm01.InterfazApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActividadFormularioProducto extends AppCompatActivity {

    private EditText etNombre, etDescripcion, etPrecio;
    private Button btnGuardar;
    private TextView tvTitulo;
    private InterfazApi api;
    private String tokenAlmacenado;
    private ModeloProducto productoEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_formulario_producto);

        etNombre = findViewById(R.id.etNombreForm);
        etDescripcion = findViewById(R.id.etDescForm);
        etPrecio = findViewById(R.id.etPrecioForm);
        btnGuardar = findViewById(R.id.btnGuardar);
        tvTitulo = findViewById(R.id.tvTituloForm);

        api = ClienteRetrofit.obtenerCliente().create(InterfazApi.class);
        SharedPreferences preferencias = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        tokenAlmacenado = preferencias.getString("TOKEN_ACCESO", "");

        if (getIntent().hasExtra("producto_objeto")) {
            productoEditar = (ModeloProducto) getIntent().getSerializableExtra("producto_objeto");
            tvTitulo.setText("Editar Producto");
            etNombre.setText(productoEditar.getNombreProducto());
            etDescripcion.setText(productoEditar.getDescripcionProducto());
            etPrecio.setText(String.valueOf(productoEditar.getPrecioProducto()));
            btnGuardar.setText("Actualizar");
        }

        btnGuardar.setOnClickListener(v -> guardarDatos());
    }

    private void guardarDatos() {
        String nombre = etNombre.getText().toString();
        String desc = etDescripcion.getText().toString();
        String precioStr = etPrecio.getText().toString();

        if (nombre.isEmpty() || precioStr.isEmpty()) {
            Toast.makeText(this, "Complete campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        double precio = Double.parseDouble(precioStr);
        ModeloProducto nuevoProducto = new ModeloProducto(nombre, desc, precio);


        String tokenParaHeader = "Bearer " + tokenAlmacenado;

        if (productoEditar == null) {
            Call<Void> llamada = api.crearProducto(tokenParaHeader, nuevoProducto);
            llamada.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ActividadFormularioProducto.this, "Creado con éxito", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ActividadFormularioProducto.this, "Error de autorización (Crear)", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ActividadFormularioProducto.this, "Error de red al crear: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Call<Void> llamada = api.actualizarProducto(tokenParaHeader, productoEditar.getIdProducto(), nuevoProducto);
            llamada.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ActividadFormularioProducto.this, "Actualizado con éxito", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ActividadFormularioProducto.this, "Error de autorización (Actualizar)", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ActividadFormularioProducto.this, "Error de red al actualizar: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
