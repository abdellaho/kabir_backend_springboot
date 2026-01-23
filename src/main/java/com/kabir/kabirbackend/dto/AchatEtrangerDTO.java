package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.AchatEtranger}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AchatEtrangerDTO implements Serializable {
    private Long id;
    @NotNull
    private Long fournisseurId;
    private String fournisseurDesignation;
    private String fournisseurTel1;
    private String fournisseurTel2;
    private String fournisseurIce;
    private String fournisseurAdresse;
    private Long operateurId;
    @NotNull
    private String codeFacture;
    private int numFacture;
    @NotNull
    private LocalDate dateFacture;
    private Instant sysDate;
    private LocalDate dateAvances1;
    private double mantantAvancs1;
    private LocalDate dateAvances2;
    private double mantantAvancs2;
    private double totalPaye;
    private double mntFacture;
    private double mntDouane;
    private double mntTransport;
    private double mntTransportIntern;
    private double mntTransit;
    private double mntMagazinage;
    private double prixAchatDetaille;
    private double totalAllMnt;
}