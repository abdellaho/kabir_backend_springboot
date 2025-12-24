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
@Table(name = "balance")
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "dateBalance", nullable = false)
    private LocalDate dateBalance;

    @Size(max = 191)
    @NotNull
    @Column(name = "nature", nullable = false, length = 191)
    private String nature;

    @NotNull
    @Column(name = "debitPrec", nullable = false, precision = 10)
    private double debitPrec;

    @NotNull
    @Column(name = "creditPrec", nullable = false, precision = 10)
    private double creditPrec;

    @NotNull
    @Column(name = "debit", nullable = false, precision = 10)
    private double debit;

    @NotNull
    @Column(name = "credit", nullable = false, precision = 10)
    private double credit;

    @NotNull
    @Column(name = "soldeDebiteur", nullable = false, precision = 10)
    private double soldeDebiteur;

    @NotNull
    @Column(name = "soldeCrediteur", nullable = false, precision = 10)
    private double soldeCrediteur;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "planComptableId", nullable = false)
    private PlanComptable planComptable;

}