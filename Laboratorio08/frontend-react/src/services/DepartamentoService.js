import axios from 'axios';

const API_URL = 'http://localhost:8080/api/departamentos';
const API_URL_DTO = 'http://localhost:8080/api/departamentos/dto';

export const obtenerDepartamentos = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

export const obtenerDepartamentosDTO = async () => {
  const response = await axios.get(API_URL_DTO);
  return response.data;
};

export const crearDepartamento = async (departamento) => {
  const response = await axios.post(API_URL, departamento);
  return response.data;
};

export const actualizarDepartamento = async (id, departamento) => {
  const response = await axios.put(`${API_URL}/${id}`, departamento);
  return response.data;
};

export const eliminarDepartamento = async (id) => {
  await axios.delete(`${API_URL}/${id}`);
};

export const obtenerDepartamentoPorId = async (id) => {
  const response = await axios.get(`${API_URL}/${id}`);
  return response.data;
};