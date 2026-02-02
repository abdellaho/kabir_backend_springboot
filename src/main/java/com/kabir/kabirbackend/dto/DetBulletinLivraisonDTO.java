package com.kabir.kabirbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.DetBulletinLivraison}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetBulletinLivraisonDTO implements Serializable {
    private Long id;
    private double commission;
    private double commissionFixe;
    private double commsiondh;
    private double mantantcommission;
    private double benDH;
    private boolean rougenormal;
    private Long bulletinPaiId;
    private Long livraisonId;
    private String livraisonCodeBl;
    private double livraisonMantantBL;

    public DetBulletinLivraisonDTO(Long livraisonId, double benDH, double livraisonMantantBL, String livraisonCodeBl) {
        this.livraisonId = livraisonId;
        this.livraisonMantantBL = livraisonMantantBL;
        this.benDH = benDH;
        this.livraisonCodeBl = livraisonCodeBl;
        this.commission = 0.0;
        this.commissionFixe = 0.0;
        this.commsiondh = 0.0;
        this.mantantcommission = 0.0;
        this.rougenormal = true;
    }
}