package com.kabir.kabirbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.DetBulletinPai}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetBulletinPaiDTO implements Serializable {
    private Long id;
    private double prixlivraison;
    private int qtevendu;
    private double prixvente;
    private double remise;
    private double mantantvendu;
    private double commission;
    private double commisiondh;
    private double mantant;
    private double benDH;
    private double commissionFixe;
    private double prixCommercial;
    private double primeCommercial;
    private double primeProduit;
    private boolean avecZero;
    private Long bulletinPaiId;
    private Long produitId;
    private String produitDesignation;
    private double produitPattc;
    private double produitPvttc;
    private int produitQteStock;

    public DetBulletinPaiDTO(
            Long produitId,
            double prixlivraison,
            long qtevendu,
            double prixvente,
            double remise,
            double mantantvendu,
            double benDH,
            double commission,
            boolean avecZero
    ) {

        this.produitId = produitId;
        this.prixlivraison = prixlivraison;
        this.qtevendu = (int) qtevendu;
        this.prixvente = prixvente;
        this.remise = remise;
        this.mantantvendu = mantantvendu;
        this.commission = commission;
        this.commisiondh = 0;
        this.mantant = 0;
        this.benDH = benDH;
        this.commissionFixe = 0;
        this.prixCommercial = 0;
        this.primeCommercial = 0;
        this.avecZero = avecZero;
    }
}