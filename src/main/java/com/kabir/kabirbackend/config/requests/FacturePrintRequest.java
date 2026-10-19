package com.kabir.kabirbackend.config.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Print request of the facture "Consulter" etats.
 * <p>
 * {@code type} mirrors the legacy {@code typeImpression} of
 * {@code FactureActionBeans.viderImprimer(Integer)}:
 * <ul>
 *     <li>0 = Consulter Chèque (etatClienLibel2)</li>
 *     <li>1 = Consulter TV détaillé (etatClientTvaDetaille)</li>
 *     <li>2 = Consulter Bilan (etatFactureBilan)</li>
 *     <li>3 = Consulter Produit (listeProduitFacture / listeProduitFactureTous)</li>
 *     <li>4 = Consulter Client (listeVenteAchatCleint2)</li>
 * </ul>
 * {@code stockId} is only used for type 3 (0 = all products) and
 * {@code repertoireId} only for type 4 (0 = all clients).
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacturePrintRequest {
    private Integer type;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Long stockId;
    private Long repertoireId;
}
