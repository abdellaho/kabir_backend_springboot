package com.kabir.kabirbackend.dto;

import com.kabir.kabirbackend.config.annotations.NotEqualZero;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.DetStockDepot}
 */
@Data
public class DetStockDepotDTO implements Serializable {
    Long id;
    @NotNull
    @NotEqualZero
    int qte;
    @NotNull
    Long stockDepotId;
    @NotNull
    Long stockId;
    String stockDesignation;
    int stockQteStock;
    double stockPvttc;
    double stockPattc;
}