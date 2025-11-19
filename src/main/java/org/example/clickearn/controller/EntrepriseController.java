package org.example.clickearn.controller;

import lombok.RequiredArgsConstructor;
import org.example.clickearn.dto.EntrepriseDTO;
import org.example.clickearn.entity.Entreprise;
import org.example.clickearn.exception.ResourceNotFoundException;
import org.example.clickearn.service.interfaces.IEntrepriseService;
import org.example.clickearn.service.interfaces.IDashboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/entreprises")
@RequiredArgsConstructor
public class EntrepriseController {

    private final IEntrepriseService entrepriseService;
    private final IDashboardService dashboardService;


    @PostMapping
    public ResponseEntity<Entreprise> createEntreprise(@RequestBody EntrepriseDTO entrepriseDTO) {
        Entreprise entreprise = new Entreprise();
        entreprise.setNomEntreprise(entrepriseDTO.getNomEntreprise());
        entreprise.setDescription(entrepriseDTO.getDescription());
        entreprise.setWebsitelogoUrl(entrepriseDTO.getWebsitelogoUrl());
        // ✅ INITIALISER LE BUDGET TOTAL (ET INITIAL)
        entreprise.setBudgetTotal(entrepriseDTO.getBudgetTotal()); // budgetTotal est maintenant le "restant"
        entreprise.setBudgetInitial(entrepriseDTO.getBudgetTotal()); // Pour se souvenir
        Entreprise created = entrepriseService.createEntreprise(entreprise);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Obtenir toutes les entreprises
    @GetMapping
    public ResponseEntity<List<Entreprise>> getAllEntreprises() {
        return ResponseEntity.ok(entrepriseService.getAllEntreprises());
    }

    // Obtenir une entreprise par ID
    @GetMapping("/{entrepriseId}")
    public ResponseEntity<Entreprise> getEntreprise(@PathVariable Long entrepriseId) {
        return entrepriseService.getEntrepriseById(entrepriseId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise", entrepriseId));
    }

    // Mettre à jour une entreprise (y compris le budget si fourni)
    @PutMapping("/{entrepriseId}")
    public ResponseEntity<Entreprise> updateEntreprise(
            @PathVariable Long entrepriseId,
            @RequestBody Entreprise entrepriseDetails) {
        Entreprise updated = entrepriseService.updateEntreprise(entrepriseId, entrepriseDetails);
        return ResponseEntity.ok(updated);
    }

    // ✅ MÉTHODE POUR METTRE À JOUR SEULEMENT LE BUDGET TOTAL
    @PutMapping("/{entrepriseId}/budget")
    public ResponseEntity<Entreprise> updateBudget(
            @PathVariable Long entrepriseId,
            @RequestBody Map<String, Double> request) {
        Double newBudget = request.get("budgetTotal");
        if (newBudget == null) {
            return ResponseEntity.badRequest().build();
        }

        Entreprise entreprise = entrepriseService.updateBudget(entrepriseId, newBudget);
        return ResponseEntity.ok(entreprise);
    }

    // Supprimer une entreprise
    @DeleteMapping("/{entrepriseId}")
    public ResponseEntity<Void> deleteEntreprise(@PathVariable Long entrepriseId) {
        entrepriseService.deleteEntreprise(entrepriseId);
        return ResponseEntity.noContent().build();
    }

    // Dashboard
    @GetMapping("/{entrepriseId}/dashboard")
    public ResponseEntity<Map<String, Object>> dashboard(@PathVariable Long entrepriseId) {
        return ResponseEntity.ok(dashboardService.getEntrepriseDashboard(entrepriseId));
    }
}