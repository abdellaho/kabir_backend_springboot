package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "achatfacture")
public class AchatFacture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeAF", nullable = false, length = 191)
    private String codeAF;

    @NotNull
    @Column(name = "numAchat", nullable = false)
    private int numAchat;

    @NotNull
    @Column(name = "dateAF", nullable = false)
    private LocalDate dateAF;

    @NotNull
    @Column(name = "sysDate", nullable = false)
    private Instant sysDate;

    @Size(max = 191)
    @NotNull
    @Column(name = "numeroFacExterne", nullable = false, length = 191)
    private String numeroFacExterne;

    @Size(max = 191)
    @NotNull
    @Column(name = "numeroIF", nullable = false, length = 191)
    private String numeroIF;

    @NotNull
    @Column(name = "mantantAF", nullable = false, precision = 10)
    private double mantantAF;

    @NotNull
    @Column(name = "dateReglement", nullable = false)
    private LocalDate dateReglement;

    @NotNull
    @Column(name = "typeReglment", nullable = false)
    private int typeReglment;

    @Size(max = 191)
    @NotNull
    @Column(name = "typePaiement", nullable = false, length = 191)
    private String typePaiement;

    @Size(max = 191)
    @NotNull
    @Column(name = "numCheque", nullable = false, length = 191)
    private String numCheque;

    @NotNull
    @Column(name = "totalMntProduit", nullable = false, precision = 10)
    private double totalMntProduit;

    @NotNull
    @Column(name = "mantantTotHT", nullable = false, precision = 10)
    private double mantantTotHT;

    @NotNull
    @Column(name = "mantantTotHTVA", nullable = false, precision = 10)
    private double mantantTotHTVA;

    @NotNull
    @Column(name = "mantantTotTTC", nullable = false, precision = 10)
    private double mantantTotTTC;

    @NotNull
    @Column(name = "tva20", nullable = false, precision = 10)
    private double tva20;

    @NotNull
    @Column(name = "tva7", nullable = false, precision = 10)
    private double tva7;

    @NotNull
    @Column(name = "mnt_ttc", nullable = false, precision = 10)
    private double mntTtc;


    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Fournisseur fournisseur;

}