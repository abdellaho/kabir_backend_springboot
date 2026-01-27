package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.CompteCaisse}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompteCaisseDTO implements Serializable {
    private Long id;
    private LocalDate date;
    @Size(max = 191)
    private String designation;
    @Size(max = 191)
    private String numFacture;
    private double montant;
    private boolean compteCaisse;
}