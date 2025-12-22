package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "detimportations")
public class Detimportation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "qteAchat", nullable = false)
    private Integer qteAchat;

    @NotNull
    @Column(name = "qteStock", nullable = false)
    private Integer qteStock;

    @NotNull
    @Column(name = "prixAchat", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixAchat;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "importationsId", nullable = false)
    private Importation importations;

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

    public Integer getQteAchat() {
        return qteAchat;
    }

    public void setQteAchat(Integer qteAchat) {
        this.qteAchat = qteAchat;
    }

    public Integer getQteStock() {
        return qteStock;
    }

    public void setQteStock(Integer qteStock) {
        this.qteStock = qteStock;
    }

    public BigDecimal getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(BigDecimal prixAchat) {
        this.prixAchat = prixAchat;
    }

    public Importation getImportations() {
        return importations;
    }

    public void setImportations(Importation importations) {
        this.importations = importations;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

}