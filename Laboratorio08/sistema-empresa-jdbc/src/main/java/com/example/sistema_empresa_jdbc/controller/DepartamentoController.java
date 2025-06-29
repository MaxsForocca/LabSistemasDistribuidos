package com.example.sistema_empresa_jdbc.controller;

import com.example.sistema_empresa_jdbc.dto.DepartamentoDTO;
import com.example.sistema_empresa_jdbc.dto.DepartamentoRequestDTO;
import com.example.sistema_empresa_jdbc.dto.DepartamentoSimpleDTO;
import com.example.sistema_empresa_jdbc.models.Departamento;
import com.example.sistema_empresa_jdbc.services.DepartamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    // Listar todos como DTO completo
    @GetMapping
    public List<DepartamentoDTO> listarTodos() {
        return departamentoService.listarTodos().stream()
                .map(dep -> toDTO(dep))
                .collect(Collectors.toList());
    }

    // Obtener uno por ID
    @GetMapping("/{id}")
    public DepartamentoDTO obtenerPorId(@PathVariable Integer id) {
        Departamento dep = departamentoService.buscarPorId(id);
        return toDTO(dep);
    }

    // Crear nuevo
    @PostMapping
    public DepartamentoDTO crear(@RequestBody DepartamentoRequestDTO req) {
        Departamento nuevo = new Departamento();
        fromRequestDTO(nuevo, req);
        Departamento creado = departamentoService.guardar(nuevo);
        return toDTO(creado);
    }

    // Actualizar existente
    @PutMapping("/{id}")
    public DepartamentoDTO actualizar(@PathVariable Integer id, @RequestBody DepartamentoRequestDTO req) {
        Departamento existente = departamentoService.buscarPorId(id);
        fromRequestDTO(existente, req);
        Departamento actualizado = departamentoService.actualizar(id, existente);
        return toDTO(actualizado);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        departamentoService.eliminar(id);
    }

    // DTO reducido para selects: id + nombre
    @GetMapping("/dto")
    public List<DepartamentoSimpleDTO> listarSoloIdNombre() {
        return departamentoService.listarTodos().stream()
                .map(dep -> new DepartamentoSimpleDTO(dep.getIDDpto(), dep.getNombre()))
                .collect(Collectors.toList());
    }

    // Conversión Departamento → DTO
    private DepartamentoDTO toDTO(Departamento dep) {
        DepartamentoDTO dto = new DepartamentoDTO();
        dto.setId(dep.getIDDpto());
        dto.setNombre(dep.getNombre());
        dto.setTelefono(dep.getTelefono());
        dto.setFax(dep.getFax());
        dto.setEstado(dep.getEstado().name());

        if (dep.getFechaCreacion() != null) {
            dto.setFechaCreacion(dep.getFechaCreacion());
        }

        return dto;
    }

    // Conversión DTO → Departamento
    private void fromRequestDTO(Departamento dep, DepartamentoRequestDTO dto) {
        dep.setNombre(dto.getNombre());
        dep.setTelefono(dto.getTelefono());
        dep.setFax(dto.getFax());
        dep.setEstado(Departamento.EstadoDepartamento.valueOf(dto.getEstado()));

        if (dto.getFechaCreacion() != null) {
            dep.setFechaCreacion(dto.getFechaCreacion());
        }
    }
}
