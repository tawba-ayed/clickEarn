package org.example.clickearn.entity;

import javax.persistence.*;import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "visiteurs")
public class Visiteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "premiere_visite")
    private LocalDateTime premiereVisite = LocalDateTime.now();

    @Column(name = "derniere_visite")
    private LocalDateTime derniereVisite;

    @Column(name = "nombre_visites")
    private Integer nombreVisites = 1;
}