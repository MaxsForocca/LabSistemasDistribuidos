package com.example.sistema_empresa_jdbc.dto;

import java.time.LocalDate;

public class DepartamentoRequestDTO {
    private String nombre;
    private String telefono;
    private String fax;
    private LocalDate fechaCreacion;
    private String estado;

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getFax() { return fax; }
    public void setFax(String fax) { this.fax = fax; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
