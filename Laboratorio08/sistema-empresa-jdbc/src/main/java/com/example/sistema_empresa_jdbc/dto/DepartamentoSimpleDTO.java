package com.example.sistema_empresa_jdbc.dto;

public class DepartamentoSimpleDTO {
    private Integer id;
    private String nombre;

    public DepartamentoSimpleDTO(Integer id, String nombre) {
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
