package org.example.clickearn.repository;

import org.example.clickearn.entity.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisiteurRepository extends JpaRepository<Visiteur, Long> {
}