package com.example.sistema_empresa_jdbc.services;

import com.example.sistema_empresa_jdbc.dto.DepartamentoDTO;
import com.example.sistema_empresa_jdbc.dto.DepartamentoRequestDTO;
import com.example.sistema_empresa_jdbc.dto.DepartamentoSimpleDTO;
import com.example.sistema_empresa_jdbc.models.Departamento;
import com.example.sistema_empresa_jdbc.dao.DepartamentoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    // CRUD básico
    public List<Departamento> listarTodos() {
        return departamentoRepository.findAll();
    }

    public Departamento buscarPorId(Integer id) {
        return departamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
    }

    public Departamento guardar(Departamento d) {
        return departamentoRepository.save(d);
    }

    public Departamento actualizar(Integer id, Departamento actualizado) {
        if (!departamentoRepository.existsById(id)) {
            throw new RuntimeException("Departamento no existe");
        }
        return departamentoRepository.save(actualizado);
    }

    public void eliminar(Integer id) {
        departamentoRepository.deleteById(id);
    }

    // Conversión a DTO completo
    public List<DepartamentoDTO> obtenerTodosDTO() {
        return listarTodos().stream()
                .map(dep -> toDTO(dep))
                .collect(Collectors.toList());
    }
    public DepartamentoDTO obtenerPorIdDTO(Integer id) {
        return toDTO(buscarPorId(id));
    }

    public DepartamentoDTO crearDesdeDTO(DepartamentoRequestDTO req) {
        Departamento nuevo = new Departamento();
        fromRequestDTO(nuevo, req);
        return toDTO(guardar(nuevo));
    }

    public DepartamentoDTO actualizarDesdeDTO(Integer id, DepartamentoRequestDTO req) {
        Departamento existente = buscarPorId(id);
        fromRequestDTO(existente, req);
        return toDTO(actualizar(id, existente));
    }

    // DTO reducido para select
    public List<DepartamentoSimpleDTO> obtenerIdNombreDTO() {
        return listarTodos().stream()
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
        dto.setFechaCreacion(dep.getFechaCreacion());
        return dto;
    }

    // Conversión DTO → Departamento
    private void fromRequestDTO(Departamento dep, DepartamentoRequestDTO dto) {
        dep.setNombre(dto.getNombre());
        dep.setTelefono(dto.getTelefono());
        dep.setFax(dto.getFax());
        dep.setEstado(Departamento.EstadoDepartamento.valueOf(dto.getEstado()));
        dep.setFechaCreacion(dto.getFechaCreacion());
    }
}
