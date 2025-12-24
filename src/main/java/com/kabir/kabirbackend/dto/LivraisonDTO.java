package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Livraison}
 */
@Data
public class LivraisonDTO implements Serializable {
    Long id;
    int numLivraison;
    @NotNull
    @NotEmpty
    @Size(max = 191)
    String codeBl;
    @NotNull
    LocalDate dateBl;
    @NotNull
    LocalDate dateReglement;
    LocalDate dateReglement2;
    LocalDate dateReglement3;
    LocalDate dateReglement4;
    int typeReglment;
    int typeReglment2;
    int typeReglment3;
    int typeReglment4;
    String numCheque;
    String numCheque2;
    String numCheque3;
    String numCheque4;
    double mantantBL;
    double mantantBLReel;
    double mantantBLBenefice;
    @Size(max = 191)
    String typePaiement;
    double mantantBLPourcent;
    int reglerNonRegler;
    @NotNull
    Instant sysDate;
    @NotNull
    int infinity;
    int etatBultinPaie;
    int livrernonlivrer;
    boolean avecRemise;
    double mntReglement;
    double mntReglement2;
    double mntReglement3;
    double mntReglement4;
    boolean facturer100;
    @NotNull
    @Size(max = 191)
    String codeTransport;
    Long employeOperateurId;
    String employeOperateurNom;
    String employeOperateurPrenom;
    @Min(value = 1)
    @NotNull
    Long personnelId;
    String personnelDesignation;
    Long personnelAncienId;
    String personnelAncienDesignation;
    @Min(value = 1)
    @NotNull
    Long fournisseurId;
    String fournisseurDesignation;
}