package com.example.sistema_empresa_jdbc.models;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import com.example.sistema_empresa_jdbc.models.Ingeniero;
import com.example.sistema_empresa_jdbc.models.Proyecto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Departamentos")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer IDDpto;

    private String Nombre;
    private String Telefono;
    private String Fax;

    @Column(name = "Fecha_Creacion")
    private Timestamp fechaCreacion;

    @Enumerated(EnumType.STRING)
    private EstadoDepartamento Estado;

    @OneToMany(mappedBy = "departamento")
    @JsonManagedReference(value = "ingeniero-dpto")
    private List<Ingeniero> ingenieros;

    @OneToMany(mappedBy = "departamento")
    @JsonManagedReference(value = "proyecto-dpto")
    private List<Proyecto> proyectos;

    public enum EstadoDepartamento {
        ACTIVO, INACTIVO
    }

    // Getters y setters
    public Integer getIDDpto() {
        return IDDpto;
    }

    public void setIDDpto(Integer IDDpto) {
        this.IDDpto = IDDpto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        this.Telefono = telefono;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        this.Fax = fax;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public EstadoDepartamento getEstado() {
        return Estado;
    }

    public void setEstado(EstadoDepartamento estado) {
        this.Estado = estado;
    }

    public List<Ingeniero> getIngenieros() {
        return ingenieros;
    }

    public void setIngenieros(List<Ingeniero> ingenieros) {
        this.ingenieros = ingenieros;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
}
