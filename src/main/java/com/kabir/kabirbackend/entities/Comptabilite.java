package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comptabilite")
public class Comptabilite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "montantTTC", nullable = false, precision = 10)
    private double montantTTC;

    @NotNull
    @Column(name = "montantTVA", nullable = false, precision = 10)
    private double montantTVA;

    @NotNull
    @Column(name = "montantTVA0", nullable = false, precision = 10)
    private double montantTVA0;

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

    @NotNull
    @Column(name = "montantHT", nullable = false, precision = 10)
    private double montantHT;

    @NotNull
    @Column(name = "montantHT7", nullable = false, precision = 10)
    private double montantHT7;

    @NotNull
    @Column(name = "montantHT10", nullable = false, precision = 10)
    private double montantHT10;

    @NotNull
    @Column(name = "montantHT14", nullable = false, precision = 10)
    private double montantHT14;

    @NotNull
    @Column(name = "montantHT20", nullable = false, precision = 10)
    private double montantHT20;

    @Size(max = 191)
    @NotNull
    @Column(name = "modeReglement", nullable = false, length = 191)
    private String modeReglement;

    @NotNull
    @Column(name = "typeManuelAuto", nullable = false)
    private int typeManuelAuto;

    @NotNull
    @Column(name = "typeReglement", nullable = false)
    private int typeReglement;

    @NotNull
    @Column(name = "dateFacture", nullable = false)
    private LocalDate dateFacture;

    @NotNull
    @Column(name = "dateRegelement", nullable = false)
    private LocalDate dateRegelement;

    @Size(max = 191)
    @NotNull
    @Column(name = "numFcture", nullable = false, length = 191)
    private String numFcture;

    @NotNull
    @Column(name = "montantDroitSupplementaire", nullable = false, precision = 10)
    private double montantDroitSupplementaire;

    @NotNull
    @Column(name = "typeRepertoire", nullable = false)
    private int typeRepertoire;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "repertoireId", nullable = false)
    private Repertoire repertoire;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employeOperateurId", nullable = false)
    private Employe employeOperateur;

}