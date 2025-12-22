package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

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
    @Column(name = "debit", nullable = false, precision = 10, scale = 2)
    private BigDecimal debit;

    @NotNull
    @Column(name = "credit", nullable = false, precision = 10, scale = 2)
    private BigDecimal credit;

    @NotNull
    @Column(name = "solde", nullable = false, precision = 10, scale = 2)
    private BigDecimal solde;

    @Size(max = 191)
    @NotNull
    @Column(name = "nature", nullable = false, length = 191)
    private String nature;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "planComptableId", nullable = false)
    private Plancomptable planComptable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Instant dateOperation) {
        this.dateOperation = dateOperation;
    }

    public LocalDate getDateReglement() {
        return dateReglement;
    }

    public void setDateReglement(LocalDate dateReglement) {
        this.dateReglement = dateReglement;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public Plancomptable getPlanComptable() {
        return planComptable;
    }

    public void setPlanComptable(Plancomptable planComptable) {
        this.planComptable = planComptable;
    }

}