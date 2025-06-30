package com.example.sistema_empresa_jdbc.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProyectoRequestDTO {
    private String nombre;
    private String descripcion;
    private LocalDate fecInicio;
    private LocalDate fecTermino;
    private BigDecimal presupuesto;
    private String estado; // "PLANIFICADO", "EN_CURSO", etc.
    private Integer idDepartamento;
    private List<Integer> idIngenieros;

    // Getters y setters
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

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public List<Integer> getIdIngenieros() {
        return idIngenieros;
    }

    public void setIdIngenieros(List<Integer> idIngenieros) {
        this.idIngenieros = idIngenieros;
    }
}
