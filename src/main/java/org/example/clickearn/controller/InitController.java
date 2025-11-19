package org.example.clickearn.controller;

import lombok.RequiredArgsConstructor;
import org.example.clickearn.entity.Entreprise;
import org.example.clickearn.service.interfaces.IEntrepriseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/init")
@RequiredArgsConstructor
public class InitController {

    private final IEntrepriseService entrepriseService;

    @PostMapping("/test-data")
    public ResponseEntity<String> initTestData() {
        try {
            // Vérifier si une entreprise existe déjà
            Optional<Entreprise> entrepriseOpt = entrepriseService.getEntrepriseById(1L);
            if (!entrepriseOpt.isPresent()) {
                // Créer une entreprise de test
                Entreprise entreprise = new Entreprise();
                entreprise.setNomEntreprise("Entreprise Test");
                entreprise.setDescription("Entreprise de test pour ClickEarn");
                entreprise = entrepriseService.createEntreprise(entreprise);
                return ResponseEntity.ok("Entreprise de test créée avec succès! ID: " + entreprise.getId());
            } else {
                return ResponseEntity.ok("Entreprise existe déjà avec ID: " + entrepriseOpt.get().getId());
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }
}

