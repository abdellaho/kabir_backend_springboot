package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "voiture")
public class Voiture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "numVoiture", nullable = false, length = 191)
    private String numVoiture;

    @NotNull
    @Column(name = "kmMax", nullable = false)
    private Integer kmMax;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumVoiture() {
        return numVoiture;
    }

    public void setNumVoiture(String numVoiture) {
        this.numVoiture = numVoiture;
    }

    public Integer getKmMax() {
        return kmMax;
    }

    public void setKmMax(Integer kmMax) {
        this.kmMax = kmMax;
    }

}