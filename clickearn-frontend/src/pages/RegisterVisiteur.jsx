import React, { useState } from "react";
import api from "../api/api";
import { useNavigate } from "react-router-dom";
import "./RegisterVisiteur.css"; // Import du fichier CSS

export default function RegisterVisiteur() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    email: "",
    motDePasse: "",
    nom: "",
    prenom: "",
    bio: "",
    avatarUrl: "",
    phoneNumber: "",
    interets: ""
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
      const res = await api.post("/auth/register/visiteur", form);
      setMessage(res.data.message || "Inscription réussie");
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
          <h2>Rejoindre ClickEarn</h2>
          <p>Créez votre compte et commencez votre parcours</p>
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
          <div className="form-section">
            <h3>Informations personnelles</h3>
            <div className="form-row">
              <div className="form-group">
                <label htmlFor="prenom">Prénom</label>
                <input
                  id="prenom"
                  name="prenom"
                  type="text"
                  placeholder="Votre prénom"
                  value={form.prenom}
                  onChange={handleChange}
                />
              </div>

              <div className="form-group">
                <label htmlFor="nom">Nom</label>
                <input
                  id="nom"
                  name="nom"
                  type="text"
                  placeholder="Votre nom"
                  value={form.nom}
                  onChange={handleChange}
                />
              </div>
            </div>

            <div className="form-row">
              <div className="form-group">
                <label htmlFor="email">Adresse email *</label>
                <input
                  id="email"
                  name="email"
                  type="email"
                  placeholder="votre@email.com"
                  value={form.email}
                  onChange={handleChange}
                  required
                />
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
          </div>

          <div className="form-section">
            <h3>Sécurité du compte</h3>
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
              <div className="password-strength">
                <div className="strength-bar">
                  <div className="strength-fill"></div>
                </div>
                <div className="password-hint">
                  Minimum 8 caractères avec chiffres et lettres
                </div>
              </div>
            </div>
          </div>

          <div className="form-section">
            <h3>Profil personnel</h3>
            <div className="form-group">
              <label htmlFor="avatarUrl">Photo de profil (URL)</label>
              <input
                id="avatarUrl"
                name="avatarUrl"
                type="url"
                placeholder="https://example.com/photo.jpg"
                value={form.avatarUrl}
                onChange={handleChange}
              />
              <div className="avatar-preview">
                {form.avatarUrl && (
                  <img src={form.avatarUrl} alt="Aperçu" onError={(e) => e.target.style.display = 'none'} />
                )}
              </div>
            </div>

            <div className="form-group">
              <label htmlFor="bio">Bio</label>
              <textarea
                id="bio"
                name="bio"
                placeholder="Parlez-nous un peu de vous..."
                value={form.bio}
                onChange={handleChange}
                rows="3"
                maxLength="200"
              />
              <div className="char-count">
                {form.bio.length}/200 caractères
              </div>
            </div>

            <div className="form-group">
              <label htmlFor="interets">Centres d'intérêt</label>
              <input
                id="interets"
                name="interets"
                type="text"
                placeholder="Ex: Technologie, Sport, Musique, Art..."
                value={form.interets}
                onChange={handleChange}
              />
              <div className="interests-hint">
                Séparez vos intérêts par des virgules
              </div>
            </div>
          </div>

          <div className="form-options">
            <div className="terms-agreement">
              <input type="checkbox" id="terms" required />
              <label htmlFor="terms">
                J'accepte les <a href="/terms">conditions d'utilisation</a> et la <a href="/privacy">politique de confidentialité</a>
              </label>
            </div>
            
            <div className="newsletter-optin">
              <input type="checkbox" id="newsletter" defaultChecked />
              <label htmlFor="newsletter">
                Recevoir les actualités et offres spéciales de ClickEarn
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
              "Rejoindre ClickEarn"
            )}
          </button>
        </form>

        <div className="register-footer">
          <p>
            Vous avez déjà un compte ? <a href="/login">Se connecter</a>
          </p>
          <p className="business-redirect">
            <a href="/register/entreprise">Créer un compte entreprise</a>
          </p>
        </div>
      </div>
    </div>
  );
}