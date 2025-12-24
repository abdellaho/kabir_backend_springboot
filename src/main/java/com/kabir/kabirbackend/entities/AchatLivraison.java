package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "achatlivraison")
public class AchatLivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeAL", nullable = false, length = 191)
    private String codeAL;

    @NotNull
    @Column(name = "numAchat", nullable = false)
    private int numAchat;

    @NotNull
    @Column(name = "dateAL", nullable = false)
    private LocalDate dateAL;

    @NotNull
    @Column(name = "sysDate", nullable = false)
    private Instant sysDate;

    @NotNull
    @Column(name = "prixNormalAchatHt", nullable = false)
    private int prixNormalAchatHt;

    @Size(max = 191)
    @NotNull
    @Column(name = "numeroBlExterne", nullable = false, length = 191)
    private String numeroBlExterne;

    @NotNull
    @Column(name = "mantantAL", nullable = false, precision = 10)
    private double mantantAL;

    @NotNull
    @Column(name = "mantantALTTC", nullable = false, precision = 10)
    private double mantantALTTC;

    @NotNull
    @Column(name = "mantantALTVA20", nullable = false, precision = 10)
    private double mantantALTVA20;

    @NotNull
    @Column(name = "mantantALTVA7", nullable = false, precision = 10)
    private double mantantALTVA7;

    @NotNull
    @Column(name = "totalMantantALTVA", nullable = false, precision = 10)
    private double totalMantantALTVA;

    @NotNull
    @Column(name = "mantantBFBenefice", nullable = false, precision = 10)
    private double mantantBFBenefice;

    @NotNull
    @Column(name = "montantTVA7", nullable = false, precision = 10)
    private double montantTVA7;

    @NotNull
    @Column(name = "montantTVA10", nullable = false, precision = 10)
    private double montantTVA10;

    @NotNull
    @Column(name = "montantTVA14", nullable = false, precision = 10)
    private double montantTVA14;

    @NotNull
    @Column(name = "montantTVA20", nullable = false, precision = 10)
    private double montantTVA20;
}