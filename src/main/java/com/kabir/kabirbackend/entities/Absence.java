package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "absence")
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "dateAbsence", nullable = false)
    private LocalDate dateAbsence;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "matin", nullable = false)
    private boolean matin = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "apresMidi", nullable = false)
    private boolean apresMidi = false;

    @NotNull
    @Column(name = "dateOperation", nullable = false)
    private Instant dateOperation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "personnelOperationId")
    private Personnel personnelOperation;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "personnelId", nullable = false)
    private Personnel personnel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateAbsence() {
        return dateAbsence;
    }

    public void setDateAbsence(LocalDate dateAbsence) {
        this.dateAbsence = dateAbsence;
    }

    public Boolean getMatin() {
        return matin;
    }

    public void setMatin(Boolean matin) {
        this.matin = matin;
    }

    public Boolean getApresMidi() {
        return apresMidi;
    }

    public void setApresMidi(Boolean apresMidi) {
        this.apresMidi = apresMidi;
    }

    public Instant getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Instant dateOperation) {
        this.dateOperation = dateOperation;
    }

    public Personnel getPersonnelOperation() {
        return personnelOperation;
    }

    public void setPersonnelOperation(Personnel personnelOperation) {
        this.personnelOperation = personnelOperation;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

}