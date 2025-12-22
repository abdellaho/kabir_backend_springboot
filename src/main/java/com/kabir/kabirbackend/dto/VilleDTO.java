package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Ville}
 */
@Data
public class VilleDTO implements Serializable {
    Long id;
    @NotNull
    @Size(max = 191)
    @NotBlank
    String nomVille;
}