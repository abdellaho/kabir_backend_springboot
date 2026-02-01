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
@Table(name = "bulletinPai")
public class BulletinPai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "code", nullable = false, length = 191)
    private String codeBulletin;

    @Size(max = 191)
    @NotNull
    @Column(name = "observation", nullable = false, length = 191)
    private String observation;

    @NotNull
    @Column(name = "num_bulletin", nullable = false)
    private int numBulletin;

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
    @Column(name = "type_bulletin_pai", nullable = false)
    private int typeBulletinPai;

    @NotNull
    @Column(name = "salaire_fix", nullable = false, precision = 10)
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
    @Column(name = "total_mnt_vendue", nullable = false, precision = 10)
    private double totalMntVendue;

    @NotNull
    @Column(name = "total_mnt_vendue_produit", nullable = false, precision = 10)
    private double totalMntVendueProduit;

    @NotNull
    @Column(name = "total_mnt_vendue_livraison", nullable = false, precision = 10)
    private double totalMntVendueLivraison;

    @NotNull
    @Column(name = "mnt_negative", nullable = false, precision = 10)
    private double mntNegative;

    @NotNull
    @Column(name = "mnt_negative_produit", nullable = false, precision = 10)
    private double mntNegativeProduit;

    @NotNull
    @Column(name = "mnt_negative_livraison", nullable = false, precision = 10)
    private double mntNegativeLivraison;

    @NotNull
    @Column(name = "mnt_cnss", nullable = false, precision = 10)
    private double mntCNSS;

    @NotNull
    @Column(name = "mntPenalite", nullable = false, precision = 10)
    private double mntPenalite;

    @NotNull
    @Column(name = "mnt_benefice", nullable = false, precision = 10)
    private double mntBenefice;

    @NotNull
    @Column(name = "commission_par_produit", nullable = false, precision = 10)
    private double commissionParProduit;

    @NotNull
    @Column(name = "prime_special", nullable = false, precision = 10)
    private double primeSpecial;

    @NotNull
    @Column(name = "frais_supp", nullable = false, precision = 10)
    private double fraisSupp;

    @NotNull
    @Column(name = "prime_commercial", nullable = false, precision = 10)
    private double primeCommercial;

    @NotNull
    @Column(name = "externe", nullable = false)
    private boolean externe = false;

    @NotNull
    @Column(name = "total_mnt_vendue_prix_commercial", nullable = false, precision = 10)
    private double totalMntVenduePrixCommercial;

    @NotNull
    @Column(name = "total_mnt_vendue_sans_prix_commercial", nullable = false, precision = 10)
    private double totalMntVendueSansPrixCommercial;

    @NotNull
    @Column(name = "prime_produit", nullable = false, precision = 10)
    private double primeProduit;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "commercial_id", nullable = false)
    private Personnel commercial;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "operateur_id", nullable = false)
    private Personnel operateur;
}