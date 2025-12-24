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
@Table(name = "bulttinpai")
public class BulttinPai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeBultin", nullable = false, length = 191)
    private String codeBultin;

    @Size(max = 191)
    @NotNull
    @Column(name = "observation", nullable = false, length = 191)
    private String observation;

    @NotNull
    @Column(name = "numbultin", nullable = false)
    private int numbultin;

    @NotNull
    @Column(name = "dateOperation", nullable = false)
    private Instant dateOperation;

    @NotNull
    @Column(name = "dateDebut", nullable = false)
    private LocalDate dateDebut;

    @NotNull
    @Column(name = "dateFin", nullable = false)
    private LocalDate dateFin;

    @NotNull
    @Column(name = "idTypeBultinPai", nullable = false)
    private int idTypeBultinPai;

    @NotNull
    @Column(name = "salairefx", nullable = false, precision = 10)
    private double salairefx;

    @NotNull
    @Column(name = "commission", nullable = false, precision = 10)
    private double commission;

    @NotNull
    @Column(name = "frais", nullable = false, precision = 10)
    private double frais;

    @NotNull
    @Column(name = "total", nullable = false, precision = 10)
    private double total;

    @NotNull
    @Column(name = "totalMntVendue", nullable = false, precision = 10)
    private double totalMntVendue;

    @NotNull
    @Column(name = "totalMntVendueProduit", nullable = false, precision = 10)
    private double totalMntVendueProduit;

    @NotNull
    @Column(name = "totalMntVendueLivraison", nullable = false, precision = 10)
    private double totalMntVendueLivraison;

    @NotNull
    @Column(name = "mntNegative", nullable = false, precision = 10)
    private double mntNegative;

    @NotNull
    @Column(name = "mntNegativeProduit", nullable = false, precision = 10)
    private double mntNegativeProduit;

    @NotNull
    @Column(name = "mntNegativeLivraison", nullable = false, precision = 10)
    private double mntNegativeLivraison;

    @NotNull
    @Column(name = "mntCNSS", nullable = false, precision = 10)
    private double mntCNSS;

    @NotNull
    @Column(name = "mntPenalite", nullable = false, precision = 10)
    private double mntPenalite;

    @NotNull
    @Column(name = "mntBenefice", nullable = false, precision = 10)
    private double mntBenefice;

    @NotNull
    @Column(name = "commissionParProduit", nullable = false, precision = 10)
    private double commissionParProduit;

    @NotNull
    @Column(name = "primeSpecial", nullable = false, precision = 10)
    private double primeSpecial;

    @NotNull
    @Column(name = "fraisSupp", nullable = false, precision = 10)
    private double fraisSupp;

    @NotNull
    @Column(name = "primeCommercial", nullable = false, precision = 10)
    private double primeCommercial;

    @NotNull
    @Column(name = "externe", nullable = false)
    private boolean externe = false;

    @NotNull
    @Column(name = "totalMntVenduePrixCommercial", nullable = false, precision = 10)
    private double totalMntVenduePrixCommercial;

    @NotNull
    @Column(name = "totalMntVendueSansPrixCommercial", nullable = false, precision = 10)
    private double totalMntVendueSansPrixCommercial;

    @NotNull
    @Column(name = "primeProduit", nullable = false, precision = 10)
    private double primeProduit;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "commercailId", nullable = false)
    private Repertoire commercail;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employeOperateurId", nullable = false)
    private Employe employeOperateur;
}