import { useEffect, useState } from 'react';
import Swal from 'sweetalert2';
import { FaEdit, FaTrash, FaHardHat, FaPlus } from 'react-icons/fa';
import '../styles/Table.css';
import {
  obtenerIngenieros,
  eliminarIngeniero,
  obtenerIngenieroPorId
} from '../services/IngenieroService.js';
import IngenieroDialog from '../components/IngenieroDialog';

const IngenieroPage = () => {
  const [ingenieros, setIngenieros] = useState([]);
  const [modoDialogo, setModoDialogo] = useState('agregar');
  const [ingenieroSeleccionado, setIngenieroSeleccionado] = useState(null);

  const cargarIngenieros = () => {
    obtenerIngenieros()
      .then(response => setIngenieros(response.data))
      .catch(error => console.error('Error al obtener ingenieros:', error));
  };

  useEffect(() => {
    cargarIngenieros();
  }, []);
  
  const handleAbrirAgregar = () => {
    setModoDialogo('agregar');
    setIngenieroSeleccionado(null);
    document.getElementById('dialogIngeniero').showModal();
  };

  const handleEditar = (id) => {
    obtenerIngenieroPorId(id).then(res => {
      setModoDialogo('editar');
      setIngenieroSeleccionado(res.data);
      document.getElementById('dialogIngeniero').showModal();
    });
  };

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
  

  return (
    <div className="page">
      <IngenieroDialog
        modo={modoDialogo}
        ingeniero={ingenieroSeleccionado}
        onSuccess={obtenerIngenieros}
      />

      <div className="page-header">
        <div className="header-left">
            <FaHardHat className="header-icon" />
            <h2 className="tittle-page">Lista de Ingenieros</h2>
        </div>
        <button className="add-button" onClick={handleAbrirAgregar}>
            <FaPlus /> Agregar Ineniero
        </button>
      </div>
      <table className="table-page">
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
                <div className="acciones-botones">
                    <button className="icon-button edit" onClick={() => handleEditar(ing.iding)}>
                    <FaEdit />
                    </button>
                    <button className="icon-button delete" onClick={() => handleEliminar(ing.iding)}>
                    <FaTrash />
                    </button>
                </div>
               </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default IngenieroPage;
