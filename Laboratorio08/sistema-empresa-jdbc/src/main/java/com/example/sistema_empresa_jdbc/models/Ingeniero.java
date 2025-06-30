package com.example.sistema_empresa_jdbc.models;

import java.lang.annotation.Inherited;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Ingenieros")
public class Ingeniero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer IDIng;

    private String Nombre;
    private String Apellido;
    private String Especialidad;
    private String Cargo;

    private BigDecimal Salario;

    @Column(name = "Fecha_Ingreso")
    private LocalDate fechaIngreso;

    private String Email;

    // FK hacia Departamentos
    @ManyToOne
    @JoinColumn(name = "IDDpto")
    @JsonBackReference(value = "ingeniero-dpto")
    private Departamento departamento;

    @ManyToMany(mappedBy = "ingenieros")
    @JsonIgnore
    private List<Proyecto> proyectos;

    // Getters y setters
    public Integer getIDIng() {
        return IDIng;
    }

    public void setIDIng(Integer IDIng) {
        this.IDIng = IDIng;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getEspecialidad() {
        return Especialidad;
    }

    public void setEspecialidad(String especialidad) {
        Especialidad = especialidad;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String cargo) {
        Cargo = cargo;
    }

    public BigDecimal getSalario() {
        return Salario;
    }

    public void setSalario(BigDecimal salario) {
        Salario = salario;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
    
}
