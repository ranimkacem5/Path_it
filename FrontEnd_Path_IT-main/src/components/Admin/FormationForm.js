import React, { useState } from 'react';
import { toast, ToastContainer } from 'react-toastify';
import { useNavigate } from 'react-router-dom'; // Import du hook useNavigate
import 'react-toastify/dist/ReactToastify.css';
import './FormationForm.css';

const FormationForm = () => {
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    duration: '',
    level: '',
    link: '',
  });
  const [loading, setLoading] = useState(false);

  const levels = ['BEGINNER', 'INTERMEDIATE', 'EXPERT']; // Niveaux définis statiquement

  const navigate = useNavigate(); // Initialisation du hook useNavigate

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validation frontend
    if (!formData.title.trim()) {
      toast.error('Le titre est requis.');
      return;
    }
    if (!formData.description.trim()) {
      toast.error('La description est requise.');
      return;
    }
    if (!formData.duration || formData.duration <= 0) {
      toast.error('La durée doit être supérieure à 0.');
      return;
    }
    if (!formData.level) {
      toast.error('Le niveau est requis.');
      return;
    }
    if (!formData.link.trim()) {
      toast.error('Le lien est requis.');
      return;
    }

    setLoading(true);

    try {
      const response = await fetch('http://localhost:8052/api/admin/formation/formation-form', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          ...formData,
          duration: parseFloat(formData.duration),
        }),
      });

      if (response.ok) {
        toast.success('Formation créée avec succès!');
        setFormData({
          title: '',
          description: '',
          duration: '',
          level: '',
          link: '',
        });

        // Redirection vers la page admin
        setTimeout(() => navigate('/admin'), 3000); // Redirection après 3 secondes
      } else {
        const errorMessage = await response.text();
        toast.error(`Erreur: ${errorMessage}`);
      }
    } catch (error) {
      toast.error('Une erreur est survenue. Veuillez réessayer.');
      console.error('Erreur:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="formation-form-container">
      <ToastContainer position="top-right" autoClose={3000} />
      <div className="form-header">
        <h2>Nouvelle Formation</h2>
        <p>Créer une nouvelle formation</p>
      </div>

      <form onSubmit={handleSubmit} className="formation-form">
        <div className="form-content">
          <div className="form-group">
            <label htmlFor="title">Titre *</label>
            <input
              type="text"
              id="title"
              name="title"
              value={formData.title}
              onChange={handleChange}
              placeholder="Entrez le titre de la formation"
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
              placeholder="Décrivez la formation"
              required
              className="form-textarea"
              rows="4"
            />
          </div>

          <div className="form-group">
            <label htmlFor="duration">Durée (en heures) *</label>
            <input
              type="number"
              id="duration"
              name="duration"
              value={formData.duration}
              onChange={handleChange}
              placeholder="Entrez la durée de la formation"
              required
              className="form-input"
            />
          </div>

          <div className="form-group">
            <label htmlFor="level">Niveau *</label>
            <select
              id="level"
              name="level"
              value={formData.level}
              onChange={handleChange}
              required
              className="form-select"
            >
              <option value="">-- Sélectionnez un niveau --</option>
              {levels.map((lvl) => (
                <option key={lvl} value={lvl}>
                  {lvl}
                </option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="link">Lien *</label>
            <input
              type="url"
              id="link"
              name="link"
              value={formData.link}
              onChange={handleChange}
              placeholder="Entrez le lien vers la formation"
              required
              className="form-input"
            />
          </div>
        </div>

        <div className="form-actions">
          <button
            type="button"
            className="btn-secondary"
            onClick={() =>
              setFormData({
                title: '',
                description: '',
                duration: '',
                level: '',
                link: '',
              })
            }
          >
            Annuler
          </button>
          <button type="submit" className="btn-primary" disabled={loading}>
            {loading ? 'Création...' : 'Créer la formation'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default FormationForm;
