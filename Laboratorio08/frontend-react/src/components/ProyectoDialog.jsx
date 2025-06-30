import { useState, useEffect, useRef } from 'react';
import { crearProyecto, actualizarProyecto } from '../services/ProyectoService.js';
import { obtenerDepartamentosDTO } from '../services/DepartamentoService.js';
import { obtenerIngenieros } from '../services/IngenieroService.js';
import { FaPlus, FaProjectDiagram, FaBuilding, FaUserCog, FaFileAlt, FaCalendarAlt, FaDollarSign, FaLayerGroup } from 'react-icons/fa';
import Swal from 'sweetalert2';
import '../styles/Dialog.css';

const ProyectoDialog = ({ modo, proyecto, onSuccess }) => {
  const dialogRef = useRef(null);

  const [formData, setFormData] = useState({
    nombre: '', descripcion: '', fecInicio: '', fecTermino: '',
    presupuesto: '', estado: '', idDepartamento: '', idIngenieros: []
  });

  const [departamentos, setDepartamentos] = useState([]);
  const [ingenieros, setIngenieros] = useState([]);

  useEffect(() => {
    obtenerDepartamentosDTO().then(setDepartamentos);
    obtenerIngenieros().then(res => setIngenieros(res.data));
  }, []);

  useEffect(() => {
    if (proyecto && departamentos.length > 0 && ingenieros.length > 0) {
      const dep = departamentos.find(d => d.nombre === proyecto.departamentoNombre);
      const idDepartamento = dep ? dep.id.toString() : '';
      const idIngenieros = proyecto.ingenieros?.map(i => i.id) || [];

      setFormData({
        nombre: proyecto.nombre || '',
        descripcion: proyecto.descripcion || '',
        fecInicio: proyecto.fecInicio || '',
        fecTermino: proyecto.fecTermino || '',
        presupuesto: proyecto.presupuesto || '',
        estado: proyecto.estado || '',
        idDepartamento,
        idIngenieros
      });
    } else if (modo === 'agregar') {
      setFormData({
        nombre: '', descripcion: '', fecInicio: '', fecTermino: '',
        presupuesto: '', estado: '', idDepartamento: '', idIngenieros: []
      });
    }
  }, [proyecto, modo, departamentos, ingenieros]);

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
      presupuesto: parseFloat(formData.presupuesto),
      idDepartamento: parseInt(formData.idDepartamento),
      idIngenieros: formData.idIngenieros.map(Number)
    };

    const accion = modo === 'editar'
      ? actualizarProyecto(proyecto.id, payload)
      : crearProyecto(payload);

    accion.then(() => {
      Swal.fire({ toast: true, position: 'top-end', icon: 'success', title: `Proyecto ${modo === 'editar' ? 'actualizado' : 'creado'} correctamente`, showConfirmButton: false, timer: 2500, timerProgressBar: true });
      cerrarDialogo();
      onSuccess();
    }).catch(() => {
      Swal.fire({ toast: true, position: 'top-end', icon: 'error', title: `No se pudo ${modo === 'editar' ? 'actualizar' : 'crear'} el proyecto`, showConfirmButton: false, timer: 2500, timerProgressBar: true });
    });
  };

  const agregarIngeniero = (id) => {
    if (!formData.idIngenieros.includes(id)) {
      setFormData(prev => ({ ...prev, idIngenieros: [...prev.idIngenieros, id] }));
    }
  };

  const eliminarIngeniero = (id) => {
    setFormData(prev => ({
      ...prev,
      idIngenieros: prev.idIngenieros.filter(pid => pid !== id)
    }));
  };

  return (
    <dialog ref={dialogRef} id="dialogProyecto" className="modal-dialog">
      <form method="dialog" className="modal-form" onSubmit={handleSubmit}>
        <div className="dialog-header">
          <FaPlus />
          <h3 className="dialog-tittle">{modo === 'editar' ? 'Editar Proyecto' : 'Agregar Proyecto'}</h3>
        </div>

        <div className="form-grid">
          <div className="input-icon"><FaProjectDiagram /><input name="nombre" placeholder="Nombre" value={formData.nombre} onChange={handleChange} required /></div>
          <div className="input-icon"><FaFileAlt /><input name="descripcion" placeholder="Descripción" value={formData.descripcion} onChange={handleChange} required /></div>
          <div className="input-icon"><FaCalendarAlt /><input type="date" name="fecInicio" value={formData.fecInicio} onChange={handleChange} required /></div>
          <div className="input-icon"><FaCalendarAlt /><input type="date" name="fecTermino" value={formData.fecTermino} onChange={handleChange} required /></div>
          <div className="input-icon"><FaDollarSign /><input type="number" step="0.01" name="presupuesto" placeholder="Presupuesto" value={formData.presupuesto} onChange={handleChange} required /></div>
          <div className="input-icon"><FaLayerGroup />
            <select name="estado" value={formData.estado} onChange={handleChange} required>
              <option value="">Estado</option>
              <option value="PLANIFICADO">Planificado</option>
              <option value="EN_CURSO">En curso</option>
              <option value="FINALIZADO">Finalizado</option>
              <option value="CANCELADO">Cancelado</option>
            </select>
          </div>
          <div className="input-icon full"><FaBuilding />
            <select name="idDepartamento" value={formData.idDepartamento} onChange={handleChange} required>
              <option value="">Seleccione Departamento</option>
              {departamentos.map(dep => (
                <option key={dep.id} value={dep.id}>{dep.nombre}</option>
              ))}
            </select>
          </div>
          <div className="input-icon full">
            <FaUserCog />
            <div className="project-select-group">
              <div className="project-selector">
                <select onChange={(e) => {
                  const selectedId = parseInt(e.target.value);
                  if (!isNaN(selectedId)) {
                    agregarIngeniero(selectedId);
                    e.target.value = '';
                  }
                }}>
                  <option value="">Seleccionar Ingeniero</option>
                  {ingenieros
                    .filter(i => !formData.idIngenieros.includes(i.idIng))
                    .map(i => (
                      <option key={i.idIng} value={i.idIng}>{i.nombre} {i.apellido}</option>
                    ))}
                </select>
              </div>
              <div className="project-tags">
                {formData.idIngenieros.map(id => {
                  const ing = ingenieros.find(i => i.idIng === id);
                  if (!ing) return null;
                  return (
                    <div key={id} className="project-tag">
                      {ing.nombre} {ing.apellido}
                      <span className="remove-tag" onClick={() => eliminarIngeniero(id)}>×</span>
                    </div>
                  );
                })}
              </div>
            </div>
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

export default ProyectoDialog;
