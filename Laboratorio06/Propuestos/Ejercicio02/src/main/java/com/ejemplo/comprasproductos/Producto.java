package com.ejemplo.comprasproductos;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Producto {
    private String nombre;
    private double precio;
    private int cantidad;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
