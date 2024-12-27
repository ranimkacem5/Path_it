import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './FormationForm.css';
const levels = ['BEGINNER', 'INTERMEDIATE', 'EXPERT'];
const FormationEditForm = () => {
  const { id } = useParams(); // Récupérer l'ID depuis l'URL
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    duration: '',
    level: '',
    link: '',
  });
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  // Récupérer les données de la formation à éditer
  useEffect(() => {
    const fetchFormation = async () => {
      try {
        const response = await fetch(`http://localhost:8052/api/admin/formation/formation-edit/${id}`);
        const data = await response.json();
        if (response.ok) {
          setFormData({
            title: data.title,
            description: data.description,
            duration: data.duration,
            level: data.level,
            link: data.link,
          });
        } else {
          toast.error('Erreur lors de la récupération de la formation.');
        }
      } catch (error) {
        console.error(error);
        toast.error('Une erreur est survenue lors du chargement de la formation.');
      }
    };

    fetchFormation();
  }, [id]);

  // Gérer les modifications des champs de formulaire
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  // Valider et soumettre le formulaire
  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validation basique
    if (!formData.title.trim()) {
      toast.error('Le titre est requis.');
      return;
    }
    if (!formData.description.trim()) {
      toast.error('La description est requise.');
      return;
    }
    if (!formData.duration || isNaN(formData.duration)) {
      toast.error('La durée est requise et doit être un nombre.');
      return;
    }
    if (!formData.level.trim()) {
      toast.error('Le niveau est requis.');
      return;
    }
    if (!formData.link.trim()) {
      toast.error('Le lien est requis.');
      return;
    }

    setLoading(true);

    try {
      const response = await fetch(`http://localhost:8052/api/admin/formation/formation-edit/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        toast.success('Formation modifiée avec succès!');
        navigate('/formations'); // Rediriger vers la page d'administration
      } else {
        toast.error('Erreur lors de la mise à jour de la formation.');
      }
    } catch (error) {
      console.error('Erreur:', error);
      toast.error('Une erreur est survenue. Veuillez réessayer.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="formation-form-container">
      <ToastContainer position="top-right" autoClose={3000} />
      <div className="form-header">
        <h2>Modifier Formation</h2>
        <p>Mettez à jour les informations de la formation</p>
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
              placeholder="Entrez le titre"
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
              placeholder="Entrez la durée (en heures)"
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
              placeholder="Entrez le lien"
              required
              className="form-input"
            />
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
            {loading ? 'Modification...' : 'Modifier la formation'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default FormationEditForm;
