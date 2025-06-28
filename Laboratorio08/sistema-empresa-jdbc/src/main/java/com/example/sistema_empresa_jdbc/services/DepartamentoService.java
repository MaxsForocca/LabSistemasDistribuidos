package com.example.sistema_empresa_jdbc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.sistema_empresa_jdbc.dao.DepartamentoRepository;
import com.example.sistema_empresa_jdbc.models.Departamento;
import java.util.List;

@Service
public class DepartamentoService {
    
    @Autowired
    private DepartamentoRepository departamentoRepository;

    public List<Departamento> listarTodos() {
        return departamentoRepository.findAll();
    }

    public Departamento buscarPorId(Integer id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
    }
    public Departamento guardar(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }
    public Departamento actualizar(Integer id, Departamento datosActualizados) {
        Departamento existente = buscarPorId(id);
        
        existente.setNombre(datosActualizados.getNombre());
        existente.setTelefono(datosActualizados.getTelefono());
        existente.setFax(datosActualizados.getFax());
        
        return departamentoRepository.save(existente);
    }
    public void eliminar(Integer id) {
        Departamento departamento = buscarPorId(id);
        departamentoRepository.delete(departamento);
    }
}
