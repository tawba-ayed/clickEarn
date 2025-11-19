package org.example.clickearn.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class PostSponsoriseDTO {
    private Long id;
    private Long campagneId;
    private String titre;
    private String contenu;
    private String imageUrl;
    private String lienPublicitaire;
    private String plateforme;
    private Double budgetAlloue;
    private Double montantDepense;
    private Integer nombreClics;
    private Integer nombreConversions;
    private Double tauxEngagement;
    private LocalDateTime datePublication;
    private String statut;
}