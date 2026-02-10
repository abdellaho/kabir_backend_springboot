package com.kabir.kabirbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Absence}
 */
@Data
public class AbsenceDTO implements Serializable {
    Long id;
    @NotNull
    LocalDate dateAbsence;
    boolean matin;
    boolean apresMidi;
    @NotNull
    Instant dateOperation;
    Long personnelOperationId;
    @NotNull
    Long personnelId;
    String personnelDesignation;
    double personnelSalaire;
    @JsonIgnore
    double nbrJour;
}