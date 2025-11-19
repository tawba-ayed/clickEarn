package org.example.clickearn.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.clickearn.enums.Plateforme;
import org.example.clickearn.enums.StatutPost;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "posts_sponsorises")
public class PostSponsorise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "campagne_id")
    @JsonIgnoreProperties({"posts", "entreprise"})
    private Campagne campagne;

    private String titre;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    private String imageUrl;

    @Column(name = "url_media")
    private String urlMedia;

    private String lienPublicitaire;

    @Enumerated(EnumType.STRING)
    private Plateforme plateforme;

    @Enumerated(EnumType.STRING)
    private StatutPost statut = StatutPost.EN_ATTENTE;

    private Double budgetAlloue;

    @Column(name = "budget_post")
    private Double budgetPost;

    private Double montantDepense = 0.0;

    private Integer nbClics = 0;

    private Integer nombreClics = 0;

    private Integer nombreConversions = 0;

    private Double prixParClic;

    private LocalDateTime datePublication;

    private Boolean actif = true;
}
