package org.example.clickearn.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CampagneDetailDTO {
    private Long id;
    private String titre;
    private String description;
    private Double budgetTotal;
    private String cible;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String statut;
    private String nomEntreprise;
    private Double budgetUtilise;
    private Integer totalClics;
    private Integer totalConversions;
    private Double roi;
    private List<PostSponsoriseDTO> posts;
}