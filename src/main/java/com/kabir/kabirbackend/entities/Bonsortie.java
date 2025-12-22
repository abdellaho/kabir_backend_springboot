package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "bonsortie")
public class Bonsortie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "numSortie", nullable = false)
    private Integer numSortie;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeSortie", nullable = false, length = 191)
    private String codeSortie;

    @NotNull
    @Column(name = "dateOperation", nullable = false)
    private Instant dateOperation;

    @NotNull
    @Column(name = "mnt", nullable = false, precision = 10, scale = 2)
    private BigDecimal mnt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employeId", nullable = false)
    private Employe employe;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "repertoireId", nullable = false)
    private Repertoire repertoire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumSortie() {
        return numSortie;
    }

    public void setNumSortie(Integer numSortie) {
        this.numSortie = numSortie;
    }

    public String getCodeSortie() {
        return codeSortie;
    }

    public void setCodeSortie(String codeSortie) {
        this.codeSortie = codeSortie;
    }

    public Instant getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Instant dateOperation) {
        this.dateOperation = dateOperation;
    }

    public BigDecimal getMnt() {
        return mnt;
    }

    public void setMnt(BigDecimal mnt) {
        this.mnt = mnt;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Repertoire getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(Repertoire repertoire) {
        this.repertoire = repertoire;
    }

}