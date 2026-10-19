package com.kabir.kabirbackend.dto;

import com.kabir.kabirbackend.entities.Stock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datasource row of {@code listeProduitLivrsTous.jrxml}: one product with the
 * quantity / amount / benefice delivered in the requested date range.
 * <p>
 * Port of the legacy {@code ma.kabir.conf.FournisseurProduit} wrapper used by
 * {@code LivraisonActionBeans.imprimerProduit()}. {@code type} is 0 for a
 * paid product (montantProduit != 0) and 1 for a free one.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockLivraisonSumPrintDTO {
    private Stock stock;
    private int qteLivre;
    private double montant;
    private double beneficeDH;
    private int type;
}
