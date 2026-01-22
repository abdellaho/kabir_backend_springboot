package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.DetAchatEtranger}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetAchatEtrangerDTO implements Serializable {
    private Long id;
    @NotNull
    private Long stockId;
    private String stockDesignation;
    private double stockTva;
    private double stockPattc;
    private double stockPvttc;
    private int stockQteStock;
    private int stockQteFacturer;
    private Long achatEtrangerId;
    private int qteAchat;
    private int qteStock;
    private double prixAchat;
}