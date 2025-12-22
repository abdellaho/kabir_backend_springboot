package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
@Table(name = "employe")
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "numEmp", nullable = false)
    private Integer numEmp;

    @NotNull
    @Column(name = "livrerNonLivrerDroit", nullable = false)
    private Integer livrerNonLivrerDroit;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeEmp", nullable = false, length = 191)
    private String codeEmp;

    @Size(max = 191)
    @NotNull
    @Column(name = "nom", nullable = false, length = 191)
    private String nom;

    @Size(max = 191)
    @NotNull
    @Column(name = "prenom", nullable = false, length = 191)
    private String prenom;

    @Size(max = 191)
    @NotNull
    @Column(name = "login", nullable = false, length = 191)
    private String login;

    @Size(max = 191)
    @NotNull
    @Column(name = "motpass", nullable = false, length = 191)
    private String motpass;

    @Size(max = 191)
    @NotNull
    @Column(name = "email", nullable = false, length = 191)
    private String email;

    @NotNull
    @Column(name = "typeUser", nullable = false)
    private Integer typeUser;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatCompte", nullable = false)
    private Boolean etatCompte = false;

    @NotNull
    @Column(name = "validationMnt", nullable = false, precision = 10, scale = 2)
    private BigDecimal validationMnt;

    @Size(max = 191)
    @NotNull
    @Column(name = "motPassFake", nullable = false, length = 191)
    private String motPassFake;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "commercial", nullable = false)
    private Boolean commercial = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "pvLibre", nullable = false)
    private Boolean pvLibre = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "gerant", nullable = false)
    private Boolean gerant = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "magasinier", nullable = false)
    private Boolean magasinier = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "bulltinPaie", nullable = false)
    private Boolean bulltinPaie = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "imprimStockSimple", nullable = false)
    private Boolean imprimStockSimple = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "livraisonLimite", nullable = false)
    private Boolean livraisonLimite = false;

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

    public Integer getNumEmp() {
        return numEmp;
    }

    public void setNumEmp(Integer numEmp) {
        this.numEmp = numEmp;
    }

    public Integer getLivrerNonLivrerDroit() {
        return livrerNonLivrerDroit;
    }

    public void setLivrerNonLivrerDroit(Integer livrerNonLivrerDroit) {
        this.livrerNonLivrerDroit = livrerNonLivrerDroit;
    }

    public String getCodeEmp() {
        return codeEmp;
    }

    public void setCodeEmp(String codeEmp) {
        this.codeEmp = codeEmp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotpass() {
        return motpass;
    }

    public void setMotpass(String motpass) {
        this.motpass = motpass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(Integer typeUser) {
        this.typeUser = typeUser;
    }

    public Boolean getEtatCompte() {
        return etatCompte;
    }

    public void setEtatCompte(Boolean etatCompte) {
        this.etatCompte = etatCompte;
    }

    public BigDecimal getValidationMnt() {
        return validationMnt;
    }

    public void setValidationMnt(BigDecimal validationMnt) {
        this.validationMnt = validationMnt;
    }

    public String getMotPassFake() {
        return motPassFake;
    }

    public void setMotPassFake(String motPassFake) {
        this.motPassFake = motPassFake;
    }

    public Boolean getCommercial() {
        return commercial;
    }

    public void setCommercial(Boolean commercial) {
        this.commercial = commercial;
    }

    public Boolean getPvLibre() {
        return pvLibre;
    }

    public void setPvLibre(Boolean pvLibre) {
        this.pvLibre = pvLibre;
    }

    public Boolean getGerant() {
        return gerant;
    }

    public void setGerant(Boolean gerant) {
        this.gerant = gerant;
    }

    public Boolean getMagasinier() {
        return magasinier;
    }

    public void setMagasinier(Boolean magasinier) {
        this.magasinier = magasinier;
    }

    public Boolean getBulltinPaie() {
        return bulltinPaie;
    }

    public void setBulltinPaie(Boolean bulltinPaie) {
        this.bulltinPaie = bulltinPaie;
    }

    public Boolean getImprimStockSimple() {
        return imprimStockSimple;
    }

    public void setImprimStockSimple(Boolean imprimStockSimple) {
        this.imprimStockSimple = imprimStockSimple;
    }

    public Boolean getLivraisonLimite() {
        return livraisonLimite;
    }

    public void setLivraisonLimite(Boolean livraisonLimite) {
        this.livraisonLimite = livraisonLimite;
    }

    public Repertoire getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(Repertoire repertoire) {
        this.repertoire = repertoire;
    }

}