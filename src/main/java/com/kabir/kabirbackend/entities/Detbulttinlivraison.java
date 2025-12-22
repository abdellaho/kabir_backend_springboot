package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "detbulttinlivraison")
public class Detbulttinlivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "commission", nullable = false, precision = 10, scale = 2)
    private BigDecimal commission;

    @NotNull
    @Column(name = "commissionFixe", nullable = false, precision = 10, scale = 2)
    private BigDecimal commissionFixe;

    @NotNull
    @Column(name = "commsiondh", nullable = false, precision = 10, scale = 2)
    private BigDecimal commsiondh;

    @NotNull
    @Column(name = "mantantcommission", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantcommission;

    @NotNull
    @Column(name = "benDH", nullable = false, precision = 10, scale = 2)
    private BigDecimal benDH;

    @NotNull
    @Column(name = "rougenormal", nullable = false)
    private Boolean rougenormal = false;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bulttinPaiId", nullable = false)
    private Bulttinpai bulttinPai;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "livraisonId", nullable = false)
    private Livraison livraison;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getCommissionFixe() {
        return commissionFixe;
    }

    public void setCommissionFixe(BigDecimal commissionFixe) {
        this.commissionFixe = commissionFixe;
    }

    public BigDecimal getCommsiondh() {
        return commsiondh;
    }

    public void setCommsiondh(BigDecimal commsiondh) {
        this.commsiondh = commsiondh;
    }

    public BigDecimal getMantantcommission() {
        return mantantcommission;
    }

    public void setMantantcommission(BigDecimal mantantcommission) {
        this.mantantcommission = mantantcommission;
    }

    public BigDecimal getBenDH() {
        return benDH;
    }

    public void setBenDH(BigDecimal benDH) {
        this.benDH = benDH;
    }

    public Boolean getRougenormal() {
        return rougenormal;
    }

    public void setRougenormal(Boolean rougenormal) {
        this.rougenormal = rougenormal;
    }

    public Bulttinpai getBulttinPai() {
        return bulttinPai;
    }

    public void setBulttinPai(Bulttinpai bulttinPai) {
        this.bulttinPai = bulttinPai;
    }

    public Livraison getLivraison() {
        return livraison;
    }

    public void setLivraison(Livraison livraison) {
        this.livraison = livraison;
    }

}