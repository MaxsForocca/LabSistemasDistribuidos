package com.example.sistema_empresa_jdbc.services;

import com.example.sistema_empresa_jdbc.dao.DepartamentoRepository;
import com.example.sistema_empresa_jdbc.dao.IngenieroRepository;
import com.example.sistema_empresa_jdbc.dao.ProyectoRepository;

import com.example.sistema_empresa_jdbc.dto.IngenieroDTO;
import com.example.sistema_empresa_jdbc.dto.IngenieroRequestDTO;

import com.example.sistema_empresa_jdbc.models.Departamento;
import com.example.sistema_empresa_jdbc.models.Ingeniero;
import com.example.sistema_empresa_jdbc.models.Proyecto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class IngenieroService {

    @Autowired
    private IngenieroRepository ingenieroRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;
    
    @Autowired
    private ProyectoRepository proyectoRepository;

    public List<IngenieroDTO> listarTodos() {
        List<Ingeniero> ingenieros = ingenieroRepository.findAll();

        return ingenieros.stream().map(ing -> {
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
                .toList();

            dto.setProyectos(proyectosDTO);

            return dto;
        }).toList();
    }

    public Ingeniero buscarPorId(Integer id) {
        return ingenieroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingeniero no encontrado"));
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

        List<Proyecto> proyectos = proyectoRepository.findAllById(req.idProyectos);
        ingeniero.setProyectos(proyectos);

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

        if (req.idProyectos != null && !req.idProyectos.isEmpty()) {
            List<Proyecto> proyectos = proyectoRepository.findAllById(req.idProyectos);
            existente.setProyectos(proyectos);
        }

        return ingenieroRepository.save(existente);
    }

    public void eliminar(Integer id) {
        Ingeniero ingeniero = buscarPorId(id);
        ingenieroRepository.delete(ingeniero);
    }
}
