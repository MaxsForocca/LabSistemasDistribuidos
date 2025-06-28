package com.example.sistema_empresa_jdbc.services;

import com.example.sistema_empresa_jdbc.models.Proyecto;
import com.example.sistema_empresa_jdbc.dao.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    public List<Proyecto> listarTodos() {
        return proyectoRepository.findAll();
    }

    public Proyecto buscarPorId(Integer id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }

    public Proyecto guardar(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public Proyecto actualizar(Integer id, Proyecto datosActualizados) {
        Proyecto existente = buscarPorId(id);

        existente.setNombre(datosActualizados.getNombre());
        existente.setDescripcion(datosActualizados.getDescripcion());
        existente.setFecInicio(datosActualizados.getFecInicio());
        existente.setFecTermino(datosActualizados.getFecTermino());
        existente.setPresupuesto(datosActualizados.getPresupuesto());
        existente.setEstado(datosActualizados.getEstado());
        existente.setDepartamento(datosActualizados.getDepartamento());

        return proyectoRepository.save(existente);
    }

    public void eliminar(Integer id) {
        Proyecto proyecto = buscarPorId(id);
        proyectoRepository.delete(proyecto);
    }
}
