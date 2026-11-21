package com.kabir.kabirbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Datasource row of {@code BonSortie.jrxml}: one detail line of one bon de sortie.
 * <p>
 * Port of the legacy {@code BonSortieActionBeans.imprimerBonSortie()} which fed the
 * {@code DetailBonSortie} list (ordered by {@code stock.designation}) to the report.
 * The new model replaced {@code bonSortie.repertoire} with {@code bonSortie.commercial}
 * and has no {@code stock.prixAchatBiggerPrixVente}, so the report field descriptions
 * are mapped to this flat DTO instead.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonSortiePrintDTO {
    /** {@code bonSortie.commercial.designation} (legacy {@code bonSortie.repertoire.designation}). */
    private String client;
    /** {@code bonSortie.dateOperation}. */
    private Date dateLivr;
    /** {@code bonSortie.codeSortie}. */
    private String numBl;
    /** {@code detailBonSortie.qteSortie}. */
    private Integer qteLivr;
    /** {@code detailBonSortie.mntProduit}. */
    private Double montant;
    /** {@code stock.benifice}. */
    private Double benifice;
    /** {@code stock.designation}. */
    private String produit;
    /** Legacy flag not present in the new {@code Stock} entity; always {@code false}. */
    private Boolean prixAchatBiggerPrixVente;
    /** {@code stock.prixVentMin1}. */
    private Double prixVentMin1;
    /** {@code stock.prixVentMin2}. */
    private Double prixVentMin2;
}
