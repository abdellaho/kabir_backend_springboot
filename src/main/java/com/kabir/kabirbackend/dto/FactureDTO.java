package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Facture}
 */
@Data
public class FactureDTO implements Serializable {
    Long id;
    int numFacture;
    @NotNull
    @Size(max = 191)
    String codeBF;
    @NotNull
    LocalDate dateBF;
    @NotNull
    LocalDate dateReglement;
    @NotNull
    LocalDate dateReglement2;
    @NotNull
    LocalDate dateReglement3;
    @NotNull
    LocalDate dateReglement4;
    int typeReglment;
    int typeReglment2;
    int typeReglment3;
    int typeReglment4;
    @NotNull
    @Size(max = 191)
    String typePaiement;
    double mantantBF;
    double mantantBFBenefice;
    double tva7Total;
    double tva20Total;
    @NotNull
    Instant sysDate;
    double mantantBFHT;
    @NotNull
    @Size(max = 191)
    String numeroLibil;
    double mntHT2O;
    double mntHT7;
    double tva7;
    double tva20;
    @NotNull
    @Size(max = 191)
    String numCheque;
    @NotNull
    @Size(max = 191)
    String numCheque2;
    @NotNull
    @Size(max = 191)
    String numCheque3;
    @NotNull
    @Size(max = 191)
    String numCheque4;
    @NotNull
    @Size(max = 191)
    String numRemise;
    @NotNull
    @Size(max = 191)
    String numRemise2;
    @NotNull
    @Size(max = 191)
    String numRemise3;
    @NotNull
    @Size(max = 191)
    String numRemise4;
    double mntReglement;
    double mntReglement2;
    double mntReglement3;
    double mntReglement4;
    int type;
    boolean facturer100;
    boolean calculer;
    boolean disableMontant;
    @NotNull
    @Size(max = 191)
    String typeTVA;
    boolean dateReglementIn;
    boolean dateReglement2In;
    boolean dateReglement3In;
    boolean dateReglement4In;
    double tva20Reglement1;
    double tva20Reglement2;
    double tva20Reglement3;
    double tva20Reglement4;
    double mntHT20Reglement1;
    double mntHT20Reglement2;
}