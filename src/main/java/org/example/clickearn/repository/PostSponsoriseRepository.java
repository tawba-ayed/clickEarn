package org.example.clickearn.repository;

import org.example.clickearn.entity.PostSponsorise;
import org.example.clickearn.enums.StatutPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostSponsoriseRepository extends JpaRepository<PostSponsorise, Long> {

    // Trouver tous les posts d'une campagne
    List<PostSponsorise> findByCampagneId(Long campagneId);

    // Trouver les posts par campagne et statut
    List<PostSponsorise> findByCampagneIdAndStatut(Long campagneId, StatutPost statut);

    // Statistiques pour une campagne
    @Query("SELECT COALESCE(SUM(p.budgetAlloue), 0), COALESCE(SUM(p.montantDepense), 0), COALESCE(SUM(p.nombreClics), 0), COALESCE(SUM(p.nombreConversions), 0) " +
            "FROM PostSponsorise p WHERE p.campagne.id = :campagneId")
    Object[] getCampagneStats(@Param("campagneId") Long campagneId);

    // Trouver tous les posts d'une entreprise (via campagne)
    @Query("SELECT p FROM PostSponsorise p WHERE p.campagne.entreprise.id = :entrepriseId")
    List<PostSponsorise> findByEntrepriseId(@Param("entrepriseId") Long entrepriseId);

    // Trouver les posts par statut pour une entreprise
    @Query("SELECT p FROM PostSponsorise p WHERE p.campagne.entreprise.id = :entrepriseId AND p.statut = :statut")
    List<PostSponsorise> findByEntrepriseIdAndStatut(@Param("entrepriseId") Long entrepriseId, @Param("statut") StatutPost statut);

    // Total clics par campagne
    @Query("SELECT COALESCE(SUM(p.nombreClics), 0) FROM PostSponsorise p WHERE p.campagne.id = :campagneId")
    Integer getTotalClicsByCampagne(@Param("campagneId") Long campagneId);

    // Total dépense par campagne
    @Query("SELECT COALESCE(SUM(p.montantDepense), 0) FROM PostSponsorise p WHERE p.campagne.id = :campagneId")
    Double getTotalDepenseByCampagne(@Param("campagneId") Long campagneId);
}