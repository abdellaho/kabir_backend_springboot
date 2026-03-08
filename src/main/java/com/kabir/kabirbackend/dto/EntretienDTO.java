package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Entretien}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EntretienDTO implements Serializable {

    private Long id;
    @NotNull
    private Long voitureId;
    private String voitureNumVoiture;
    private int voitureKmMax;
    @NotNull
    private LocalDate dateEntretien;
    private Instant dateSys;
    private int kmDetecte;
    private int kmMax;
    private boolean huile;
    private boolean filtreHuile;
    private boolean filtreCarburant;
    private boolean filtreAir;
    private boolean plaquetteAV;
    private boolean plaquetteAR;
    private boolean pneuAV;
    private boolean pneuAR;
    private boolean kitDistribution;
    private boolean batterie;
}