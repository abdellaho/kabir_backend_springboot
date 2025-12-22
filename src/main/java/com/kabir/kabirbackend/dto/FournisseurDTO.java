package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Fournisseur}
 */
@Value
public class FournisseurDTO implements Serializable {
    Long id;
    @NotNull
    @Size(max = 191)
    String designation;
    @NotNull
    Integer type;
    @NotNull
    @Size(max = 191)
    String tel1;
    @NotNull
    @Size(max = 191)
    String tel2;
    @NotNull
    @Size(max = 191)
    String ice;
    @NotNull
    @Size(max = 191)
    String adresse;
    LocalDate dateSuppression;
    @NotNull
    boolean archiver;
    @NotNull
    boolean supprimer;
    VilleDTO ville;
}