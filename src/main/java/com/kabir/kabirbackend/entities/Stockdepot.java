package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "stockdepot")
public class Stockdepot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "qteStockDepot", nullable = false)
    private Integer qteStockDepot;

    @NotNull
    @Column(name = "dateOperation", nullable = false)
    private LocalDate dateOperation;

    @NotNull
    @Column(name = "dateSys", nullable = false)
    private Instant dateSys;

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

    public Integer getQteStockDepot() {
        return qteStockDepot;
    }

    public void setQteStockDepot(Integer qteStockDepot) {
        this.qteStockDepot = qteStockDepot;
    }

    public LocalDate getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(LocalDate dateOperation) {
        this.dateOperation = dateOperation;
    }

    public Instant getDateSys() {
        return dateSys;
    }

    public void setDateSys(Instant dateSys) {
        this.dateSys = dateSys;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

}