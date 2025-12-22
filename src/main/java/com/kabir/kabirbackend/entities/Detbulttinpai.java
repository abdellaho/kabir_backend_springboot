package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
@Table(name = "detbulttinpai")
public class Detbulttinpai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "prixlivraison", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixlivraison;

    @NotNull
    @Column(name = "qtevendu", nullable = false)
    private Integer qtevendu;

    @NotNull
    @Column(name = "prixvente", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixvente;

    @NotNull
    @Column(name = "remise", nullable = false, precision = 10, scale = 2)
    private BigDecimal remise;

    @NotNull
    @Column(name = "mantantvendu", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantvendu;

    @NotNull
    @Column(name = "commission", nullable = false, precision = 10, scale = 2)
    private BigDecimal commission;

    @NotNull
    @Column(name = "commsiondh", nullable = false, precision = 10, scale = 2)
    private BigDecimal commsiondh;

    @NotNull
    @Column(name = "mantant", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantant;

    @NotNull
    @Column(name = "benDH", nullable = false, precision = 10, scale = 2)
    private BigDecimal benDH;

    @NotNull
    @Column(name = "commissionFixe", nullable = false, precision = 10, scale = 2)
    private BigDecimal commissionFixe;

    @NotNull
    @Column(name = "prixCommercial", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixCommercial;

    @NotNull
    @Column(name = "primeCommercial", nullable = false, precision = 10, scale = 2)
    private BigDecimal primeCommercial;

    @NotNull
    @Column(name = "primeProduit", nullable = false, precision = 10, scale = 2)
    private BigDecimal primeProduit;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "avecZero", nullable = false)
    private Boolean avecZero = false;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bulttinPaiId", nullable = false)
    private Bulttinpai bulttinPai;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produitId", nullable = false)
    private Stock produit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrixlivraison() {
        return prixlivraison;
    }

    public void setPrixlivraison(BigDecimal prixlivraison) {
        this.prixlivraison = prixlivraison;
    }

    public Integer getQtevendu() {
        return qtevendu;
    }

    public void setQtevendu(Integer qtevendu) {
        this.qtevendu = qtevendu;
    }

    public BigDecimal getPrixvente() {
        return prixvente;
    }

    public void setPrixvente(BigDecimal prixvente) {
        this.prixvente = prixvente;
    }

    public BigDecimal getRemise() {
        return remise;
    }

    public void setRemise(BigDecimal remise) {
        this.remise = remise;
    }

    public BigDecimal getMantantvendu() {
        return mantantvendu;
    }

    public void setMantantvendu(BigDecimal mantantvendu) {
        this.mantantvendu = mantantvendu;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getCommsiondh() {
        return commsiondh;
    }

    public void setCommsiondh(BigDecimal commsiondh) {
        this.commsiondh = commsiondh;
    }

    public BigDecimal getMantant() {
        return mantant;
    }

    public void setMantant(BigDecimal mantant) {
        this.mantant = mantant;
    }

    public BigDecimal getBenDH() {
        return benDH;
    }

    public void setBenDH(BigDecimal benDH) {
        this.benDH = benDH;
    }

    public BigDecimal getCommissionFixe() {
        return commissionFixe;
    }

    public void setCommissionFixe(BigDecimal commissionFixe) {
        this.commissionFixe = commissionFixe;
    }

    public BigDecimal getPrixCommercial() {
        return prixCommercial;
    }

    public void setPrixCommercial(BigDecimal prixCommercial) {
        this.prixCommercial = prixCommercial;
    }

    public BigDecimal getPrimeCommercial() {
        return primeCommercial;
    }

    public void setPrimeCommercial(BigDecimal primeCommercial) {
        this.primeCommercial = primeCommercial;
    }

    public BigDecimal getPrimeProduit() {
        return primeProduit;
    }

    public void setPrimeProduit(BigDecimal primeProduit) {
        this.primeProduit = primeProduit;
    }

    public Boolean getAvecZero() {
        return avecZero;
    }

    public void setAvecZero(Boolean avecZero) {
        this.avecZero = avecZero;
    }

    public Bulttinpai getBulttinPai() {
        return bulttinPai;
    }

    public void setBulttinPai(Bulttinpai bulttinPai) {
        this.bulttinPai = bulttinPai;
    }

    public Stock getProduit() {
        return produit;
    }

    public void setProduit(Stock produit) {
        this.produit = produit;
    }

}