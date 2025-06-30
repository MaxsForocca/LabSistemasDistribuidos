package com.example.sistema_empresa_jdbc.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProyectoDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private LocalDate fecInicio;
    private LocalDate fecTermino;
    private BigDecimal presupuesto;
    private String estado;

    private String departamentoNombre;
    private List<IngenieroSimpleDTO> ingenieros;

    // Getters y setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecInicio() {
        return fecInicio;
    }

    public void setFecInicio(LocalDate fecInicio) {
        this.fecInicio = fecInicio;
    }

    public LocalDate getFecTermino() {
        return fecTermino;
    }

    public void setFecTermino(LocalDate fecTermino) {
        this.fecTermino = fecTermino;
    }

    public BigDecimal getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(BigDecimal presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDepartamentoNombre() {
        return departamentoNombre;
    }

    public void setDepartamentoNombre(String departamentoNombre) {
        this.departamentoNombre = departamentoNombre;
    }

    public List<IngenieroSimpleDTO> getIngenieros() {
        return ingenieros;
    }

    public void setIngenieros(List<IngenieroSimpleDTO> ingenieros) {
        this.ingenieros = ingenieros;
    }
}
