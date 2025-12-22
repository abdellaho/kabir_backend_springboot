package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Stock}
 */
@Value
public class StockDTO implements Serializable {
    Long id;
    @NotNull
    @Size(max = 191)
    String designation;
    @NotNull
    Instant sysDate;
    @NotNull
    double pahtGrossiste;
    @NotNull
    double prixCommercial;
    @NotNull
    double tva;
    @NotNull
    double pattc;
    @NotNull
    double pvttc;
    @NotNull
    double pvaht;
    @NotNull
    double benifice;
    @NotNull
    int qteStock;
    @NotNull
    int qtePVMin1;
    @NotNull
    int qtePVMin2;
    @NotNull
    int qtePVMin3;
    @NotNull
    int qtePVMin4;
    @NotNull
    int qteFacturer;
    @NotNull
    double prixVentMin1;
    @NotNull
    double prixVentMin2;
    @NotNull
    double prixVentMin3;
    @NotNull
    double prixVentMin4;
    @NotNull
    double remiseMax1;
    @NotNull
    double remiseMax2;
    @NotNull
    double remiseMax3;
    @NotNull
    double remiseMax4;
    @NotNull
    double prixImport;
    @NotNull
    double commission;
    @NotNull
    boolean archiver;
    @NotNull
    boolean supprimer;
    @NotNull
    int qteStockImport;
    @NotNull
    double montant1;
    @NotNull
    double montant2;
    @NotNull
    double montant3;
    @NotNull
    double prime1;
    @NotNull
    double prime2;
    @NotNull
    double prime3;
    LocalDate dateSuppression;
    Long fournisseurId;
    String fournisseurDesignation;
}