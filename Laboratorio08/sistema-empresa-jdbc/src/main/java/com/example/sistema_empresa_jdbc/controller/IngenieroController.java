package com.example.sistema_empresa_jdbc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sistema_empresa_jdbc.dao.AuditoriaIngenieroDAO;
import com.example.sistema_empresa_jdbc.dto.AuditoriaIngenieroDTO;
import com.example.sistema_empresa_jdbc.dto.IngenieroDTO;
import com.example.sistema_empresa_jdbc.dto.IngenieroRequestDTO;
import com.example.sistema_empresa_jdbc.dto.IngenieroSimpleDTO;
import com.example.sistema_empresa_jdbc.models.Ingeniero;
import com.example.sistema_empresa_jdbc.services.IngenieroService;


@RestController
@RequestMapping("/api/ingenieros")
public class IngenieroController {

    @Autowired
    private IngenieroService ingenieroService;
    @Autowired
    private AuditoriaIngenieroDAO auditoriaIngenieroDAO;

    @GetMapping
    public List<IngenieroDTO> listar() {
        return ingenieroService.listarTodos();
    }

    @GetMapping("/{id}")
    public IngenieroDTO obtener(@PathVariable Integer id) {
        return ingenieroService.buscarDTOPorId(id);
    }

    @PostMapping
    public Ingeniero crear(@RequestBody IngenieroRequestDTO request) {
        return ingenieroService.guardar(request);
    }

    @PutMapping("/{id}")
    public Ingeniero actualizar(@PathVariable Integer id, @RequestBody IngenieroRequestDTO request) {
        return ingenieroService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        ingenieroService.eliminar(id);
    }

    @GetMapping("/dto")
    public List<IngenieroSimpleDTO> listarSimple() {
        return ingenieroService.listarSimples();
    }
    /**
     * Obtener historial de cambios de un ingeniero específico
     * GET /api/ingenieros/{id}/historial
     */
    @GetMapping("/{id}/historial")
    public ResponseEntity<List<AuditoriaIngenieroDTO>> obtenerHistorialIngeniero(@PathVariable Integer id) {
        try {
            List<AuditoriaIngenieroDTO> historial = auditoriaIngenieroDAO.obtenerHistorialIngeniero(id);
            return ResponseEntity.ok(historial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Verificar si un ingeniero tiene historial de cambios
     * GET /api/ingenieros/{id}/tiene-historial
     */
    @GetMapping("/{id}/tiene-historial")
    public ResponseEntity<Map<String, Object>> tieneHistorial(@PathVariable Integer id) {
        try {
            boolean tieneHistorial = auditoriaIngenieroDAO.tieneHistorial(id);
            int totalCambios = auditoriaIngenieroDAO.contarPorIngeniero(id);
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("tieneHistorial", tieneHistorial);
            respuesta.put("totalCambios", totalCambios);
            
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener último cambio de un ingeniero
     * GET /api/ingenieros/{id}/ultimo-cambio
     */
    @GetMapping("/{id}/ultimo-cambio")
    public ResponseEntity<AuditoriaIngenieroDTO> obtenerUltimoCambio(@PathVariable Integer id) {
        try {
            AuditoriaIngenieroDTO ultimoCambio = auditoriaIngenieroDAO.obtenerUltimoCambio(id);
            
            if (ultimoCambio != null) {
                return ResponseEntity.ok(ultimoCambio);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener estadísticas generales de auditoría de ingenieros
     * GET /api/ingenieros/estadisticas-auditoria
     
    @GetMapping("/estadisticas-auditoria")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticasAuditoria() {
        try {
            Object[] stats = auditoriaIngenieroDAO.obtenerEstadisticas();
            
            Map<String, Object> estadisticas = new HashMap<>();
            estadisticas.put("totalCambios", stats[0]);
            estadisticas.put("inserciones", stats[1]);
            estadisticas.put("actualizaciones", stats[2]);
            estadisticas.put("eliminaciones", stats[3]);
            
            return ResponseEntity.ok(estadisticas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
*/
    /**
     * Obtener todos los cambios recientes (últimos 20)
     * GET /api/ingenieros/cambios-recientes
     */
    @GetMapping("/cambios-recientes")
    public ResponseEntity<List<AuditoriaIngenieroDTO>> obtenerCambiosRecientes() {
        try {
            List<AuditoriaIngenieroDTO> cambiosRecientes = auditoriaIngenieroDAO.obtenerUltimos(20);
            return ResponseEntity.ok(cambiosRecientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}