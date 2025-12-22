package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "detfacture")
public class Detfacture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "qteFacturer", nullable = false)
    private Integer qteFacturer;

    @NotNull
    @Column(name = "remiseFacture", nullable = false, precision = 10, scale = 2)
    private BigDecimal remiseFacture;

    @NotNull
    @Column(name = "prixVente", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixVente;

    @NotNull
    @Column(name = "montantProduit", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantProduit;

    @NotNull
    @Column(name = "beneficeDH", nullable = false, precision = 10, scale = 2)
    private BigDecimal beneficeDH;

    @NotNull
    @Column(name = "benepourcentage", nullable = false, precision = 10, scale = 2)
    private BigDecimal benepourcentage;

    @NotNull
    @Column(name = "tva7", nullable = false, precision = 10, scale = 2)
    private BigDecimal tva7;

    @NotNull
    @Column(name = "tva20", nullable = false, precision = 10, scale = 2)
    private BigDecimal tva20;

    @NotNull
    @Column(name = "montantProduitHT", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantProduitHT;

    @NotNull
    @Column(name = "directFacture", nullable = false)
    private Integer directFacture;

    @NotNull
    @Column(name = "avecRemise", nullable = false)
    private Boolean avecRemise = false;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "factureId", nullable = false)
    private Facture facture;

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

    public Integer getQteFacturer() {
        return qteFacturer;
    }

    public void setQteFacturer(Integer qteFacturer) {
        this.qteFacturer = qteFacturer;
    }

    public BigDecimal getRemiseFacture() {
        return remiseFacture;
    }

    public void setRemiseFacture(BigDecimal remiseFacture) {
        this.remiseFacture = remiseFacture;
    }

    public BigDecimal getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(BigDecimal prixVente) {
        this.prixVente = prixVente;
    }

    public BigDecimal getMontantProduit() {
        return montantProduit;
    }

    public void setMontantProduit(BigDecimal montantProduit) {
        this.montantProduit = montantProduit;
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

    public BigDecimal getMontantProduitHT() {
        return montantProduitHT;
    }

    public void setMontantProduitHT(BigDecimal montantProduitHT) {
        this.montantProduitHT = montantProduitHT;
    }

    public Integer getDirectFacture() {
        return directFacture;
    }

    public void setDirectFacture(Integer directFacture) {
        this.directFacture = directFacture;
    }

    public Boolean getAvecRemise() {
        return avecRemise;
    }

    public void setAvecRemise(Boolean avecRemise) {
        this.avecRemise = avecRemise;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

}