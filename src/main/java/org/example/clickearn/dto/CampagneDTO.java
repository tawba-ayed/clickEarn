package org.example.clickearn.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class CampagneDTO {
    private Long id;
    private Long entrepriseId;
    private String titre;
    private String description;
    private Double budgetTotal;
    private String cible;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String statut;
    private String nomEntreprise;
}