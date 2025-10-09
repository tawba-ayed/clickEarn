package org.example.clickearn.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "posts_sponsorises")
public class PostSponsorise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "entreprise_id", nullable = false)
    private Entreprise entreprise;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    private String urlMedia;

    private Integer nbClics = 0;

    private Double prixParClic;

    private Double budgetPost;

    private LocalDate datePublication = LocalDate.now();

    private Boolean actif = true;
}
