import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";

export default function useAuth() {
const { user, token, login, logout } = useContext(AuthContext);
  const isAuthenticated = !!token; // true si un token est présent
  return { user, token, login, logout, isAuthenticated };
}
