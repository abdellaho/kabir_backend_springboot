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
@Table(name = "detfacture")
public class DetFacture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "qteFacturer", nullable = false)
    private int qteFacturer;

    @NotNull
    @Column(name = "remiseFacture", nullable = false, precision = 10)
    private double remiseFacture;

    @NotNull
    @Column(name = "prixVente", nullable = false, precision = 10)
    private double prixVente;

    @NotNull
    @Column(name = "montantProduit", nullable = false, precision = 10)
    private double montantProduit;

    @NotNull
    @Column(name = "beneficeDH", nullable = false, precision = 10)
    private double beneficeDH;

    @NotNull
    @Column(name = "benepourcentage", nullable = false, precision = 10)
    private double benepourcentage;

    @NotNull
    @Column(name = "tva7", nullable = false, precision = 10)
    private double tva7;

    @NotNull
    @Column(name = "tva20", nullable = false, precision = 10)
    private double tva20;

    @NotNull
    @Column(name = "montantProduitHT", nullable = false, precision = 10)
    private double montantProduitHT;

    @NotNull
    @Column(name = "directFacture", nullable = false)
    private Integer directFacture;

    @NotNull
    @Column(name = "avecRemise", nullable = false)
    private Boolean avecRemise = false;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "factureId", nullable = false)
    private Facture facture;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stockId", nullable = false)
    private Stock stock;

}