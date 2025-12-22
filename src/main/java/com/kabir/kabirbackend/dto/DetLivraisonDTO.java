package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Detlivraison}
 */
@Value
public class DetLivraisonDTO implements Serializable {
    Long id;
    @NotNull
    int qteLivrer;
    @NotNull
    int champsRouge;
    @NotNull
    double remiseLivraison;
    @NotNull
    double prixVente;
    @NotNull
    double montantProduit;
    @NotNull
    double beneficeDH;
    @NotNull
    double benepourcentage;
    @NotNull
    int infinity;
    @NotNull
    boolean avecRemise;
    Long livraisonId;
    String livraisonCodeBl;
    LocalDate livraisonDateBl;
    Long stockId;
    String stockDesignation;
}