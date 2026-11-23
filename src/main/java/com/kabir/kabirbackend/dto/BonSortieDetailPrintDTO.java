package com.kabir.kabirbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Datasource row of {@code etatBonSortieDetail.jrxml} and
 * {@code etatBonSortieDetailAll.jrxml}. {@code type} is 0 for a bon de sortie
 * row and 1 for a livraison row (legacy {@code BonSortieDetail}).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonSortieDetailPrintDTO {
    private Integer type;
    private Date date;
    private String num;
    private Integer qte;
    private String client;
    private String produit;
}
