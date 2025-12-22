package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "bulttinpai")
public class Bulttinpai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeBultin", nullable = false, length = 191)
    private String codeBultin;

    @Size(max = 191)
    @NotNull
    @Column(name = "observation", nullable = false, length = 191)
    private String observation;

    @NotNull
    @Column(name = "numbultin", nullable = false)
    private Integer numbultin;

    @NotNull
    @Column(name = "dateOperation", nullable = false)
    private Instant dateOperation;

    @NotNull
    @Column(name = "dateDebut", nullable = false)
    private LocalDate dateDebut;

    @NotNull
    @Column(name = "dateFin", nullable = false)
    private LocalDate dateFin;

    @NotNull
    @Column(name = "idTypeBultinPai", nullable = false)
    private Integer idTypeBultinPai;

    @NotNull
    @Column(name = "salairefx", nullable = false, precision = 10, scale = 2)
    private BigDecimal salairefx;

    @NotNull
    @Column(name = "commission", nullable = false, precision = 10, scale = 2)
    private BigDecimal commission;

    @NotNull
    @Column(name = "frais", nullable = false, precision = 10, scale = 2)
    private BigDecimal frais;

    @NotNull
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @NotNull
    @Column(name = "totalMntVendue", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalMntVendue;

    @NotNull
    @Column(name = "totalMntVendueProduit", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalMntVendueProduit;

    @NotNull
    @Column(name = "totalMntVendueLivraison", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalMntVendueLivraison;

    @NotNull
    @Column(name = "mntNegative", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntNegative;

    @NotNull
    @Column(name = "mntNegativeProduit", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntNegativeProduit;

    @NotNull
    @Column(name = "mntNegativeLivraison", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntNegativeLivraison;

    @NotNull
    @Column(name = "mntCNSS", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntCNSS;

    @NotNull
    @Column(name = "mntPenalite", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntPenalite;

    @NotNull
    @Column(name = "mntBenefice", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntBenefice;

    @NotNull
    @Column(name = "commissionParProduit", nullable = false, precision = 10, scale = 2)
    private BigDecimal commissionParProduit;

    @NotNull
    @Column(name = "primeSpecial", nullable = false, precision = 10, scale = 2)
    private BigDecimal primeSpecial;

    @NotNull
    @Column(name = "fraisSupp", nullable = false, precision = 10, scale = 2)
    private BigDecimal fraisSupp;

    @NotNull
    @Column(name = "primeCommercial", nullable = false, precision = 10, scale = 2)
    private BigDecimal primeCommercial;

    @NotNull
    @Column(name = "externe", nullable = false)
    private Boolean externe = false;

    @NotNull
    @Column(name = "totalMntVenduePrixCommercial", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalMntVenduePrixCommercial;

    @NotNull
    @Column(name = "totalMntVendueSansPrixCommercial", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalMntVendueSansPrixCommercial;

    @NotNull
    @Column(name = "primeProduit", nullable = false, precision = 10, scale = 2)
    private BigDecimal primeProduit;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "commercailId", nullable = false)
    private Repertoire commercail;

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

    public String getCodeBultin() {
        return codeBultin;
    }

    public void setCodeBultin(String codeBultin) {
        this.codeBultin = codeBultin;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Integer getNumbultin() {
        return numbultin;
    }

    public void setNumbultin(Integer numbultin) {
        this.numbultin = numbultin;
    }

    public Instant getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Instant dateOperation) {
        this.dateOperation = dateOperation;
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

    public Integer getIdTypeBultinPai() {
        return idTypeBultinPai;
    }

    public void setIdTypeBultinPai(Integer idTypeBultinPai) {
        this.idTypeBultinPai = idTypeBultinPai;
    }

    public BigDecimal getSalairefx() {
        return salairefx;
    }

    public void setSalairefx(BigDecimal salairefx) {
        this.salairefx = salairefx;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getFrais() {
        return frais;
    }

    public void setFrais(BigDecimal frais) {
        this.frais = frais;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalMntVendue() {
        return totalMntVendue;
    }

    public void setTotalMntVendue(BigDecimal totalMntVendue) {
        this.totalMntVendue = totalMntVendue;
    }

    public BigDecimal getTotalMntVendueProduit() {
        return totalMntVendueProduit;
    }

    public void setTotalMntVendueProduit(BigDecimal totalMntVendueProduit) {
        this.totalMntVendueProduit = totalMntVendueProduit;
    }

    public BigDecimal getTotalMntVendueLivraison() {
        return totalMntVendueLivraison;
    }

    public void setTotalMntVendueLivraison(BigDecimal totalMntVendueLivraison) {
        this.totalMntVendueLivraison = totalMntVendueLivraison;
    }

    public BigDecimal getMntNegative() {
        return mntNegative;
    }

    public void setMntNegative(BigDecimal mntNegative) {
        this.mntNegative = mntNegative;
    }

    public BigDecimal getMntNegativeProduit() {
        return mntNegativeProduit;
    }

    public void setMntNegativeProduit(BigDecimal mntNegativeProduit) {
        this.mntNegativeProduit = mntNegativeProduit;
    }

    public BigDecimal getMntNegativeLivraison() {
        return mntNegativeLivraison;
    }

    public void setMntNegativeLivraison(BigDecimal mntNegativeLivraison) {
        this.mntNegativeLivraison = mntNegativeLivraison;
    }

    public BigDecimal getMntCNSS() {
        return mntCNSS;
    }

    public void setMntCNSS(BigDecimal mntCNSS) {
        this.mntCNSS = mntCNSS;
    }

    public BigDecimal getMntPenalite() {
        return mntPenalite;
    }

    public void setMntPenalite(BigDecimal mntPenalite) {
        this.mntPenalite = mntPenalite;
    }

    public BigDecimal getMntBenefice() {
        return mntBenefice;
    }

    public void setMntBenefice(BigDecimal mntBenefice) {
        this.mntBenefice = mntBenefice;
    }

    public BigDecimal getCommissionParProduit() {
        return commissionParProduit;
    }

    public void setCommissionParProduit(BigDecimal commissionParProduit) {
        this.commissionParProduit = commissionParProduit;
    }

    public BigDecimal getPrimeSpecial() {
        return primeSpecial;
    }

    public void setPrimeSpecial(BigDecimal primeSpecial) {
        this.primeSpecial = primeSpecial;
    }

    public BigDecimal getFraisSupp() {
        return fraisSupp;
    }

    public void setFraisSupp(BigDecimal fraisSupp) {
        this.fraisSupp = fraisSupp;
    }

    public BigDecimal getPrimeCommercial() {
        return primeCommercial;
    }

    public void setPrimeCommercial(BigDecimal primeCommercial) {
        this.primeCommercial = primeCommercial;
    }

    public Boolean getExterne() {
        return externe;
    }

    public void setExterne(Boolean externe) {
        this.externe = externe;
    }

    public BigDecimal getTotalMntVenduePrixCommercial() {
        return totalMntVenduePrixCommercial;
    }

    public void setTotalMntVenduePrixCommercial(BigDecimal totalMntVenduePrixCommercial) {
        this.totalMntVenduePrixCommercial = totalMntVenduePrixCommercial;
    }

    public BigDecimal getTotalMntVendueSansPrixCommercial() {
        return totalMntVendueSansPrixCommercial;
    }

    public void setTotalMntVendueSansPrixCommercial(BigDecimal totalMntVendueSansPrixCommercial) {
        this.totalMntVendueSansPrixCommercial = totalMntVendueSansPrixCommercial;
    }

    public BigDecimal getPrimeProduit() {
        return primeProduit;
    }

    public void setPrimeProduit(BigDecimal primeProduit) {
        this.primeProduit = primeProduit;
    }

    public Repertoire getCommercail() {
        return commercail;
    }

    public void setCommercail(Repertoire commercail) {
        this.commercail = commercail;
    }

    public Employe getEmployeOperateur() {
        return employeOperateur;
    }

    public void setEmployeOperateur(Employe employeOperateur) {
        this.employeOperateur = employeOperateur;
    }

}