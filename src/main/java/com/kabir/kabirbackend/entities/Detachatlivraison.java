package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "detachatlivraison")
public class Detachatlivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "unitegratuit", nullable = false)
    private Integer unitegratuit;

    @NotNull
    @Column(name = "qteacheter", nullable = false)
    private Integer qteacheter;

    @NotNull
    @Column(name = "prixAchatHt", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixAchatHt;

    @NotNull
    @Column(name = "prixAchatTtc", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixAchatTtc;

    @NotNull
    @Column(name = "remiseAchat", nullable = false, precision = 10, scale = 2)
    private BigDecimal remiseAchat;

    @NotNull
    @Column(name = "prixVenteAchatHT", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixVenteAchatHT;

    @NotNull
    @Column(name = "prixVenteTtc", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixVenteTtc;

    @NotNull
    @Column(name = "mantantHt", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantHt;

    @NotNull
    @Column(name = "mantantTTC", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantTTC;

    @NotNull
    @Column(name = "tva7", nullable = false, precision = 10, scale = 2)
    private BigDecimal tva7;

    @NotNull
    @Column(name = "tva20", nullable = false, precision = 10, scale = 2)
    private BigDecimal tva20;

    @NotNull
    @Column(name = "tva14", nullable = false, precision = 10, scale = 2)
    private BigDecimal tva14;

    @NotNull
    @Column(name = "tva10", nullable = false, precision = 10, scale = 2)
    private BigDecimal tva10;

    @NotNull
    @Column(name = "beneficeDH", nullable = false, precision = 10, scale = 2)
    private BigDecimal beneficeDH;

    @NotNull
    @Column(name = "benepourcentage", nullable = false, precision = 10, scale = 2)
    private BigDecimal benepourcentage;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "achatLivraisonId", nullable = false)
    private Achatlivraison achatLivraison;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stockId", nullable = false)
    private Stock stock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUnitegratuit() {
        return unitegratuit;
    }

    public void setUnitegratuit(Integer unitegratuit) {
        this.unitegratuit = unitegratuit;
    }

    public Integer getQteacheter() {
        return qteacheter;
    }

    public void setQteacheter(Integer qteacheter) {
        this.qteacheter = qteacheter;
    }

    public BigDecimal getPrixAchatHt() {
        return prixAchatHt;
    }

    public void setPrixAchatHt(BigDecimal prixAchatHt) {
        this.prixAchatHt = prixAchatHt;
    }

    public BigDecimal getPrixAchatTtc() {
        return prixAchatTtc;
    }

    public void setPrixAchatTtc(BigDecimal prixAchatTtc) {
        this.prixAchatTtc = prixAchatTtc;
    }

    public BigDecimal getRemiseAchat() {
        return remiseAchat;
    }

    public void setRemiseAchat(BigDecimal remiseAchat) {
        this.remiseAchat = remiseAchat;
    }

    public BigDecimal getPrixVenteAchatHT() {
        return prixVenteAchatHT;
    }

    public void setPrixVenteAchatHT(BigDecimal prixVenteAchatHT) {
        this.prixVenteAchatHT = prixVenteAchatHT;
    }

    public BigDecimal getPrixVenteTtc() {
        return prixVenteTtc;
    }

    public void setPrixVenteTtc(BigDecimal prixVenteTtc) {
        this.prixVenteTtc = prixVenteTtc;
    }

    public BigDecimal getMantantHt() {
        return mantantHt;
    }

    public void setMantantHt(BigDecimal mantantHt) {
        this.mantantHt = mantantHt;
    }

    public BigDecimal getMantantTTC() {
        return mantantTTC;
    }

    public void setMantantTTC(BigDecimal mantantTTC) {
        this.mantantTTC = mantantTTC;
    }

    public BigDecimal getTva7() {
        return tva7;
    }

    public void setTva7(BigDecimal tva7) {
        this.tva7 = tva7;
    }

    public BigDecimal getTva20() {
        return tva20;
    }

    public void setTva20(BigDecimal tva20) {
        this.tva20 = tva20;
    }

    public BigDecimal getTva14() {
        return tva14;
    }

    public void setTva14(BigDecimal tva14) {
        this.tva14 = tva14;
    }

    public BigDecimal getTva10() {
        return tva10;
    }

    public void setTva10(BigDecimal tva10) {
        this.tva10 = tva10;
    }

    public BigDecimal getBeneficeDH() {
        return beneficeDH;
    }

    public void setBeneficeDH(BigDecimal beneficeDH) {
        this.beneficeDH = beneficeDH;
    }

    public BigDecimal getBenepourcentage() {
        return benepourcentage;
    }

    public void setBenepourcentage(BigDecimal benepourcentage) {
        this.benepourcentage = benepourcentage;
    }

    public Achatlivraison getAchatLivraison() {
        return achatLivraison;
    }

    public void setAchatLivraison(Achatlivraison achatLivraison) {
        this.achatLivraison = achatLivraison;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

}