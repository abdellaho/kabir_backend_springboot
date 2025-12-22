package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

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
    private Integer numFacture;

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
    @Column(name = "mantantAvancs1", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantAvancs1;

    @NotNull
    @Column(name = "dateAvances2", nullable = false)
    private LocalDate dateAvances2;

    @NotNull
    @Column(name = "mantantAvancs2", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantAvancs2;

    @NotNull
    @Column(name = "totalPaye", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPaye;

    @NotNull
    @Column(name = "mntFacture", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntFacture;

    @NotNull
    @Column(name = "mntDouane", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntDouane;

    @NotNull
    @Column(name = "mntTransport", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntTransport;

    @NotNull
    @Column(name = "mntTransportIntern", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntTransportIntern;

    @NotNull
    @Column(name = "mntTransit", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntTransit;

    @NotNull
    @Column(name = "mntMagasinage", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntMagasinage;

    @NotNull
    @Column(name = "prixAchatDetaille", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixAchatDetaille;

    @NotNull
    @Column(name = "totalAllMnt", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAllMnt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "repertoireFourId", nullable = false)
    private Repertoire repertoireFour;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employeOperateurId", nullable = false)
    private Employe employeOperateur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeFacture() {
        return codeFacture;
    }

    public void setCodeFacture(String codeFacture) {
        this.codeFacture = codeFacture;
    }

    public String getDevisEuroDolar() {
        return devisEuroDolar;
    }

    public void setDevisEuroDolar(String devisEuroDolar) {
        this.devisEuroDolar = devisEuroDolar;
    }

    public Integer getNumFacture() {
        return numFacture;
    }

    public void setNumFacture(Integer numFacture) {
        this.numFacture = numFacture;
    }

    public LocalDate getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(LocalDate dateFacture) {
        this.dateFacture = dateFacture;
    }

    public Instant getSysDate() {
        return sysDate;
    }

    public void setSysDate(Instant sysDate) {
        this.sysDate = sysDate;
    }

    public LocalDate getDateAvances1() {
        return dateAvances1;
    }

    public void setDateAvances1(LocalDate dateAvances1) {
        this.dateAvances1 = dateAvances1;
    }

    public BigDecimal getMantantAvancs1() {
        return mantantAvancs1;
    }

    public void setMantantAvancs1(BigDecimal mantantAvancs1) {
        this.mantantAvancs1 = mantantAvancs1;
    }

    public LocalDate getDateAvances2() {
        return dateAvances2;
    }

    public void setDateAvances2(LocalDate dateAvances2) {
        this.dateAvances2 = dateAvances2;
    }

    public BigDecimal getMantantAvancs2() {
        return mantantAvancs2;
    }

    public void setMantantAvancs2(BigDecimal mantantAvancs2) {
        this.mantantAvancs2 = mantantAvancs2;
    }

    public BigDecimal getTotalPaye() {
        return totalPaye;
    }

    public void setTotalPaye(BigDecimal totalPaye) {
        this.totalPaye = totalPaye;
    }

    public BigDecimal getMntFacture() {
        return mntFacture;
    }

    public void setMntFacture(BigDecimal mntFacture) {
        this.mntFacture = mntFacture;
    }

    public BigDecimal getMntDouane() {
        return mntDouane;
    }

    public void setMntDouane(BigDecimal mntDouane) {
        this.mntDouane = mntDouane;
    }

    public BigDecimal getMntTransport() {
        return mntTransport;
    }

    public void setMntTransport(BigDecimal mntTransport) {
        this.mntTransport = mntTransport;
    }

    public BigDecimal getMntTransportIntern() {
        return mntTransportIntern;
    }

    public void setMntTransportIntern(BigDecimal mntTransportIntern) {
        this.mntTransportIntern = mntTransportIntern;
    }

    public BigDecimal getMntTransit() {
        return mntTransit;
    }

    public void setMntTransit(BigDecimal mntTransit) {
        this.mntTransit = mntTransit;
    }

    public BigDecimal getMntMagasinage() {
        return mntMagasinage;
    }

    public void setMntMagasinage(BigDecimal mntMagasinage) {
        this.mntMagasinage = mntMagasinage;
    }

    public BigDecimal getPrixAchatDetaille() {
        return prixAchatDetaille;
    }

    public void setPrixAchatDetaille(BigDecimal prixAchatDetaille) {
        this.prixAchatDetaille = prixAchatDetaille;
    }

    public BigDecimal getTotalAllMnt() {
        return totalAllMnt;
    }

    public void setTotalAllMnt(BigDecimal totalAllMnt) {
        this.totalAllMnt = totalAllMnt;
    }

    public Repertoire getRepertoireFour() {
        return repertoireFour;
    }

    public void setRepertoireFour(Repertoire repertoireFour) {
        this.repertoireFour = repertoireFour;
    }

    public Employe getEmployeOperateur() {
        return employeOperateur;
    }

    public void setEmployeOperateur(Employe employeOperateur) {
        this.employeOperateur = employeOperateur;
    }

}