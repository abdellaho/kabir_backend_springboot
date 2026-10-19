package com.kabir.kabirbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datasource row of {@code etatCommercialDetailProduit.jrxml} (Consulter
 * Commercial, mode "par Produit"): one aggregated product of one commercial.
 * <p>
 * Port of the legacy {@code ma.kabir.conf.DetLivraisonImpr} constructor
 * {@code (stock.designation, sum(qteLivrer), sum(montantProduit), type)}
 * used by {@code LivraisonActionBeans.imprimerCommercialDetail()}.
 * {@code type} is 0 for a paid product (montantProduit != 0) and 1 for a free one.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivraisonProduitPrintDTO {
    private String designation;
    private Long qteLivrer;
    private Double montantProduit;
    private Integer type;
}
