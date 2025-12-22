package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "paramcommiss")
public class Paramcommiss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "mntDepart", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntDepart;

    @NotNull
    @Column(name = "mntFin", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntFin;

    @NotNull
    @Column(name = "pourc", nullable = false, precision = 10, scale = 2)
    private BigDecimal pourc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMntDepart() {
        return mntDepart;
    }

    public void setMntDepart(BigDecimal mntDepart) {
        this.mntDepart = mntDepart;
    }

    public BigDecimal getMntFin() {
        return mntFin;
    }

    public void setMntFin(BigDecimal mntFin) {
        this.mntFin = mntFin;
    }

    public BigDecimal getPourc() {
        return pourc;
    }

    public void setPourc(BigDecimal pourc) {
        this.pourc = pourc;
    }

}