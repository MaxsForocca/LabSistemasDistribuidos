package com.example.sistema_empresa_jdbc.models;
import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "Proyecto_Ingeniero")
@IdClass(ProyectoIngenieroId.class)
public class ProyectoIngeniero {

    @Id
    @ManyToOne
    @JoinColumn(name = "IDProy")
    private Proyecto proyecto;

    @Id
    @ManyToOne
    @JoinColumn(name = "IDIng")
    private Ingeniero ingeniero;

    @Column(name = "Fecha_Asignacion")
    private LocalDate fechaAsignacion;

    @Column(name = "Rol_Proyecto")
    private String rolProyecto;

    @Column(name = "Horas_Asignadas")
    private Integer horasAsignadas;

    // Getters y setters
}
