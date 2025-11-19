package org.example.clickearn.repository;

import org.example.clickearn.entity.Retrait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RetraitRepository extends JpaRepository<Retrait, Long> {
    List<Retrait> findByUtilisateurId(Long utilisateurId);
}