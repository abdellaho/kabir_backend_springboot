package com.kabir.kabirbackend.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.DetAchatSimple}
 */
@Data
public class DetAchatSimpleDTO implements Serializable {
    Long id;
    int qte;
    double prixVente;
    int uniteGratuite;
    double remise;
    private double montant;
    Long stockId;
    String stockDesignation;
    double stockPvttc;
    int stockQteStock;
    Long achatSimpleId;
}