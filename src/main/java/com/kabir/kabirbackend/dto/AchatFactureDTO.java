package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.AchatFacture}
 */
@Data
public class AchatFactureDTO implements Serializable {
    Long id;
    String codeAF;
    int numAchat;
    @NotNull
    LocalDate dateAF;
    @NotNull
    Instant sysDate;
    @NotNull
    @Size(max = 191)
    String numeroFacExterne;
    String numeroIF;
    @NotNull
    LocalDate dateReglement;
    int typeReglment;
    String typePaiement;
    String numCheque;
    private double mantantAF;
    private double totalMntProduit;
    private double mantantTotHT;
    private double mantantTotHTVA;
    private double mantantTotTTC;
    double tva20;
    double tva7;
    double mntTtc;
    @NotNull
    Long fournisseurId;
    String fournisseurDesignation;
    String fournisseurIce;

}