package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Caisse}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaisseDTO implements Serializable {
    private Long id;
    private double montant;
    @NotNull
    private LocalDate dateOperation;
    private int type;
}