package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "livraison")
public class Livraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "numLivraison", nullable = false)
    private int numLivraison;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeBl", nullable = false, length = 191)
    private String codeBl;

    @NotNull
    @Column(name = "dateBl", nullable = false)
    private LocalDate dateBl;

    @NotNull
    @Column(name = "dateReglement", nullable = false)
    private LocalDate dateReglement;

    @Column(name = "dateReglement2")
    private LocalDate dateReglement2;

    @Column(name = "dateReglement3")
    private LocalDate dateReglement3;

    @Column(name = "dateReglement4")
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

    @Column(name = "numCheque")
    private String numCheque;

    @Column(name = "numCheque2")
    private String numCheque2;

    @Column(name = "numCheque3")
    private String numCheque3;

    @Column(name = "numCheque4")
    private String numCheque4;

    @NotNull
    @Column(name = "mantantBL", nullable = false, precision = 10)
    private double mantantBL;

    @NotNull
    @Column(name = "mantantBLReel", nullable = false, precision = 10)
    private double mantantBLReel;

    @NotNull
    @Column(name = "mantantBLBenefice", nullable = false, precision = 10)
    private double mantantBLBenefice;

    @Size(max = 191)
    @Column(name = "typePaiement", nullable = false, length = 191)
    private String typePaiement;

    @NotNull
    @Column(name = "mantantBLPourcent", nullable = false, precision = 10)
    private double mantantBLPourcent;

    @NotNull
    @Column(name = "reglerNonRegler", nullable = false)
    private int reglerNonRegler;

    @NotNull
    @Column(name = "sysDate", nullable = false)
    private Instant sysDate;

    @NotNull
    @Column(name = "infinity", nullable = false)
    private int infinity;

    @NotNull
    @Column(name = "etatBultinPaie", nullable = false)
    private int etatBultinPaie;

    @NotNull
    @Column(name = "livrernonlivrer", nullable = false)
    private int livrernonlivrer;

    @NotNull
    @Column(name = "avecRemise", nullable = false)
    private boolean avecRemise = false;

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
    @ColumnDefault("0")
    @Column(name = "facturer100", nullable = false)
    private boolean facturer100 = false;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeTransport", length = 191)
    private String codeTransport;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employeOperateurId")
    private Personnel employeOperateur;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "personnelId", nullable = false)
    private Personnel personnel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "personnelAncienId")
    private Personnel personnelAncien;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "repertoireId", nullable = false)
    private Repertoire repertoire;
}