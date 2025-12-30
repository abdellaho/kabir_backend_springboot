package com.kabir.kabirbackend.dto;

import com.kabir.kabirbackend.config.annotations.NotEqualZero;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.DetailBonSortie}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailBonSortieDTO implements Serializable {
    private Long id;
    @NotEqualZero
    private int qteSortie;
    private double mntProduit;
    private double total;
    private Long bonSortieId;
    private String bonSortieCodeSortie;
    @NotNull
    private Long stockId;
    private String stockDesignation;
    private int stockQteStock;
}