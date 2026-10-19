package com.kabir.kabirbackend.config.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Body of {@code POST /livraison/imprimer/num-cheque}: the cheque number typed in
 * the Angular dialog (legacy {@code LivraisonActionBeans.numChequeLibel}).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivraisonNumChequeRequest {
    private String numCheque;
}
