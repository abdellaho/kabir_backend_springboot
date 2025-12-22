package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cheque")
public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "numero", nullable = false, length = 191)
    private String numero;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeCheque", nullable = false, length = 191)
    private String codeCheque;

    @NotNull
    @Column(name = "dateCheque", nullable = false)
    private LocalDate dateCheque;

    @NotNull
    @Column(name = "typeRepertoire", nullable = false)
    private Integer typeRepertoire;

    @NotNull
    @Column(name = "montant", nullable = false, precision = 10, scale = 2)
    private BigDecimal montant;

    @NotNull
    @Column(name = "typePersoScte", nullable = false)
    private Integer typePersoScte;

    @NotNull
    @Column(name = "numCheque", nullable = false)
    private Integer numCheque;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatcheque", nullable = false)
    private Boolean etatcheque = false;

    @NotNull
    @Column(name = "typeReglment", nullable = false)
    private Integer typeReglment;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "repertoireId", nullable = false)
    private Repertoire repertoire;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employeOperateurId", nullable = false)
    private Employe employeOperateur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodeCheque() {
        return codeCheque;
    }

    public void setCodeCheque(String codeCheque) {
        this.codeCheque = codeCheque;
    }

    public LocalDate getDateCheque() {
        return dateCheque;
    }

    public void setDateCheque(LocalDate dateCheque) {
        this.dateCheque = dateCheque;
    }

    public Integer getTypeRepertoire() {
        return typeRepertoire;
    }

    public void setTypeRepertoire(Integer typeRepertoire) {
        this.typeRepertoire = typeRepertoire;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public Integer getTypePersoScte() {
        return typePersoScte;
    }

    public void setTypePersoScte(Integer typePersoScte) {
        this.typePersoScte = typePersoScte;
    }

    public Integer getNumCheque() {
        return numCheque;
    }

    public void setNumCheque(Integer numCheque) {
        this.numCheque = numCheque;
    }

    public Boolean getEtatcheque() {
        return etatcheque;
    }

    public void setEtatcheque(Boolean etatcheque) {
        this.etatcheque = etatcheque;
    }

    public Integer getTypeReglment() {
        return typeReglment;
    }

    public void setTypeReglment(Integer typeReglment) {
        this.typeReglment = typeReglment;
    }

    public Repertoire getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(Repertoire repertoire) {
        this.repertoire = repertoire;
    }

    public Employe getEmployeOperateur() {
        return employeOperateur;
    }

    public void setEmployeOperateur(Employe employeOperateur) {
        this.employeOperateur = employeOperateur;
    }

}