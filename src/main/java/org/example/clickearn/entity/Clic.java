package org.example.clickearn.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "clics")
public class Clic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Relation vers le post sponsorisé ---
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostSponsorise postSponsorise;

    // --- Relation vers le visiteur (participant) ---
    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private Visiteur participant;

    private LocalDateTime dateHeure = LocalDateTime.now();

}
