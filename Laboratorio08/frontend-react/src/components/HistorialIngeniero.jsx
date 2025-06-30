import React, { useState, useEffect } from 'react';
import axios from 'axios';

const HistorialIngeniero = ({ ingenieroId, nombreIngeniero }) => {
  const [historial, setHistorial] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [tieneHistorial, setTieneHistorial] = useState(false);
  const [totalCambios, setTotalCambios] = useState(0);

  useEffect(() => {
    if (ingenieroId) {
      cargarHistorial();
      verificarHistorial();
    }
  }, [ingenieroId]);

  const cargarHistorial = async () => {
    setLoading(true);
    setError(null);
    
    try {
      const response = await axios.get(`http://localhost:8080/api/ingenieros/${ingenieroId}/historial`);
      setHistorial(response.data);
    } catch (err) {
      setError('Error al cargar el historial');
      console.error('Error:', err);
    } finally {
      setLoading(false);
    }
  };

  const verificarHistorial = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/ingenieros/${ingenieroId}/tiene-historial`);
      setTieneHistorial(response.data.tieneHistorial);
      setTotalCambios(response.data.totalCambios);
    } catch (err) {
      console.error('Error al verificar historial:', err);
    }
  };

  const formatearFecha = (fechaHora) => {
    return new Date(fechaHora).toLocaleString('es-ES', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  const getOperacionTexto = (operacion) => {
    switch (operacion) {
      case 'INSERT': return 'Creado';
      case 'UPDATE': return 'Modificado';
      case 'DELETE': return 'Eliminado';
      default: return operacion;
    }
  };

  if (loading) {
    return (
      <div className="text-center p-3">
        <div className="spinner-border spinner-border-sm" role="status">
          <span className="visually-hidden">Cargando...</span>
        </div>
        <span className="ms-2">Cargando historial...</span>
      </div>
    );
  }

  if (error) {
    return (
      <div className="alert alert-danger" role="alert">
        {error}
        <button 
          className="btn btn-sm btn-outline-danger ms-2"
          onClick={cargarHistorial}
        >
          Reintentar
        </button>
      </div>
    );
  }

  if (!tieneHistorial) {
    return (
      <div className="alert alert-info" role="alert">
        Este ingeniero no tiene historial de cambios registrado.
      </div>
    );
  }

  return (
    <div className="historial-ingeniero">
      <h5>
        <span>Historial de Cambios</span>
        <span className="badge">{totalCambios} cambios</span>
      </h5>

      {historial.length === 0 ? (
        <div className="alert alert-warning" role="alert">
          No se encontraron cambios registrados.
        </div>
      ) : (
        <div className="historial-lista">
          {historial.map((cambio, index) => (
            <div key={cambio.id} className="historial-item">
              <div className="card border-start" style={{
                borderLeftColor: cambio.operacion === 'INSERT' ? '#198754' : 
                                cambio.operacion === 'UPDATE' ? '#ffc107' : '#dc3545'
              }}>
                <div className="card-body">
                  
                  {/* Header del cambio */}
                  <div className="cambio-header">
                    <div className="cambio-info">
                      <span className={`badge ${
                        cambio.operacion === 'INSERT' ? 'bg-success' : 
                        cambio.operacion === 'UPDATE' ? 'bg-warning' : 'bg-danger'
                      }`}>
                        {getOperacionTexto(cambio.operacion)}
                      </span>
                      
                      <span className="fecha-cambio">
                        {formatearFecha(cambio.fechaCambio)}
                      </span>
                      
                      <span className="usuario-cambio">
                        {cambio.usuario}
                      </span>
                    </div>
                  </div>

                  {/* Descripción del cambio */}
                  <div className="descripcion-cambio">
                    {cambio.descripcionCambio}
                  </div>

                  {/* Detalles de cambios para UPDATE */}
                  {cambio.operacion === 'UPDATE' && cambio.salarioAnterior && cambio.salarioNuevo && 
                   cambio.salarioAnterior !== cambio.salarioNuevo && (
                    <div className="detalle-cambio">
                      <strong>Salario:</strong>
                      <br />
                      <span className="valor-anterior">
                        ${cambio.salarioAnterior?.toLocaleString()}
                      </span>
                      <span className="flecha-cambio">→</span>
                      <span className="valor-nuevo">
                        ${cambio.salarioNuevo?.toLocaleString()}
                      </span>
                    </div>
                  )}

                  {/* Información para INSERT */}
                  {cambio.operacion === 'INSERT' && (
                    <div className="detalle-cambio">
                      <strong>Datos iniciales:</strong>
                      <br />
                      <span>Nombre: {cambio.nombreNuevo} {cambio.apellidoNuevo}</span>
                      {cambio.salarioNuevo && (
                        <>
                          <br />
                          <span>Salario: ${cambio.salarioNuevo.toLocaleString()}</span>
                        </>
                      )}
                      {cambio.dptoNuevo && (
                        <>
                          <br />
                          <span>Departamento ID: {cambio.dptoNuevo}</span>
                        </>
                      )}
                    </div>
                  )}

                  {/* Información para DELETE */}
                  {cambio.operacion === 'DELETE' && (
                    <div className="detalle-cambio">
                      <strong>Datos eliminados:</strong>
                      <br />
                      <span>Nombre: {cambio.nombreAnterior} {cambio.apellidoAnterior}</span>
                      {cambio.salarioAnterior && (
                        <>
                          <br />
                          <span>Salario: ${cambio.salarioAnterior.toLocaleString()}</span>
                        </>
                      )}
                      {cambio.dptoAnterior && (
                        <>
                          <br />
                          <span>Departamento ID: {cambio.dptoAnterior}</span>
                        </>
                      )}
                    </div>
                  )}
                </div>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* Botón para recargar */}
      <div className="text-center mt-3">
        <button 
          className="btn btn-outline-primary btn-sm"
          onClick={cargarHistorial}
          disabled={loading}
        >
          Actualizar historial
        </button>
      </div>
    </div>
  );
};

export default HistorialIngeniero;