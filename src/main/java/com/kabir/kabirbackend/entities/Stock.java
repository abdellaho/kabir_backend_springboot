package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "designation", nullable = false, length = 191)
    private String designation;

    @NotNull
    @Column(name = "sysDate", nullable = false)
    private Instant sysDate;

    @NotNull
    @Column(name = "pahtGrossiste", nullable = false, precision = 10, scale = 2)
    private BigDecimal pahtGrossiste;

    @NotNull
    @Column(name = "prixCommercial", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixCommercial;

    @NotNull
    @Column(name = "tva", nullable = false, precision = 10, scale = 2)
    private BigDecimal tva;

    @NotNull
    @Column(name = "pattc", nullable = false, precision = 10, scale = 2)
    private BigDecimal pattc;

    @NotNull
    @Column(name = "pvttc", nullable = false, precision = 10, scale = 2)
    private BigDecimal pvttc;

    @NotNull
    @Column(name = "pvaht", nullable = false, precision = 10, scale = 2)
    private BigDecimal pvaht;

    @NotNull
    @Column(name = "benifice", nullable = false, precision = 10, scale = 2)
    private BigDecimal benifice;

    @NotNull
    @Column(name = "qteStock", nullable = false)
    private Integer qteStock;

    @NotNull
    @Column(name = "qtePVMin1", nullable = false)
    private Integer qtePVMin1;

    @NotNull
    @Column(name = "qtePVMin2", nullable = false)
    private Integer qtePVMin2;

    @NotNull
    @Column(name = "qtePVMin3", nullable = false)
    private Integer qtePVMin3;

    @NotNull
    @Column(name = "qtePVMin4", nullable = false)
    private Integer qtePVMin4;

    @NotNull
    @Column(name = "qteFacturer", nullable = false)
    private Integer qteFacturer;

    @NotNull
    @Column(name = "prixVentMin1", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixVentMin1;

    @NotNull
    @Column(name = "prixVentMin2", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixVentMin2;

    @NotNull
    @Column(name = "prixVentMin3", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixVentMin3;

    @NotNull
    @Column(name = "prixVentMin4", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixVentMin4;

    @NotNull
    @Column(name = "remiseMax1", nullable = false, precision = 10, scale = 2)
    private BigDecimal remiseMax1;

    @NotNull
    @Column(name = "remiseMax2", nullable = false, precision = 10, scale = 2)
    private BigDecimal remiseMax2;

    @NotNull
    @Column(name = "remiseMax3", nullable = false, precision = 10, scale = 2)
    private BigDecimal remiseMax3;

    @NotNull
    @Column(name = "remiseMax4", nullable = false, precision = 10, scale = 2)
    private BigDecimal remiseMax4;

    @NotNull
    @Column(name = "prixImport", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixImport;

    @NotNull
    @Column(name = "commission", nullable = false, precision = 10, scale = 2)
    private BigDecimal commission;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "archiver", nullable = false)
    private Boolean archiver = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "supprimer", nullable = false)
    private Boolean supprimer = false;

    @NotNull
    @Column(name = "qteStockImport", nullable = false)
    private Integer qteStockImport;

    @NotNull
    @Column(name = "montant1", nullable = false, precision = 10, scale = 2)
    private BigDecimal montant1;

    @NotNull
    @Column(name = "montant2", nullable = false, precision = 10, scale = 2)
    private BigDecimal montant2;

    @NotNull
    @Column(name = "montant3", nullable = false, precision = 10, scale = 2)
    private BigDecimal montant3;

    @NotNull
    @Column(name = "prime1", nullable = false, precision = 10, scale = 2)
    private BigDecimal prime1;

    @NotNull
    @Column(name = "prime2", nullable = false, precision = 10, scale = 2)
    private BigDecimal prime2;

    @NotNull
    @Column(name = "prime3", nullable = false, precision = 10, scale = 2)
    private BigDecimal prime3;

    @Column(name = "dateSuppression")
    private LocalDate dateSuppression;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fournisseurId", nullable = false)
    private Fournisseur fournisseur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Instant getSysDate() {
        return sysDate;
    }

    public void setSysDate(Instant sysDate) {
        this.sysDate = sysDate;
    }

    public BigDecimal getPahtGrossiste() {
        return pahtGrossiste;
    }

    public void setPahtGrossiste(BigDecimal pahtGrossiste) {
        this.pahtGrossiste = pahtGrossiste;
    }

    public BigDecimal getPrixCommercial() {
        return prixCommercial;
    }

    public void setPrixCommercial(BigDecimal prixCommercial) {
        this.prixCommercial = prixCommercial;
    }

    public BigDecimal getTva() {
        return tva;
    }

    public void setTva(BigDecimal tva) {
        this.tva = tva;
    }

    public BigDecimal getPattc() {
        return pattc;
    }

    public void setPattc(BigDecimal pattc) {
        this.pattc = pattc;
    }

    public BigDecimal getPvttc() {
        return pvttc;
    }

    public void setPvttc(BigDecimal pvttc) {
        this.pvttc = pvttc;
    }

    public BigDecimal getPvaht() {
        return pvaht;
    }

    public void setPvaht(BigDecimal pvaht) {
        this.pvaht = pvaht;
    }

    public BigDecimal getBenifice() {
        return benifice;
    }

    public void setBenifice(BigDecimal benifice) {
        this.benifice = benifice;
    }

    public Integer getQteStock() {
        return qteStock;
    }

    public void setQteStock(Integer qteStock) {
        this.qteStock = qteStock;
    }

    public Integer getQtePVMin1() {
        return qtePVMin1;
    }

    public void setQtePVMin1(Integer qtePVMin1) {
        this.qtePVMin1 = qtePVMin1;
    }

    public Integer getQtePVMin2() {
        return qtePVMin2;
    }

    public void setQtePVMin2(Integer qtePVMin2) {
        this.qtePVMin2 = qtePVMin2;
    }

    public Integer getQtePVMin3() {
        return qtePVMin3;
    }

    public void setQtePVMin3(Integer qtePVMin3) {
        this.qtePVMin3 = qtePVMin3;
    }

    public Integer getQtePVMin4() {
        return qtePVMin4;
    }

    public void setQtePVMin4(Integer qtePVMin4) {
        this.qtePVMin4 = qtePVMin4;
    }

    public Integer getQteFacturer() {
        return qteFacturer;
    }

    public void setQteFacturer(Integer qteFacturer) {
        this.qteFacturer = qteFacturer;
    }

    public BigDecimal getPrixVentMin1() {
        return prixVentMin1;
    }

    public void setPrixVentMin1(BigDecimal prixVentMin1) {
        this.prixVentMin1 = prixVentMin1;
    }

    public BigDecimal getPrixVentMin2() {
        return prixVentMin2;
    }

    public void setPrixVentMin2(BigDecimal prixVentMin2) {
        this.prixVentMin2 = prixVentMin2;
    }

    public BigDecimal getPrixVentMin3() {
        return prixVentMin3;
    }

    public void setPrixVentMin3(BigDecimal prixVentMin3) {
        this.prixVentMin3 = prixVentMin3;
    }

    public BigDecimal getPrixVentMin4() {
        return prixVentMin4;
    }

    public void setPrixVentMin4(BigDecimal prixVentMin4) {
        this.prixVentMin4 = prixVentMin4;
    }

    public BigDecimal getRemiseMax1() {
        return remiseMax1;
    }

    public void setRemiseMax1(BigDecimal remiseMax1) {
        this.remiseMax1 = remiseMax1;
    }

    public BigDecimal getRemiseMax2() {
        return remiseMax2;
    }

    public void setRemiseMax2(BigDecimal remiseMax2) {
        this.remiseMax2 = remiseMax2;
    }

    public BigDecimal getRemiseMax3() {
        return remiseMax3;
    }

    public void setRemiseMax3(BigDecimal remiseMax3) {
        this.remiseMax3 = remiseMax3;
    }

    public BigDecimal getRemiseMax4() {
        return remiseMax4;
    }

    public void setRemiseMax4(BigDecimal remiseMax4) {
        this.remiseMax4 = remiseMax4;
    }

    public BigDecimal getPrixImport() {
        return prixImport;
    }

    public void setPrixImport(BigDecimal prixImport) {
        this.prixImport = prixImport;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public Boolean getArchiver() {
        return archiver;
    }

    public void setArchiver(Boolean archiver) {
        this.archiver = archiver;
    }

    public Boolean getSupprimer() {
        return supprimer;
    }

    public void setSupprimer(Boolean supprimer) {
        this.supprimer = supprimer;
    }

    public Integer getQteStockImport() {
        return qteStockImport;
    }

    public void setQteStockImport(Integer qteStockImport) {
        this.qteStockImport = qteStockImport;
    }

    public BigDecimal getMontant1() {
        return montant1;
    }

    public void setMontant1(BigDecimal montant1) {
        this.montant1 = montant1;
    }

    public BigDecimal getMontant2() {
        return montant2;
    }

    public void setMontant2(BigDecimal montant2) {
        this.montant2 = montant2;
    }

    public BigDecimal getMontant3() {
        return montant3;
    }

    public void setMontant3(BigDecimal montant3) {
        this.montant3 = montant3;
    }

    public BigDecimal getPrime1() {
        return prime1;
    }

    public void setPrime1(BigDecimal prime1) {
        this.prime1 = prime1;
    }

    public BigDecimal getPrime2() {
        return prime2;
    }

    public void setPrime2(BigDecimal prime2) {
        this.prime2 = prime2;
    }

    public BigDecimal getPrime3() {
        return prime3;
    }

    public void setPrime3(BigDecimal prime3) {
        this.prime3 = prime3;
    }

    public LocalDate getDateSuppression() {
        return dateSuppression;
    }

    public void setDateSuppression(LocalDate dateSuppression) {
        this.dateSuppression = dateSuppression;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

}