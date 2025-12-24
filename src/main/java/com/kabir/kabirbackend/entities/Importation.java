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
@Table(name = "importations")
public class Importation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeFacture", nullable = false, length = 191)
    private String codeFacture;

    @Size(max = 191)
    @NotNull
    @Column(name = "devisEuroDolar", nullable = false, length = 191)
    private String devisEuroDolar;

    @NotNull
    @Column(name = "numFacture", nullable = false)
    private int numFacture;

    @NotNull
    @Column(name = "dateFacture", nullable = false)
    private LocalDate dateFacture;

    @NotNull
    @Column(name = "sysDate", nullable = false)
    private Instant sysDate;

    @NotNull
    @Column(name = "dateAvances1", nullable = false)
    private LocalDate dateAvances1;

    @NotNull
    @Column(name = "mantantAvancs1", nullable = false, precision = 10)
    private double mantantAvancs1;

    @NotNull
    @Column(name = "dateAvances2", nullable = false)
    private LocalDate dateAvances2;

    @NotNull
    @Column(name = "mantantAvancs2", nullable = false, precision = 10)
    private double mantantAvancs2;

    @NotNull
    @Column(name = "totalPaye", nullable = false, precision = 10)
    private double totalPaye;

    @NotNull
    @Column(name = "mntFacture", nullable = false, precision = 10)
    private double mntFacture;

    @NotNull
    @Column(name = "mntDouane", nullable = false, precision = 10)
    private double mntDouane;

    @NotNull
    @Column(name = "mntTransport", nullable = false, precision = 10)
    private double mntTransport;

    @NotNull
    @Column(name = "mntTransportIntern", nullable = false, precision = 10)
    private double mntTransportIntern;

    @NotNull
    @Column(name = "mntTransit", nullable = false, precision = 10)
    private double mntTransit;

    @NotNull
    @Column(name = "mntMagasinage", nullable = false, precision = 10)
    private double mntMagasinage;

    @NotNull
    @Column(name = "prixAchatDetaille", nullable = false, precision = 10)
    private double prixAchatDetaille;

    @NotNull
    @Column(name = "totalAllMnt", nullable = false, precision = 10)
    private double totalAllMnt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "repertoireFourId", nullable = false)
    private Repertoire repertoireFour;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employeOperateurId", nullable = false)
    private Employe employeOperateur;

}