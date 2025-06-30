import { useEffect, useState } from 'react';
import Swal from 'sweetalert2';
import { FaEdit, FaTrash, FaProjectDiagram, FaPlus } from 'react-icons/fa';
import '../styles/Table.css';
import {
  obtenerProyectos,
  eliminarProyecto,
  obtenerProyectoPorId
} from '../services/ProyectoService';
import ProyectoDialog from '../components/ProyectoDialog'; // <- Este lo crearás similar a IngenieroDialog

const ProyectoPage = () => {
  const [proyectos, setProyectos] = useState([]);
  const [modoDialogo, setModoDialogo] = useState('agregar');
  const [proyectoSeleccionado, setProyectoSeleccionado] = useState(null);

  const cargarProyectos = () => {
    obtenerProyectos()
      .then(data => setProyectos(data))
      .catch(error => console.error('Error al obtener proyectos:', error));
  };

  useEffect(() => {
    cargarProyectos();
  }, []);

  const handleAbrirAgregar = () => {
    setModoDialogo('agregar');
    setProyectoSeleccionado(null);
    document.getElementById('dialogProyecto').showModal();
  };

  const handleEditar = (id) => {
    obtenerProyectoPorId(id).then(data => {
      setModoDialogo('editar');
      setProyectoSeleccionado(data);
      document.getElementById('dialogProyecto').showModal();
    });
  };

  const handleEliminar = (id) => {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción eliminará el proyecto.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        eliminarProyecto(id)
          .then(() => {
            Swal.fire({
              toast: true,
              position: 'top-end',
              icon: 'success',
              title: 'Proyecto eliminado.',
              showConfirmButton: false,
              timer: 2500,
              timerProgressBar: true
            });
            cargarProyectos();
          })
          .catch(() => {
            Swal.fire({
              toast: true,
              position: 'top-end',
              icon: 'error',
              title: 'Error al eliminar el proyecto.',
              showConfirmButton: false,
              timer: 2500,
              timerProgressBar: true
            });
          });
      }
    });
  };

  return (
    <div className="page">
      <ProyectoDialog
        modo={modoDialogo}
        proyecto={proyectoSeleccionado}
        onSuccess={cargarProyectos}
      />

      <div className="page-header">
        <div className="header-left">
          <FaProjectDiagram className="header-icon" />
          <h2 className="tittle-page">Lista de Proyectos</h2>
        </div>
        <button className="add-button" onClick={handleAbrirAgregar}>
          <FaPlus /> Agregar Proyecto
        </button>
      </div>

      <table className="table-page">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Inicio</th>
            <th>Término</th>
            <th>Presupuesto</th>
            <th>Estado</th>
            <th>Departamento</th>
            <th>Ingenieros</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {proyectos.map(proy => (
            <tr key={proy.id}>
              <td>{proy.id}</td>
              <td>{proy.nombre}</td>
              <td>{proy.descripcion}</td>
              <td>{proy.fecInicio}</td>
              <td>{proy.fecTermino}</td>
              <td>S/. {proy.presupuesto.toLocaleString()}</td>
              <td>{proy.estado}</td>
              <td>{proy.departamentoNombre}</td>
              <td>
                {proy.ingenieros.length > 0 ? (
                  <div className="project-cell">
                    {proy.ingenieros.map(ing => (
                      <div key={ing.id} className="project-container">
                        {ing.nombre} {ing.apellido}
                      </div>
                    ))}
                  </div>
                ) : (
                  <div className="project-container alert">Sin ingenieros</div>
                )}
              </td>
              <td>
                <div className="acciones-botones">
                  <button className="icon-button edit" onClick={() => handleEditar(proy.id)}>
                    <FaEdit />
                  </button>
                  <button className="icon-button delete" onClick={() => handleEliminar(proy.id)}>
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

export default ProyectoPage;
