package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "achatfacture")
public class Achatfacture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeAF", nullable = false, length = 191)
    private String codeAF;

    @NotNull
    @Column(name = "numAchat", nullable = false)
    private Integer numAchat;

    @NotNull
    @Column(name = "dateAF", nullable = false)
    private LocalDate dateAF;

    @NotNull
    @Column(name = "sysDate", nullable = false)
    private Instant sysDate;

    @NotNull
    @Column(name = "dateAvances", nullable = false)
    private LocalDate dateAvances;

    @NotNull
    @Column(name = "mantantAvancs", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantAvancs;

    @NotNull
    @Column(name = "prixNormalAchatHt", nullable = false)
    private Integer prixNormalAchatHt;

    @Size(max = 191)
    @NotNull
    @Column(name = "numeroFacExterne", nullable = false, length = 191)
    private String numeroFacExterne;

    @Size(max = 191)
    @NotNull
    @Column(name = "numeroIF", nullable = false, length = 191)
    private String numeroIF;

    @NotNull
    @Column(name = "mantantAF", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantAF;

    @NotNull
    @Column(name = "mantantBFBenefice", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantBFBenefice;

    @NotNull
    @Column(name = "montantNonTaxable", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantNonTaxable;

    @NotNull
    @Column(name = "dateReglement", nullable = false)
    private LocalDate dateReglement;

    @NotNull
    @Column(name = "typeReglment", nullable = false)
    private Integer typeReglment;

    @Size(max = 191)
    @NotNull
    @Column(name = "typePaiement", nullable = false, length = 191)
    private String typePaiement;

    @Size(max = 191)
    @NotNull
    @Column(name = "numCheque", nullable = false, length = 191)
    private String numCheque;

    @NotNull
    @Column(name = "mantantTotHT", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantTotHT;

    @NotNull
    @Column(name = "mantantTotHTVA", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantTotHTVA;

    @NotNull
    @Column(name = "mantantTotTTC", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantTotTTC;

    @NotNull
    @Column(name = "tva20", nullable = false, precision = 10, scale = 2)
    private BigDecimal tva20;

    @NotNull
    @Column(name = "tva7", nullable = false, precision = 10, scale = 2)
    private BigDecimal tva7;

    @NotNull
    @Column(name = "tvaArbtraire", nullable = false, precision = 10, scale = 2)
    private BigDecimal tvaArbtraire;

    @NotNull
    @Column(name = "manuelAutoMatique", nullable = false)
    private Integer manuelAutoMatique;

    @NotNull
    @Column(name = "mntManuelTva7", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntManuelTva7;

    @NotNull
    @Column(name = "mntManuelTva10", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntManuelTva10;

    @NotNull
    @Column(name = "mntManuelTva12", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntManuelTva12;

    @NotNull
    @Column(name = "mntManuelTva13", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntManuelTva13;

    @NotNull
    @Column(name = "mntManuelTva14", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntManuelTva14;

    @NotNull
    @Column(name = "mntManuelTva20", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntManuelTva20;

    @NotNull
    @Column(name = "montantTVA7", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTVA7;

    @NotNull
    @Column(name = "montantTVA10", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTVA10;

    @NotNull
    @Column(name = "montantTVA12", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTVA12;

    @NotNull
    @Column(name = "montantTVA13", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTVA13;

    @NotNull
    @Column(name = "montantTVA14", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTVA14;

    @NotNull
    @Column(name = "montantTVA20", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTVA20;

    @NotNull
    @Column(name = "mntHtTVA7", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntHtTVA7;

    @NotNull
    @Column(name = "mntHtTVA10", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntHtTVA10;

    @NotNull
    @Column(name = "mntHtTVA12", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntHtTVA12;

    @NotNull
    @Column(name = "mntHtTVA13", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntHtTVA13;

    @NotNull
    @Column(name = "mntHtTVA14", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntHtTVA14;

    @NotNull
    @Column(name = "mntHtTVA20", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntHtTVA20;

    @NotNull
    @Column(name = "mntTtcTVA7", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntTtcTVA7;

    @NotNull
    @Column(name = "mntTtcTVA10", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntTtcTVA10;

    @NotNull
    @Column(name = "mntTtcTVA12", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntTtcTVA12;

    @NotNull
    @Column(name = "mntTtcTVA13", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntTtcTVA13;

    @NotNull
    @Column(name = "mntTtcTVA14", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntTtcTVA14;

    @NotNull
    @Column(name = "mntTtcTVA20", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntTtcTVA20;

    @NotNull
    @Column(name = "mntTtc", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntTtc;

    @NotNull
    @Column(name = "montantDroitSupplementaire", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantDroitSupplementaire;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "disabledHT", nullable = false)
    private Boolean disabledHT = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "disabledManuel", nullable = false)
    private Boolean disabledManuel = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeAF() {
        return codeAF;
    }

    public void setCodeAF(String codeAF) {
        this.codeAF = codeAF;
    }

    public Integer getNumAchat() {
        return numAchat;
    }

    public void setNumAchat(Integer numAchat) {
        this.numAchat = numAchat;
    }

    public LocalDate getDateAF() {
        return dateAF;
    }

    public void setDateAF(LocalDate dateAF) {
        this.dateAF = dateAF;
    }

    public Instant getSysDate() {
        return sysDate;
    }

    public void setSysDate(Instant sysDate) {
        this.sysDate = sysDate;
    }

    public LocalDate getDateAvances() {
        return dateAvances;
    }

    public void setDateAvances(LocalDate dateAvances) {
        this.dateAvances = dateAvances;
    }

    public BigDecimal getMantantAvancs() {
        return mantantAvancs;
    }

    public void setMantantAvancs(BigDecimal mantantAvancs) {
        this.mantantAvancs = mantantAvancs;
    }

    public Integer getPrixNormalAchatHt() {
        return prixNormalAchatHt;
    }

    public void setPrixNormalAchatHt(Integer prixNormalAchatHt) {
        this.prixNormalAchatHt = prixNormalAchatHt;
    }

    public String getNumeroFacExterne() {
        return numeroFacExterne;
    }

    public void setNumeroFacExterne(String numeroFacExterne) {
        this.numeroFacExterne = numeroFacExterne;
    }

    public String getNumeroIF() {
        return numeroIF;
    }

    public void setNumeroIF(String numeroIF) {
        this.numeroIF = numeroIF;
    }

    public BigDecimal getMantantAF() {
        return mantantAF;
    }

    public void setMantantAF(BigDecimal mantantAF) {
        this.mantantAF = mantantAF;
    }

    public BigDecimal getMantantBFBenefice() {
        return mantantBFBenefice;
    }

    public void setMantantBFBenefice(BigDecimal mantantBFBenefice) {
        this.mantantBFBenefice = mantantBFBenefice;
    }

    public BigDecimal getMontantNonTaxable() {
        return montantNonTaxable;
    }

    public void setMontantNonTaxable(BigDecimal montantNonTaxable) {
        this.montantNonTaxable = montantNonTaxable;
    }

    public LocalDate getDateReglement() {
        return dateReglement;
    }

    public void setDateReglement(LocalDate dateReglement) {
        this.dateReglement = dateReglement;
    }

    public Integer getTypeReglment() {
        return typeReglment;
    }

    public void setTypeReglment(Integer typeReglment) {
        this.typeReglment = typeReglment;
    }

    public String getTypePaiement() {
        return typePaiement;
    }

    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }

    public String getNumCheque() {
        return numCheque;
    }

    public void setNumCheque(String numCheque) {
        this.numCheque = numCheque;
    }

    public BigDecimal getMantantTotHT() {
        return mantantTotHT;
    }

    public void setMantantTotHT(BigDecimal mantantTotHT) {
        this.mantantTotHT = mantantTotHT;
    }

    public BigDecimal getMantantTotHTVA() {
        return mantantTotHTVA;
    }

    public void setMantantTotHTVA(BigDecimal mantantTotHTVA) {
        this.mantantTotHTVA = mantantTotHTVA;
    }

    public BigDecimal getMantantTotTTC() {
        return mantantTotTTC;
    }

    public void setMantantTotTTC(BigDecimal mantantTotTTC) {
        this.mantantTotTTC = mantantTotTTC;
    }

    public BigDecimal getTva20() {
        return tva20;
    }

    public void setTva20(BigDecimal tva20) {
        this.tva20 = tva20;
    }

    public BigDecimal getTva7() {
        return tva7;
    }

    public void setTva7(BigDecimal tva7) {
        this.tva7 = tva7;
    }

    public BigDecimal getTvaArbtraire() {
        return tvaArbtraire;
    }

    public void setTvaArbtraire(BigDecimal tvaArbtraire) {
        this.tvaArbtraire = tvaArbtraire;
    }

    public Integer getManuelAutoMatique() {
        return manuelAutoMatique;
    }

    public void setManuelAutoMatique(Integer manuelAutoMatique) {
        this.manuelAutoMatique = manuelAutoMatique;
    }

    public BigDecimal getMntManuelTva7() {
        return mntManuelTva7;
    }

    public void setMntManuelTva7(BigDecimal mntManuelTva7) {
        this.mntManuelTva7 = mntManuelTva7;
    }

    public BigDecimal getMntManuelTva10() {
        return mntManuelTva10;
    }

    public void setMntManuelTva10(BigDecimal mntManuelTva10) {
        this.mntManuelTva10 = mntManuelTva10;
    }

    public BigDecimal getMntManuelTva12() {
        return mntManuelTva12;
    }

    public void setMntManuelTva12(BigDecimal mntManuelTva12) {
        this.mntManuelTva12 = mntManuelTva12;
    }

    public BigDecimal getMntManuelTva13() {
        return mntManuelTva13;
    }

    public void setMntManuelTva13(BigDecimal mntManuelTva13) {
        this.mntManuelTva13 = mntManuelTva13;
    }

    public BigDecimal getMntManuelTva14() {
        return mntManuelTva14;
    }

    public void setMntManuelTva14(BigDecimal mntManuelTva14) {
        this.mntManuelTva14 = mntManuelTva14;
    }

    public BigDecimal getMntManuelTva20() {
        return mntManuelTva20;
    }

    public void setMntManuelTva20(BigDecimal mntManuelTva20) {
        this.mntManuelTva20 = mntManuelTva20;
    }

    public BigDecimal getMontantTVA7() {
        return montantTVA7;
    }

    public void setMontantTVA7(BigDecimal montantTVA7) {
        this.montantTVA7 = montantTVA7;
    }

    public BigDecimal getMontantTVA10() {
        return montantTVA10;
    }

    public void setMontantTVA10(BigDecimal montantTVA10) {
        this.montantTVA10 = montantTVA10;
    }

    public BigDecimal getMontantTVA12() {
        return montantTVA12;
    }

    public void setMontantTVA12(BigDecimal montantTVA12) {
        this.montantTVA12 = montantTVA12;
    }

    public BigDecimal getMontantTVA13() {
        return montantTVA13;
    }

    public void setMontantTVA13(BigDecimal montantTVA13) {
        this.montantTVA13 = montantTVA13;
    }

    public BigDecimal getMontantTVA14() {
        return montantTVA14;
    }

    public void setMontantTVA14(BigDecimal montantTVA14) {
        this.montantTVA14 = montantTVA14;
    }

    public BigDecimal getMontantTVA20() {
        return montantTVA20;
    }

    public void setMontantTVA20(BigDecimal montantTVA20) {
        this.montantTVA20 = montantTVA20;
    }

    public BigDecimal getMntHtTVA7() {
        return mntHtTVA7;
    }

    public void setMntHtTVA7(BigDecimal mntHtTVA7) {
        this.mntHtTVA7 = mntHtTVA7;
    }

    public BigDecimal getMntHtTVA10() {
        return mntHtTVA10;
    }

    public void setMntHtTVA10(BigDecimal mntHtTVA10) {
        this.mntHtTVA10 = mntHtTVA10;
    }

    public BigDecimal getMntHtTVA12() {
        return mntHtTVA12;
    }

    public void setMntHtTVA12(BigDecimal mntHtTVA12) {
        this.mntHtTVA12 = mntHtTVA12;
    }

    public BigDecimal getMntHtTVA13() {
        return mntHtTVA13;
    }

    public void setMntHtTVA13(BigDecimal mntHtTVA13) {
        this.mntHtTVA13 = mntHtTVA13;
    }

    public BigDecimal getMntHtTVA14() {
        return mntHtTVA14;
    }

    public void setMntHtTVA14(BigDecimal mntHtTVA14) {
        this.mntHtTVA14 = mntHtTVA14;
    }

    public BigDecimal getMntHtTVA20() {
        return mntHtTVA20;
    }

    public void setMntHtTVA20(BigDecimal mntHtTVA20) {
        this.mntHtTVA20 = mntHtTVA20;
    }

    public BigDecimal getMntTtcTVA7() {
        return mntTtcTVA7;
    }

    public void setMntTtcTVA7(BigDecimal mntTtcTVA7) {
        this.mntTtcTVA7 = mntTtcTVA7;
    }

    public BigDecimal getMntTtcTVA10() {
        return mntTtcTVA10;
    }

    public void setMntTtcTVA10(BigDecimal mntTtcTVA10) {
        this.mntTtcTVA10 = mntTtcTVA10;
    }

    public BigDecimal getMntTtcTVA12() {
        return mntTtcTVA12;
    }

    public void setMntTtcTVA12(BigDecimal mntTtcTVA12) {
        this.mntTtcTVA12 = mntTtcTVA12;
    }

    public BigDecimal getMntTtcTVA13() {
        return mntTtcTVA13;
    }

    public void setMntTtcTVA13(BigDecimal mntTtcTVA13) {
        this.mntTtcTVA13 = mntTtcTVA13;
    }

    public BigDecimal getMntTtcTVA14() {
        return mntTtcTVA14;
    }

    public void setMntTtcTVA14(BigDecimal mntTtcTVA14) {
        this.mntTtcTVA14 = mntTtcTVA14;
    }

    public BigDecimal getMntTtcTVA20() {
        return mntTtcTVA20;
    }

    public void setMntTtcTVA20(BigDecimal mntTtcTVA20) {
        this.mntTtcTVA20 = mntTtcTVA20;
    }

    public BigDecimal getMntTtc() {
        return mntTtc;
    }

    public void setMntTtc(BigDecimal mntTtc) {
        this.mntTtc = mntTtc;
    }

    public BigDecimal getMontantDroitSupplementaire() {
        return montantDroitSupplementaire;
    }

    public void setMontantDroitSupplementaire(BigDecimal montantDroitSupplementaire) {
        this.montantDroitSupplementaire = montantDroitSupplementaire;
    }

    public Boolean getDisabledHT() {
        return disabledHT;
    }

    public void setDisabledHT(Boolean disabledHT) {
        this.disabledHT = disabledHT;
    }

    public Boolean getDisabledManuel() {
        return disabledManuel;
    }

    public void setDisabledManuel(Boolean disabledManuel) {
        this.disabledManuel = disabledManuel;
    }

}