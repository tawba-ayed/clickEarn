package org.example.clickearn.service.interfaces;

import org.example.clickearn.entity.Entreprise;
import java.util.List;
import java.util.Optional;

public interface IEntrepriseService {
    Entreprise createEntreprise(Entreprise entreprise);
    Entreprise updateEntreprise(Long entrepriseId, Entreprise entrepriseDetails);
    // ✅ AJOUTER CETTE LIGNE
    Entreprise updateBudget(Long entrepriseId, Double newBudget);
    Optional<Entreprise> getEntrepriseById(Long entrepriseId);
    List<Entreprise> getAllEntreprises();
    void deleteEntreprise(Long entrepriseId);
    List<Entreprise> searchEntreprisesByName(String nomEntreprise);
    Entreprise updateEntrepriseLogo(Long entrepriseId, String logoUrl);
    Boolean existsByNomEntreprise(String nomEntreprise);
}