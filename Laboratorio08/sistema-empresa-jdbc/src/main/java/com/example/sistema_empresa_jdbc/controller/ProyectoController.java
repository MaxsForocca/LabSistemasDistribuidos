package com.example.sistema_empresa_jdbc.controller;

import com.example.sistema_empresa_jdbc.models.Proyecto;
import com.example.sistema_empresa_jdbc.services.ProyectoService;

import com.example.sistema_empresa_jdbc.dto.ProyectoSimpleDTO;
import com.example.sistema_empresa_jdbc.dto.ProyectoDTO;
import com.example.sistema_empresa_jdbc.dto.ProyectoRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping
    public List<ProyectoDTO> listarTodos() {
        return proyectoService.listarTodosDTO();
    }

    @GetMapping("/{id}")
    public ProyectoDTO obtenerPorId(@PathVariable Integer id) {
        return proyectoService.buscarDTOPorId(id);
    }

    @PostMapping
    public ProyectoDTO crear(@RequestBody ProyectoRequestDTO dto) {
        Proyecto proyectoGuardado = proyectoService.guardar(dto);
        return proyectoService.toDTO(proyectoGuardado);
    }

    @PutMapping("/{id}")
    public ProyectoDTO actualizar(@PathVariable Integer id, @RequestBody ProyectoRequestDTO dto) {
        System.out.println("==== Datos recibidos ====");
        System.out.println("Nombre: " + dto.getNombre());
        System.out.println("Descripción: " + dto.getDescripcion());
        System.out.println("Fecha Inicio: " + dto.getFecInicio());
        System.out.println("Fecha Término: " + dto.getFecTermino());
        System.out.println("Presupuesto: " + dto.getPresupuesto());
        System.out.println("Estado: " + dto.getEstado());
        System.out.println("ID Departamento: " + dto.getIdDepartamento());
        System.out.println("ID Ingenieros: " + dto.getIdIngenieros());
        
        Proyecto proyectoActualizado = proyectoService.actualizar(id, dto);
        return proyectoService.toDTO(proyectoActualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        proyectoService.eliminar(id);
    }

    @GetMapping("/dto")
    public List<ProyectoSimpleDTO> listarSoloIdNombre() {
        return proyectoService.listarTodos().stream()
            .map(proy -> new ProyectoSimpleDTO(proy.getIDProy(), proy.getNombre()))
            .toList();
    }
}