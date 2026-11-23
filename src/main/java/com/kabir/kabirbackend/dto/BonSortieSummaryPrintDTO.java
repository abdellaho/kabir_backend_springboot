package com.kabir.kabirbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datasource row of {@code etatBonSortie.jrxml}: quantities per product,
 * split between bons de sortie ({@code qteSortie}) and livraisons ({@code qteVente}).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonSortieSummaryPrintDTO {
    private String stock;
    private Double pvttc;
    private Long qteSortie;
    private Long qteVente;
}
