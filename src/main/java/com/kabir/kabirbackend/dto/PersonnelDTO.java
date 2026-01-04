package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Personnel}
 */
@Data
public class PersonnelDTO implements Serializable {
    Long id;
    @NotNull
    @Size(max = 191)
    @NotBlank
    String designation;
    @NotNull
    @Size(max = 191)
    String cin;
    @Size(max = 191)
    String login;
    @Size(max = 191)
    String password;
    String passwordFake;
    @NotNull
    int typePersonnel;
    @NotNull
    boolean etatComptePersonnel;
    @NotNull
    @Size(max = 191)
    String tel1;
    @NotNull
    @Size(max = 191)
    String tel2;
    @NotNull
    @Size(max = 191)
    String adresse;
    @Size(max = 191)
    String email;
    @NotNull
    LocalDate dateEntrer;
    LocalDate dateSuppression;
    @NotNull
    double salaire;
    @NotNull
    boolean archiver;
    @NotNull
    boolean supprimer;
    @NotNull
    boolean consulterStock;
    @NotNull
    boolean ajouterStock;
    @NotNull
    boolean modifierStock;
    @NotNull
    boolean supprimerStock;
    @NotNull
    boolean consulterRepertoire;
    @NotNull
    boolean ajouterRepertoire;
    @NotNull
    boolean modifierRepertoire;
    @NotNull
    boolean supprimerRepertoire;
}