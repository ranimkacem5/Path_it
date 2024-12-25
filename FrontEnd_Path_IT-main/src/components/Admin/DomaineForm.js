import React, { useState } from 'react';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './DomaineForm.css';

const DomaineForm = () => {
  const [formData, setFormData] = useState({
    nom: '',
    description: '',
    roadmap: null,
  });
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setFormData((prevState) => ({
      ...prevState,
      roadmap: file,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Frontend validation
    if (!formData.nom.trim()) {
      toast.error('Le nom du domaine est requis.');
      return;
    }
    if (!formData.description.trim()) {
      toast.error('La description est requise.');
      return;
    }
    if (!formData.roadmap) {
      toast.error('Le fichier roadmap est requis.');
      return;
    }

    setLoading(true);

    // Préparation des données pour l'envoi
    const formDataToSend = new FormData();
    formDataToSend.append('nom', formData.nom);
    formDataToSend.append('description', formData.description);
    formDataToSend.append('roadmap', formData.roadmap);

    try {
      const response = await fetch('http://localhost:8052/api/admin/domaines/domaine-form', {
        method: 'POST',
        body: formDataToSend,
      });

      if (response.ok) {
        toast.success('Domaine créé avec succès!');
        setFormData({
          nom: '',
          description: '',
          roadmap: null,
        });
      } else {
        const errorMessage = response.status === 409
          ? 'Un domaine avec ce nom existe déjà.'
          : 'Erreur lors de la création du domaine.';
        toast.error(errorMessage);
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
        <h2>Nouveau Domaine</h2>
        <p>Créer un nouveau domaine</p>
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
            <label htmlFor="roadmap">Roadmap (Image) *</label>
            <input
              type="file"
              id="roadmap"
              name="roadmap"
              accept="image/*"
              onChange={handleFileChange}
              required
              className="form-input"
            />
          </div>
        </div>

        <div className="form-actions">
          <button
            type="button"
            className="btn-secondary"
            onClick={() => setFormData({ nom: '', description: '', roadmap: null })}
          >
            Annuler
          </button>
          <button type="submit" className="btn-primary" disabled={loading}>
            {loading ? 'Création...' : 'Créer le domaine'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default DomaineForm;
