import { useState, useEffect, useRef } from 'react';
import '../styles/Dialog.css';
import { crearDepartamento, actualizarDepartamento } from '../services/DepartamentoService.js';
import Swal from 'sweetalert2';
import { FaBuilding, FaPhone, FaFax, FaCalendarAlt } from 'react-icons/fa';

const DepartamentoDialog = ({ modo, departamento, onSuccess }) => {
  const dialogRef = useRef(null);

  const [formData, setFormData] = useState({
    nombre: '',
    telefono: '',
    fax: '',
    fechaCreacion: '',
    estado: 'ACTIVO'
  });

  useEffect(() => {
    if (modo === 'editar' && departamento) {
      setFormData({
        nombre: departamento.nombre || '',
        telefono: departamento.telefono || '',
        fax: departamento.fax || '',
        fechaCreacion: departamento.fechaCreacion?.slice(0, 10) || '',
        estado: departamento.estado || 'ACTIVO'
      });
    } else {
      setFormData({
        nombre: '',
        telefono: '',
        fax: '',
        fechaCreacion: '',
        estado: 'ACTIVO'
      });
    }
  }, [modo, departamento]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const cerrarDialogo = () => {
    dialogRef.current.close();
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const accion = modo === 'editar'
      ? actualizarDepartamento(departamento.id, formData)
      : crearDepartamento(formData);

    accion.then(() => {
      Swal.fire({
        toast: true,
        position: 'top-end',
        icon: 'success',
        title: `Departamento ${modo === 'editar' ? 'actualizado' : 'creado'} correctamente`,
        showConfirmButton: false,
        timer: 2500
      });
      cerrarDialogo();
      onSuccess();
    }).catch(() => {
      Swal.fire({
        toast: true,
        position: 'top-end',
        icon: 'error',
        title: `No se pudo ${modo === 'editar' ? 'actualizar' : 'crear'} el departamento`,
        showConfirmButton: false,
        timer: 2500
      });
    });
  };

  return (
    <dialog ref={dialogRef} id="dialogDepartamento" className="modal-dialog">
      <form className="modal-form" onSubmit={handleSubmit}>
        <div className="dialog-header">
          <FaBuilding />
          <h3>{modo === 'editar' ? 'Editar Departamento' : 'Agregar Departamento'}</h3>
        </div>

        <div className="form-grid">
          <div className="input-icon"><FaBuilding /><input name="nombre" placeholder="Nombre" value={formData.nombre} onChange={handleChange} required /></div>
          <div className="input-icon"><FaPhone /><input name="telefono" placeholder="Teléfono" value={formData.telefono} onChange={handleChange} required /></div>
          <div className="input-icon"><FaFax /><input name="fax" placeholder="Fax" value={formData.fax} onChange={handleChange} required /></div>
          <div className="input-icon"><FaCalendarAlt /><input type="date" name="fechaCreacion" value={formData.fechaCreacion} onChange={handleChange} required /></div>
          <div className="input-icon full">
            <select name="estado" value={formData.estado} onChange={handleChange}>
              <option value="ACTIVO">ACTIVO</option>
              <option value="INACTIVO">INACTIVO</option>
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

export default DepartamentoDialog;
