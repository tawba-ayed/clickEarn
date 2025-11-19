package org.example.clickearn.repository;

import org.example.clickearn.entity.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
    
    Optional<Entreprise> findByNomEntrepriseContainingIgnoreCase(String nomEntreprise);
    
    List<Entreprise> findAllByNomEntrepriseContainingIgnoreCase(String nomEntreprise);

    Boolean existsByNomEntreprise(String nomEntreprise);
}