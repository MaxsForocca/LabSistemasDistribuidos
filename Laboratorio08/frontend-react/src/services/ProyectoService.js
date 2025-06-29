import axios from 'axios';

const API_URL = 'http://localhost:8080/api/proyectos';
const API_URL_DTO = 'http://localhost:8080/api/proyectos/dto';

export const obtenerProyectos = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

export const obtenerProyectosDTO = async () => {
  const response = await axios.get(API_URL_DTO);
  return response.data;
};

export const crearProyecto = async (proyecto) => {
  const response = await axios.post(API_URL, proyecto);
  return response.data;
};

export const actualizarProyecto = async (id, proyecto) => {
  const response = await axios.put(`${API_URL}/${id}`, proyecto);
  return response.data;
};

export const eliminarProyecto = async (id) => {
  await axios.delete(`${API_URL}/${id}`);
};

export const obtenerProyectoPorId = async (id) => {
  const response = await axios.get(`${API_URL}/${id}`);
  return response.data;
};
