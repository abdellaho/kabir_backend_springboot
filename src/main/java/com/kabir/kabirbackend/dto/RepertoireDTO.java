package com.kabir.kabirbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Repertoire}
 */
@Data
public class RepertoireDTO implements Serializable {
    Long id;
    @NotNull
    @Size(max = 191)
    String designation;
    @NotNull
    @Size(max = 191)
    String tel1;
    @NotNull
    @Size(max = 191)
    String tel2;
    @NotNull
    @Size(max = 191)
    String tel3;
    @NotNull
    @Size(max = 191)
    String adresse;
    @NotNull
    @Size(max = 191)
    String email;
    @NotNull
    int typeRepertoire;
    @NotNull
    int typeReglment;
    @NotNull
    @Size(max = 191)
    String ife;
    @NotNull
    @Size(max = 191)
    String ice;
    @NotNull
    boolean archiver;
    @NotNull
    boolean bloquer;
    @NotNull
    Instant sysDate;
    LocalDate dateSuppression;
    @NotNull
    @Size(max = 191)
    String observation;
    @NotNull
    int nbrOperationClient;
    @NotNull
    double plafond;
    Long personnelId;
    String personnelDesignation;
    Long villeId;
    String villeNomVille;
    @JsonIgnore
    LocalDate dateDernierBL;
}