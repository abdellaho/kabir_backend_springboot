package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    @Column(name = "debitPrec", nullable = false, precision = 10, scale = 2)
    private BigDecimal debitPrec;

    @NotNull
    @Column(name = "creditPrec", nullable = false, precision = 10, scale = 2)
    private BigDecimal creditPrec;

    @NotNull
    @Column(name = "debit", nullable = false, precision = 10, scale = 2)
    private BigDecimal debit;

    @NotNull
    @Column(name = "credit", nullable = false, precision = 10, scale = 2)
    private BigDecimal credit;

    @NotNull
    @Column(name = "soldeDebiteur", nullable = false, precision = 10, scale = 2)
    private BigDecimal soldeDebiteur;

    @NotNull
    @Column(name = "soldeCrediteur", nullable = false, precision = 10, scale = 2)
    private BigDecimal soldeCrediteur;

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

    public LocalDate getDateBalance() {
        return dateBalance;
    }

    public void setDateBalance(LocalDate dateBalance) {
        this.dateBalance = dateBalance;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public BigDecimal getDebitPrec() {
        return debitPrec;
    }

    public void setDebitPrec(BigDecimal debitPrec) {
        this.debitPrec = debitPrec;
    }

    public BigDecimal getCreditPrec() {
        return creditPrec;
    }

    public void setCreditPrec(BigDecimal creditPrec) {
        this.creditPrec = creditPrec;
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

    public BigDecimal getSoldeDebiteur() {
        return soldeDebiteur;
    }

    public void setSoldeDebiteur(BigDecimal soldeDebiteur) {
        this.soldeDebiteur = soldeDebiteur;
    }

    public BigDecimal getSoldeCrediteur() {
        return soldeCrediteur;
    }

    public void setSoldeCrediteur(BigDecimal soldeCrediteur) {
        this.soldeCrediteur = soldeCrediteur;
    }

    public Plancomptable getPlanComptable() {
        return planComptable;
    }

    public void setPlanComptable(Plancomptable planComptable) {
        this.planComptable = planComptable;
    }

}