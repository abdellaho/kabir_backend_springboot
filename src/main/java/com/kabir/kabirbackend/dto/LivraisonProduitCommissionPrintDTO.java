package com.kabir.kabirbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datasource row of {@code etatCommercialDetailProduitByCommission.jrxml} (Consulter
 * Commercial, mode "par Commission"): one delivered product with its unit price, its
 * rate and the resulting commission.
 * <p>
 * Port of the legacy {@code LivraisonActionBeans.imprimerCommercialDetail()} branch
 * {@code byBlOrProduit == 3}, adapted to the new app: the rate comes from the single
 * {@code Stock.commission} field instead of the legacy {@code Stock.taux1..4}.
 * {@code type} is always 0 (paid products; free rows are merged into the same row).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivraisonProduitCommissionPrintDTO {
    private String designation;
    private Long qteLivrer;
    private Double montantProduit;
    private Integer type;
    private Double prixUnitaire;
    private Double taux;
    private Double commission;
}
