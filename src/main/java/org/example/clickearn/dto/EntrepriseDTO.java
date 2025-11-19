package org.example.clickearn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntrepriseDTO {
    private Long id;
    private String description;
    private String websitelogoUrl;
    private String nomEntreprise;
    // ✅ AJOUTER CE CHAMP
    private Double budgetTotal;
}