// src/components/Sidebar.jsx
import { NavLink } from 'react-router-dom';
import { FaUserTie, FaBuilding, FaProjectDiagram } from 'react-icons/fa';
import '../styles/Sidebar.css';

const Sidebar = () => {
  return (
    <div className="sidebar">
      <nav>
        <div className="sidebar-item">
          <NavLink to="/ingenieros" className={({ isActive }) => isActive ? 'sidebar-link active' : 'sidebar-link'}>
            <FaUserTie size={24} />
          </NavLink>
        </div>
        <div className="sidebar-separator"></div>

        <div className="sidebar-item">
          <NavLink to="/departamentos" className={({ isActive }) => isActive ? 'sidebar-link active' : 'sidebar-link'}>
            <FaBuilding size={24} />
          </NavLink>
        </div>
        <div className="sidebar-separator"></div>

        <div className="sidebar-item">
          <NavLink to="/proyectos" className={({ isActive }) => isActive ? 'sidebar-link active' : 'sidebar-link'}>
            <FaProjectDiagram size={24} />
          </NavLink>
        </div>
      </nav>
    </div>
  );
};

export default Sidebar;