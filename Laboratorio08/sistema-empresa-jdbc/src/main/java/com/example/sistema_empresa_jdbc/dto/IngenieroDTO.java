package com.example.sistema_empresa_jdbc.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class IngenieroDTO {
    private Integer idIng;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String cargo;
    private BigDecimal salario;
    private LocalDate fechaIngreso;
    private String email;
    private String departamentoNombre;
    private List<ProyectoSimpleDTO> proyectos;

    // Getters y setters

    public Integer getIdIng() {
        return idIng;
    }

    public void setIdIng(Integer idIng) {
        this.idIng = idIng;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartamentoNombre() {
        return departamentoNombre;
    }

    public void setDepartamentoNombre(String departamentoNombre) {
        this.departamentoNombre = departamentoNombre;
    }

    public List<ProyectoSimpleDTO> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<ProyectoSimpleDTO> proyectos) {
        this.proyectos = proyectos;
    }

    // Clase interna para representar proyectos de forma simple
    public static class ProyectoSimpleDTO {
        private Integer idProy;
        private String nombre;

        public ProyectoSimpleDTO(Integer id, String nombre) {
            this.idProy = id;
            this.nombre = nombre;
        }

        public Integer getIdProy() {
            return idProy;
        }

        public void setIdProy(Integer idProy) {
            this.idProy = idProy;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    }
}
