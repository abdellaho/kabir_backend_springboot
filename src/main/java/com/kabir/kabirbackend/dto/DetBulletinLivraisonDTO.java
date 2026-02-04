package com.kabir.kabirbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

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
    private LocalDate livraisonDateBl;
    private double livraisonMantantBL;
    private double livraisonMantantBLReel;
    private int livraisonReglerNonRegler;
    private String livraisonRepertoireDesignation;

    public DetBulletinLivraisonDTO(Long livraisonId, String livraisonCodeBl, LocalDate livraisonDateBl, double livraisonMantantBL, double livraisonMantantBLReel, int livraisonReglerNonRegler, String livraisonRepertoireDesignation, double benDH) {
        this.livraisonId = livraisonId;
        this.livraisonDateBl = livraisonDateBl;
        this.livraisonCodeBl = livraisonCodeBl;
        this.livraisonMantantBL = livraisonMantantBL;
        this.livraisonMantantBLReel = livraisonMantantBLReel;
        this.livraisonReglerNonRegler = livraisonReglerNonRegler;
        this.livraisonRepertoireDesignation = livraisonRepertoireDesignation;
        this.benDH = benDH;
        this.commission = 0.0;
        this.commissionFixe = 0.0;
        this.commsiondh = 0.0;
        this.mantantcommission = 0.0;
        this.rougenormal = true;
    }
}