package org.example.clickearn.service;

import lombok.RequiredArgsConstructor;
import org.example.clickearn.entity.Utilisateur;
import org.example.clickearn.enums.Role;
import org.example.clickearn.exception.BadRequestException;
import org.example.clickearn.exception.ResourceNotFoundException;
import org.example.clickearn.repository.UtilisateurRepository;
import org.example.clickearn.service.interfaces.IUtilisateurService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilisateurService implements IUtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Utilisateur createUtilisateur(String email, String password, Role role) {
        if (utilisateurRepository.existsByEmail(email)) {
            throw new BadRequestException("Un utilisateur avec cet email existe déjà");
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(email);
        utilisateur.setMotDePasse(passwordEncoder.encode(password));
        utilisateur.setRole(role);
        
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    @Transactional
    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        if (utilisateurRepository.existsByEmail(utilisateur.getEmail())) {
            throw new BadRequestException("Un utilisateur avec cet email existe déjà");
        }

        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    @Override
    public Optional<Utilisateur> getUtilisateurByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @Override
    public List<Utilisateur> getUtilisateursByRole(Role role) {
        return utilisateurRepository.findAll().stream()
                .filter(u -> u.getRole() == role)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    @Transactional
    public Utilisateur updateUtilisateur(Long id, Utilisateur utilisateurDetails) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", id));

        if (utilisateurDetails.getEmail() != null && !utilisateurDetails.getEmail().equals(utilisateur.getEmail())) {
            if (utilisateurRepository.existsByEmail(utilisateurDetails.getEmail())) {
                throw new BadRequestException("Cet email est déjà utilisé");
            }
            utilisateur.setEmail(utilisateurDetails.getEmail());
        }

        if (utilisateurDetails.getPhoneNumber() != null) {
            utilisateur.setPhoneNumber(utilisateurDetails.getPhoneNumber());
        }

        if (utilisateurDetails.getRole() != null) {
            utilisateur.setRole(utilisateurDetails.getRole());
        }

        return utilisateurRepository.save(utilisateur);
    }

    @Override
    @Transactional
    public Utilisateur updatePassword(Long id, String ancienPassword, String nouveauPassword) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", id));

        if (!passwordEncoder.matches(ancienPassword, utilisateur.getMotDePasse())) {
            throw new BadRequestException("Ancien mot de passe incorrect");
        }

        utilisateur.setMotDePasse(passwordEncoder.encode(nouveauPassword));
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    @Transactional
    public Utilisateur updateEmail(Long id, String nouvelEmail) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", id));

        if (utilisateurRepository.existsByEmail(nouvelEmail)) {
            throw new BadRequestException("Cet email est déjà utilisé");
        }

        utilisateur.setEmail(nouvelEmail);
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    @Transactional
    public void deleteUtilisateur(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", id));
        utilisateurRepository.delete(utilisateur);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return utilisateurRepository.existsByEmail(email);
    }

    @Override
    public Boolean validatePassword(Long id, String password) {
        return utilisateurRepository.findById(id)
                .map(u -> passwordEncoder.matches(password, u.getMotDePasse()))
                .orElse(false);
    }
}

