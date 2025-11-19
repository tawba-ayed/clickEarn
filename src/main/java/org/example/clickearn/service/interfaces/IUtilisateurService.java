package org.example.clickearn.service.interfaces;

import org.example.clickearn.entity.Utilisateur;
import org.example.clickearn.enums.Role;

import java.util.List;
import java.util.Optional;

public interface IUtilisateurService {
    // Création
    Utilisateur createUtilisateur(String email, String password, Role role);
    Utilisateur createUtilisateur(Utilisateur utilisateur);
    
    // Lecture
    Optional<Utilisateur> getUtilisateurById(Long id);
    Optional<Utilisateur> getUtilisateurByEmail(String email);
    List<Utilisateur> getAllUtilisateurs();
    List<Utilisateur> getUtilisateursByRole(Role role);
    
    // Modification
    Utilisateur updateUtilisateur(Long id, Utilisateur utilisateurDetails);
    Utilisateur updatePassword(Long id, String ancienPassword, String nouveauPassword);
    Utilisateur updateEmail(Long id, String nouvelEmail);
    
    // Suppression
    void deleteUtilisateur(Long id);
    
    // Validation
    Boolean existsByEmail(String email);
    Boolean validatePassword(Long id, String password);
}

