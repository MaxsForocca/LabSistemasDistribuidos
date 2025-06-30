package com.example.sistema_empresa_jdbc.services;

import com.example.sistema_empresa_jdbc.dao.DepartamentoRepository;
import com.example.sistema_empresa_jdbc.dao.IngenieroRepository;
import com.example.sistema_empresa_jdbc.dao.ProyectoRepository;

import com.example.sistema_empresa_jdbc.dto.IngenieroDTO;
import com.example.sistema_empresa_jdbc.dto.IngenieroRequestDTO;
import com.example.sistema_empresa_jdbc.dto.IngenieroSimpleDTO;

import com.example.sistema_empresa_jdbc.models.Departamento;
import com.example.sistema_empresa_jdbc.models.Ingeniero;
import com.example.sistema_empresa_jdbc.models.Proyecto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class IngenieroService {

    @Autowired
    private IngenieroRepository ingenieroRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;
    
    @Autowired
    private ProyectoRepository proyectoRepository;

    public List<IngenieroDTO> listarTodos() {
        return ingenieroRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public IngenieroDTO buscarDTOPorId(Integer id) {
        Ingeniero ing = ingenieroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ingeniero no encontrado"));
        return toDTO(ing);
    }

    public Ingeniero guardar(IngenieroRequestDTO req) {
        Ingeniero ingeniero = new Ingeniero();
        ingeniero.setNombre(req.nombre);
        ingeniero.setApellido(req.apellido);
        ingeniero.setEspecialidad(req.especialidad);
        ingeniero.setCargo(req.cargo);
        ingeniero.setSalario(req.salario);
        ingeniero.setFechaIngreso(req.fechaIngreso);
        ingeniero.setEmail(req.email);

        Departamento dpto = departamentoRepository.findById(req.idDepartamento)
            .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
        ingeniero.setDepartamento(dpto);

        return ingenieroRepository.save(ingeniero);
    }

    public Ingeniero actualizar(Integer id, IngenieroRequestDTO req) {
        Ingeniero existente = ingenieroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ingeniero no encontrado"));

        existente.setNombre(req.nombre);
        existente.setApellido(req.apellido);
        existente.setCargo(req.cargo);
        existente.setEspecialidad(req.especialidad);
        existente.setSalario(req.salario);
        existente.setFechaIngreso(req.fechaIngreso);
        existente.setEmail(req.email);

        if (req.idDepartamento != null) {
            Departamento dpto = departamentoRepository.findById(req.idDepartamento)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
            existente.setDepartamento(dpto);
        }

        return ingenieroRepository.save(existente);
    }

    public void eliminar(Integer id) {
        Ingeniero ingeniero = buscarPorId(id);
        ingenieroRepository.delete(ingeniero);
    }

    public Ingeniero buscarPorId(Integer id) {
        return ingenieroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ingeniero no encontrado"));
    }

    public List<IngenieroSimpleDTO> listarSimples() {
        return ingenieroRepository.findAll().stream()
            .map(ing -> {
                IngenieroSimpleDTO dto = new IngenieroSimpleDTO();
                dto.setId(ing.getIDIng());
                dto.setNombre(ing.getNombre());
                dto.setApellido(ing.getApellido());
                return dto;
            })
            .toList();
    }

    private IngenieroDTO toDTO(Ingeniero ing) {
        IngenieroDTO dto = new IngenieroDTO();
        dto.setIdIng(ing.getIDIng());
        dto.setNombre(ing.getNombre());
        dto.setApellido(ing.getApellido());
        dto.setEspecialidad(ing.getEspecialidad());
        dto.setCargo(ing.getCargo());
        dto.setSalario(ing.getSalario());
        dto.setFechaIngreso(ing.getFechaIngreso());
        dto.setEmail(ing.getEmail());

        if (ing.getDepartamento() != null) {
            dto.setDepartamentoNombre(ing.getDepartamento().getNombre());
        }

        List<IngenieroDTO.ProyectoSimpleDTO> proyectosDTO = ing.getProyectos().stream()
            .map(p -> new IngenieroDTO.ProyectoSimpleDTO(p.getIDProy(), p.getNombre()))
            .collect(Collectors.toList());

        dto.setProyectos(proyectosDTO);

        return dto;
    }
}
