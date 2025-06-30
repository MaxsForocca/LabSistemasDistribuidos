package com.example.sistema_empresa_jdbc.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AuditoriaIngenieroDTO {
    
    private Integer id;
    private Integer idIngeniero;
    private String operacion;
    private String nombreAnterior;
    private String apellidoAnterior;
    private BigDecimal salarioAnterior;
    private Integer dptoAnterior;
    private String nombreNuevo;
    private String apellidoNuevo;
    private BigDecimal salarioNuevo;
    private Integer dptoNuevo;
    private String usuario;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCambio;
    
    private String descripcionCambio;

    // Constructor vacío
    public AuditoriaIngenieroDTO() {}

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Integer getIdIngeniero() { return idIngeniero; }
    public void setIdIngeniero(Integer idIngeniero) { this.idIngeniero = idIngeniero; }
    
    public String getOperacion() { return operacion; }
    public void setOperacion(String operacion) { this.operacion = operacion; }
    
    public String getNombreAnterior() { return nombreAnterior; }
    public void setNombreAnterior(String nombreAnterior) { this.nombreAnterior = nombreAnterior; }
    
    public String getApellidoAnterior() { return apellidoAnterior; }
    public void setApellidoAnterior(String apellidoAnterior) { this.apellidoAnterior = apellidoAnterior; }
    
    public BigDecimal getSalarioAnterior() { return salarioAnterior; }
    public void setSalarioAnterior(BigDecimal salarioAnterior) { this.salarioAnterior = salarioAnterior; }
    
    public Integer getDptoAnterior() { return dptoAnterior; }
    public void setDptoAnterior(Integer dptoAnterior) { this.dptoAnterior = dptoAnterior; }
    
    public String getNombreNuevo() { return nombreNuevo; }
    public void setNombreNuevo(String nombreNuevo) { this.nombreNuevo = nombreNuevo; }
    
    public String getApellidoNuevo() { return apellidoNuevo; }
    public void setApellidoNuevo(String apellidoNuevo) { this.apellidoNuevo = apellidoNuevo; }
    
    public BigDecimal getSalarioNuevo() { return salarioNuevo; }
    public void setSalarioNuevo(BigDecimal salarioNuevo) { this.salarioNuevo = salarioNuevo; }
    
    public Integer getDptoNuevo() { return dptoNuevo; }
    public void setDptoNuevo(Integer dptoNuevo) { this.dptoNuevo = dptoNuevo; }
    
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    
    public LocalDateTime getFechaCambio() { return fechaCambio; }
    public void setFechaCambio(LocalDateTime fechaCambio) { this.fechaCambio = fechaCambio; }
    
    public String getDescripcionCambio() { return descripcionCambio; }
    public void setDescripcionCambio(String descripcionCambio) { this.descripcionCambio = descripcionCambio; }
}