import axios from 'axios';

const API_URL = 'http://localhost:8080/api/ingenieros';

// Obtener todos los ingenieros
export const obtenerIngenieros = () => {
  return axios.get(API_URL);
};

// Obtener un ingeniero por ID
export const obtenerIngenieroPorId = (id) => {
  return axios.get(`${API_URL}/${id}`);
};

// Crear un nuevo ingeniero
export const crearIngeniero = (ingeniero) => {
  return axios.post(API_URL, ingeniero);
};

// Actualizar un ingeniero existente
export const actualizarIngeniero = (id, ingeniero) => {
  return axios.put(`${API_URL}/${id}`, ingeniero);
};

// Eliminar un ingeniero
export const eliminarIngeniero = (id) => {
  return axios.delete(`${API_URL}/${id}`);
};
