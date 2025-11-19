package org.example.clickearn.controller;

import org.example.clickearn.entity.Campagne;
import org.example.clickearn.enums.StatutCampagne;
import org.example.clickearn.service.CampagneStats;
import org.example.clickearn.service.interfaces.ICampagneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entreprises/{entrepriseId}/campagnes")
@RequiredArgsConstructor
public class CampagneController {

    private final ICampagneService campagneService;

    // Créer une campagne
    @PostMapping
    public ResponseEntity<Campagne> createCampagne(
            @PathVariable Long entrepriseId,
            @RequestBody Campagne campagne) { // ✅ S'assurer que @RequestBody est présent
        Campagne nouvelleCampagne = campagneService.createCampagne(campagne, entrepriseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleCampagne);
    }

    // Obtenir toutes les campagnes d'une entreprise (filtrées par entrepriseId)
    @GetMapping
    public ResponseEntity<List<Campagne>> getAllCampagnes(@PathVariable Long entrepriseId) {
        try {
            List<Campagne> campagnes = campagneService.getCampagnesByEntreprise(entrepriseId);
            // S'assurer que chaque campagne n'a que les données nécessaires pour éviter les problèmes de sérialisation
            return ResponseEntity.ok(campagnes);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des campagnes pour l'entreprise " + entrepriseId + ": " + e.getMessage(), e);
        }
    }

    // Obtenir une campagne spécifique
    @GetMapping("/{campagneId}")
    public ResponseEntity<Campagne> getCampagne(
            @PathVariable Long entrepriseId,
            @PathVariable Long campagneId) {
        Campagne campagne = campagneService.getCampagneByIdAndEntreprise(campagneId, entrepriseId);
        return ResponseEntity.ok(campagne);
    }

    // Modifier une campagne
    @PutMapping("/{campagneId}")
    public ResponseEntity<Campagne> updateCampagne(
            @PathVariable Long entrepriseId,
            @PathVariable Long campagneId,
            @RequestBody Campagne campagneDetails) {
        Campagne campagne = campagneService.updateCampagne(campagneId, entrepriseId, campagneDetails);
        return ResponseEntity.ok(campagne);
    }

    // Changer le statut d'une campagne
    @PatchMapping("/{campagneId}/statut")
    public ResponseEntity<Campagne> updateStatut(
            @PathVariable Long entrepriseId,
            @PathVariable Long campagneId,
            @RequestParam StatutCampagne statut) {
        Campagne campagne = campagneService.updateCampagneStatut(campagneId, entrepriseId, statut);
        return ResponseEntity.ok(campagne);
    }

    // Obtenir les statistiques d'une campagne
    @GetMapping("/{campagneId}/stats")
    public ResponseEntity<CampagneStats> getCampagneStats(
            @PathVariable Long entrepriseId,
            @PathVariable Long campagneId) {
        CampagneStats stats = campagneService.getCampagneStats(campagneId, entrepriseId);
        return ResponseEntity.ok(stats);
    }

    // ✅ AJOUTER CETTE MÉTHODE POUR SUPPRIMER UNE CAMPAGNE
    @DeleteMapping("/{campagneId}")
    public ResponseEntity<?> deleteCampagne(
            @PathVariable Long entrepriseId,
            @PathVariable Long campagneId) {
        try {
            campagneService.deleteCampagne(campagneId, entrepriseId);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e) {
            // Gérer les erreurs (campagne non trouvée, etc.)
            return ResponseEntity.badRequest().body("Erreur lors de la suppression: " + e.getMessage());
        }
    }
}