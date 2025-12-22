package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "prime")
public class Prime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "montant", nullable = false, precision = 10, scale = 2)
    private BigDecimal montant;

    @NotNull
    @Column(name = "prime", nullable = false, precision = 10, scale = 2)
    private BigDecimal prime;

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

    public BigDecimal getPrime() {
        return prime;
    }

    public void setPrime(BigDecimal prime) {
        this.prime = prime;
    }

}