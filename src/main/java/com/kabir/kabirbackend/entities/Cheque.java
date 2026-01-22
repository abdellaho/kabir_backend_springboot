package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cheque")
public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "numero", nullable = false, length = 191)
    private String numero;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeCheque", nullable = false, length = 191)
    private String codeCheque;

    @NotNull
    @Column(name = "dateCheque", nullable = false)
    private LocalDate dateCheque;

    @NotNull
    @Column(name = "montant", nullable = false, precision = 10)
    private double montant;

    @NotNull
    @Column(name = "typePersoScte", nullable = false)
    private int typePersoScte;

    @NotNull
    @Column(name = "numCheque", nullable = false)
    private int numCheque;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatcheque", nullable = false)
    private boolean etatcheque = false;

    @NotNull
    @Column(name = "typeReglment", nullable = false)
    private int typeReglment;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "repertoireId", nullable = false)
    private Repertoire repertoire;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employeOperateurId", nullable = false)
    private Employe employeOperateur;

}