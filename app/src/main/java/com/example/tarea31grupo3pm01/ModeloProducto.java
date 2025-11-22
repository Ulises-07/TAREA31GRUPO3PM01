package com.example.tarea31grupo3pm01;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ModeloProducto implements Serializable {

    @SerializedName("id_producto")
    private int idProducto;

    @SerializedName("nombre_producto")
    private String nombreProducto;

    @SerializedName("descripcion_producto")
    private String descripcionProducto;

    @SerializedName("precio_producto")
    private double precioProducto;

    public ModeloProducto() {}

    public ModeloProducto(String nombreProducto, String descripcionProducto, double precioProducto) {
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.precioProducto = precioProducto;
    }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String getDescripcionProducto() { return descripcionProducto; }
    public void setDescripcionProducto(String descripcionProducto) { this.descripcionProducto = descripcionProducto; }

    public double getPrecioProducto() { return precioProducto; }
    public void setPrecioProducto(double precioProducto) { this.precioProducto = precioProducto; }
}
