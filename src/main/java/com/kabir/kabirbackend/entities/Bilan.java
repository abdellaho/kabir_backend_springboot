package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bilan")
public class Bilan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "date1", nullable = false)
    private LocalDate date1;

    @NotNull
    @Column(name = "date2", nullable = false)
    private LocalDate date2;

    @NotNull
    @Column(name = "stock", nullable = false, precision = 10, scale = 2)
    private BigDecimal stock;

    @NotNull
    @Column(name = "banque", nullable = false, precision = 10, scale = 2)
    private BigDecimal banque;

    @NotNull
    @Column(name = "caisse1", nullable = false, precision = 10, scale = 2)
    private BigDecimal caisse1;

    @NotNull
    @Column(name = "caisse2", nullable = false, precision = 10, scale = 2)
    private BigDecimal caisse2;

    @NotNull
    @Column(name = "actifDivers", nullable = false, precision = 10, scale = 2)
    private BigDecimal actifDivers;

    @NotNull
    @Column(name = "capital", nullable = false, precision = 10, scale = 2)
    private BigDecimal capital;

    @NotNull
    @Column(name = "dgi", nullable = false, precision = 10, scale = 2)
    private BigDecimal dgi;

    @NotNull
    @Column(name = "cnss", nullable = false, precision = 10, scale = 2)
    private BigDecimal cnss;

    @NotNull
    @Column(name = "loyer", nullable = false, precision = 10, scale = 2)
    private BigDecimal loyer;

    @NotNull
    @Column(name = "salaire", nullable = false, precision = 10, scale = 2)
    private BigDecimal salaire;

    @NotNull
    @Column(name = "tva", nullable = false, precision = 10, scale = 2)
    private BigDecimal tva;

    @NotNull
    @Column(name = "chiffreAffaire", nullable = false, precision = 10, scale = 2)
    private BigDecimal chiffreAffaire;

    @NotNull
    @Column(name = "passifDivers", nullable = false, precision = 10, scale = 2)
    private BigDecimal passifDivers;

    @NotNull
    @Column(name = "compteCourant", nullable = false, precision = 10, scale = 2)
    private BigDecimal compteCourant;

    @NotNull
    @Column(name = "resultatPrecedent", nullable = false, precision = 10, scale = 2)
    private BigDecimal resultatPrecedent;

    @NotNull
    @Column(name = "resultat", nullable = false, precision = 10, scale = 2)
    private BigDecimal resultat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate1() {
        return date1;
    }

    public void setDate1(LocalDate date1) {
        this.date1 = date1;
    }

    public LocalDate getDate2() {
        return date2;
    }

    public void setDate2(LocalDate date2) {
        this.date2 = date2;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public BigDecimal getBanque() {
        return banque;
    }

    public void setBanque(BigDecimal banque) {
        this.banque = banque;
    }

    public BigDecimal getCaisse1() {
        return caisse1;
    }

    public void setCaisse1(BigDecimal caisse1) {
        this.caisse1 = caisse1;
    }

    public BigDecimal getCaisse2() {
        return caisse2;
    }

    public void setCaisse2(BigDecimal caisse2) {
        this.caisse2 = caisse2;
    }

    public BigDecimal getActifDivers() {
        return actifDivers;
    }

    public void setActifDivers(BigDecimal actifDivers) {
        this.actifDivers = actifDivers;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getDgi() {
        return dgi;
    }

    public void setDgi(BigDecimal dgi) {
        this.dgi = dgi;
    }

    public BigDecimal getCnss() {
        return cnss;
    }

    public void setCnss(BigDecimal cnss) {
        this.cnss = cnss;
    }

    public BigDecimal getLoyer() {
        return loyer;
    }

    public void setLoyer(BigDecimal loyer) {
        this.loyer = loyer;
    }

    public BigDecimal getSalaire() {
        return salaire;
    }

    public void setSalaire(BigDecimal salaire) {
        this.salaire = salaire;
    }

    public BigDecimal getTva() {
        return tva;
    }

    public void setTva(BigDecimal tva) {
        this.tva = tva;
    }

    public BigDecimal getChiffreAffaire() {
        return chiffreAffaire;
    }

    public void setChiffreAffaire(BigDecimal chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }

    public BigDecimal getPassifDivers() {
        return passifDivers;
    }

    public void setPassifDivers(BigDecimal passifDivers) {
        this.passifDivers = passifDivers;
    }

    public BigDecimal getCompteCourant() {
        return compteCourant;
    }

    public void setCompteCourant(BigDecimal compteCourant) {
        this.compteCourant = compteCourant;
    }

    public BigDecimal getResultatPrecedent() {
        return resultatPrecedent;
    }

    public void setResultatPrecedent(BigDecimal resultatPrecedent) {
        this.resultatPrecedent = resultatPrecedent;
    }

    public BigDecimal getResultat() {
        return resultat;
    }

    public void setResultat(BigDecimal resultat) {
        this.resultat = resultat;
    }

}