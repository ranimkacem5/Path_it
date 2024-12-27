import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './FormationsPage.css';

const FormationsPage = () => {
  const [formations, setFormations] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchFormations = async () => {
      try {
        const response = await fetch('http://localhost:8052/api/admin/formation/formations', { method: 'GET' });
        if (!response.ok) {
          throw new Error(`Erreur HTTP : ${response.status}`);
        }
        const data = await response.json();
        console.log('Données reçues :', data);
        setFormations(data);
      } catch (error) {
        console.error('Erreur lors de la récupération des formations :', error);
      }
    };

    fetchFormations();
  }, []);

  const handleAddFormation = () => {
    navigate('/formation-form');
  };

  const handleEditFormation = (id) => {
    navigate(`/formation-edit/${id}`);
  };

  const handleDeleteFormation = async (id) => {
    if (window.confirm('Êtes-vous sûr de vouloir supprimer cette formation ?')) {
      try {
        const response = await fetch(`http://localhost:8052/api/admin/formation/delete/${id}`, { method: 'DELETE' });
        if (response.ok) {
          alert('Formation supprimée avec succès.');
          setFormations((prevFormations) => prevFormations.filter((formation) => formation.id !== id));
        } else {
          throw new Error(`Erreur HTTP : ${response.status}`);
        }
      } catch (error) {
        console.error('Erreur lors de la suppression de la formation :', error);
        alert('Impossible de supprimer la formation. Veuillez réessayer.');
      }
    }
  };

  return (
    <div className="formations-container">
      <h1>Liste des Formations</h1>
      <button className="add-formation-btn" onClick={handleAddFormation}>
        Ajouter Formation
      </button>
      <table className="formations-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Titre</th>
            <th>Description</th>
            <th>Durée</th>
            <th>Niveau</th>
            <th>Lien</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {formations.length > 0 ? (
            formations.map((formation) => (
              <tr key={formation.id}>
                <td>{formation.id}</td>
                <td>{formation.title}</td>
                <td>{formation.description}</td>
                <td>{formation.duration}</td>
                <td>{formation.level}</td>
                <td>
                  <a href={formation.link} target="_blank" rel="noopener noreferrer">
                    Lien
                  </a>
                </td>
                <td>
                  <button className="edit-btn" onClick={() => handleEditFormation(formation.id)}>
                    Modifier
                  </button>
                  <button className="delete-btn" onClick={() => handleDeleteFormation(formation.id)}>
                    Supprimer
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="7">Aucune formation trouvée</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default FormationsPage;
