import React from "react";
import useAuth from "../auth/useAuth";

export default function Dashboard() {
  const { user, logout } = useAuth();
  return (
    <div>
      <h2>Dashboard</h2>
      <p>Bienvenue {user?.email}</p>
      <button onClick={logout}>Se déconnecter</button>
    </div>
  );
}
