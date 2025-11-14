import React, { useState } from "react";
import api from "../api/api";
import useAuth from "../auth/useAuth";
import { useNavigate } from "react-router-dom";
import "./Login.css"; // Import du fichier CSS

export default function Login() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({ email: "", motDePasse: "" });
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  const handleChange = e => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setIsLoading(true);
     
    try {
      const res = await api.post("/auth/login", form);

      const token = res.data.accessToken;
      login({ email: form.email }, token);
      navigate("/dashboard");
    } catch (err) {
      console.error(err);
      setError(err.response?.data?.message || "Identifiants invalides");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <div className="login-header">
          <div className="logo">
            <span className="logo-icon">💎</span>
            <h1>ClickEarn</h1>
          </div>
          <h2>Connexion à votre compte</h2>
          <p>Accédez à votre tableau de bord et commencez à gagner</p>
        </div>

        {error && (
          <div className="error-message">
            <span className="error-icon">⚠️</span>
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="login-form">
          <div className="form-group">
            <label htmlFor="email">Adresse email</label>
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
            <label htmlFor="motDePasse">Mot de passe</label>
            <input
              id="motDePasse"
              name="motDePasse"
              type="password"
              placeholder="Votre mot de passe"
              value={form.motDePasse}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-options">
            <div className="remember-me">
              <input type="checkbox" id="remember" />
              <label htmlFor="remember">Se souvenir de moi</label>
            </div>
            <a href="/forgot-password" className="forgot-password">
              Mot de passe oublié ?
            </a>
          </div>

          <button 
            type="submit" 
            className={`submit-button ${isLoading ? 'loading' : ''}`}
            disabled={isLoading}
          >
            {isLoading ? (
              <>
                <span className="spinner"></span>
                Connexion en cours...
              </>
            ) : (
              "Se connecter"
            )}
          </button>
        </form>

        <div className="login-footer">
          <p>
            Pas encore de compte ? <a href="/register/visiteur">Créer un compte</a>
          </p>
        </div>
      </div>
    </div>
  );
}