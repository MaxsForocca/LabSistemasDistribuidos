import { useEffect, useState } from 'react';
import Swal from 'sweetalert2';
import { FaEdit, FaTrash, FaHardHat, FaPlus } from 'react-icons/fa';
import '../styles/Table.css';
import { Link } from 'react-router-dom';
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
          Swal.fire({
            toast: true,
            position: 'top-end',
            icon: 'success',
            title: 'El ingeniero ha sido eliminado.',
            showConfirmButton: false,
            timer: 2500,
            timerProgressBar: true
          });
          cargarIngenieros();
        })
        .catch(() => {
          Swal.fire({
            toast: true,
            position: 'top-end',
            icon: 'error',
            title: 'No se pudo eliminar el ingeniero.',
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
      <IngenieroDialog
        modo={modoDialogo}
        ingeniero={ingenieroSeleccionado}
        onSuccess={cargarIngenieros}
      />

      <div className="page-header">
        <div className="header-left">
          <FaHardHat className="header-icon" />
          <h2 className="tittle-page">Lista de Ingenieros</h2>
        </div>
        <button className="add-button" onClick={handleAbrirAgregar}>
          <FaPlus /> Agregar Ingeniero
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
            <th>Departamento</th>
            <th>Proyectos</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {ingenieros.map(ing => (
            <tr key={ing.idIng}>
              <td>{ing.idIng}</td>
              <td>{ing.nombre}</td>
              <td>{ing.apellido}</td>
              <td>{ing.email}</td>
              <td>{ing.especialidad}</td>
              <td>{ing.cargo}</td>
              <td>S/. {ing.salario.toFixed(2)}</td>
              <td>{ing.fechaIngreso}</td>
              <td>
                <Link to="/departamentos" style={{ color: '#007bff', textDecoration: 'underline' }}>
                  {ing.departamentoNombre}
                </Link>
              </td>
              <td>
                {ing.proyectos.length > 0 ? (
                  <div className="project-cell">
                    {ing.proyectos.map(proy => (
                      <div className="project-container"
                        key={proy.idProy}
                        style={{
                          
                        }}
                      >
                        {proy.nombre}
                      </div>
                    ))}
                  </div>
                ) : (
                  <div className="project-container alert">
                    Sin proyectos
                  </div>
                )}
              </td>
              <td>
                <div className="acciones-botones">
                  <button className="icon-button edit" onClick={() => handleEditar(ing.idIng)}>
                    <FaEdit />
                  </button>
                  <button className="icon-button delete" onClick={() => handleEliminar(ing.idIng)}>
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

