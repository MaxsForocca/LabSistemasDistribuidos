package com.example.sistema_empresa_jdbc.dao;

import com.example.sistema_empresa_jdbc.dto.AuditoriaIngenieroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AuditoriaIngenieroDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<AuditoriaIngenieroDTO> auditoriaRowMapper = new RowMapper<AuditoriaIngenieroDTO>() {
        @Override
        public AuditoriaIngenieroDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AuditoriaIngenieroDTO auditoria = new AuditoriaIngenieroDTO();
            
            auditoria.setId(rs.getInt("id"));
            auditoria.setIdIngeniero(rs.getInt("id_ingeniero"));
            auditoria.setOperacion(rs.getString("operacion"));
            auditoria.setNombreAnterior(rs.getString("nombre_anterior"));
            auditoria.setApellidoAnterior(rs.getString("apellido_anterior"));
            if (rs.getBigDecimal("salario_anterior") != null) {
                auditoria.setSalarioAnterior(rs.getBigDecimal("salario_anterior"));
            }
            if (rs.getInt("dpto_anterior") != 0) {
                auditoria.setDptoAnterior(rs.getInt("dpto_anterior"));
            }
            auditoria.setNombreNuevo(rs.getString("nombre_nuevo"));
            auditoria.setApellidoNuevo(rs.getString("apellido_nuevo"));
            if (rs.getBigDecimal("salario_nuevo") != null) {
                auditoria.setSalarioNuevo(rs.getBigDecimal("salario_nuevo"));
            }
            if (rs.getInt("dpto_nuevo") != 0) {
                auditoria.setDptoNuevo(rs.getInt("dpto_nuevo"));
            }
            auditoria.setUsuario(rs.getString("usuario"));
            auditoria.setFechaCambio(rs.getTimestamp("fecha_cambio").toLocalDateTime());
            auditoria.setDescripcionCambio(rs.getString("descripcion_cambio"));
            
            return auditoria;
        }
    };

    public List<AuditoriaIngenieroDTO> obtenerHistorialIngeniero(Integer idIngeniero) {
        String sql = """
            SELECT 
                id, id_ingeniero, operacion,
                nombre_anterior, apellido_anterior, salario_anterior, dpto_anterior,
                nombre_nuevo, apellido_nuevo, salario_nuevo, dpto_nuevo,
                usuario, fecha_cambio, descripcion_cambio
            FROM Auditoria_Ingenieros 
            WHERE id_ingeniero = ?
            ORDER BY fecha_cambio DESC
            """;
        return jdbcTemplate.query(sql, auditoriaRowMapper, idIngeniero);
    }

    public boolean tieneHistorial(Integer idIngeniero) {
        String sql = "SELECT COUNT(*) FROM Auditoria_Ingenieros WHERE id_ingeniero = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idIngeniero);
        return count != null && count > 0;
    }

    public int contarPorIngeniero(Integer idIngeniero) {
        String sql = "SELECT COUNT(*) FROM Auditoria_Ingenieros WHERE id_ingeniero = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, idIngeniero);
    }

    public AuditoriaIngenieroDTO obtenerUltimoCambio(Integer idIngeniero) {
        String sql = """
            SELECT 
                id, id_ingeniero, operacion,
                nombre_anterior, apellido_anterior, salario_anterior, dpto_anterior,
                nombre_nuevo, apellido_nuevo, salario_nuevo, dpto_nuevo,
                usuario, fecha_cambio, descripcion_cambio
            FROM Auditoria_Ingenieros 
            WHERE id_ingeniero = ?
            ORDER BY fecha_cambio DESC
            LIMIT 1
            """;
        
        List<AuditoriaIngenieroDTO> resultados = jdbcTemplate.query(sql, auditoriaRowMapper, idIngeniero);
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    public List<AuditoriaIngenieroDTO> obtenerUltimos(int limite) {
        String sql = """
            SELECT 
                id, id_ingeniero, operacion,
                nombre_anterior, apellido_anterior, salario_anterior, dpto_anterior,
                nombre_nuevo, apellido_nuevo, salario_nuevo, dpto_nuevo,
                usuario, fecha_cambio, descripcion_cambio
            FROM Auditoria_Ingenieros 
            ORDER BY fecha_cambio DESC
            LIMIT ?
            """;
        return jdbcTemplate.query(sql, auditoriaRowMapper, limite);
    }
}