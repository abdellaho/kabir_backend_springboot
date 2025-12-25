package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.StockDepot}
 */
@Data
public class StockDepotDTO implements Serializable {
    Long id;
    @NotNull
    LocalDate dateOperation;
    @NotNull
    Instant dateSys;
    @NotNull
    @NotEmpty
    @NotBlank
    String numBlExterne;
    double montantTTC;
}