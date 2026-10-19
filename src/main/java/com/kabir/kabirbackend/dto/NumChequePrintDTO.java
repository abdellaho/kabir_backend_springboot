package com.kabir.kabirbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datasource row of {@code etatNumCheque.jrxml}: one product delivered against a
 * cheque number with its total quantity and total amount.
 * <p>
 * Port of the legacy {@code ma.kabir.conf.NumCheque} used by
 * {@code LivraisonActionBeans.imprimerParNumCheque()}.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumChequePrintDTO {
    private Long id;
    private String designation;
    private long qteLivrer;
    private double montantProduit;
}
