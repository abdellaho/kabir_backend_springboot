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
    LocalDate dateAvances;
    double mantantAvancs;
    int prixNormalAchatHt;
    @NotNull
    @Size(max = 191)
    String numeroFacExterne;
    String numeroIF;
    double mantantAF;
    double mantantBFBenefice;
    double montantNonTaxable;
    @NotNull
    LocalDate dateReglement;
    int typeReglment;
    String typePaiement;
    String numCheque;
    double mntHt;
    double montantTVA;
    double totalMntProduit;
    double mantantTotHT;
    double mantantTotHTVA;
    double mantantTotTTC;
    double tva20;
    double tva7;
    double tvaArbtraire;
    int manuelAutomatique;
    double mntManuelTva7;
    double mntManuelTva10;
    double mntManuelTva12;
    double mntManuelTva13;
    double mntManuelTva14;
    double mntManuelTva20;
    double montantTVA7;
    double montantTVA10;
    double montantTVA12;
    double montantTVA13;
    double montantTVA14;
    double montantTVA20;
    double mntHtTVA7;
    double mntHtTVA10;
    double mntHtTVA12;
    double mntHtTVA13;
    double mntHtTVA14;
    double mntHtTVA20;
    double mntTtcTVA7;
    double mntTtcTVA10;
    double mntTtcTVA12;
    double mntTtcTVA13;
    double mntTtcTVA14;
    double mntTtcTVA20;
    double mntTtc;
    double montantDroitSupplementaire;
    boolean disabledHT;
    boolean disabledManuel;
    @NotNull
    Long fournisseurId;
}