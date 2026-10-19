package com.kabir.kabirbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class FactureDTO implements Serializable, Cloneable {
    Long id;
    int numFacture;
    @NotNull
    @Size(max = 191)
    String codeBF;
    @NotNull
    LocalDate dateBF;
    @NotNull
    LocalDate dateReglement;
    LocalDate dateReglement2;
    LocalDate dateReglement3;
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
    @Size(max = 191)
    String numCheque;
    @Size(max = 191)
    String numCheque2;
    @Size(max = 191)
    String numCheque3;
    @Size(max = 191)
    String numCheque4;
    @Size(max = 191)
    String numRemise;
    @Size(max = 191)
    String numRemise2;
    @Size(max = 191)
    String numRemise3;
    @Size(max = 191)
    String numRemise4;
    double mntReglement;
    double mntReglement2;
    double mntReglement3;
    double mntReglement4;
    int type;
    private Long employeOperateurId;
    private String employeOperateurDesignation;
    private Long personnelId;
    private String personnelDesignation;
    private Long repertoireId;
    private String repertoireDesignation;
    private String repertoireTel1;
    private String repertoireTel2;
    private String repertoireTel3;
    private String repertoireIce;
    private Long livraisonId;
    @JsonIgnore
    boolean facturer100;
    @JsonIgnore
    boolean calculer;
    @JsonIgnore
    @Size(max = 191)
    String typeTVA;
    @JsonIgnore
    boolean dateReglement2In;
    @JsonIgnore
    boolean dateReglement3In;
    @JsonIgnore
    boolean dateReglement4In;
    @JsonIgnore
    double tva20Reglement1;
    @JsonIgnore
    double tva20Reglement2;
    @JsonIgnore
    double tva20Reglement3;
    @JsonIgnore
    double tva20Reglement4;
    @JsonIgnore
    double mntHT20Reglement1;
    @JsonIgnore
    double mntHT20Reglement2;
    @JsonIgnore
    double mntHT20Reglement3;
    @JsonIgnore
    double mntHT20Reglement4;
    @JsonIgnore
    double totalNumRemise2;
    @JsonIgnore
    double totalNumRemise3;
    @JsonIgnore
    double totalNumRemise4;
    @JsonIgnore
    double mntHT20One;
    @JsonIgnore
    boolean dateReglementIn;
    @JsonIgnore
    double tva20One;
    @JsonIgnore
    LocalDate dateReglementOne;
    @JsonIgnore
    double mantantBFOne;
    @JsonIgnore
    String numRemiseTmp;
    @JsonIgnore
    String numChequeOne;
    @JsonIgnore
    String numRemiseOne;
    @JsonIgnore
    String typeReglementOne;
    @JsonIgnore
    String mntReglementOne;
    @JsonIgnore
    String dateReglementOneLettre;
    @JsonIgnore
    double totalNumRemise;

    @Override
    public FactureDTO clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (FactureDTO) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}