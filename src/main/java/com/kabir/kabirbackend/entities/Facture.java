package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "facture")
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "numFacture", nullable = false)
    private Integer numFacture;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeBF", nullable = false, length = 191)
    private String codeBF;

    @NotNull
    @Column(name = "dateBF", nullable = false)
    private LocalDate dateBF;

    @NotNull
    @Column(name = "dateReglement", nullable = false)
    private LocalDate dateReglement;

    @NotNull
    @Column(name = "dateReglement2", nullable = false)
    private LocalDate dateReglement2;

    @NotNull
    @Column(name = "dateReglement3", nullable = false)
    private LocalDate dateReglement3;

    @NotNull
    @Column(name = "dateReglement4", nullable = false)
    private LocalDate dateReglement4;

    @NotNull
    @Column(name = "typeReglment", nullable = false)
    private int typeReglment;

    @NotNull
    @Column(name = "typeReglment2", nullable = false)
    private int typeReglment2;

    @NotNull
    @Column(name = "typeReglment3", nullable = false)
    private int typeReglment3;

    @NotNull
    @Column(name = "typeReglment4", nullable = false)
    private int typeReglment4;

    @Size(max = 191)
    @NotNull
    @Column(name = "typePaiement", nullable = false, length = 191)
    private String typePaiement;

    @NotNull
    @Column(name = "mantantBF", nullable = false, precision = 10)
    private double mantantBF;

    @NotNull
    @Column(name = "mantantBFBenefice", nullable = false, precision = 10)
    private double mantantBFBenefice;

    @NotNull
    @Column(name = "tva7Total", nullable = false, precision = 10)
    private double tva7Total;

    @NotNull
    @Column(name = "tva20Total", nullable = false, precision = 10)
    private double tva20Total;

    @NotNull
    @Column(name = "sysDate", nullable = false)
    private Instant sysDate;

    @NotNull
    @Column(name = "mantantBFHT", nullable = false, precision = 10)
    private double mantantBFHT;

    @Size(max = 191)
    @NotNull
    @Column(name = "numeroLibil", nullable = false, length = 191)
    private String numeroLibil;

    @NotNull
    @Column(name = "mntHT2O", nullable = false, precision = 10)
    private double mntHT2O;

    @NotNull
    @Column(name = "mntHT7", nullable = false, precision = 10)
    private double mntHT7;

    @NotNull
    @Column(name = "tva7", nullable = false, precision = 10)
    private double tva7;

    @NotNull
    @Column(name = "tva20", nullable = false, precision = 10)
    private double tva20;

    @Size(max = 191)
    @NotNull
    @Column(name = "numCheque", nullable = false, length = 191)
    private String numCheque;

    @Size(max = 191)
    @NotNull
    @Column(name = "numCheque2", nullable = false, length = 191)
    private String numCheque2;

    @Size(max = 191)
    @NotNull
    @Column(name = "numCheque3", nullable = false, length = 191)
    private String numCheque3;

    @Size(max = 191)
    @NotNull
    @Column(name = "numCheque4", nullable = false, length = 191)
    private String numCheque4;

    @Size(max = 191)
    @NotNull
    @Column(name = "numRemise", nullable = false, length = 191)
    private String numRemise;

    @Size(max = 191)
    @NotNull
    @Column(name = "numRemise2", nullable = false, length = 191)
    private String numRemise2;

    @Size(max = 191)
    @NotNull
    @Column(name = "numRemise3", nullable = false, length = 191)
    private String numRemise3;

    @Size(max = 191)
    @NotNull
    @Column(name = "numRemise4", nullable = false, length = 191)
    private String numRemise4;

    @NotNull
    @Column(name = "mntReglement", nullable = false, precision = 10)
    private double mntReglement;

    @NotNull
    @Column(name = "mntReglement2", nullable = false, precision = 10)
    private double mntReglement2;

    @NotNull
    @Column(name = "mntReglement3", nullable = false, precision = 10)
    private double mntReglement3;

    @NotNull
    @Column(name = "mntReglement4", nullable = false, precision = 10)
    private double mntReglement4;

    @NotNull
    @Column(name = "type", nullable = false)
    private int type;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "facturer100", nullable = false)
    private boolean facturer100 = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "calculer", nullable = false)
    private boolean calculer = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "disableMontant", nullable = false)
    private boolean disableMontant = false;

    @Size(max = 191)
    @NotNull
    @Column(name = "typeTVA", nullable = false, length = 191)
    private String typeTVA;

    @Transient
    private boolean dateReglementIn;

    @Transient
    private boolean dateReglement2In;

    @Transient
    private boolean dateReglement3In;

    @Transient
    private boolean dateReglement4In;

    @NotNull
    @Column(name = "tva20Reglement1", nullable = false, precision = 10)
    private double tva20Reglement1;

    @NotNull
    @Column(name = "tva20Reglement2", nullable = false, precision = 10)
    private double tva20Reglement2;

    @NotNull
    @Column(name = "tva20Reglement3", nullable = false, precision = 10)
    private double tva20Reglement3;

    @NotNull
    @Column(name = "tva20Reglement4", nullable = false, precision = 10)
    private double tva20Reglement4;

    @NotNull
    @Column(name = "mntHT20Reglement1", nullable = false, precision = 10)
    private double mntHT20Reglement1;

    @NotNull
    @Column(name = "mntHT20Reglement2", nullable = false, precision = 10)
    private double mntHT20Reglement2;

}