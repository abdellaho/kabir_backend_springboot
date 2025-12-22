package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "compta")
public class Compta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "dateDebut", nullable = false)
    private LocalDate dateDebut;

    @NotNull
    @Column(name = "dateFin", nullable = false)
    private LocalDate dateFin;

    @NotNull
    @Column(name = "montantTVAPrecedent", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTVAPrecedent;

    @NotNull
    @Column(name = "montantTVAAchat", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTVAAchat;

    @NotNull
    @Column(name = "montantTVAVente", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTVAVente;

    @NotNull
    @Column(name = "resutMnt", nullable = false, precision = 10, scale = 2)
    private BigDecimal resutMnt;

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

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public BigDecimal getMontantTVAPrecedent() {
        return montantTVAPrecedent;
    }

    public void setMontantTVAPrecedent(BigDecimal montantTVAPrecedent) {
        this.montantTVAPrecedent = montantTVAPrecedent;
    }

    public BigDecimal getMontantTVAAchat() {
        return montantTVAAchat;
    }

    public void setMontantTVAAchat(BigDecimal montantTVAAchat) {
        this.montantTVAAchat = montantTVAAchat;
    }

    public BigDecimal getMontantTVAVente() {
        return montantTVAVente;
    }

    public void setMontantTVAVente(BigDecimal montantTVAVente) {
        this.montantTVAVente = montantTVAVente;
    }

    public BigDecimal getResutMnt() {
        return resutMnt;
    }

    public void setResutMnt(BigDecimal resutMnt) {
        this.resutMnt = resutMnt;
    }

    public Repertoire getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(Repertoire repertoire) {
        this.repertoire = repertoire;
    }

}