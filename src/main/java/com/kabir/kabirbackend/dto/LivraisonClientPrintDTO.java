package com.kabir.kabirbackend.dto;

import com.kabir.kabirbackend.entities.Livraison;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datasource row of {@code listeJournaliereCleint.jrxml}: one livraison of one
 * client together with the benefice total of that client.
 * <p>
 * Port of the legacy {@code ma.kabir.conf.ClientLivraison} wrapper used by
 * {@code LivraisonActionBeans.imprimerClient()}.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivraisonClientPrintDTO {
    private Livraison livraison;
    private double sumBeneficeDH;
}
