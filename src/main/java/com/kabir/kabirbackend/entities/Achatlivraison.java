package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "achatlivraison")
public class Achatlivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeAL", nullable = false, length = 191)
    private String codeAL;

    @NotNull
    @Column(name = "numAchat", nullable = false)
    private Integer numAchat;

    @NotNull
    @Column(name = "dateAL", nullable = false)
    private LocalDate dateAL;

    @NotNull
    @Column(name = "sysDate", nullable = false)
    private Instant sysDate;

    @NotNull
    @Column(name = "prixNormalAchatHt", nullable = false)
    private Integer prixNormalAchatHt;

    @Size(max = 191)
    @NotNull
    @Column(name = "numeroBlExterne", nullable = false, length = 191)
    private String numeroBlExterne;

    @NotNull
    @Column(name = "mantantAL", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantAL;

    @NotNull
    @Column(name = "mantantALTTC", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantALTTC;

    @NotNull
    @Column(name = "mantantALTVA20", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantALTVA20;

    @NotNull
    @Column(name = "mantantALTVA7", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantALTVA7;

    @NotNull
    @Column(name = "totalMantantALTVA", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalMantantALTVA;

    @NotNull
    @Column(name = "mantantBFBenefice", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantBFBenefice;

    @NotNull
    @Column(name = "montantTVA7", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTVA7;

    @NotNull
    @Column(name = "montantTVA10", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTVA10;

    @NotNull
    @Column(name = "montantTVA14", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTVA14;

    @NotNull
    @Column(name = "montantTVA20", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTVA20;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeAL() {
        return codeAL;
    }

    public void setCodeAL(String codeAL) {
        this.codeAL = codeAL;
    }

    public Integer getNumAchat() {
        return numAchat;
    }

    public void setNumAchat(Integer numAchat) {
        this.numAchat = numAchat;
    }

    public LocalDate getDateAL() {
        return dateAL;
    }

    public void setDateAL(LocalDate dateAL) {
        this.dateAL = dateAL;
    }

    public Instant getSysDate() {
        return sysDate;
    }

    public void setSysDate(Instant sysDate) {
        this.sysDate = sysDate;
    }

    public Integer getPrixNormalAchatHt() {
        return prixNormalAchatHt;
    }

    public void setPrixNormalAchatHt(Integer prixNormalAchatHt) {
        this.prixNormalAchatHt = prixNormalAchatHt;
    }

    public String getNumeroBlExterne() {
        return numeroBlExterne;
    }

    public void setNumeroBlExterne(String numeroBlExterne) {
        this.numeroBlExterne = numeroBlExterne;
    }

    public BigDecimal getMantantAL() {
        return mantantAL;
    }

    public void setMantantAL(BigDecimal mantantAL) {
        this.mantantAL = mantantAL;
    }

    public BigDecimal getMantantALTTC() {
        return mantantALTTC;
    }

    public void setMantantALTTC(BigDecimal mantantALTTC) {
        this.mantantALTTC = mantantALTTC;
    }

    public BigDecimal getMantantALTVA20() {
        return mantantALTVA20;
    }

    public void setMantantALTVA20(BigDecimal mantantALTVA20) {
        this.mantantALTVA20 = mantantALTVA20;
    }

    public BigDecimal getMantantALTVA7() {
        return mantantALTVA7;
    }

    public void setMantantALTVA7(BigDecimal mantantALTVA7) {
        this.mantantALTVA7 = mantantALTVA7;
    }

    public BigDecimal getTotalMantantALTVA() {
        return totalMantantALTVA;
    }

    public void setTotalMantantALTVA(BigDecimal totalMantantALTVA) {
        this.totalMantantALTVA = totalMantantALTVA;
    }

    public BigDecimal getMantantBFBenefice() {
        return mantantBFBenefice;
    }

    public void setMantantBFBenefice(BigDecimal mantantBFBenefice) {
        this.mantantBFBenefice = mantantBFBenefice;
    }

    public BigDecimal getMontantTVA7() {
        return montantTVA7;
    }

    public void setMontantTVA7(BigDecimal montantTVA7) {
        this.montantTVA7 = montantTVA7;
    }

    public BigDecimal getMontantTVA10() {
        return montantTVA10;
    }

    public void setMontantTVA10(BigDecimal montantTVA10) {
        this.montantTVA10 = montantTVA10;
    }

    public BigDecimal getMontantTVA14() {
        return montantTVA14;
    }

    public void setMontantTVA14(BigDecimal montantTVA14) {
        this.montantTVA14 = montantTVA14;
    }

    public BigDecimal getMontantTVA20() {
        return montantTVA20;
    }

    public void setMontantTVA20(BigDecimal montantTVA20) {
        this.montantTVA20 = montantTVA20;
    }

}