package com.example.sistema_empresa_jdbc.controller;

import com.example.sistema_empresa_jdbc.models.Proyecto;
import com.example.sistema_empresa_jdbc.services.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping
    public List<Proyecto> listar() {
        return proyectoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Proyecto obtener(@PathVariable Integer id) {
        return proyectoService.buscarPorId(id);
    }

    @PostMapping
    public Proyecto crear(@RequestBody Proyecto proyecto) {
        return proyectoService.guardar(proyecto);
    }

    @PutMapping("/{id}")
    public Proyecto actualizar(@PathVariable Integer id, @RequestBody Proyecto proyecto) {
        return proyectoService.actualizar(id, proyecto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        proyectoService.eliminar(id);
    }
}
