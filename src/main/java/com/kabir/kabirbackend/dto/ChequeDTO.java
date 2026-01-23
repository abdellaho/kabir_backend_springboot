package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Cheque}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChequeDTO implements Serializable {
    private Long id;
    @NotNull
    @Size(max = 191)
    private String numero;
    @NotNull
    @Size(max = 191)
    private String codeCheque;
    @NotNull
    private LocalDate dateCheque;
    private double montant;
    private int typePersoScte;
    private int numCheque;
    private boolean etatcheque = false;
    private int typeReglment;
    private Long fournisseurId;
    private String fournisseurDesignation;
    private String fournisseurTel1;
    private String fournisseurTel2;
    private String fournisseurAdresse;
    private String fournisseurIce;
    private Long operateurId;
}