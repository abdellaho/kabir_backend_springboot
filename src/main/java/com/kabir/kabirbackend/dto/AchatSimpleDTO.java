package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.AchatSimple}
 */
@Data
public class AchatSimpleDTO implements Serializable {
    Long id;
    @NotNull
    @Size(max = 191)
    String numBlExterne;
    double montant;
    @NotNull
    LocalDate dateOperation;
    Instant dateSys;
    Long fournisseurId;
    String fournisseurDesignation;
}