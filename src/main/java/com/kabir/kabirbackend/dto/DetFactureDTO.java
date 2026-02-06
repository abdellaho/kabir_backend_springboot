package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.DetFacture}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetFactureDTO implements Serializable {
    private Long id;
    private int qteFacturer;
    private double remiseFacture;
    private double prixVente;
    private double montantProduit;
    private double beneficeDH;
    private double benepourcentage;
    private double tva7;
    private double tva20;
    private double montantProduitHT;
    @NotNull
    private int directFacture;
    @NotNull
    private boolean avecRemise = false;
    private Long factureId;
    private String factureCodeBF;
    private LocalDate factureDateBF;
    private Long stockId;
    private String stockDesignation;
    private double stockPvttc;
    private int stockQteFacturer;
}