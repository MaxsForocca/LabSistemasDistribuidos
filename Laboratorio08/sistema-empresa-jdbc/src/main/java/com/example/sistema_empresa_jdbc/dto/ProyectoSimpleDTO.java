package com.example.sistema_empresa_jdbc.dto;

public class ProyectoSimpleDTO {
    private Integer id;
    private String nombre;

    public ProyectoSimpleDTO(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
