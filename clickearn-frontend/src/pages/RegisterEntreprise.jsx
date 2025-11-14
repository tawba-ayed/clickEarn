import React, { useState } from "react";
import api from "../api/api";
import { useNavigate } from "react-router-dom";
import "./RegisterEntreprise.css"; // Import du fichier CSS

export default function RegisterEntreprise() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    email: "",
    motDePasse: "",
    nomEntreprise: "",
    description: "",
    websitelogoUrl: "",
    phoneNumber: ""
  });
  const [message, setMessage] = useState(null);
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  const handleChange = e => setForm({...form, [e.target.name]: e.target.value});

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setIsLoading(true);
    
    try {
      const res = await api.post("/auth/register/entreprise", form);
      setMessage(res.data.message || "Inscription entreprise réussie");
      setTimeout(() => navigate("/login"), 1200);
    } catch (err) {
      console.error(err);
      setError(err.response?.data?.message || "Erreur lors de l'inscription");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="register-container">
      <div className="register-card">
        <div className="register-header">
          <div className="logo">
            <span className="logo-icon">💎</span>
            <h1>ClickEarn</h1>
          </div>
          <h2>Inscription Entreprise</h2>
          <p>Créez votre compte entreprise et commencez à développer votre présence</p>
        </div>

        {message && (
          <div className="success-message">
            <span className="success-icon">✅</span>
            {message}
          </div>
        )}

        {error && (
          <div className="error-message">
            <span className="error-icon">⚠️</span>
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="register-form">
          <div className="form-row">
            <div className="form-group">
              <label htmlFor="nomEntreprise">Nom de l'entreprise *</label>
              <input
                id="nomEntreprise"
                name="nomEntreprise"
                type="text"
                placeholder="Nom de votre entreprise"
                value={form.nomEntreprise}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="email">Email professionnel *</label>
              <input
                id="email"
                name="email"
                type="email"
                placeholder="contact@entreprise.com"
                value={form.email}
                onChange={handleChange}
                required
              />
            </div>
          </div>

          <div className="form-row">
            <div className="form-group">
              <label htmlFor="motDePasse">Mot de passe *</label>
              <input
                id="motDePasse"
                name="motDePasse"
                type="password"
                placeholder="Créez un mot de passe sécurisé"
                value={form.motDePasse}
                onChange={handleChange}
                required
              />
              <div className="password-hint">
                Minimum 8 caractères avec chiffres et lettres
              </div>
            </div>

            <div className="form-group">
              <label htmlFor="phoneNumber">Téléphone</label>
              <input
                id="phoneNumber"
                name="phoneNumber"
                type="tel"
                placeholder="+33 1 23 45 67 89"
                value={form.phoneNumber}
                onChange={handleChange}
              />
            </div>
          </div>

          <div className="form-group">
            <label htmlFor="description">Description de l'entreprise</label>
            <textarea
              id="description"
              name="description"
              placeholder="Décrivez brièvement votre entreprise, vos activités..."
              value={form.description}
              onChange={handleChange}
              rows="3"
            />
          </div>

          <div className="form-group">
            <label htmlFor="websitelogoUrl">Logo ou Site Web (URL)</label>
            <input
              id="websitelogoUrl"
              name="websitelogoUrl"
              type="url"
              placeholder="https://www.votre-entreprise.com/logo.png"
              value={form.websitelogoUrl}
              onChange={handleChange}
            />
            <div className="url-hint">
              Lien vers votre logo ou site web
            </div>
          </div>

          <div className="form-options">
            <div className="terms-agreement">
              <input type="checkbox" id="terms" required />
              <label htmlFor="terms">
                J'accepte les <a href="/terms">conditions d'utilisation</a> et la <a href="/privacy">politique de confidentialité</a>
              </label>
            </div>
          </div>

          <button 
            type="submit" 
            className={`submit-button ${isLoading ? 'loading' : ''}`}
            disabled={isLoading}
          >
            {isLoading ? (
              <>
                <span className="spinner"></span>
                Création du compte...
              </>
            ) : (
              "Créer mon compte entreprise"
            )}
          </button>
        </form>

        <div className="register-footer">
          <p>
            Vous avez déjà un compte ? <a href="/login">Se connecter</a>
          </p>
          <p className="login-redirect">
            <a href="/register/visiteur">Créer un compte utilisateur standard</a>
          </p>
        </div>
      </div>
    </div>
  );
}