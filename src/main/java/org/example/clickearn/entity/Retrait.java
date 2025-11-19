package org.example.clickearn.entity;

import org.example.clickearn.enums.StatutRetrait;
import javax.persistence.*;import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "retraits")
public class Retrait {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @Column(nullable = false)
    private Double montant;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatutRetrait statut = StatutRetrait.EN_ATTENTE;

    @Column(name = "date_demande")
    private LocalDateTime dateDemande = LocalDateTime.now();

    @Column(name = "date_traitement")
    private LocalDateTime dateTraitement;

    @Column(length = 255)
    private String commentaire;
}