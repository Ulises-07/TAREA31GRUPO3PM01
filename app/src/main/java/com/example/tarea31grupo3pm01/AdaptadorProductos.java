package com.example.tarea31grupo3pm01;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea31grupo3pm01.ModeloProducto;
import java.util.List;

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.VisorProducto> {

    private Context contexto;
    private List<ModeloProducto> listaProductos;
    private OnProductoListener listener;

    public interface OnProductoListener {
        void onEliminarClick(int idProducto);
    }

    public AdaptadorProductos(Context contexto, List<ModeloProducto> listaProductos, OnProductoListener listener) {
        this.contexto = contexto;
        this.listaProductos = listaProductos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VisorProducto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(contexto).inflate(R.layout.item_producto, parent, false);
        return new VisorProducto(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull VisorProducto holder, int position) {
        ModeloProducto producto = listaProductos.get(position);
        holder.tvNombre.setText(producto.getNombreProducto());
        holder.tvPrecio.setText("$" + producto.getPrecioProducto());
        holder.tvDescripcion.setText(producto.getDescripcionProducto());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(contexto, ActividadFormularioProducto.class);
            intent.putExtra("producto_objeto", producto);
            contexto.startActivity(intent);
        });

        holder.btnEliminar.setOnClickListener(v -> {
            listener.onEliminarClick(producto.getIdProducto());
        });
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class VisorProducto extends RecyclerView.ViewHolder {
        TextView tvNombre, tvPrecio, tvDescripcion;
        ImageButton btnEliminar;

        public VisorProducto(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreProducto);
            tvPrecio = itemView.findViewById(R.id.tvPrecioProducto);
            tvDescripcion = itemView.findViewById(R.id.tvDescProducto);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
