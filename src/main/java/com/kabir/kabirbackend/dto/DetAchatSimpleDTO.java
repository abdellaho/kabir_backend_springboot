package com.kabir.kabirbackend.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.DetAchatSimple}
 */
@Data
public class DetAchatSimpleDTO implements Serializable {
    private Long id;
    private int qte;
    private int uniteGratuite;
    private double remise;
    private double prixAchat;
    private double montant;
    private Long stockId;
    private String stockDesignation;
    private Long achatSimpleId;
    private double achatSimpleMontant;
    private LocalDate achatSimpleDateOperation;
}