package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "fournisseur")
public class Fournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "designation", nullable = false, length = 191)
    private String designation;

    @NotNull
    @Column(name = "type", nullable = false)
    private Integer type;

    @Size(max = 191)
    @NotNull
    @Column(name = "tel1", nullable = false, length = 191)
    private String tel1;

    @Size(max = 191)
    @NotNull
    @Column(name = "tel2", nullable = false, length = 191)
    private String tel2;

    @Size(max = 191)
    @NotNull
    @Column(name = "ice", nullable = false, length = 191)
    private String ice;

    @Size(max = 191)
    @NotNull
    @Column(name = "adresse", nullable = false, length = 191)
    private String adresse;

    @Column(name = "dateSuppression")
    private LocalDate dateSuppression;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "archiver", nullable = false)
    private Boolean archiver = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "supprimer", nullable = false)
    private Boolean supprimer = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "villeId")
    private Ville ville;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getIce() {
        return ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public LocalDate getDateSuppression() {
        return dateSuppression;
    }

    public void setDateSuppression(LocalDate dateSuppression) {
        this.dateSuppression = dateSuppression;
    }

    public Boolean getArchiver() {
        return archiver;
    }

    public void setArchiver(Boolean archiver) {
        this.archiver = archiver;
    }

    public Boolean getSupprimer() {
        return supprimer;
    }

    public void setSupprimer(Boolean supprimer) {
        this.supprimer = supprimer;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

}