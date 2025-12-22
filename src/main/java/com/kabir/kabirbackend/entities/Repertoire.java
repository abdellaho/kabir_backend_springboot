package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "repertoire")
public class Repertoire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "designation", nullable = false, length = 191)
    private String designation;

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
    @Column(name = "tel3", nullable = false, length = 191)
    private String tel3;

    @Size(max = 191)
    @NotNull
    @Column(name = "adresse", nullable = false, length = 191)
    private String adresse;

    @Size(max = 191)
    @NotNull
    @Column(name = "email", nullable = false, length = 191)
    private String email;

    @NotNull
    @Column(name = "typeRepertoire", nullable = false)
    private Integer typeRepertoire;

    @NotNull
    @Column(name = "typeReglment", nullable = false)
    private Integer typeReglment;

    @Size(max = 191)
    @NotNull
    @Column(name = "ife", nullable = false, length = 191)
    private String ife;

    @Size(max = 191)
    @NotNull
    @Column(name = "ice", nullable = false, length = 191)
    private String ice;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "archiver", nullable = false)
    private Boolean archiver = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "bloquer", nullable = false)
    private Boolean bloquer = false;

    @NotNull
    @Column(name = "sysDate", nullable = false)
    private Instant sysDate;

    @Column(name = "dateSuppression")
    private LocalDate dateSuppression;

    @Size(max = 191)
    @NotNull
    @Column(name = "observation", nullable = false, length = 191)
    private String observation;

    @NotNull
    @Column(name = "nbrOperationClient", nullable = false)
    private Integer nbrOperationClient;

    @NotNull
    @Column(name = "plafond", nullable = false, precision = 10, scale = 2)
    private BigDecimal plafond;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "personnelId")
    private Personnel personnel;

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

    public String getTel3() {
        return tel3;
    }

    public void setTel3(String tel3) {
        this.tel3 = tel3;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTypeRepertoire() {
        return typeRepertoire;
    }

    public void setTypeRepertoire(Integer typeRepertoire) {
        this.typeRepertoire = typeRepertoire;
    }

    public Integer getTypeReglment() {
        return typeReglment;
    }

    public void setTypeReglment(Integer typeReglment) {
        this.typeReglment = typeReglment;
    }

    public String getIfe() {
        return ife;
    }

    public void setIfe(String ife) {
        this.ife = ife;
    }

    public String getIce() {
        return ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public Boolean getArchiver() {
        return archiver;
    }

    public void setArchiver(Boolean archiver) {
        this.archiver = archiver;
    }

    public Boolean getBloquer() {
        return bloquer;
    }

    public void setBloquer(Boolean bloquer) {
        this.bloquer = bloquer;
    }

    public Instant getSysDate() {
        return sysDate;
    }

    public void setSysDate(Instant sysDate) {
        this.sysDate = sysDate;
    }

    public LocalDate getDateSuppression() {
        return dateSuppression;
    }

    public void setDateSuppression(LocalDate dateSuppression) {
        this.dateSuppression = dateSuppression;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Integer getNbrOperationClient() {
        return nbrOperationClient;
    }

    public void setNbrOperationClient(Integer nbrOperationClient) {
        this.nbrOperationClient = nbrOperationClient;
    }

    public BigDecimal getPlafond() {
        return plafond;
    }

    public void setPlafond(BigDecimal plafond) {
        this.plafond = plafond;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

}