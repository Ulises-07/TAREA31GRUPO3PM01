package com.example.tarea31grupo3pm01;

public class ModeloProducto {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;

    public ModeloProducto(int id, String nombre, String descripcion, double precio) {
        this.id = id; this.nombre = nombre; this.descripcion = descripcion; this.precio = precio;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
}