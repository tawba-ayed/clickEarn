package org.example.clickearn.repository;

import org.example.clickearn.entity.Campagne;
import org.example.clickearn.entity.Entreprise;
import org.example.clickearn.enums.StatutCampagne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CampagneRepository extends JpaRepository<Campagne, Long> {

    // Trouver toutes les campagnes d'une entreprise
    List<Campagne> findByEntrepriseId(Long entrepriseId);

    // Trouver les campagnes par statut pour une entreprise
    List<Campagne> findByEntrepriseIdAndStatut(Long entrepriseId, StatutCampagne statut);

    // Vérifier si une campagne appartient à une entreprise
    @Query("SELECT c FROM Campagne c WHERE c.id = :campagneId AND c.entreprise.id = :entrepriseId")
    Optional<Campagne> findByIdAndEntrepriseId(@Param("campagneId") Long campagneId, @Param("entrepriseId") Long entrepriseId);

    // Compter le nombre de campagnes par statut
    @Query("SELECT COUNT(c) FROM Campagne c WHERE c.entreprise.id = :entrepriseId AND c.statut = :statut")
    Long countByEntrepriseIdAndStatut(@Param("entrepriseId") Long entrepriseId, @Param("statut") StatutCampagne statut);

    // Ajouter cette méthode dans CampagneRepository
    long countByEntrepriseId(Long entrepriseId);

    // Trouver les campagnes par date
    @Query("SELECT c FROM Campagne c WHERE c.entreprise.id = :entrepriseId AND c.dateDebut >= :dateDebut AND c.dateFin <= :dateFin")
    List<Campagne> findByEntrepriseIdAndDateRange(@Param("entrepriseId") Long entrepriseId, 
                                                     @Param("dateDebut") LocalDate dateDebut, 
                                                     @Param("dateFin") LocalDate dateFin);
}