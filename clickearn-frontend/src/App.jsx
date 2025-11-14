import React from "react";
import { BrowserRouter, Routes, Route, Link, useLocation } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import RegisterVisiteur from "./pages/RegisterVisiteur";
import RegisterEntreprise from "./pages/RegisterEntreprise";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import PrivateRoute from "./routes/PrivateRoute";
import "./App.css";
import useAuth from "./auth/useAuth";


// Composant de navigation séparé pour utiliser useLocation
function Navigation() {
 const location = useLocation();
  const { isAuthenticated, logout } = useAuth();

  return (
    <nav className="main-nav">
      <div className="nav-container">
        <div className="nav-brand">
          <Link to="/" className="brand-link">
            <span className="logo-icon">💎</span>
            <span className="brand-text">ClickEarn</span>
          </Link>
        </div>

        <div className="nav-links">
          {!isAuthenticated ? (
            <>
              <Link 
                to="/register/visiteur" 
                className={`nav-link ${location.pathname === '/register/visiteur' ? 'active' : ''}`}
              >
                <span className="nav-icon">👤</span>
                Inscription Visiteur
              </Link>
              <Link 
                to="/register/entreprise" 
                className={`nav-link ${location.pathname === '/register/entreprise' ? 'active' : ''}`}
              >
                <span className="nav-icon">🏢</span>
                Inscription Entreprise
              </Link>
              <Link 
                to="/login" 
                className={`nav-link ${location.pathname === '/login' ? 'active' : ''}`}
              >
                <span className="nav-icon">🔐</span>
                Connexion
              </Link>
            </>
          ) : (
            <>
              <Link 
                to="/dashboard" 
                className={`nav-link dashboard-link ${location.pathname === '/dashboard' ? 'active' : ''}`}
              >
                <span className="nav-icon">📊</span>
                Tableau de bord
              </Link>
              <button 
                className="nav-link logout-button"
                onClick={logout}
              >
                <span className="nav-icon">🚪</span>
                Déconnexion
              </button>
            </>
          )}
        </div>
      </div>
    </nav>
  );
}

// Composant de page d'accueil
function HomePage() {
  return (
    <div className="home-container">
      <div className="hero-section">
        <div className="hero-content">
          <div className="hero-badge">
            🚀 Plateforme de Marketing Performance
          </div>
          <h1 className="hero-title">
            Maximisez vos <span className="highlight">revenus</span> avec ClickEarn
          </h1>
          <p className="hero-description">
            Rejoignez des milliers d'entreprises et de visiteurs qui génèrent des revenus 
            grâce à nos solutions de marketing performance innovantes.
          </p>
          <div className="hero-actions">
            <Link to="/register/entreprise" className="btn btn-primary">
              Commencer en tant qu'Entreprise
            </Link>
            <Link to="/register/visiteur" className="btn btn-secondary">
              Rejoindre en tant que Visiteur
            </Link>
          </div>
          <div className="hero-stats">
            <div className="stat">
              <div className="stat-number">10K+</div>
              <div className="stat-label">Utilisateurs actifs</div>
            </div>
            <div className="stat">
              <div className="stat-number">500+</div>
              <div className="stat-label">Entreprises</div>
            </div>
            <div className="stat">
              <div className="stat-number">1M€+</div>
              <div className="stat-label">Revenus générés</div>
            </div>
          </div>
        </div>
        <div className="hero-visual">
          <div className="visual-card">
            <div className="card-header">
              <div className="card-dots">
                <span></span>
                <span></span>
                <span></span>
              </div>
            </div>
            <div className="card-content">
              <div className="metric-group">
                <div className="metric">
                  <span className="metric-label">Clics aujourd'hui</span>
                  <span className="metric-value">1,247</span>
                </div>
                <div className="metric">
                  <span className="metric-label">Conversions</span>
                  <span className="metric-value">89</span>
                </div>
                <div className="metric">
                  <span className="metric-label">Revenus</span>
                  <span className="metric-value">€245.50</span>
                </div>
              </div>
              <div className="chart-placeholder">
                <div className="chart-bar" style={{height: '60%'}}></div>
                <div className="chart-bar" style={{height: '80%'}}></div>
                <div className="chart-bar" style={{height: '45%'}}></div>
                <div className="chart-bar" style={{height: '90%'}}></div>
                <div className="chart-bar" style={{height: '70%'}}></div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="features-section">
        <div className="container">
          <h2 className="section-title">Pourquoi choisir ClickEarn ?</h2>
          <div className="features-grid">
            <div className="feature-card">
              <div className="feature-icon">💸</div>
              <h3>Rémunération Juste</h3>
              <p>Gagnez des revenus équitables pour chaque interaction valide avec nos campagnes publicitaires.</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">🚀</div>
              <h3>Performance Maximale</h3>
              <p>Des outils analytiques avancés pour optimiser vos campagnes et maximiser votre ROI.</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">🛡️</div>
              <h3>Sécurité Garantie</h3>
              <p>Plateforme sécurisée avec protection des données et paiements garantis.</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">🌍</div>
              <h3>Portée Mondiale</h3>
              <p>Accédez à un réseau international d'annonceurs et de visiteurs.</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <div className="app">
          <Navigation />
          <main className="main-content">
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/register/visiteur" element={<RegisterVisiteur />} />
              <Route path="/register/entreprise" element={<RegisterEntreprise />} />
              <Route path="/login" element={<Login />} />
              <Route path="/dashboard" element={
                <PrivateRoute>
                  <Dashboard />
                </PrivateRoute>
              } />
              <Route path="*" element={
                <div className="not-found">
                  <div className="not-found-content">
                    <h1>404</h1>
                    <p>Page non trouvée</p>
                    <Link to="/" className="btn btn-primary">
                      Retour à l'accueil
                    </Link>
                  </div>
                </div>
              } />
            </Routes>
          </main>
          <footer className="main-footer">
            <div className="footer-content">
              <div className="footer-section">
                <div className="footer-brand">
                  <span className="logo-icon">💎</span>
                  <span className="brand-text">ClickEarn</span>
                </div>
                <p>La plateforme de marketing performance qui récompense chaque interaction.</p>
              </div>
              <div className="footer-section">
                <h4>Navigation</h4>
                <Link to="/">Accueil</Link>
                <Link to="/register/visiteur">Inscription Visiteur</Link>
                <Link to="/register/entreprise">Inscription Entreprise</Link>
                <Link to="/login">Connexion</Link>
              </div>
              <div className="footer-section">
                <h4>Légal</h4>
                <a href="/privacy">Confidentialité</a>
                <a href="/terms">Conditions d'utilisation</a>
                <a href="/cookies">Cookies</a>
              </div>
              <div className="footer-section">
                <h4>Contact</h4>
                <a href="mailto:support@clickearn.com">support@clickearn.com</a>
                <a href="/help">Centre d'aide</a>
                <a href="/contact">Nous contacter</a>
              </div>
            </div>
            <div className="footer-bottom">
              <p>&copy; 2025 ClickEarn. Tous droits réservés.</p>
            </div>
          </footer>
        </div>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;