package com.example.tarea31grupo3pm01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.ViewHolder> {
    private List<ModeloProducto> lista;
    private Context contexto;

    public AdaptadorProductos(Context contexto, List<ModeloProducto> lista) {
        this.contexto = contexto; this.lista = lista;
    }

    @NonNull
    @Override
    public AdaptadorProductos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(contexto).inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorProductos.ViewHolder holder, int position) {
        ModeloProducto p = lista.get(position);
        holder.tvNombre.setText(p.getNombre());
        holder.tvDescripcion.setText(p.getDescripcion());
        holder.tvPrecio.setText(String.valueOf(p.getPrecio()));
        holder.itemView.setOnClickListener(v -> {
            // Abrir editar
            Intent it = new Intent(contexto, EditarActivity.class);
            it.putExtra("id", p.getId());
            it.putExtra("nombre", p.getNombre());
            it.putExtra("descripcion", p.getDescripcion());
            it.putExtra("precio", p.getPrecio());
            contexto.startActivity(it);
            if (contexto instanceof Activity) ((Activity) contexto).finish();
        });
    }

    @Override
    public int getItemCount() { return lista.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvDescripcion, tvPrecio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
        }
    }
}
