package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Voiture}
 */
@Data
public class VoitureDTO implements Serializable {
    Long id;
    @NotNull
    @Size(max = 191)
    String numVoiture;
    int kmMax;
}