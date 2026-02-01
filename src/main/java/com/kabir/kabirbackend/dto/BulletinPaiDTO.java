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
 * DTO for {@link com.kabir.kabirbackend.entities.BulletinPai}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulletinPaiDTO implements Serializable {
    private Long id;
    @NotNull
    @Size(max = 191)
    private String codeBulletin;
    @NotNull
    @Size(max = 191)
    private String observation;
    private int numBulletin;
    @NotNull
    private Instant dateOperation;
    @NotNull
    private LocalDate dateDebut;
    @NotNull
    private LocalDate dateFin;
    private int typeBulletinPai;
    private double salairefx;
    private double commission;
    private double frais;
    private double total;
    private double totalMntVendue;
    private double totalMntVendueProduit;
    private double totalMntVendueLivraison;
    private double mntNegative;
    private double mntNegativeProduit;
    private double mntNegativeLivraison;
    private double mntCNSS;
    private double mntPenalite;
    private double mntBenefice;
    private double commissionParProduit;
    private double primeSpecial;
    private double fraisSupp;
    private double primeCommercial;
    private boolean externe = false;
    private double totalMntVenduePrixCommercial;
    private double totalMntVendueSansPrixCommercial;
    private double primeProduit;
    private Long commercialId;
    private String commercialDesignation;
    private String commercialCin;
    private String commercialTel1;
    private String commercialTel2;
    private Long operateurId;
    private String operateurDesignation;
}