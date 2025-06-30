package com.example.sistema_empresa_jdbc.controller;

import com.example.sistema_empresa_jdbc.models.Ingeniero;
import com.example.sistema_empresa_jdbc.services.IngenieroService;

import com.example.sistema_empresa_jdbc.dto.IngenieroDTO;
import com.example.sistema_empresa_jdbc.dto.IngenieroRequestDTO;
import com.example.sistema_empresa_jdbc.dto.IngenieroSimpleDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ingenieros")
public class IngenieroController {

    @Autowired
    private IngenieroService ingenieroService;

    @GetMapping
    public List<IngenieroDTO> listar() {
        return ingenieroService.listarTodos();
    }

    @GetMapping("/{id}")
    public IngenieroDTO obtener(@PathVariable Integer id) {
        return ingenieroService.buscarDTOPorId(id);
    }

    @PostMapping
    public Ingeniero crear(@RequestBody IngenieroRequestDTO request) {
        return ingenieroService.guardar(request);
    }

    @PutMapping("/{id}")
    public Ingeniero actualizar(@PathVariable Integer id, @RequestBody IngenieroRequestDTO request) {
        return ingenieroService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        ingenieroService.eliminar(id);
    }

    @GetMapping("/dto")
    public List<IngenieroSimpleDTO> listarSimple() {
        return ingenieroService.listarSimples();
    }
}
