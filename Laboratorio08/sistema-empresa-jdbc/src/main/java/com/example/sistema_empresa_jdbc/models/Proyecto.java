package com.example.sistema_empresa_jdbc.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer IDProy;

    private String Nombre;
    private String Descripcion;

    @Column(name = "Fec_Inicio")
    private LocalDate fecInicio;

    @Column(name = "Fec_Termino")
    private LocalDate fecTermino;

    private BigDecimal Presupuesto;

    @Enumerated(EnumType.STRING)
    private EstadoProyecto Estado;

    public enum EstadoProyecto {
        PLANIFICADO, EN_CURSO, TERMINADO, CANCELADO
    }

    // FK hacia Departamentos
    @ManyToOne
    @JoinColumn(name = "IDDpto")
    @JsonBackReference
    private Departamento departamento;

    // Relación N:M
    @ManyToMany
    @JoinTable(
        name = "Proyecto_Ingeniero",
        joinColumns = @JoinColumn(name = "IDProy"),
        inverseJoinColumns = @JoinColumn(name = "IDIng")
    )
    @JsonBackReference
    private List<Ingeniero> ingenieros;

    // Getters y setters
    public Integer getIDProy() {
        return IDProy;
    }

    public void setIDProy(Integer IDProy) {
        this.IDProy = IDProy;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
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
        return Presupuesto;
    }

    public void setPresupuesto(BigDecimal presupuesto) {
        this.Presupuesto = presupuesto;
    }

    public EstadoProyecto getEstado() {
        return Estado;
    }

    public void setEstado(EstadoProyecto estado) {
        this.Estado = estado;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public List<Ingeniero> getIngenieros() {
        return ingenieros;
    }

    public void setIngenieros(List<Ingeniero> ingenieros) {
        this.ingenieros = ingenieros;
    }

}
