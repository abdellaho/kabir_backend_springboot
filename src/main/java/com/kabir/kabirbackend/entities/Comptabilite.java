package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "comptabilite")
public class Comptabilite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "montantTTC", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTTC;

    @NotNull
    @Column(name = "montantTVA", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTVA;

    @NotNull
    @Column(name = "montantTVA0", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTVA0;

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

    @NotNull
    @Column(name = "montantHT", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantHT;

    @NotNull
    @Column(name = "montantHT7", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantHT7;

    @NotNull
    @Column(name = "montantHT10", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantHT10;

    @NotNull
    @Column(name = "montantHT14", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantHT14;

    @NotNull
    @Column(name = "montantHT20", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantHT20;

    @Size(max = 191)
    @NotNull
    @Column(name = "modeReglement", nullable = false, length = 191)
    private String modeReglement;

    @NotNull
    @Column(name = "typeManuelAuto", nullable = false)
    private Integer typeManuelAuto;

    @NotNull
    @Column(name = "typeReglement", nullable = false)
    private Integer typeReglement;

    @NotNull
    @Column(name = "dateFacture", nullable = false)
    private LocalDate dateFacture;

    @NotNull
    @Column(name = "dateRegelement", nullable = false)
    private LocalDate dateRegelement;

    @Size(max = 191)
    @NotNull
    @Column(name = "numFcture", nullable = false, length = 191)
    private String numFcture;

    @NotNull
    @Column(name = "montantDroitSupplementaire", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantDroitSupplementaire;

    @NotNull
    @Column(name = "typeRepertoire", nullable = false)
    private Integer typeRepertoire;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "repertoireId", nullable = false)
    private Repertoire repertoire;

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

    public BigDecimal getMontantTTC() {
        return montantTTC;
    }

    public void setMontantTTC(BigDecimal montantTTC) {
        this.montantTTC = montantTTC;
    }

    public BigDecimal getMontantTVA() {
        return montantTVA;
    }

    public void setMontantTVA(BigDecimal montantTVA) {
        this.montantTVA = montantTVA;
    }

    public BigDecimal getMontantTVA0() {
        return montantTVA0;
    }

    public void setMontantTVA0(BigDecimal montantTVA0) {
        this.montantTVA0 = montantTVA0;
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

    public BigDecimal getMontantHT() {
        return montantHT;
    }

    public void setMontantHT(BigDecimal montantHT) {
        this.montantHT = montantHT;
    }

    public BigDecimal getMontantHT7() {
        return montantHT7;
    }

    public void setMontantHT7(BigDecimal montantHT7) {
        this.montantHT7 = montantHT7;
    }

    public BigDecimal getMontantHT10() {
        return montantHT10;
    }

    public void setMontantHT10(BigDecimal montantHT10) {
        this.montantHT10 = montantHT10;
    }

    public BigDecimal getMontantHT14() {
        return montantHT14;
    }

    public void setMontantHT14(BigDecimal montantHT14) {
        this.montantHT14 = montantHT14;
    }

    public BigDecimal getMontantHT20() {
        return montantHT20;
    }

    public void setMontantHT20(BigDecimal montantHT20) {
        this.montantHT20 = montantHT20;
    }

    public String getModeReglement() {
        return modeReglement;
    }

    public void setModeReglement(String modeReglement) {
        this.modeReglement = modeReglement;
    }

    public Integer getTypeManuelAuto() {
        return typeManuelAuto;
    }

    public void setTypeManuelAuto(Integer typeManuelAuto) {
        this.typeManuelAuto = typeManuelAuto;
    }

    public Integer getTypeReglement() {
        return typeReglement;
    }

    public void setTypeReglement(Integer typeReglement) {
        this.typeReglement = typeReglement;
    }

    public LocalDate getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(LocalDate dateFacture) {
        this.dateFacture = dateFacture;
    }

    public LocalDate getDateRegelement() {
        return dateRegelement;
    }

    public void setDateRegelement(LocalDate dateRegelement) {
        this.dateRegelement = dateRegelement;
    }

    public String getNumFcture() {
        return numFcture;
    }

    public void setNumFcture(String numFcture) {
        this.numFcture = numFcture;
    }

    public BigDecimal getMontantDroitSupplementaire() {
        return montantDroitSupplementaire;
    }

    public void setMontantDroitSupplementaire(BigDecimal montantDroitSupplementaire) {
        this.montantDroitSupplementaire = montantDroitSupplementaire;
    }

    public Integer getTypeRepertoire() {
        return typeRepertoire;
    }

    public void setTypeRepertoire(Integer typeRepertoire) {
        this.typeRepertoire = typeRepertoire;
    }

    public Repertoire getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(Repertoire repertoire) {
        this.repertoire = repertoire;
    }

    public Employe getEmployeOperateur() {
        return employeOperateur;
    }

    public void setEmployeOperateur(Employe employeOperateur) {
        this.employeOperateur = employeOperateur;
    }

}