package com.example.sistema_empresa_jdbc.dao;

import org.springframework.stereotype.Repository;
import com.example.sistema_empresa_jdbc.models.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

// DepartamentoDAO

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
    // Aquí puedes agregar consultas personalizadas si lo necesitas
    
}
