package com.kabir.kabirbackend.dto;

import com.kabir.kabirbackend.entities.Livraison;
import com.kabir.kabirbackend.entities.Repertoire;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datasource row of {@code listeJournaliereCleintVille.jrxml}: one livraison of one
 * client together with the client repertoire (which carries the ville) and the
 * benefice total of that client.
 * <p>
 * Port of the legacy {@code ma.kabir.conf.ClientLivraison} wrapper used by
 * {@code LivraisonActionBeans.imprimerVille()}.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivraisonVillePrintDTO {
    private Livraison livraison;
    private double sumBeneficeDH;
    private Repertoire repertoire;
}
