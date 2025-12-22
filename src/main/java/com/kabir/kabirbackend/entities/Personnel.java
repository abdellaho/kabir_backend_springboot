package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "personnel")
public class Personnel {
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
    @Column(name = "cin", nullable = false, length = 191)
    private String cin;

    @Size(max = 191)
    @NotNull
    @Column(name = "login", nullable = false, length = 191)
    private String login;

    @Size(max = 191)
    @NotNull
    @Column(name = "password", nullable = false, length = 191)
    private String password;

    @NotNull
    @Column(name = "typePersonnel", nullable = false)
    private Integer typePersonnel;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatComptePersonnel", nullable = false)
    private boolean etatComptePersonnel = false;

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
    @Column(name = "adresse", nullable = false, length = 191)
    private String adresse;

    @Size(max = 191)
    @NotNull
    @Column(name = "email", nullable = false, length = 191)
    private String email;

    @NotNull
    @Column(name = "dateEntrer", nullable = false)
    private LocalDate dateEntrer;

    @Column(name = "dateSuppression")
    private LocalDate dateSuppression;

    @NotNull
    @Column(name = "salaire", nullable = false, precision = 10, scale = 2)
    private BigDecimal salaire;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "archiver", nullable = false)
    private boolean archiver = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "supprimer", nullable = false)
    private boolean supprimer = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "consulterStock", nullable = false)
    private boolean consulterStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ajouterStock", nullable = false)
    private boolean ajouterStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "modifierStock", nullable = false)
    private boolean modifierStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "supprimerStock", nullable = false)
    private boolean supprimerStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "consulterRepertoire", nullable = false)
    private boolean consulterRepertoire = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ajouterRepertoire", nullable = false)
    private boolean ajouterRepertoire = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "modifierRepertoire", nullable = false)
    private boolean modifierRepertoire = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "supprimerRepertoire", nullable = false)
    private boolean supprimerRepertoire = false;

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

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTypePersonnel() {
        return typePersonnel;
    }

    public void setTypePersonnel(Integer typePersonnel) {
        this.typePersonnel = typePersonnel;
    }

    public boolean getEtatComptePersonnel() {
        return etatComptePersonnel;
    }

    public void setEtatComptePersonnel(boolean etatComptePersonnel) {
        this.etatComptePersonnel = etatComptePersonnel;
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

    public LocalDate getDateEntrer() {
        return dateEntrer;
    }

    public void setDateEntrer(LocalDate dateEntrer) {
        this.dateEntrer = dateEntrer;
    }

    public LocalDate getDateSuppression() {
        return dateSuppression;
    }

    public void setDateSuppression(LocalDate dateSuppression) {
        this.dateSuppression = dateSuppression;
    }

    public BigDecimal getSalaire() {
        return salaire;
    }

    public void setSalaire(BigDecimal salaire) {
        this.salaire = salaire;
    }

    public boolean getArchiver() {
        return archiver;
    }

    public void setArchiver(boolean archiver) {
        this.archiver = archiver;
    }

    public boolean getSupprimer() {
        return supprimer;
    }

    public void setSupprimer(boolean supprimer) {
        this.supprimer = supprimer;
    }

    public boolean getConsulterStock() {
        return consulterStock;
    }

    public void setConsulterStock(boolean consulterStock) {
        this.consulterStock = consulterStock;
    }

    public boolean getAjouterStock() {
        return ajouterStock;
    }

    public void setAjouterStock(boolean ajouterStock) {
        this.ajouterStock = ajouterStock;
    }

    public boolean getModifierStock() {
        return modifierStock;
    }

    public void setModifierStock(boolean modifierStock) {
        this.modifierStock = modifierStock;
    }

    public boolean getSupprimerStock() {
        return supprimerStock;
    }

    public void setSupprimerStock(boolean supprimerStock) {
        this.supprimerStock = supprimerStock;
    }

    public boolean getConsulterRepertoire() {
        return consulterRepertoire;
    }

    public void setConsulterRepertoire(boolean consulterRepertoire) {
        this.consulterRepertoire = consulterRepertoire;
    }

    public boolean getAjouterRepertoire() {
        return ajouterRepertoire;
    }

    public void setAjouterRepertoire(boolean ajouterRepertoire) {
        this.ajouterRepertoire = ajouterRepertoire;
    }

    public boolean getModifierRepertoire() {
        return modifierRepertoire;
    }

    public void setModifierRepertoire(boolean modifierRepertoire) {
        this.modifierRepertoire = modifierRepertoire;
    }

    public boolean getSupprimerRepertoire() {
        return supprimerRepertoire;
    }

    public void setSupprimerRepertoire(boolean supprimerRepertoire) {
        this.supprimerRepertoire = supprimerRepertoire;
    }

}