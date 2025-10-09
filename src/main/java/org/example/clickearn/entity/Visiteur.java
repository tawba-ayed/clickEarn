package org.example.clickearn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter
@Setter
@Table(name = "visiteurs")
public class Visiteur {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    private String nom;
    private String prenom;
    private String bio;
    private String avatarUrl;

    @Column(columnDefinition = "TEXT")
    private String interets;

    @Column(nullable = false)
    private Double solde = 0.0;

}