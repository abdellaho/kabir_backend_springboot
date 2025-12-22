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
@Table(name = "livraison")
public class Livraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "numLivraison", nullable = false)
    private Integer numLivraison;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeBl", nullable = false, length = 191)
    private String codeBl;

    @NotNull
    @Column(name = "dateBl", nullable = false)
    private LocalDate dateBl;

    @NotNull
    @Column(name = "dateReglement", nullable = false)
    private LocalDate dateReglement;

    @Column(name = "dateReglement2")
    private LocalDate dateReglement2;

    @Column(name = "dateReglement3")
    private LocalDate dateReglement3;

    @Column(name = "dateReglement4")
    private LocalDate dateReglement4;

    @NotNull
    @Column(name = "typeReglment", nullable = false)
    private Integer typeReglment;

    @NotNull
    @Column(name = "typeReglment2", nullable = false)
    private Integer typeReglment2;

    @NotNull
    @Column(name = "typeReglment3", nullable = false)
    private Integer typeReglment3;

    @NotNull
    @Column(name = "typeReglment4", nullable = false)
    private Integer typeReglment4;

    @NotNull
    @Column(name = "mantantBL", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantBL;

    @NotNull
    @Column(name = "mantantBLReel", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantBLReel;

    @NotNull
    @Column(name = "mantantBLBenefice", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantBLBenefice;

    @Size(max = 191)
    @NotNull
    @Column(name = "typePaiement", nullable = false, length = 191)
    private String typePaiement;

    @NotNull
    @Column(name = "mantantBLPourcent", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantBLPourcent;

    @NotNull
    @Column(name = "reglerNonRegler", nullable = false)
    private Integer reglerNonRegler;

    @NotNull
    @Column(name = "sysDate", nullable = false)
    private Instant sysDate;

    @NotNull
    @Column(name = "infinity", nullable = false)
    private Integer infinity;

    @NotNull
    @Column(name = "etatBultinPaie", nullable = false)
    private Integer etatBultinPaie;

    @NotNull
    @Column(name = "livrernonlivrer", nullable = false)
    private Integer livrernonlivrer;

    @NotNull
    @Column(name = "avecRemise", nullable = false)
    private Boolean avecRemise = false;

    @NotNull
    @Column(name = "mntReglement", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntReglement;

    @NotNull
    @Column(name = "mntReglement2", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntReglement2;

    @NotNull
    @Column(name = "mntReglement3", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntReglement3;

    @NotNull
    @Column(name = "mntReglement4", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntReglement4;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "facturer100", nullable = false)
    private Boolean facturer100 = false;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeTransport", nullable = false, length = 191)
    private String codeTransport;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "employeOperateurId")
    private Employe employeOperateur;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "personnelId", nullable = false)
    private Personnel personnel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnelAncienId")
    private Personnel personnelAncien;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fournisseurId", nullable = false)
    private Fournisseur fournisseur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumLivraison() {
        return numLivraison;
    }

    public void setNumLivraison(Integer numLivraison) {
        this.numLivraison = numLivraison;
    }

    public String getCodeBl() {
        return codeBl;
    }

    public void setCodeBl(String codeBl) {
        this.codeBl = codeBl;
    }

    public LocalDate getDateBl() {
        return dateBl;
    }

    public void setDateBl(LocalDate dateBl) {
        this.dateBl = dateBl;
    }

    public LocalDate getDateReglement() {
        return dateReglement;
    }

    public void setDateReglement(LocalDate dateReglement) {
        this.dateReglement = dateReglement;
    }

    public LocalDate getDateReglement2() {
        return dateReglement2;
    }

    public void setDateReglement2(LocalDate dateReglement2) {
        this.dateReglement2 = dateReglement2;
    }

    public LocalDate getDateReglement3() {
        return dateReglement3;
    }

    public void setDateReglement3(LocalDate dateReglement3) {
        this.dateReglement3 = dateReglement3;
    }

    public LocalDate getDateReglement4() {
        return dateReglement4;
    }

    public void setDateReglement4(LocalDate dateReglement4) {
        this.dateReglement4 = dateReglement4;
    }

    public Integer getTypeReglment() {
        return typeReglment;
    }

    public void setTypeReglment(Integer typeReglment) {
        this.typeReglment = typeReglment;
    }

    public Integer getTypeReglment2() {
        return typeReglment2;
    }

    public void setTypeReglment2(Integer typeReglment2) {
        this.typeReglment2 = typeReglment2;
    }

    public Integer getTypeReglment3() {
        return typeReglment3;
    }

    public void setTypeReglment3(Integer typeReglment3) {
        this.typeReglment3 = typeReglment3;
    }

    public Integer getTypeReglment4() {
        return typeReglment4;
    }

    public void setTypeReglment4(Integer typeReglment4) {
        this.typeReglment4 = typeReglment4;
    }

    public BigDecimal getMantantBL() {
        return mantantBL;
    }

    public void setMantantBL(BigDecimal mantantBL) {
        this.mantantBL = mantantBL;
    }

    public BigDecimal getMantantBLReel() {
        return mantantBLReel;
    }

    public void setMantantBLReel(BigDecimal mantantBLReel) {
        this.mantantBLReel = mantantBLReel;
    }

    public BigDecimal getMantantBLBenefice() {
        return mantantBLBenefice;
    }

    public void setMantantBLBenefice(BigDecimal mantantBLBenefice) {
        this.mantantBLBenefice = mantantBLBenefice;
    }

    public String getTypePaiement() {
        return typePaiement;
    }

    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }

    public BigDecimal getMantantBLPourcent() {
        return mantantBLPourcent;
    }

    public void setMantantBLPourcent(BigDecimal mantantBLPourcent) {
        this.mantantBLPourcent = mantantBLPourcent;
    }

    public Integer getReglerNonRegler() {
        return reglerNonRegler;
    }

    public void setReglerNonRegler(Integer reglerNonRegler) {
        this.reglerNonRegler = reglerNonRegler;
    }

    public Instant getSysDate() {
        return sysDate;
    }

    public void setSysDate(Instant sysDate) {
        this.sysDate = sysDate;
    }

    public Integer getInfinity() {
        return infinity;
    }

    public void setInfinity(Integer infinity) {
        this.infinity = infinity;
    }

    public Integer getEtatBultinPaie() {
        return etatBultinPaie;
    }

    public void setEtatBultinPaie(Integer etatBultinPaie) {
        this.etatBultinPaie = etatBultinPaie;
    }

    public Integer getLivrernonlivrer() {
        return livrernonlivrer;
    }

    public void setLivrernonlivrer(Integer livrernonlivrer) {
        this.livrernonlivrer = livrernonlivrer;
    }

    public Boolean getAvecRemise() {
        return avecRemise;
    }

    public void setAvecRemise(Boolean avecRemise) {
        this.avecRemise = avecRemise;
    }

    public BigDecimal getMntReglement() {
        return mntReglement;
    }

    public void setMntReglement(BigDecimal mntReglement) {
        this.mntReglement = mntReglement;
    }

    public BigDecimal getMntReglement2() {
        return mntReglement2;
    }

    public void setMntReglement2(BigDecimal mntReglement2) {
        this.mntReglement2 = mntReglement2;
    }

    public BigDecimal getMntReglement3() {
        return mntReglement3;
    }

    public void setMntReglement3(BigDecimal mntReglement3) {
        this.mntReglement3 = mntReglement3;
    }

    public BigDecimal getMntReglement4() {
        return mntReglement4;
    }

    public void setMntReglement4(BigDecimal mntReglement4) {
        this.mntReglement4 = mntReglement4;
    }

    public Boolean getFacturer100() {
        return facturer100;
    }

    public void setFacturer100(Boolean facturer100) {
        this.facturer100 = facturer100;
    }

    public String getCodeTransport() {
        return codeTransport;
    }

    public void setCodeTransport(String codeTransport) {
        this.codeTransport = codeTransport;
    }

    public Employe getEmployeOperateur() {
        return employeOperateur;
    }

    public void setEmployeOperateur(Employe employeOperateur) {
        this.employeOperateur = employeOperateur;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public Personnel getPersonnelAncien() {
        return personnelAncien;
    }

    public void setPersonnelAncien(Personnel personnelAncien) {
        this.personnelAncien = personnelAncien;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

}