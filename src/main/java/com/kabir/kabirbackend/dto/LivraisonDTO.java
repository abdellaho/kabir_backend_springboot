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
    private Long id;
    private int numLivraison;
    @NotNull
    @NotEmpty
    @Size(max = 191)
    private String codeBl;
    @NotNull
    private LocalDate dateBl;
    @NotNull
    private LocalDate dateReglement;
    private LocalDate dateReglement2;
    private LocalDate dateReglement3;
    private LocalDate dateReglement4;
    private int typeReglment;
    private int typeReglment2;
    private int typeReglment3;
    private int typeReglment4;
    private String numCheque;
    private String numCheque2;
    private String numCheque3;
    private String numCheque4;
    private double mantantBL;
    private double mantantBLReel;
    private double mantantBLBenefice;
    @Size(max = 191)
    String typePaiement;
    private double mantantBLPourcent;
    private int reglerNonRegler;
    @NotNull
    private Instant sysDate;
    @NotNull
    private int infinity;
    private int etatBultinPaie;
    private int livrernonlivrer;
    private boolean avecRemise;
    private double mntReglement;
    private double mntReglement2;
    private double mntReglement3;
    private double mntReglement4;
    private boolean facturer100;
    @NotNull
    @Size(max = 191)
    private String codeTransport;
    private Long employeOperateurId;
    private String employeOperateurDesignation;
    @Min(value = 1)
    @NotNull
    private Long personnelId;
    private String personnelDesignation;
    private Long personnelAncienId;
    private String personnelAncienDesignation;
    @Min(value = 1)
    @NotNull
    private Long repertoireId;
    private String repertoireDesignation;
    private String repertoireObservation;
    private int repertoireNbrOperationClient;
    private Long repertoireIdOld;

}