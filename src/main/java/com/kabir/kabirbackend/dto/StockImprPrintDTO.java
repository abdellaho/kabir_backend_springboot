package com.kabir.kabirbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datasource row of {@code etatLivraisonProduitsParClient.jrxml}: one delivered
 * product of one client with its delivered quantity, aligned with a second
 * (non delivered) product designation.
 * <p>
 * Port of the legacy {@code ma.kabir.conf.StockImpr} used by
 * {@code LivraisonActionBeans.imprimerProduitsClient()}.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockImprPrintDTO {
    private Long id;
    private String designation;
    private long qteLivrer;
    private String designation2;

    public StockImprPrintDTO(Long id, String designation, long qteLivrer) {
        this.id = id;
        this.designation = designation;
        this.qteLivrer = qteLivrer;
    }
}
