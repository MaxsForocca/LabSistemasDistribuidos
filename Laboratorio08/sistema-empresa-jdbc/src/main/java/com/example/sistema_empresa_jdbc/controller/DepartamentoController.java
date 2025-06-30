package com.example.sistema_empresa_jdbc.controller;

import com.example.sistema_empresa_jdbc.dto.DepartamentoDTO;
import com.example.sistema_empresa_jdbc.dto.DepartamentoRequestDTO;
import com.example.sistema_empresa_jdbc.dto.DepartamentoSimpleDTO;

import com.example.sistema_empresa_jdbc.models.Departamento;
import com.example.sistema_empresa_jdbc.services.DepartamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping
    public List<DepartamentoDTO> listarTodos() {
        return departamentoService.obtenerTodosDTO();
    }

    @GetMapping("/{id}")
    public DepartamentoDTO obtenerPorId(@PathVariable Integer id) {
        return departamentoService.obtenerPorIdDTO(id);
    }

    @PostMapping
    public DepartamentoDTO crear(@RequestBody DepartamentoRequestDTO req) {
        return departamentoService.crearDesdeDTO(req);
    }

    @PutMapping("/{id}")
    public DepartamentoDTO actualizar(@PathVariable Integer id, @RequestBody DepartamentoRequestDTO req) {
        return departamentoService.actualizarDesdeDTO(id, req);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        departamentoService.eliminar(id);
    }

    @GetMapping("/dto")
    public List<DepartamentoSimpleDTO> listarSoloIdNombre() {
        return departamentoService.obtenerIdNombreDTO();
    }
}
