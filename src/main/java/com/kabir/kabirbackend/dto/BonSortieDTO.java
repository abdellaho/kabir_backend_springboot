package com.kabir.kabirbackend.dto;

import com.kabir.kabirbackend.config.annotations.NotEqualZero;
import com.kabir.kabirbackend.config.annotations.NotEqualZeroDouble;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.BonSortie}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonSortieDTO implements Serializable {
    private Long id;

    @NotEqualZero
    @Size(min = 1)
    private int numSortie;

    @NotNull
    @Size(max = 191)
    private String codeSortie;

    @NotNull
    private Instant dateOperation;

    @NotEqualZeroDouble
    private double mnt;

    @NotNull
    private Long personnelId;

    private String personnelDesignation;

    @NotNull
    private Long repertoireId;
    private String repertoireDesignation;
}