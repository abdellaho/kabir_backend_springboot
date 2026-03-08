package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "entretien")
public class Entretien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Voiture voiture;

    private LocalDate dateEntretien;
    private Instant dateSys;

    private int kmDetecte;
    private int kmMax;
    private boolean huile;
    private boolean filtreHuile;
    private boolean filtreCarburant;
    private boolean filtreAir;
    private boolean plaquetteAV;
    private boolean plaquetteAR;
    private boolean pneuAV;
    private boolean pneuAR;
    private boolean kitDistribution;
    private boolean batterie;


}