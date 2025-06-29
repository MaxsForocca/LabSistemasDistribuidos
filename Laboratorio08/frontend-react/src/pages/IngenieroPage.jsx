import { useEffect, useState } from 'react';
import Swal from 'sweetalert2';
import '../styles/Table.css';
import {
  obtenerIngenieros,
  eliminarIngeniero,
  crearIngeniero, 
  actualizarIngeniero, 
  // obtenerIngenieroPorId
} from '../services/IngenieroService.js';

const IngenieroPage = () => {
  const [ingenieros, setIngenieros] = useState([]);

  const cargarIngenieros = () => {
    obtenerIngenieros()
      .then(response => setIngenieros(response.data))
      .catch(error => console.error('Error al obtener ingenieros:', error));
  };

  useEffect(() => {
    cargarIngenieros();
  }, []);

  const handleEliminar = (id) => {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción eliminará al ingeniero.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        eliminarIngeniero(id)
          .then(() => {
            Swal.fire('Eliminado', 'El ingeniero ha sido eliminado.', 'success');
            cargarIngenieros();
          })
          .catch(() => {
            Swal.fire('Error', 'No se pudo eliminar el ingeniero.', 'error');
          });
      }
    });
  };

  const handleEditar = (id) => {
    Swal.fire('Editar', `Abrir modal de edición para el ingeniero ID: ${id}`, 'info');
  };

  return (
    <div className="ingeniero-page">
      <h2>Lista de Ingenieros</h2>
      <table className="ingeniero-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Email</th>
            <th>Especialidad</th>
            <th>Cargo</th>
            <th>Salario</th>
            <th>Fecha de Ingreso</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {ingenieros.map(ing => (
            <tr key={ing.iding}>
              <td>{ing.iding}</td>
              <td>{ing.nombre}</td>
              <td>{ing.apellido}</td>
              <td>{ing.email}</td>
              <td>{ing.especialidad}</td>
              <td>{ing.cargo}</td>
              <td>S/. {ing.salario.toFixed(2)}</td>
              <td>{ing.fechaIngreso}</td>
              <td>
                <button onClick={() => handleEditar(ing.iding)}>Editar</button>
                <button onClick={() => handleEliminar(ing.iding)} style={{ marginLeft: '5px', color: 'red' }}>
                  Eliminar
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default IngenieroPage;
