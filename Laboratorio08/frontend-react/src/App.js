// src/App.js
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Navigate } from 'react-router-dom';

import Sidebar from './components/Sidebar';
import IngenieroPage from './pages/IngenieroPage';
import DepartamentoPage from './pages/DepartamentoPage';
import ProyectoPage from './pages/ProyectoPage';

// styles
import './styles/App.css';

function App() {
  return (
    <Router>
      <div className="app-container">
        <Sidebar />
        <div className="app-content">
          <Routes>
            <Route path="/" element={<Navigate to="/ingenieros" replace />} />
            <Route path="/ingenieros" element={<IngenieroPage />} />
            <Route path="/departamentos" element={<DepartamentoPage />} />
            <Route path="/proyectos" element={<ProyectoPage />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
