package org.example.clickearn.service;

import lombok.RequiredArgsConstructor;
import org.example.clickearn.enums.StatutCampagne;
import org.example.clickearn.enums.StatutPost;
import org.example.clickearn.repository.CampagneRepository;
import org.example.clickearn.repository.PostSponsoriseRepository;
import org.example.clickearn.service.interfaces.IDashboardService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService implements IDashboardService {

    private final CampagneRepository campagneRepository;
    private final PostSponsoriseRepository postSponsoriseRepository;

    @Override
    public Map<String, Object> getEntrepriseDashboard(Long entrepriseId) {
        Map<String, Object> dashboard = new HashMap<>();

        // Statistiques basiques
        long totalCampagnes = campagneRepository.countByEntrepriseId(entrepriseId);
        long campagnesActives = campagneRepository.countByEntrepriseIdAndStatut(entrepriseId, StatutCampagne.ACTIVE);
        
        // Calculer le budget total et les clics depuis les posts
        List<org.example.clickearn.entity.PostSponsorise> posts = postSponsoriseRepository.findAll();
        
        double budgetTotalDepense = 0.0;
        int clicsTotaux = 0;
        int publicationsActives = 0;
        
        for (org.example.clickearn.entity.PostSponsorise post : posts) {
            if (post.getCampagne() != null && post.getCampagne().getEntreprise() != null 
                && post.getCampagne().getEntreprise().getId().equals(entrepriseId)) {
                if (post.getMontantDepense() != null) {
                    budgetTotalDepense += post.getMontantDepense();
                }
                if (post.getNombreClics() != null) {
                    clicsTotaux += post.getNombreClics();
                }
                if (post.getStatut() != null && post.getStatut() == StatutPost.ACTIF) {
                    publicationsActives++;
                }
            }
        }

        dashboard.put("totalCampagnes", totalCampagnes);
        dashboard.put("campagnesActives", campagnesActives);
        dashboard.put("budgetTotalDepense", budgetTotalDepense);
        dashboard.put("clicsTotaux", clicsTotaux);
        dashboard.put("publicationsActives", publicationsActives);
        dashboard.put("tauxEngagement", clicsTotaux > 0 ? (double) clicsTotaux / 100.0 : 0.0);

        return dashboard;
    }

    @Override
    public Map<String, Object> getDashboardStats(Long entrepriseId) {
        return getEntrepriseDashboard(entrepriseId);
    }

    @Override
    public Map<String, Object> getDashboardStatsByDateRange(Long entrepriseId, LocalDate dateDebut, LocalDate dateFin) {
        Map<String, Object> stats = new HashMap<>();
        
        // Compter les campagnes dans la période
        long totalCampagnes = campagneRepository.countByEntrepriseId(entrepriseId);
        
        // Calculer les stats des posts dans la période
        List<org.example.clickearn.entity.PostSponsorise> posts = postSponsoriseRepository.findByEntrepriseId(entrepriseId);
        
        double budgetTotalDepense = 0.0;
        int clicsTotaux = 0;
        int publicationsActives = 0;
        
        for (org.example.clickearn.entity.PostSponsorise post : posts) {
            if (post.getCampagne() != null && post.getCampagne().getEntreprise() != null 
                && post.getCampagne().getEntreprise().getId().equals(entrepriseId)) {
                // Filtrer par dates si nécessaire
                if (post.getDatePublication() != null && 
                    !post.getDatePublication().toLocalDate().isBefore(dateDebut) &&
                    !post.getDatePublication().toLocalDate().isAfter(dateFin)) {
                    
                    if (post.getMontantDepense() != null) {
                        budgetTotalDepense += post.getMontantDepense();
                    }
                    if (post.getNombreClics() != null) {
                        clicsTotaux += post.getNombreClics();
                    }
                    if (post.getStatut() == StatutPost.ACTIF) {
                        publicationsActives++;
                    }
                }
            }
        }

        stats.put("totalCampagnes", totalCampagnes);
        stats.put("budgetTotalDepense", budgetTotalDepense);
        stats.put("clicsTotaux", clicsTotaux);
        stats.put("publicationsActives", publicationsActives);
        stats.put("tauxEngagement", clicsTotaux > 0 ? (double) clicsTotaux / 100.0 : 0.0);
        stats.put("dateDebut", dateDebut);
        stats.put("dateFin", dateFin);

        return stats;
    }

    @Override
    public Double getBudgetTotalDepense(Long entrepriseId) {
        List<org.example.clickearn.entity.PostSponsorise> posts = postSponsoriseRepository.findByEntrepriseId(entrepriseId);
        return posts.stream()
                .filter(p -> p.getCampagne() != null && 
                           p.getCampagne().getEntreprise() != null && 
                           p.getCampagne().getEntreprise().getId().equals(entrepriseId))
                .mapToDouble(p -> p.getMontantDepense() != null ? p.getMontantDepense() : 0.0)
                .sum();
    }

    @Override
    public Integer getTotalClics(Long entrepriseId) {
        List<org.example.clickearn.entity.PostSponsorise> posts = postSponsoriseRepository.findByEntrepriseId(entrepriseId);
        return posts.stream()
                .filter(p -> p.getCampagne() != null && 
                           p.getCampagne().getEntreprise() != null && 
                           p.getCampagne().getEntreprise().getId().equals(entrepriseId))
                .mapToInt(p -> p.getNombreClics() != null ? p.getNombreClics() : 0)
                .sum();
    }

    @Override
    public Integer getTotalConversions(Long entrepriseId) {
        List<org.example.clickearn.entity.PostSponsorise> posts = postSponsoriseRepository.findByEntrepriseId(entrepriseId);
        return posts.stream()
                .filter(p -> p.getCampagne() != null && 
                           p.getCampagne().getEntreprise() != null && 
                           p.getCampagne().getEntreprise().getId().equals(entrepriseId))
                .mapToInt(p -> p.getNombreConversions() != null ? p.getNombreConversions() : 0)
                .sum();
    }

    @Override
    public Double getROI(Long entrepriseId) {
        Double budgetDepense = getBudgetTotalDepense(entrepriseId);
        Integer conversions = getTotalConversions(entrepriseId);
        
        if (budgetDepense == null || budgetDepense == 0.0) {
            return 0.0;
        }
        
        // ROI simple : (conversions * valeur moyenne) / budget dépensé
        // Pour simplifier, on considère chaque conversion vaut 10 DT
        double valeurConversions = conversions * 10.0;
        return (valeurConversions / budgetDepense) * 100.0;
    }

    @Override
    public Map<String, Object> comparePeriods(Long entrepriseId, LocalDate periode1Debut, LocalDate periode1Fin, 
                                             LocalDate periode2Debut, LocalDate periode2Fin) {
        Map<String, Object> periode1 = getDashboardStatsByDateRange(entrepriseId, periode1Debut, periode1Fin);
        Map<String, Object> periode2 = getDashboardStatsByDateRange(entrepriseId, periode2Debut, periode2Fin);
        
        Map<String, Object> comparison = new HashMap<>();
        comparison.put("periode1", periode1);
        comparison.put("periode2", periode2);
        
        // Calculer les différences
        double budgetDiff = ((Double) periode2.get("budgetTotalDepense")) - ((Double) periode1.get("budgetTotalDepense"));
        int clicsDiff = ((Integer) periode2.get("clicsTotaux")) - ((Integer) periode1.get("clicsTotaux"));
        
        comparison.put("budgetDifference", budgetDiff);
        comparison.put("clicsDifference", clicsDiff);
        comparison.put("budgetVariation", ((Double) periode1.get("budgetTotalDepense")) > 0 ? 
                         (budgetDiff / ((Double) periode1.get("budgetTotalDepense"))) * 100 : 0.0);
        comparison.put("clicsVariation", ((Integer) periode1.get("clicsTotaux")) > 0 ? 
                       ((double) clicsDiff / (Integer) periode1.get("clicsTotaux")) * 100 : 0.0);
        
        return comparison;
    }
}