import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './DomaineForm.css';

const DomaineEdit = () => {
  const { id } = useParams(); // Retrieve the ID from the URL
  const [formData, setFormData] = useState({
    nom: '',
    description: '',
    roadmap: null,
  });
  const [currentRoadmap, setCurrentRoadmap] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  // Fetch domain data on component mount and handle errors
  useEffect(() => {
    const fetchDomaine = async () => {
      try {
        const response = await fetch(`http://localhost:8052/api/admin/domaines/domaine-edit/${id}`);
        const data = await response.json();
        if (response.ok) {
          setFormData({ nom: data.nom, description: data.description, roadmap: null });
          setCurrentRoadmap(data.roadmap);
        } else {
          toast.error('Erreur lors de la récupération du domaine.');
        }
      } catch (error) {
        console.error(error);
        toast.error('Une erreur est survenue lors du chargement du domaine.');
      }
    };

    fetchDomaine();
  }, [id]);

  // Handle form field changes
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  // Handle roadmap file selection
  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setFormData((prevState) => ({
      ...prevState,
      roadmap: file,
    }));
  };

  // Form validation and submission handling
  const handleSubmit = async (e) => {
    e.preventDefault();

    // Basic validation
    if (!formData.nom.trim()) {
      toast.error('Le nom du domaine est requis.');
      return;
    }
    if (!formData.description.trim()) {
      toast.error('La description est requise.');
      return;
    }

    setLoading(true);

    const formDataToSend = new FormData();
    formDataToSend.append('nom', formData.nom);
    formDataToSend.append('description', formData.description);
    if (formData.roadmap) {
      formDataToSend.append('roadmap', formData.roadmap);
    }

    try {
      const response = await fetch(`http://localhost:8052/api/admin/domaines/domaine-edit/${id}`, {
        method: 'PUT',
        body: formDataToSend,
      });

      if (response.ok) {
        toast.success('Domaine modifié avec succès!');
        navigate('/admin'); // Redirect to admin page
      } else {
        toast.error('Erreur lors de la mise à jour du domaine.');
      }
    } catch (error) {
      console.error('Erreur:', error);
      toast.error('Une erreur est survenue. Veuillez réessayer.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="domaine-form-container">
      <ToastContainer position="top-right" autoClose={3000} />
      <div className="form-header">
        <h2>Modifier Domaine</h2>
        <p>Mettez à jour les informations du domaine</p>
      </div>

      <form onSubmit={handleSubmit} className="domaine-form" encType="multipart/form-data">
        <div className="form-content">
          <div className="form-group">
            <label htmlFor="nom">Nom du domaine *</label>
            <input
              type="text"
              id="nom"
              name="nom"
              value={formData.nom}
              onChange={handleChange}
              placeholder="Entrez le nom du domaine"
              required
              className="form-input"
            />
          </div>

          <div className="form-group">
            <label htmlFor="description">Description *</label>
            <textarea
              id="description"
              name="description"
              value={formData.description}
              onChange={handleChange}
              placeholder="Décrivez le domaine"
              required
              className="form-textarea"
              rows="4"
            />
          </div>

          <div className="form-group">
            <label htmlFor="roadmap">Roadmap (Image)</label>
            <input
              type="file"
              id="roadmap"
              name="roadmap"
              accept="image/*"
              onChange={handleFileChange}
              className="form-input"
            />
            {currentRoadmap && (
              <div className="current-roadmap">
                <p>Image actuelle :</p>
                <img
                  src={`http://localhost:8052/static/images/${currentRoadmap}`}
                  alt="Roadmap actuelle"
                  className="roadmap-preview"
                />
              </div>
            )}
          </div>
        </div>

        <div className="form-actions">
          <button
            type="button"
            className="btn-secondary"
            onClick={() => navigate('/admin')}
          >
            Annuler
          </button>
          <button type="submit" className="btn-primary" disabled={loading}>
            {loading ? 'Modification...' : 'Modifier le domaine'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default DomaineEdit;
