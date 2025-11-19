package org.example.clickearn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Getter
@Setter
@Table(name = "entreprises")
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "websitelogo_url")
    private String websitelogoUrl;

    @Column(name = "nom_entreprise")
    private String nomEntreprise;

    // ✅ CHAMP BUDGET TOTAL (MAINTENANT UTILISÉ COMME BUDGET RESTANT)
    @Column(name = "budget_total")
    private Double budgetTotal;

    // ✅ CHAMP BUDGET INITIAL (POUR SE SOUVENIR DE LA LIMITE)
    @Column(name = "budget_initial")
    private Double budgetInitial;

    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Campagne> campagnes;
}