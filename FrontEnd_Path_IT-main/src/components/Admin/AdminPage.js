
import React, { useEffect, useState } from 'react';
import './AdminPage.css';
import { useNavigate } from 'react-router-dom';

const AdminPage = () => {
  const [domaines, setDomaines] = useState([]); 
  const navigate = useNavigate(); // Déplacement ici pour respecter les règles des hooks
   
  useEffect(() => {
    const fetchDomaines = async () => {
      try {
        const response = await fetch('http://localhost:8052/api/admin/domaines/AdminPage',{method:'GET'});
        if (!response.ok) {
          throw new Error('Erreur lors de la récupération des domaines');
        }
        const data = await response.json();
        console.log(data); // Vérifiez ici les données reçues
        setDomaines(data);
      } catch (error) {
        console.error(error.message);
      }
    };
  
    fetchDomaines();
  }, []);
  


  const handleAddDomain = () => {
    navigate('/domaine-form');
  };
  const handleEditDomain = (id) => {
    navigate(`/domaine-edit/${id}`);
  };
  const handleGoToFormations = () => {
    navigate('/formations'); // Naviguer vers la page des formations
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
          <li onClick={handleGoToFormations} className="clickable">
            <span className="icon">📚</span>
            Formations
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
          {domaines.length > 0 ? (
            domaines.map((domaine) => (
              <div className="info-card" key={domaine.id}>
                <h3>{domaine.nom}</h3>
                <p>{domaine.description}</p>
                {domaine.roadmap && (
                  <img
                    src={`/${domaine.roadmap}`}
                    alt={domaine.nom}
                    className="domaine-image"
                  />
                  
                )}
                      <button
                  className="edit-button"
                  onClick={() => handleEditDomain(domaine.id)}
                >
                  Modifier
                </button>
              </div>
              
            ))
          ) : (
            <p>Aucun domaine trouvé</p>
          )}
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
