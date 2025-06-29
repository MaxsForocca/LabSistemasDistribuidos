package com.example.sistema_empresa_jdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.sistema_empresa_jdbc.services.DepartamentoService;

import com.example.sistema_empresa_jdbc.dto.DepartamentoSimpleDTO;

import com.example.sistema_empresa_jdbc.models.Departamento;
import java.util.List;


@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {
    
    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping
    public List<Departamento> listar() {
        return departamentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Departamento obtener(@PathVariable Integer id) {
        return departamentoService.buscarPorId(id);
    }

    @PostMapping
    public Departamento crear(@RequestBody Departamento departamento) {
        return departamentoService.guardar(departamento);
    }
    
    @PutMapping("/{id}")
    public Departamento actualizar(@PathVariable Integer id, @RequestBody Departamento departamento) {
        return departamentoService.actualizar(id, departamento);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        departamentoService.eliminar(id);
    }

    @GetMapping("/dto")
    public List<DepartamentoSimpleDTO> listarSoloIdNombre() {
        return departamentoService.listarTodos().stream()
            .map(dep -> new DepartamentoSimpleDTO(dep.getIDDpto(), dep.getNombre()))
            .toList();
    }
}
