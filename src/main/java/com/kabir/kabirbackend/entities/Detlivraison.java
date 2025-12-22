package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "detlivraison")
public class Detlivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "qteLivrer", nullable = false)
    private Integer qteLivrer;

    @NotNull
    @Column(name = "champsRouge", nullable = false)
    private Integer champsRouge;

    @NotNull
    @Column(name = "remiseLivraison", nullable = false, precision = 10, scale = 2)
    private BigDecimal remiseLivraison;

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
    @Column(name = "infinity", nullable = false)
    private Integer infinity;

    @NotNull
    @Column(name = "avecRemise", nullable = false)
    private Boolean avecRemise = false;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "livraisonId", nullable = false)
    private Livraison livraison;

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

    public Integer getQteLivrer() {
        return qteLivrer;
    }

    public void setQteLivrer(Integer qteLivrer) {
        this.qteLivrer = qteLivrer;
    }

    public Integer getChampsRouge() {
        return champsRouge;
    }

    public void setChampsRouge(Integer champsRouge) {
        this.champsRouge = champsRouge;
    }

    public BigDecimal getRemiseLivraison() {
        return remiseLivraison;
    }

    public void setRemiseLivraison(BigDecimal remiseLivraison) {
        this.remiseLivraison = remiseLivraison;
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

    public Integer getInfinity() {
        return infinity;
    }

    public void setInfinity(Integer infinity) {
        this.infinity = infinity;
    }

    public Boolean getAvecRemise() {
        return avecRemise;
    }

    public void setAvecRemise(Boolean avecRemise) {
        this.avecRemise = avecRemise;
    }

    public Livraison getLivraison() {
        return livraison;
    }

    public void setLivraison(Livraison livraison) {
        this.livraison = livraison;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

}