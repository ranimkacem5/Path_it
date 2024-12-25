import React from 'react';
import './AdminPage.css';
import { useNavigate } from 'react-router-dom';

const AdminPage = () => {
  const navigate = useNavigate(); // Déplacement ici pour respecter les règles des hooks

  const handleAddDomain = () => {
    navigate('/domaine-form');
  };

  return (
    <div className="admin-container">
      {/* Sidebar */}
      <div className="sidebar">
        <div className="sidebar-header">
          <h1>Admin Dashboard</h1>
        </div>
        <ul>
          <li className="active">
            <span className="icon">🏠</span>
            Accueil
          </li>
          <li>
            <span className="icon">👥</span>
            Utilisateurs
          </li>
          <li>
            <span className="icon">📊</span>
            Analytics
          </li>
          <li>
            <span className="icon">📚</span>
            Domaines
          </li>
          <li>
            <span className="icon">⚙️</span>
            Paramètres
          </li>
        </ul>
      </div>

      {/* Main Content */}
      <div className="main-content">
        <div className="dashboard-header">
          <h1>Welcome Back, Admin!</h1>
          <button>Check Now</button>
        </div>
        <button className="add-domain-btn" onClick={handleAddDomain}>
          <i className="fas fa-plus"></i> Ajouter domaine
        </button>
        <div className="info-cards">
          <div className="info-card"></div>
          <div className="info-card"></div>
          <div className="info-card"></div>
        </div>
      </div>

      {/* Upcoming Check-ups */}
      <div className="checkups">
        <div className="calendar">
          <h2>Upcoming Check-ups</h2>
          <p>December 2024</p>
        </div>
      </div>
    </div>
  );
};

export default AdminPage;
