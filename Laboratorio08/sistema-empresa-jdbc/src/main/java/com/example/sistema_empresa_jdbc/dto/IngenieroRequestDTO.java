package com.example.sistema_empresa_jdbc.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class IngenieroRequestDTO {
    public String nombre;
    public String apellido;
    public String especialidad;
    public String cargo;
    public BigDecimal salario;
    public LocalDate fechaIngreso;
    public String email;
    public Integer idDepartamento;
}
