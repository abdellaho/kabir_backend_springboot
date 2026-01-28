package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Compta}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComptaDTO implements Serializable {
    private Long id;
    @NotNull
    private LocalDate dateDebut;
    @NotNull
    private LocalDate dateFin;
    private double montantTVAPrecedent;
    private double montantTVAAchat;
    private double montantTVAVente;
    private double resutMnt;
}