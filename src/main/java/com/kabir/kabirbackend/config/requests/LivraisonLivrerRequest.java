package com.kabir.kabirbackend.config.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Body of {@code PATCH /livraison/{id}/livrer}: the row-level "Livrer" radio value
 * (legacy {@code LivraisonActionBeans.livrernonlivrer}, Oui=1 / Non=0 / Retour=2).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivraisonLivrerRequest {
    private int livrernonlivrer;
}
