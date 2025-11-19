package org.example.clickearn.repository;

import org.example.clickearn.entity.Clic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClickRepository extends JpaRepository<Clic, Long> {
}