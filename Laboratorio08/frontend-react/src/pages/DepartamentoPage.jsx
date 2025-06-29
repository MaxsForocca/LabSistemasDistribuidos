import { useEffect, useState, useRef } from 'react';
import { FaBuilding, FaEdit, FaTrash, FaPlus } from 'react-icons/fa';
import Swal from 'sweetalert2';
import '../styles/Table.css';
import {
  obtenerDepartamentos,
  obtenerDepartamentoPorId,
  eliminarDepartamento
} from '../services/DepartamentoService.js';
import DepartamentoDialog from '../components/DepartamentoDialog';

const DepartamentoPage = () => {
  const [departamentos, setDepartamentos] = useState([]);
  const [modoDialogo, setModoDialogo] = useState('agregar');
  const [departamentoSeleccionado, setDepartamentoSeleccionado] = useState(null);
  const dialogRef = useRef();

  const cargarDepartamentos = () => {
    obtenerDepartamentos()
      .then(data => setDepartamentos(data))
      .catch(error => console.error('Error al obtener departamentos:', error));
  };

  useEffect(() => {
    cargarDepartamentos();
  }, []);

  const handleAbrirAgregar = () => {
    setModoDialogo('agregar');
    setDepartamentoSeleccionado(null);
    document.getElementById('dialogDepartamento').showModal();
  };

  const handleEditar = (id) => {
    obtenerDepartamentoPorId(id).then(data => {
      setModoDialogo('editar');
      setDepartamentoSeleccionado(data);
      document.getElementById('dialogDepartamento').showModal();
    });
  };

  const handleEliminar = (id) => {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción eliminará el departamento.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        eliminarDepartamento(id)
          .then(() => {
            Swal.fire({
              toast: true,
              position: 'top-end',
              icon: 'success',
              title: 'Departamento eliminado correctamente',
              showConfirmButton: false,
              timer: 2500
            });
            cargarDepartamentos();
          })
          .catch(() => {
            Swal.fire({
              toast: true,
              position: 'top-end',
              icon: 'error',
              title: 'No se pudo eliminar el departamento',
              showConfirmButton: false,
              timer: 2500
            });
          });
      }
    });
  };

  return (
    <div className="page">
      <DepartamentoDialog
        modo={modoDialogo}
        departamento={departamentoSeleccionado}
        onSuccess={cargarDepartamentos}
      />

      <div className="page-header">
        <div className="header-left">
          <FaBuilding className="header-icon" />
          <h2 className="tittle-page">Lista de Departamentos</h2>
        </div>
        <button className="add-button" onClick={handleAbrirAgregar}>
          <FaPlus /> Agregar Departamento
        </button>
      </div>

      <table className="table-page">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Teléfono</th>
            <th>Fax</th>
            <th>Fecha de Creación</th>
            <th>Estado</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {departamentos.map(dep => (
            <tr key={dep.id}>
              <td>{dep.id}</td>
              <td>{dep.nombre}</td>
              <td>{dep.telefono}</td>
              <td>{dep.fax}</td>
              <td>{dep.fechaCreacion?.slice(0, 10)}</td>
              <td className="center">
                <div className={`project-container ${dep.estado === 'INACTIVO' ? 'inactive-estate' : 'active-estate'}`}>
                  {dep.estado}
                </div>
              </td>
              <td>
                <div className="acciones-botones">
                  <button className="icon-button edit" onClick={() => handleEditar(dep.id)}>
                    <FaEdit />
                  </button>
                  <button className="icon-button delete" onClick={() => handleEliminar(dep.id)}>
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

export default DepartamentoPage;
