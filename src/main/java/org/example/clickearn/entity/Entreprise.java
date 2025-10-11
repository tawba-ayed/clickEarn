package org.example.clickearn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity @Getter
@Setter
@Table(name = "entreprises")
public class Entreprise {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    private String description;
    private String websitelogoUrl;

    @Column(name = "nom_entreprise")
    private String nomEntreprise;

    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL)
    private List<Campagne> campagnes;

}
