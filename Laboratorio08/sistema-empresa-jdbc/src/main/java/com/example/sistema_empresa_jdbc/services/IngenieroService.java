package com.example.sistema_empresa_jdbc.services;

import com.example.sistema_empresa_jdbc.dao.IngenieroRepository;
import com.example.sistema_empresa_jdbc.models.Ingeniero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IngenieroService {

    @Autowired
    private IngenieroRepository ingenieroRepository;

    public List<Ingeniero> listarTodos() {
        return ingenieroRepository.findAll();
    }

    public Ingeniero buscarPorId(Integer id) {
        return ingenieroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingeniero no encontrado"));
    }

    public Ingeniero guardar(Ingeniero ingeniero) {
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
        existente.setDepartamento(datosActualizados.getDepartamento());

        return ingenieroRepository.save(existente);
    }

    public void eliminar(Integer id) {
        Ingeniero ingeniero = buscarPorId(id);
        ingenieroRepository.delete(ingeniero);
    }
}
