package com.kabir.kabirbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.DetAchatFacture}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetAchatFactureDTO implements Serializable {
    private Long id;
    private int uniteGratuit;
    private int qteAcheter;
    private double prixAchatHt;
    private double prixAchatTtc;
    private double remiseAchat;
    private double prixVenteTtc;
    private double mantantHt;
    private double mantantTTC;
    private double prixVenteAchatHT;
    private double beneficeDH;
    private double benePourcentage;
    private double tva20;
    private double tva7;
    private Long achatFactureId;
    private String achatFactureCodeAF;
    private Long stockId;
    private String stockDesignation;
    private double stockPvttc;
    private int stockQteStock;
    private int stockQteFacturer;
}