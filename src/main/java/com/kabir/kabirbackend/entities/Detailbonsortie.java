package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "detailbonsortie")
public class Detailbonsortie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "qteSortie", nullable = false)
    private Integer qteSortie;

    @NotNull
    @Column(name = "mntProduit", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntProduit;

    @NotNull
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bonSortieId", nullable = false)
    private Bonsortie bonSortie;

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

    public Integer getQteSortie() {
        return qteSortie;
    }

    public void setQteSortie(Integer qteSortie) {
        this.qteSortie = qteSortie;
    }

    public BigDecimal getMntProduit() {
        return mntProduit;
    }

    public void setMntProduit(BigDecimal mntProduit) {
        this.mntProduit = mntProduit;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Bonsortie getBonSortie() {
        return bonSortie;
    }

    public void setBonSortie(Bonsortie bonSortie) {
        this.bonSortie = bonSortie;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

}