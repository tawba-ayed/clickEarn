package org.example.clickearn.service;

import lombok.RequiredArgsConstructor;
import org.example.clickearn.entity.Entreprise;
import org.example.clickearn.exception.BadRequestException;
import org.example.clickearn.exception.ResourceNotFoundException;
import org.example.clickearn.repository.EntrepriseRepository;
import org.example.clickearn.service.interfaces.IEntrepriseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntrepriseService implements IEntrepriseService {

    private final EntrepriseRepository entrepriseRepository;

    // ...
    @Override
    @Transactional
    public Entreprise createEntreprise(Entreprise entreprise) {
        // Vérifier si le nom existe déjà
        if (entreprise.getNomEntreprise() != null &&
                entrepriseRepository.existsByNomEntreprise(entreprise.getNomEntreprise())) {
            throw new BadRequestException("Une entreprise avec ce nom existe déjà");
        }
        // ✅ INITIALISER LE BUDGET INITIAL ET LE BUDGET TOTAL
        entreprise.setBudgetInitial(entreprise.getBudgetTotal());
        // budgetTotal est utilisé comme budget restant
        return entrepriseRepository.save(entreprise);
    }
// ...

    @Override
    @Transactional
    public Entreprise updateEntreprise(Long entrepriseId, Entreprise entrepriseDetails) {
        Entreprise entreprise = entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise", entrepriseId));

        if (entrepriseDetails.getNomEntreprise() != null) {
            entreprise.setNomEntreprise(entrepriseDetails.getNomEntreprise());
        }
        if (entrepriseDetails.getDescription() != null) {
            entreprise.setDescription(entrepriseDetails.getDescription());
        }
        if (entrepriseDetails.getWebsitelogoUrl() != null) {
            entreprise.setWebsitelogoUrl(entrepriseDetails.getWebsitelogoUrl());
        }
        // ✅ METTRE À JOUR LE BUDGET TOTAL
        if (entrepriseDetails.getBudgetTotal() != null) {
            entreprise.setBudgetTotal(entrepriseDetails.getBudgetTotal());
        }

        return entrepriseRepository.save(entreprise);
    }

    // ✅ AJOUTER CETTE MÉTHODE POUR RESPECTER L'INTERFACE
    @Override
    @Transactional
    public Entreprise updateBudget(Long entrepriseId, Double newBudget) {
        Entreprise entreprise = entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise", entrepriseId));

        entreprise.setBudgetTotal(newBudget);
        return entrepriseRepository.save(entreprise);
    }

    @Override
    public Optional<Entreprise> getEntrepriseById(Long entrepriseId) {
        return entrepriseRepository.findById(entrepriseId);
    }

    @Override
    public List<Entreprise> getAllEntreprises() {
        return entrepriseRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteEntreprise(Long entrepriseId) {
        Entreprise entreprise = entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise", entrepriseId));
        entrepriseRepository.delete(entreprise);
    }

    @Override
    public List<Entreprise> searchEntreprisesByName(String nomEntreprise) {
        return entrepriseRepository.findAllByNomEntrepriseContainingIgnoreCase(nomEntreprise);
    }

    @Override
    @Transactional
    public Entreprise updateEntrepriseLogo(Long entrepriseId, String logoUrl) {
        Entreprise entreprise = entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise", entrepriseId));
        entreprise.setWebsitelogoUrl(logoUrl);
        return entrepriseRepository.save(entreprise);
    }

    @Override
    public Boolean existsByNomEntreprise(String nomEntreprise) {
        return entrepriseRepository.existsByNomEntreprise(nomEntreprise);
    }
}