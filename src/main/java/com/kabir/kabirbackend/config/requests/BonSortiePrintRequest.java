package com.kabir.kabirbackend.config.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Print request of the bon de sortie "Consulter" etats (POST /bon-sortie/imprimer/etat).
 * <p>
 * {@code stockId} selects the report/mode, like the legacy
 * {@code BonSortieActionBeans.imprimer()}:
 * <ul>
 *     <li>0 = "Résumé par produit" ({@code etatBonSortie})</li>
 *     <li>-1 = all products, detailed ({@code etatBonSortieDetailAll})</li>
 *     <li>&gt; 0 = one product, detailed ({@code etatBonSortieDetail})</li>
 * </ul>
 * {@code personnelId} is the commercial and is mandatory.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BonSortiePrintRequest {
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Long personnelId;
    private Long stockId;
}
