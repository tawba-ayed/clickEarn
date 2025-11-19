package org.example.clickearn.service.interfaces;

import org.example.clickearn.entity.Campagne;
import org.example.clickearn.enums.StatutCampagne;
import org.example.clickearn.service.CampagneStats;

import java.time.LocalDate;
import java.util.List;

public interface ICampagneService {
    // Création
    Campagne createCampagne(Campagne campagne, Long entrepriseId);
    
    // Lecture
    Campagne getCampagneByIdAndEntreprise(Long campagneId, Long entrepriseId);
    List<Campagne> getCampagnesByEntreprise(Long entrepriseId);
    List<Campagne> getCampagnesByStatut(Long entrepriseId, StatutCampagne statut);
    List<Campagne> getCampagnesActives(Long entrepriseId);
    List<Campagne> getCampagnesByDateRange(Long entrepriseId, LocalDate dateDebut, LocalDate dateFin);
    
    // Modification
    Campagne updateCampagne(Long campagneId, Long entrepriseId, Campagne campagneDetails);
    Campagne updateCampagneStatut(Long campagneId, Long entrepriseId, StatutCampagne nouveauStatut);
    Campagne activerCampagne(Long campagneId, Long entrepriseId);
    Campagne terminerCampagne(Long campagneId, Long entrepriseId);
    
    // Suppression
    void deleteCampagne(Long campagneId, Long entrepriseId);
    
    // Statistiques
    CampagneStats getCampagneStats(Long campagneId, Long entrepriseId);
    Long countCampagnesByEntreprise(Long entrepriseId);
    Long countCampagnesByStatut(Long entrepriseId, StatutCampagne statut);
}

