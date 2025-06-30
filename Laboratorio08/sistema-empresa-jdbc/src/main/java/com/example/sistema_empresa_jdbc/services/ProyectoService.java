package com.example.sistema_empresa_jdbc.services;

import com.example.sistema_empresa_jdbc.models.Proyecto;

import com.example.sistema_empresa_jdbc.models.Departamento;
import com.example.sistema_empresa_jdbc.models.Ingeniero;

import com.example.sistema_empresa_jdbc.dto.IngenieroSimpleDTO;
import com.example.sistema_empresa_jdbc.dto.ProyectoDTO;
import com.example.sistema_empresa_jdbc.dto.ProyectoRequestDTO;

import com.example.sistema_empresa_jdbc.dao.DepartamentoRepository;
import com.example.sistema_empresa_jdbc.dao.IngenieroRepository;
import com.example.sistema_empresa_jdbc.dao.ProyectoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProyectoService {
    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private IngenieroRepository ingenieroRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    public List<ProyectoDTO> listarTodosDTO() {
        return proyectoRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public List<Proyecto> listarTodos() {
        return proyectoRepository.findAll();
    }

    public ProyectoDTO buscarDTOPorId(Integer id) {
        Proyecto proyecto = buscarPorId(id);
        return toDTO(proyecto);
    }

    public Proyecto guardar(ProyectoRequestDTO dto) {
        Proyecto nuevo = new Proyecto();
        nuevo.setNombre(dto.getNombre());
        nuevo.setDescripcion(dto.getDescripcion());
        nuevo.setFecInicio(dto.getFecInicio());
        nuevo.setFecTermino(dto.getFecTermino());
        nuevo.setPresupuesto(dto.getPresupuesto());
        nuevo.setEstado(Proyecto.EstadoProyecto.valueOf(dto.getEstado()));

        Departamento depto = departamentoRepository.findById(dto.getIdDepartamento())
            .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));

        nuevo.setDepartamento(depto);

        List<Ingeniero> listaIng = ingenieroRepository.findAllById(dto.getIdIngenieros());
        nuevo.setIngenieros(listaIng);

        return proyectoRepository.save(nuevo);
    }

    public Proyecto actualizar(Integer id, ProyectoRequestDTO dto) {
        Proyecto existente = buscarPorId(id);

        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setFecInicio(dto.getFecInicio());
        existente.setFecTermino(dto.getFecTermino());
        existente.setPresupuesto(dto.getPresupuesto());

        try {
            existente.setEstado(Proyecto.EstadoProyecto.valueOf(dto.getEstado()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado no válido: " + dto.getEstado());
        }

        if (dto.getIdDepartamento() != null) {
            Departamento departamento = departamentoRepository.findById(dto.getIdDepartamento())
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado con ID: " + dto.getIdDepartamento()));
            existente.setDepartamento(departamento);
        }

        if (dto.getIdIngenieros() != null && !dto.getIdIngenieros().isEmpty()) {
            List<Ingeniero> ingenieros = ingenieroRepository.findAllById(dto.getIdIngenieros());
            existente.setIngenieros(ingenieros);
        } else {
            existente.setIngenieros(List.of());
        }

        return proyectoRepository.save(existente);
    }


    public void eliminar(Integer id) {
        Proyecto proyecto = buscarPorId(id);
        proyectoRepository.delete(proyecto);
    }

    public Proyecto buscarPorId(Integer id) {
        return proyectoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con ID: " + id));
    }

    public ProyectoDTO toDTO(Proyecto proy) {
        ProyectoDTO dto = new ProyectoDTO();

        dto.setId(proy.getIDProy());
        dto.setNombre(proy.getNombre());
        dto.setDescripcion(proy.getDescripcion());
        dto.setFecInicio(proy.getFecInicio());
        dto.setFecTermino(proy.getFecTermino());
        dto.setPresupuesto(proy.getPresupuesto());
        dto.setEstado(proy.getEstado().toString());

        if (proy.getDepartamento() != null) {
            dto.setDepartamentoNombre(proy.getDepartamento().getNombre());
        }

        List<IngenieroSimpleDTO> ingenierosDTO = proy.getIngenieros().stream()
            .map(i -> {
                IngenieroSimpleDTO iDto = new IngenieroSimpleDTO();
                iDto.setId(i.getIDIng());
                iDto.setNombre(i.getNombre());
                iDto.setApellido(i.getApellido());
                return iDto;
            }).toList();

        dto.setIngenieros(ingenierosDTO);

        return dto;
    }
}

