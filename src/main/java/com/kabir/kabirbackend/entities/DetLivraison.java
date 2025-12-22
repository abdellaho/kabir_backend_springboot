package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detlivraison")
public class DetLivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "qteLivrer", nullable = false)
    private int qteLivrer;

    @NotNull
    @Column(name = "champsRouge", nullable = false)
    private int champsRouge;

    @NotNull
    @Column(name = "remiseLivraison", nullable = false, precision = 10, scale = 2)
    private double remiseLivraison;

    @NotNull
    @Column(name = "prixVente", nullable = false, precision = 10, scale = 2)
    private double prixVente;

    @NotNull
    @Column(name = "montantProduit", nullable = false, precision = 10, scale = 2)
    private double montantProduit;

    @NotNull
    @Column(name = "beneficeDH", nullable = false, precision = 10, scale = 2)
    private double beneficeDH;

    @NotNull
    @Column(name = "benepourcentage", nullable = false, precision = 10, scale = 2)
    private double benepourcentage;

    @NotNull
    @Column(name = "infinity", nullable = false)
    private int infinity;

    @NotNull
    @Column(name = "avecRemise", nullable = false)
    private boolean avecRemise = false;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "livraisonId", nullable = false)
    private Livraison livraison;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "stockId", nullable = false)
    private Stock stock;

}