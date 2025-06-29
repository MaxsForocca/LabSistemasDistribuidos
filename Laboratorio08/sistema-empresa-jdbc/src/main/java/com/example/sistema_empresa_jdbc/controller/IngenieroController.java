package com.example.sistema_empresa_jdbc.controller;

import com.example.sistema_empresa_jdbc.models.Ingeniero;
import com.example.sistema_empresa_jdbc.services.IngenieroService;

import com.example.sistema_empresa_jdbc.dto.IngenieroDTO;

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
    public Ingeniero obtener(@PathVariable Integer id) {
        return ingenieroService.buscarPorId(id);
    }

    @PostMapping
    public Ingeniero crear(@RequestBody Ingeniero ingeniero) {
        return ingenieroService.guardar(ingeniero);
    }

    @PutMapping("/{id}")
    public Ingeniero actualizar(@PathVariable Integer id, @RequestBody Ingeniero ingeniero) {
        return ingenieroService.actualizar(id, ingeniero);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        ingenieroService.eliminar(id);
    }
}
