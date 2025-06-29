// src/components/IngenieroDialog.jsx
import { crearIngeniero, actualizarIngeniero } from '../services/IngenieroService.js';
import React, { useState, useEffect } from 'react';
import Swal from 'sweetalert2';

const IngenieroDialog = ({ modo, ingeniero, onSuccess }) => {
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

  const handleSubmit = (e) => {
    e.preventDefault();
    const accion = modo === 'editar'
      ? actualizarIngeniero(ingeniero.iding, formData)
      : crearIngeniero(formData);

    accion
      .then(() => {
        Swal.fire('Éxito', `Ingeniero ${modo === 'editar' ? 'actualizado' : 'creado'} correctamente`, 'success');
        document.getElementById('dialogIngeniero').close();
        onSuccess();
      })
      .catch(() => {
        Swal.fire('Error', `No se pudo ${modo === 'editar' ? 'actualizar' : 'crear'} el ingeniero`, 'error');
      });
  };

  return (
    <dialog id="dialogIngeniero" className="modal-dialog">
      <form method="dialog" className="modal-form" onSubmit={handleSubmit}>
        <h3>{modo === 'editar' ? 'Editar Ingeniero' : 'Agregar Ingeniero'}</h3>

        <input name="nombre" placeholder="Nombre" value={formData.nombre} onChange={handleChange} required />
        <input name="apellido" placeholder="Apellido" value={formData.apellido} onChange={handleChange} required />
        <input type="email" name="email" placeholder="Email" value={formData.email} onChange={handleChange} required />
        <input name="especialidad" placeholder="Especialidad" value={formData.especialidad} onChange={handleChange} required />
        <input name="cargo" placeholder="Cargo" value={formData.cargo} onChange={handleChange} required />
        <input type="number" name="salario" placeholder="Salario" step="0.01" value={formData.salario} onChange={handleChange} required />
        <input type="date" name="fechaIngreso" value={formData.fechaIngreso} onChange={handleChange} required />

        <div className="dialog-actions">
          <button type="submit" className="icon-button edit">Guardar</button>
          <button type="button" className="icon-button delete" onClick={() => document.getElementById('dialogIngeniero').close()}>
            Cancelar
          </button>
        </div>
      </form>
    </dialog>
  );
};

export default IngenieroDialog;
