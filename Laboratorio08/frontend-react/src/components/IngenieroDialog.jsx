import { crearIngeniero, actualizarIngeniero } from '../services/IngenieroService.js';
import { obtenerDepartamentosDTO } from '../services/DepartamentoService.js';
import { useState, useEffect, useRef } from 'react';
import { FaBuilding, FaUser, FaEnvelope, FaTools, FaBriefcase, FaDollarSign, FaCalendarAlt, FaPlus } from 'react-icons/fa';
import '../styles/Dialog.css';
import Swal from 'sweetalert2';

const IngenieroDialog = ({ modo, ingeniero, onSuccess }) => {
  const dialogRef = useRef(null);

  const [formData, setFormData] = useState({
    nombre: '', apellido: '', email: '', especialidad: '',
    cargo: '', salario: '', fechaIngreso: '',
    idDepartamento: ''
  });

  const [departamentos, setDepartamentos] = useState([]);

  useEffect(() => {
    obtenerDepartamentosDTO().then(setDepartamentos);
  }, []);

  useEffect(() => {
    if (ingeniero && departamentos.length > 0) {
      const depEncontrado = departamentos.find(d => d.nombre === ingeniero.departamentoNombre);
      const idDepartamento = depEncontrado ? depEncontrado.id.toString() : '';

      setFormData({
        nombre: ingeniero.nombre || '',
        apellido: ingeniero.apellido || '',
        email: ingeniero.email || '',
        especialidad: ingeniero.especialidad || '',
        cargo: ingeniero.cargo || '',
        salario: ingeniero.salario || '',
        fechaIngreso: ingeniero.fechaIngreso || '',
        idDepartamento
      });
    } else if (!ingeniero && modo === 'agregar') {
      setFormData({
        nombre: '',
        apellido: '',
        email: '',
        especialidad: '',
        cargo: '',
        salario: '',
        fechaIngreso: '',
        idDepartamento: ''
      });
    }
  }, [ingeniero, modo, departamentos]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const cerrarDialogo = () => {
    const dialog = dialogRef.current;
    if (!dialog || dialog.classList.contains('closing')) return;

    dialog.classList.add('closing');
    dialog.addEventListener('animationend', () => {
      dialog.classList.remove('closing');
      dialog.close();
    }, { once: true });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const payload = {
      ...formData,
      salario: parseFloat(formData.salario),
      idDepartamento: parseInt(formData.idDepartamento)
    };

    const accion = modo === 'editar'
      ? actualizarIngeniero(ingeniero.idIng, payload)
      : crearIngeniero(payload);

    accion
      .then(() => {
        Swal.fire({
          toast: true,
          position: 'top-end',
          icon: 'success',
          title: `Ingeniero ${modo === 'editar' ? 'actualizado' : 'creado'} correctamente`,
          showConfirmButton: false,
          timer: 2500,
          timerProgressBar: true
        });
        cerrarDialogo();
        onSuccess();
      })
      .catch(() => {
        Swal.fire({
          toast: true,
          position: 'top-end',
          icon: 'error',
          title: `No se pudo ${modo === 'editar' ? 'actualizar' : 'crear'} el ingeniero`,
          showConfirmButton: false,
          timer: 2500,
          timerProgressBar: true
        });
      });
  };

  return (
    <dialog ref={dialogRef} id="dialogIngeniero" className="modal-dialog">
      <form method="dialog" className="modal-form" onSubmit={handleSubmit}>
        <div className="dialog-header">
          <FaPlus />
          <h3 className="dialog-tittle">{modo === 'editar' ? 'Editar Ingeniero' : 'Agregar Ingeniero'}</h3>
        </div>

        <div className="form-grid">
          <div className="input-icon"><FaUser /><input name="nombre" placeholder="Nombre" value={formData.nombre} onChange={handleChange} required /></div>
          <div className="input-icon"><FaUser /><input name="apellido" placeholder="Apellido" value={formData.apellido} onChange={handleChange} required /></div>
          <div className="input-icon"><FaEnvelope /><input type="email" name="email" placeholder="Email" value={formData.email} onChange={handleChange} required /></div>
          <div className="input-icon"><FaTools /><input name="especialidad" placeholder="Especialidad" value={formData.especialidad} onChange={handleChange} required /></div>
          <div className="input-icon"><FaBriefcase /><input name="cargo" placeholder="Cargo" value={formData.cargo} onChange={handleChange} required /></div>
          <div className="input-icon"><FaDollarSign /><input type="number" name="salario" placeholder="Salario" step="0.01" value={formData.salario} onChange={handleChange} required /></div>
          <div className="input-icon"><FaCalendarAlt /><input type="date" name="fechaIngreso" value={formData.fechaIngreso} onChange={handleChange} required /></div>
          <div className="input-icon"><FaBuilding />
            <select name="idDepartamento" value={formData.idDepartamento} onChange={handleChange} required>
              <option value="">Seleccione Departamento</option>
              {departamentos.map(dep => (
                <option key={dep.id} value={dep.id}>{dep.nombre}</option>
              ))}
            </select>
          </div>
        </div>

        <div className="dialog-actions">
          <button type="submit" className="dlg-button save">Guardar</button>
          <button type="button" className="dlg-button delete" onClick={cerrarDialogo}>Cancelar</button>
        </div>
      </form>
    </dialog>
  );
};

export default IngenieroDialog;
