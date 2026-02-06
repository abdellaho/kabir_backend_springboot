package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Stock}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO implements Serializable {
    private Long id;

    @NotNull
    @Size(max = 191)
    private String designation;

    @NotNull
    private Instant sysDate;

    @NotNull
    private double pahtGrossiste;

    @NotNull
    private double tva;

    @NotNull
    private double pattc;

    @NotNull
    private double pvttc;

    @NotNull
    private double pvaht;

    @NotNull
    private double benifice;

    @NotNull
    private int qteStock;

    @NotNull
    private int qteSortie;

    @NotNull
    private int qtePVMin1;

    @NotNull
    private int qtePVMin2;

    @NotNull
    private int qtePVMin3;

    @NotNull
    private int qtePVMin4;

    @NotNull
    private int qteFacturer;

    @NotNull
    double prixVentMin1;

    @NotNull
    private double prixVentMin2;

    @NotNull
    private double prixVentMin3;

    @NotNull
    private double prixVentMin4;

    @NotNull
    private double remiseMax1;

    @NotNull
    private double remiseMax2;

    @NotNull
    private double remiseMax3;

    @NotNull
    private double remiseMax4;

    @NotNull
    private double prixImport;

    @NotNull
    private double commission;

    @NotNull
    private boolean archiver;

    @NotNull
    private boolean supprimer;

    @NotNull
    private int qteStockImport;

    @NotNull
    private double montant1;

    @NotNull
    private double montant2;

    @NotNull
    private double montant3;

    @NotNull
    private double prime1;

    @NotNull
    private double prime2;

    @NotNull
    private double prime3;

    @NotNull
    int typeProduit;

    private LocalDate dateSuppression;

    @NotNull
    private Long fournisseurId;

    private String fournisseurDesignation;
}