package com.kabir.kabirbackend.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.DetAchatSimple}
 */
@Data
public class DetAchatSimpleDTO implements Serializable {
    Long id;
    int qte;
    int uniteGratuite;
    double remise;
    private double prixAchat;
    private double remiseAchat;
    private double montant;
    Long stockId;
    String stockDesignation;
    Long achatSimpleId;
}