package org.example.clickearn.service.interfaces;

import java.time.LocalDate;
import java.util.Map;

public interface IDashboardService {
    // Dashboard principal
    Map<String, Object> getEntrepriseDashboard(Long entrepriseId);
    
    // Statistiques détaillées
    Map<String, Object> getDashboardStats(Long entrepriseId);
    Map<String, Object> getDashboardStatsByDateRange(Long entrepriseId, LocalDate dateDebut, LocalDate dateFin);
    
    // Métriques spécifiques
    Double getBudgetTotalDepense(Long entrepriseId);
    Integer getTotalClics(Long entrepriseId);
    Integer getTotalConversions(Long entrepriseId);
    Double getROI(Long entrepriseId);
    
    // Comparaisons
    Map<String, Object> comparePeriods(Long entrepriseId, LocalDate periode1Debut, LocalDate periode1Fin, 
                                      LocalDate periode2Debut, LocalDate periode2Fin);
}

