package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "bilan")
public class Bilan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "date1", nullable = false)
    private LocalDate date1;

    @NotNull
    @Column(name = "date2", nullable = false)
    private LocalDate date2;

    @NotNull
    @Column(name = "stock", nullable = false, precision = 10)
    private double stock;

    @NotNull
    @Column(name = "banque", nullable = false, precision = 10)
    private double banque;

    @NotNull
    @Column(name = "caisse1", nullable = false, precision = 10)
    private double caisse1;

    @NotNull
    @Column(name = "caisse2", nullable = false, precision = 10)
    private double caisse2;

    @NotNull
    @Column(name = "actifDivers", nullable = false, precision = 10)
    private double actifDivers;

    @NotNull
    @Column(name = "capital", nullable = false, precision = 10)
    private double capital;

    @NotNull
    @Column(name = "dgi", nullable = false, precision = 10)
    private double dgi;

    @NotNull
    @Column(name = "cnss", nullable = false, precision = 10)
    private double cnss;

    @NotNull
    @Column(name = "loyer", nullable = false, precision = 10)
    private double loyer;

    @NotNull
    @Column(name = "salaire", nullable = false, precision = 10)
    private double salaire;

    @NotNull
    @Column(name = "tva", nullable = false, precision = 10)
    private double tva;

    @NotNull
    @Column(name = "chiffreAffaire", nullable = false, precision = 10)
    private double chiffreAffaire;

    @NotNull
    @Column(name = "passifDivers", nullable = false, precision = 10)
    private double passifDivers;

    @NotNull
    @Column(name = "compteCourant", nullable = false, precision = 10)
    private double compteCourant;

    @NotNull
    @Column(name = "resultatPrecedent", nullable = false, precision = 10)
    private double resultatPrecedent;

    @NotNull
    @Column(name = "resultat", nullable = false, precision = 10)
    private double resultat;

}