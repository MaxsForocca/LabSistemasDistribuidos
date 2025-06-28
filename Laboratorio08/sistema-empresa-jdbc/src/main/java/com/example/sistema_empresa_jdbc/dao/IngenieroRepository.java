package com.example.sistema_empresa_jdbc.dao;

import org.springframework.stereotype.Repository;
import com.example.sistema_empresa_jdbc.models.Ingeniero;
import org.springframework.data.jpa.repository.JpaRepository;

// IngenieroDAO
@Repository
public interface IngenieroRepository extends JpaRepository<Ingeniero, Integer> {
    // Puedes agregar consultas personalizadas si lo necesitas
}
