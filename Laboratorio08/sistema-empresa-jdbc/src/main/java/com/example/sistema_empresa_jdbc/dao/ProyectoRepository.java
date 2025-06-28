package com.example.sistema_empresa_jdbc.dao;

import org.springframework.stereotype.Repository;
import com.example.sistema_empresa_jdbc.models.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

// ProyectoDAO
@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
    // Aquí puedes agregar consultas personalizadas si lo necesitas
    
}
