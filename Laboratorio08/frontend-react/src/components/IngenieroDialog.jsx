// src/components/IngenieroDialog.jsx
import { crearIngeniero, actualizarIngeniero } from '../services/IngenieroService.js';
import { useState, useEffect, useRef } from 'react';
import { FaBuilding, FaProjectDiagram } from 'react-icons/fa';
import { FaUser, FaEnvelope, FaTools, FaBriefcase, FaDollarSign, FaCalendarAlt, FaPlus } from 'react-icons/fa';
import '../styles/Dialog.css';
import Swal from 'sweetalert2';

const IngenieroDialog = ({ modo, ingeniero, onSuccess }) => {
  const dialogRef = useRef(null);

  const [formData, setFormData] = useState({
    nombre: '',
    apellido: '',
    email: '',
    especialidad: '',
    cargo: '',
    salario: '',
    fechaIngreso: '',
  });

  useEffect(() => {
    if (ingeniero) {
      setFormData({
        nombre: ingeniero.nombre || '',
        apellido: ingeniero.apellido || '',
        email: ingeniero.email || '',
        especialidad: ingeniero.especialidad || '',
        cargo: ingeniero.cargo || '',
        salario: ingeniero.salario || '',
        fechaIngreso: ingeniero.fechaIngreso || '',
      });
    } else {
      setFormData({
        nombre: '',
        apellido: '',
        email: '',
        especialidad: '',
        cargo: '',
        salario: '',
        fechaIngreso: '',
      });
    }
  }, [ingeniero]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const cerrarDialogo = () => {
    const dialog = dialogRef.current;
    if (!dialog || dialog.classList.contains('closing')) return;

    dialog.classList.add('closing');
    const onAnimationEnd = () => {
      dialog.classList.remove('closing');
      dialog.close();
      dialog.removeEventListener('animationend', onAnimationEnd);
    };
    dialog.addEventListener('animationend', onAnimationEnd);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const accion = modo === 'editar'
      ? actualizarIngeniero(ingeniero.iding, formData)
      : crearIngeniero(formData);

    accion
      .then(() => {
        Swal.fire('Éxito', `Ingeniero ${modo === 'editar' ? 'actualizado' : 'creado'} correctamente`, 'success');
        cerrarDialogo();
        onSuccess();
      })
      .catch(() => {
        Swal.fire('Error', `No se pudo ${modo === 'editar' ? 'actualizar' : 'crear'} el ingeniero`, 'error');
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
        <div className="input-icon">
            <FaUser />
            <input name="nombre" placeholder="Nombre" value={formData.nombre} onChange={handleChange} required />
        </div>

        <div className="input-icon">
            <FaUser />
            <input name="apellido" placeholder="Apellido" value={formData.apellido} onChange={handleChange} required />
        </div>

        <div className="input-icon">
            <FaEnvelope />
            <input type="email" name="email" placeholder="Email" value={formData.email} onChange={handleChange} required />
        </div>

        <div className="input-icon">
            <FaTools />
            <input name="especialidad" placeholder="Especialidad" value={formData.especialidad} onChange={handleChange} required />
        </div>

        <div className="input-icon">
            <FaBriefcase />
            <input name="cargo" placeholder="Cargo" value={formData.cargo} onChange={handleChange} required />
        </div>

        <div className="input-icon">
            <FaDollarSign />
            <input type="number" name="salario" placeholder="Salario" step="0.01" value={formData.salario} onChange={handleChange} required />
        </div>

        <div className="input-icon full">
            <FaCalendarAlt />
            <input type="date" name="fechaIngreso" value={formData.fechaIngreso} onChange={handleChange} required />
        </div>
        </div>

        <div className="dialog-actions">
        <button type="submit" className="dlg-button save">Guardar</button>
        <button type="button" className="dlg-button delete" onClick={cerrarDialogo}>
            Cancelar
        </button>
        </div>
    </form>
    </dialog>
  );
};

export default IngenieroDialog;
