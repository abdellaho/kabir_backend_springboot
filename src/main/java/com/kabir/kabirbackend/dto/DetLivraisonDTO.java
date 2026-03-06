package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.DetLivraison}
 */
@Data
public class DetLivraisonDTO implements Serializable {
    private Long id;
    @NotNull
    private int qteLivrer;
    @NotNull
    private int champsRouge;
    @NotNull
    private double remiseLivraison;
    @NotNull
    private double prixVente;
    @NotNull
    private double montantProduit;
    @NotNull
    private double beneficeDH;
    @NotNull
    private double benepourcentage;
    @NotNull
    private int infinity;
    @NotNull
    private boolean avecRemise;
    private Long livraisonId;
    private String livraisonCodeBl;
    private LocalDate livraisonDateBl;
    private String livraisonRepertoireDesignation;
    private String livraisonRepertoireTel1;
    private String livraisonRepertoireAdresse;
    private Long stockId;
    private String stockDesignation;
    private int stockQteStock;
    private int stockQteFacturer;
    private double stockPvttc;
    private double stockPattc;
    private double stockBenifice;


}