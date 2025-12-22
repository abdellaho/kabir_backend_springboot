package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Employe}
 */
@Value
public class EmployeDTO implements Serializable {
    Long id;
    @NotNull
    Integer numEmp;
    @NotNull
    Integer livrerNonLivrerDroit;
    @NotNull
    @Size(max = 191)
    String codeEmp;
    @NotNull
    @Size(max = 191)
    String nom;
    @NotNull
    @Size(max = 191)
    String prenom;
    @NotNull
    @Size(max = 191)
    String login;
    @NotNull
    @Size(max = 191)
    String motpass;
    @NotNull
    @Size(max = 191)
    String email;
    @NotNull
    Integer typeUser;
    @NotNull
    boolean etatCompte;
    @NotNull
    BigDecimal validationMnt;
    @NotNull
    @Size(max = 191)
    String motPassFake;
    @NotNull
    boolean commercial;
    @NotNull
    boolean pvLibre;
    @NotNull
    boolean gerant;
    @NotNull
    boolean magasinier;
    @NotNull
    boolean bulltinPaie;
    @NotNull
    boolean imprimStockSimple;
    @NotNull
    boolean livraisonLimite;
    Long repertoireId;
    String repertoireDesignation;
}