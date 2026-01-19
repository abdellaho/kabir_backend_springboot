package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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

    @NotNull
    @Column(name = "dateAvances", nullable = false)
    private LocalDate dateAvances;

    @NotNull
    @Column(name = "mantantAvancs", nullable = false, precision = 10)
    private double mantantAvancs;

    @NotNull
    @Column(name = "prixNormalAchatHt", nullable = false)
    private int prixNormalAchatHt;

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
    @Column(name = "mantantBFBenefice", nullable = false, precision = 10)
    private double mantantBFBenefice;

    @NotNull
    @Column(name = "montantNonTaxable", nullable = false, precision = 10)
    private double montantNonTaxable;

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
    @Column(name = "tvaArbtraire", nullable = false, precision = 10)
    private double tvaArbtraire;

    @NotNull
    @Column(name = "manuelAutoMatique", nullable = false)
    private int manuelAutoMatique;

    @NotNull
    @Column(name = "mntManuelTva7", nullable = false, precision = 10)
    private double mntManuelTva7;

    @NotNull
    @Column(name = "mntManuelTva10", nullable = false, precision = 10)
    private double mntManuelTva10;

    @NotNull
    @Column(name = "mntManuelTva12", nullable = false, precision = 10)
    private double mntManuelTva12;

    @NotNull
    @Column(name = "mntManuelTva13", nullable = false, precision = 10)
    private double mntManuelTva13;

    @NotNull
    @Column(name = "mntManuelTva14", nullable = false, precision = 10)
    private double mntManuelTva14;

    @NotNull
    @Column(name = "mntManuelTva20", nullable = false, precision = 10)
    private double mntManuelTva20;

    @NotNull
    @Column(name = "montantTVA7", nullable = false, precision = 10)
    private double montantTVA7;

    @NotNull
    @Column(name = "montantTVA10", nullable = false, precision = 10)
    private double montantTVA10;

    @NotNull
    @Column(name = "montantTVA12", nullable = false, precision = 10)
    private double montantTVA12;

    @NotNull
    @Column(name = "montantTVA13", nullable = false, precision = 10)
    private double montantTVA13;

    @NotNull
    @Column(name = "montantTVA14", nullable = false, precision = 10)
    private double montantTVA14;

    @NotNull
    @Column(name = "montantTVA20", nullable = false, precision = 10)
    private double montantTVA20;

    @NotNull
    @Column(name = "mntHtTVA7", nullable = false, precision = 10)
    private double mntHtTVA7;

    @NotNull
    @Column(name = "mntHtTVA10", nullable = false, precision = 10)
    private double mntHtTVA10;

    @NotNull
    @Column(name = "mntHtTVA12", nullable = false, precision = 10)
    private double mntHtTVA12;

    @NotNull
    @Column(name = "mntHtTVA13", nullable = false, precision = 10)
    private double mntHtTVA13;

    @NotNull
    @Column(name = "mntHtTVA14", nullable = false, precision = 10)
    private double mntHtTVA14;

    @NotNull
    @Column(name = "mntHtTVA20", nullable = false, precision = 10)
    private double mntHtTVA20;

    @NotNull
    @Column(name = "mntTtcTVA7", nullable = false, precision = 10)
    private double mntTtcTVA7;

    @NotNull
    @Column(name = "mntTtcTVA10", nullable = false, precision = 10)
    private double mntTtcTVA10;

    @NotNull
    @Column(name = "mntTtcTVA12", nullable = false, precision = 10)
    private double mntTtcTVA12;

    @NotNull
    @Column(name = "mntTtcTVA13", nullable = false, precision = 10)
    private double mntTtcTVA13;

    @NotNull
    @Column(name = "mntTtcTVA14", nullable = false, precision = 10)
    private double mntTtcTVA14;

    @NotNull
    @Column(name = "mntTtcTVA20", nullable = false, precision = 10)
    private double mntTtcTVA20;

    @NotNull
    @Column(name = "mntTtc", nullable = false, precision = 10)
    private double mntTtc;

    @NotNull
    @Column(name = "montantDroitSupplementaire", nullable = false, precision = 10)
    private double montantDroitSupplementaire;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "disabledHT", nullable = false)
    private boolean disabledHT = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "disabledManuel", nullable = false)
    private boolean disabledManuel = false;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Fournisseur fournisseur;

}