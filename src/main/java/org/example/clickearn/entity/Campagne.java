package org.example.clickearn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.clickearn.enums.StatutCampagne;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "campagnes")
public class Campagne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entreprise_id", nullable = false)
    @JsonIgnoreProperties({"campagnes", "posts"})
    private Entreprise entreprise;

    @Column(nullable = false, length = 100)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "budget_total")
    private Double budgetTotal;

    @Column(length = 100)
    private String cible;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatutCampagne statut = StatutCampagne.EN_ATTENTE;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @OneToMany(mappedBy = "campagne", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PostSponsorise> posts;

    // ✅ Méthode pour retourner le nombre de posts
    @JsonProperty("nombrePosts")
    public int getNombrePosts() {
        return posts != null ? posts.size() : 0;
    }
}