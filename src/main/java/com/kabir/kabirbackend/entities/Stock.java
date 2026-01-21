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
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "designation", nullable = false, length = 191)
    private String designation;

    @NotNull
    @Column(name = "sysDate", nullable = false)
    private Instant sysDate;

    @NotNull
    @Column(name = "pahtGrossiste", nullable = false, precision = 10)
    private double pahtGrossiste;

    @NotNull
    @Column(name = "tva", nullable = false, precision = 10)
    private double tva;

    @NotNull
    @Column(name = "pattc", nullable = false, precision = 10)
    private double pattc;

    @NotNull
    @Column(name = "pvttc", nullable = false, precision = 10)
    private double pvttc;

    @NotNull
    @Column(name = "pvaht", nullable = false, precision = 10)
    private double pvaht;

    @NotNull
    @Column(name = "benifice", nullable = false, precision = 10)
    private double benifice;

    @NotNull
    @Column(name = "qteStock", nullable = false)
    private int qteStock;

    @NotNull
    @Column(name = "qteSortie", nullable = false, columnDefinition = "int default 0")
    private int qteSortie;

    @NotNull
    @Column(name = "qtePVMin1", nullable = false)
    private int qtePVMin1;

    @NotNull
    @Column(name = "qtePVMin2", nullable = false)
    private int qtePVMin2;

    @NotNull
    @Column(name = "qtePVMin3", nullable = false)
    private int qtePVMin3;

    @NotNull
    @Column(name = "qtePVMin4", nullable = false)
    private int qtePVMin4;

    @NotNull
    @Column(name = "qteFacturer", nullable = false)
    private int qteFacturer;

    @NotNull
    @Column(name = "prixVentMin1", nullable = false, precision = 10)
    private double prixVentMin1;

    @NotNull
    @Column(name = "prixVentMin2", nullable = false, precision = 10)
    private double prixVentMin2;

    @NotNull
    @Column(name = "prixVentMin3", nullable = false, precision = 10)
    private double prixVentMin3;

    @NotNull
    @Column(name = "prixVentMin4", nullable = false, precision = 10)
    private double prixVentMin4;

    @NotNull
    @Column(name = "remiseMax1", nullable = false, precision = 10)
    private double remiseMax1;

    @NotNull
    @Column(name = "remiseMax2", nullable = false, precision = 10)
    private double remiseMax2;

    @NotNull
    @Column(name = "remiseMax3", nullable = false, precision = 10)
    private double remiseMax3;

    @NotNull
    @Column(name = "remiseMax4", nullable = false, precision = 10)
    private double remiseMax4;

    @NotNull
    @Column(name = "prixImport", nullable = false, precision = 10)
    private double prixImport;

    @NotNull
    @Column(name = "commission", nullable = false, precision = 10)
    private double commission;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "archiver", nullable = false)
    private boolean archiver = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "supprimer", nullable = false)
    private boolean supprimer = false;

    @NotNull
    @Column(name = "qteStockImport", nullable = false)
    private int qteStockImport;

    @NotNull
    @Column(name = "montant1", nullable = false, precision = 10)
    private double montant1;

    @NotNull
    @Column(name = "montant2", nullable = false, precision = 10)
    private double montant2;

    @NotNull
    @Column(name = "montant3", nullable = false, precision = 10)
    private double montant3;

    @NotNull
    @Column(name = "prime1", nullable = false, precision = 10)
    private double prime1;

    @NotNull
    @Column(name = "prime2", nullable = false, precision = 10)
    private double prime2;

    @NotNull
    @Column(name = "prime3", nullable = false, precision = 10)
    private double prime3;

    @NotNull
    @Column(name = "type_produit", nullable = false, columnDefinition = "INTEGER(1) default 1")
    private int typeProduit;

    @Column(name = "dateSuppression")
    private LocalDate dateSuppression;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "fournisseurId", nullable = false)
    private Fournisseur fournisseur;

}