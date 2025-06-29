package com.example.sistema_empresa_jdbc.services;

import com.example.sistema_empresa_jdbc.dao.DepartamentoRepository;
import com.example.sistema_empresa_jdbc.dao.IngenieroRepository;
import com.example.sistema_empresa_jdbc.dto.IngenieroDTO;

import com.example.sistema_empresa_jdbc.models.Departamento;
import com.example.sistema_empresa_jdbc.models.Ingeniero;

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

    public Ingeniero guardar(Ingeniero ingeniero) {
        if (ingeniero.getDepartamento() == null) {
            List<Departamento> departamentos = departamentoRepository.findAll();
            if (departamentos.isEmpty()) {
                throw new RuntimeException("No hay departamentos disponibles para asignar");
            }
            Departamento departamentoAleatorio = departamentos.get(
                    new Random().nextInt(departamentos.size())
            );
            ingeniero.setDepartamento(departamentoAleatorio);
        }

        return ingenieroRepository.save(ingeniero);
    }

    public Ingeniero actualizar(Integer id, Ingeniero datosActualizados) {
        Ingeniero existente = buscarPorId(id);

        existente.setNombre(datosActualizados.getNombre());
        existente.setApellido(datosActualizados.getApellido());
        existente.setCargo(datosActualizados.getCargo());
        existente.setEspecialidad(datosActualizados.getEspecialidad());
        existente.setSalario(datosActualizados.getSalario());
        existente.setFechaIngreso(datosActualizados.getFechaIngreso());
        existente.setEmail(datosActualizados.getEmail());

        // Asignar departamento solo si viene, o mantener el existente
        if (datosActualizados.getDepartamento() != null) {
            existente.setDepartamento(datosActualizados.getDepartamento());
        }

        return ingenieroRepository.save(existente);
    }

    public void eliminar(Integer id) {
        Ingeniero ingeniero = buscarPorId(id);
        ingenieroRepository.delete(ingeniero);
    }
}
