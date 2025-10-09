package org.example.clickearn.entity;


import jakarta.persistence.*;
import lombok.Getter;
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

    // --- Relation vers le visiteur (participant) ---
    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private Visiteur participant;

    // --- Date et heure du retrait ---
    private LocalDateTime date = LocalDateTime.now();

    // --- Montant retiré ---
    private Double montant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutRetrait statut = StatutRetrait.EN_ATTENTE;
}
