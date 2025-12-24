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
@Table(name = "solde")
public class Solde {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "dateOperation", nullable = false)
    private Instant dateOperation;

    @NotNull
    @Column(name = "dateReglement", nullable = false)
    private LocalDate dateReglement;

    @NotNull
    @Column(name = "debit", nullable = false, precision = 10)
    private double debit;

    @NotNull
    @Column(name = "credit", nullable = false, precision = 10)
    private double credit;

    @NotNull
    @Column(name = "solde", nullable = false, precision = 10)
    private double solde;

    @Size(max = 191)
    @NotNull
    @Column(name = "nature", nullable = false, length = 191)
    private String nature;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "planComptableId", nullable = false)
    private PlanComptable planComptable;

}