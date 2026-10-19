package com.kabir.kabirbackend.config.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Print request of the livraison "Consulter" etats.
 * <p>
 * {@code type} mirrors the legacy {@code typeImpression} of
 * {@code LivraisonActionBeans.viderImprimerCommercialDetail(Integer)}:
 * <ul>
 *     <li>0 = Consulter Commercial</li>
 *     <li>1 = Consulter Produit</li>
 *     <li>2 = Consulter Villes</li>
 *     <li>3 = Consulter Client</li>
 *     <li>4 = Consulter Client + Regle / Non Regle</li>
 *     <li>5 = Consulter (journalier commercial)</li>
 *     <li>6 = Consulter Client + Produit</li>
 * </ul>
 * {@code byBlOrProduit} mirrors the legacy radio of the commercial dialog:
 * 0 = par BL, 1 = par Produit.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LivraisonPrintRequest {
    private int type;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Long repertoireId;
    private Long personnelId;
    private Long stockId;
    private int byBlOrProduit;
    /** Legacy "Consulter Client + Regle" selector: 0 = Non Regle, 1 = Regle, 2/unset = aucun. */
    private Integer reglerNonRegler;
    /** Legacy "Consulter Villes" ville selector (0 = Tous Les Villes). */
    private Long villeId;
    /** Legacy "Consulter Client + Produit" client selector (0 = none selected). */
    private Long repertoireClientId2;
}
