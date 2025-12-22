package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "caisse")
public class Caisse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "montant", nullable = false, precision = 10, scale = 2)
    private BigDecimal montant;

    @NotNull
    @Column(name = "dateOperation", nullable = false)
    private Instant dateOperation;

    @NotNull
    @Column(name = "type", nullable = false)
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public Instant getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Instant dateOperation) {
        this.dateOperation = dateOperation;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}