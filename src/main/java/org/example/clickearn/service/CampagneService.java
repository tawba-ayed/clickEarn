package org.example.clickearn.service;

import org.example.clickearn.entity.Campagne;
import org.example.clickearn.entity.Entreprise;
import org.example.clickearn.entity.PostSponsorise;
import org.example.clickearn.enums.StatutCampagne;
import org.example.clickearn.enums.StatutPost;
import org.example.clickearn.exception.BadRequestException;
import org.example.clickearn.exception.ResourceNotFoundException;
import org.example.clickearn.repository.CampagneRepository;
import org.example.clickearn.repository.EntrepriseRepository;
import org.example.clickearn.repository.PostSponsoriseRepository;
import org.example.clickearn.service.interfaces.ICampagneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampagneService implements ICampagneService {

    private final CampagneRepository campagneRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final PostSponsoriseRepository postSponsoriseRepository;

    // ...
    @Override
    @Transactional
    public Campagne createCampagne(Campagne campagne, Long entrepriseId) {
        System.out.println("=== DEBUG: Début de createCampagne ===");
        System.out.println("Entreprise ID: " + entrepriseId);
        System.out.println("Budget de la nouvelle campagne: " + campagne.getBudgetTotal());

        Entreprise entreprise = entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise", entrepriseId));

        // ✅ VALIDER LE BUDGET RESTANT
        Double budgetRestant = entreprise.getBudgetTotal();
        if (campagne.getBudgetTotal() > budgetRestant) {
            System.out.println("❌ ERREUR: Budget demandé (" + campagne.getBudgetTotal() + ") > Budget restant (" + budgetRestant + ")");
            throw new BadRequestException(
                    String.format(
                            "Impossible de créer la campagne. Le budget demandé (%.2f DT) dépasse le budget restant de l'entreprise (%.2f DT).",
                            campagne.getBudgetTotal(),
                            budgetRestant
                    )
            );
        } else {
            System.out.println("✅ Validation OK: Budget demandé (" + campagne.getBudgetTotal() + ") <= Budget restant (" + budgetRestant + ")");
        }

        // ✅ DIMINUER LE BUDGET TOTAL DE L'ENTREPRISE
        entreprise.setBudgetTotal(budgetRestant - campagne.getBudgetTotal());
        entrepriseRepository.save(entreprise);

        campagne.setEntreprise(entreprise);
        campagne.setStatut(StatutCampagne.EN_ATTENTE);

        System.out.println("=== DEBUG: Fin de createCampagne, sauvegarde... ===");
        return campagneRepository.save(campagne);
    }
    // ...
    // ✅ MODIFIER UNE CAMPAGNE - AVEC VALIDATION CORRECTE (CALCUL DYNAMIQUE)
    @Override
    @Transactional
    public Campagne updateCampagne(Long campagneId, Long entrepriseId, Campagne campagneDetails) {
        Campagne campagne = campagneRepository.findByIdAndEntrepriseId(campagneId, entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Campagne", campagneId));

        // Récupérer l'entreprise
        Entreprise entreprise = campagne.getEntreprise();

        // Récupérer toutes les campagnes existantes pour cette entreprise
        List<Campagne> campagnesExistantes = campagneRepository.findByEntrepriseId(entrepriseId);

        // Calculer le budget total déjà alloué (sans compter celle-ci)
        double totalAlloue = campagnesExistantes.stream()
                .filter(c -> !c.getId().equals(campagneId)) // Exclure la campagne en cours de modification
                .mapToDouble(c -> c.getBudgetTotal() != null ? c.getBudgetTotal() : 0)
                .sum();

        // Récupérer le budget total de l'entreprise
        double budgetTotalEntreprise = entreprise.getBudgetTotal() != null ? entreprise.getBudgetTotal() : 0;

        // Calculer le budget restant
        double budgetRestant = budgetTotalEntreprise - totalAlloue;

        // ✅ VÉRIFIER SI LE NOUVEAU BUDGET DÉPASSE LE RESTANT
        if (campagneDetails.getBudgetTotal() > budgetRestant) {
            throw new BadRequestException(
                    String.format(
                            "Impossible de modifier la campagne. Le nouveau budget (%.2f DT) dépasse le budget restant de l'entreprise (%.2f DT).",
                            campagneDetails.getBudgetTotal(),
                            budgetRestant
                    )
            );
        }

        campagne.setTitre(campagneDetails.getTitre());
        campagne.setDescription(campagneDetails.getDescription());
        campagne.setBudgetTotal(campagneDetails.getBudgetTotal()); // Mise à jour du budget
        campagne.setCible(campagneDetails.getCible());
        campagne.setDateDebut(campagneDetails.getDateDebut());
        campagne.setDateFin(campagneDetails.getDateFin());

        // Mettre à jour l'image si fournie
        if (campagneDetails.getImageUrl() != null) {
            campagne.setImageUrl(campagneDetails.getImageUrl());
        }

        return campagneRepository.save(campagne);
    }

    // ✅ SUPPRIMER UNE CAMPAGNE - PAS BESOIN DE TOUCHER AU BUDGET RESTANT ICI
    @Override
    @Transactional
    public void deleteCampagne(Long campagneId, Long entrepriseId) {
        Campagne campagne = campagneRepository.findByIdAndEntrepriseId(campagneId, entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Campagne", campagneId));

        // Vérifier s'il y a des posts actifs
        List<PostSponsorise> postsActifs = postSponsoriseRepository.findByCampagneIdAndStatut(campagneId, StatutPost.ACTIF);
        if (!postsActifs.isEmpty()) {
            throw new BadRequestException("Impossible de supprimer une campagne avec des posts actifs");
        }

        campagneRepository.delete(campagne);
    }

    // ✅ AUTRES MÉTHODES (inchangées sauf mention contraire)

    @Override
    @Transactional
    public Campagne updateCampagneStatut(Long campagneId, Long entrepriseId, StatutCampagne nouveauStatut) {
        Campagne campagne = campagneRepository.findByIdAndEntrepriseId(campagneId, entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Campagne", campagneId));

        campagne.setStatut(nouveauStatut);
        return campagneRepository.save(campagne);
    }

    @Override
    public List<Campagne> getCampagnesByEntreprise(Long entrepriseId) {
        return campagneRepository.findByEntrepriseId(entrepriseId);
    }

    @Override
    public Campagne getCampagneByIdAndEntreprise(Long campagneId, Long entrepriseId) {
        return campagneRepository.findByIdAndEntrepriseId(campagneId, entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Campagne", campagneId));
    }

    @Override
    public CampagneStats getCampagneStats(Long campagneId, Long entrepriseId) {
        Campagne campagne = campagneRepository.findByIdAndEntrepriseId(campagneId, entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Campagne", campagneId));

        Object[] stats = postSponsoriseRepository.getCampagneStats(campagneId);

        Double totalBudgetAlloue = stats[0] != null ? (Double) stats[0] : 0.0;
        Double totalMontantDepense = stats[1] != null ? (Double) stats[1] : 0.0;
        Integer totalClics = stats[2] != null ? ((Number) stats[2]).intValue() : 0;
        Integer totalConversions = stats[3] != null ? ((Number) stats[3]).intValue() : 0;

        return CampagneStats.builder()
                .campagne(campagne)
                .totalBudgetAlloue(totalBudgetAlloue)
                .totalMontantDepense(totalMontantDepense)
                .totalClics(totalClics)
                .totalConversions(totalConversions)
                .build();
    }

    @Override
    public List<Campagne> getCampagnesByStatut(Long entrepriseId, StatutCampagne statut) {
        return campagneRepository.findByEntrepriseIdAndStatut(entrepriseId, statut);
    }

    @Override
    public List<Campagne> getCampagnesActives(Long entrepriseId) {
        return campagneRepository.findByEntrepriseIdAndStatut(entrepriseId, StatutCampagne.ACTIVE);
    }

    @Override
    public List<Campagne> getCampagnesByDateRange(Long entrepriseId, LocalDate dateDebut, LocalDate dateFin) {
        return campagneRepository.findByEntrepriseIdAndDateRange(entrepriseId, dateDebut, dateFin);
    }

    @Override
    @Transactional
    public Campagne activerCampagne(Long campagneId, Long entrepriseId) {
        Campagne campagne = campagneRepository.findByIdAndEntrepriseId(campagneId, entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Campagne", campagneId));
        campagne.setStatut(StatutCampagne.ACTIVE);
        return campagneRepository.save(campagne);
    }

    @Override
    @Transactional
    public Campagne terminerCampagne(Long campagneId, Long entrepriseId) {
        Campagne campagne = campagneRepository.findByIdAndEntrepriseId(campagneId, entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Campagne", campagneId));
        campagne.setStatut(StatutCampagne.TERMINEE);
        return campagneRepository.save(campagne);
    }

    @Override
    public Long countCampagnesByEntreprise(Long entrepriseId) {
        return campagneRepository.countByEntrepriseId(entrepriseId);
    }

    @Override
    public Long countCampagnesByStatut(Long entrepriseId, StatutCampagne statut) {
        return campagneRepository.countByEntrepriseIdAndStatut(entrepriseId, statut);
    }
}