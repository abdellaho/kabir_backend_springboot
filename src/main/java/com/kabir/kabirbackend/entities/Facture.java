package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "facture")
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "numFacture", nullable = false)
    private Integer numFacture;

    @Size(max = 191)
    @NotNull
    @Column(name = "codeBF", nullable = false, length = 191)
    private String codeBF;

    @NotNull
    @Column(name = "dateBF", nullable = false)
    private LocalDate dateBF;

    @NotNull
    @Column(name = "dateReglement", nullable = false)
    private LocalDate dateReglement;

    @NotNull
    @Column(name = "dateReglement2", nullable = false)
    private LocalDate dateReglement2;

    @NotNull
    @Column(name = "dateReglement3", nullable = false)
    private LocalDate dateReglement3;

    @NotNull
    @Column(name = "dateReglement4", nullable = false)
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

    @Size(max = 191)
    @NotNull
    @Column(name = "typePaiement", nullable = false, length = 191)
    private String typePaiement;

    @NotNull
    @Column(name = "mantantBF", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantBF;

    @NotNull
    @Column(name = "mantantBFBenefice", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantBFBenefice;

    @NotNull
    @Column(name = "tva7Total", nullable = false, precision = 10, scale = 2)
    private BigDecimal tva7Total;

    @NotNull
    @Column(name = "tva20Total", nullable = false, precision = 10, scale = 2)
    private BigDecimal tva20Total;

    @NotNull
    @Column(name = "sysDate", nullable = false)
    private Instant sysDate;

    @NotNull
    @Column(name = "mantantBFHT", nullable = false, precision = 10, scale = 2)
    private BigDecimal mantantBFHT;

    @Size(max = 191)
    @NotNull
    @Column(name = "numeroLibil", nullable = false, length = 191)
    private String numeroLibil;

    @NotNull
    @Column(name = "mntHT2O", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntHT2O;

    @NotNull
    @Column(name = "mntHT7", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntHT7;

    @NotNull
    @Column(name = "tva7", nullable = false, precision = 10, scale = 2)
    private BigDecimal tva7;

    @NotNull
    @Column(name = "tva20", nullable = false, precision = 10, scale = 2)
    private BigDecimal tva20;

    @Size(max = 191)
    @NotNull
    @Column(name = "numCheque", nullable = false, length = 191)
    private String numCheque;

    @Size(max = 191)
    @NotNull
    @Column(name = "numCheque2", nullable = false, length = 191)
    private String numCheque2;

    @Size(max = 191)
    @NotNull
    @Column(name = "numCheque3", nullable = false, length = 191)
    private String numCheque3;

    @Size(max = 191)
    @NotNull
    @Column(name = "numCheque4", nullable = false, length = 191)
    private String numCheque4;

    @Size(max = 191)
    @NotNull
    @Column(name = "numRemise", nullable = false, length = 191)
    private String numRemise;

    @Size(max = 191)
    @NotNull
    @Column(name = "numRemise2", nullable = false, length = 191)
    private String numRemise2;

    @Size(max = 191)
    @NotNull
    @Column(name = "numRemise3", nullable = false, length = 191)
    private String numRemise3;

    @Size(max = 191)
    @NotNull
    @Column(name = "numRemise4", nullable = false, length = 191)
    private String numRemise4;

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
    @Column(name = "type", nullable = false)
    private Integer type;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "facturer100", nullable = false)
    private Boolean facturer100 = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "calculer", nullable = false)
    private Boolean calculer = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "disableMontant", nullable = false)
    private Boolean disableMontant = false;

    @Size(max = 191)
    @NotNull
    @Column(name = "typeTVA", nullable = false, length = 191)
    private String typeTVA;

    @ColumnDefault("0")
    @Column(name = "dateReglementIn")
    private Boolean dateReglementIn;

    @ColumnDefault("0")
    @Column(name = "dateReglement2In")
    private Boolean dateReglement2In;

    @ColumnDefault("0")
    @Column(name = "dateReglement3In")
    private Boolean dateReglement3In;

    @ColumnDefault("0")
    @Column(name = "dateReglement4In")
    private Boolean dateReglement4In;

    @NotNull
    @Column(name = "tva20Reglement1", nullable = false, precision = 10, scale = 2)
    private BigDecimal tva20Reglement1;

    @NotNull
    @Column(name = "tva20Reglement2", nullable = false, precision = 10, scale = 2)
    private BigDecimal tva20Reglement2;

    @NotNull
    @Column(name = "tva20Reglement3", nullable = false, precision = 10, scale = 2)
    private BigDecimal tva20Reglement3;

    @NotNull
    @Column(name = "tva20Reglement4", nullable = false, precision = 10, scale = 2)
    private BigDecimal tva20Reglement4;

    @NotNull
    @Column(name = "mntHT20Reglement1", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntHT20Reglement1;

    @NotNull
    @Column(name = "mntHT20Reglement2", nullable = false, precision = 10, scale = 2)
    private BigDecimal mntHT20Reglement2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumFacture() {
        return numFacture;
    }

    public void setNumFacture(Integer numFacture) {
        this.numFacture = numFacture;
    }

    public String getCodeBF() {
        return codeBF;
    }

    public void setCodeBF(String codeBF) {
        this.codeBF = codeBF;
    }

    public LocalDate getDateBF() {
        return dateBF;
    }

    public void setDateBF(LocalDate dateBF) {
        this.dateBF = dateBF;
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

    public String getTypePaiement() {
        return typePaiement;
    }

    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }

    public BigDecimal getMantantBF() {
        return mantantBF;
    }

    public void setMantantBF(BigDecimal mantantBF) {
        this.mantantBF = mantantBF;
    }

    public BigDecimal getMantantBFBenefice() {
        return mantantBFBenefice;
    }

    public void setMantantBFBenefice(BigDecimal mantantBFBenefice) {
        this.mantantBFBenefice = mantantBFBenefice;
    }

    public BigDecimal getTva7Total() {
        return tva7Total;
    }

    public void setTva7Total(BigDecimal tva7Total) {
        this.tva7Total = tva7Total;
    }

    public BigDecimal getTva20Total() {
        return tva20Total;
    }

    public void setTva20Total(BigDecimal tva20Total) {
        this.tva20Total = tva20Total;
    }

    public Instant getSysDate() {
        return sysDate;
    }

    public void setSysDate(Instant sysDate) {
        this.sysDate = sysDate;
    }

    public BigDecimal getMantantBFHT() {
        return mantantBFHT;
    }

    public void setMantantBFHT(BigDecimal mantantBFHT) {
        this.mantantBFHT = mantantBFHT;
    }

    public String getNumeroLibil() {
        return numeroLibil;
    }

    public void setNumeroLibil(String numeroLibil) {
        this.numeroLibil = numeroLibil;
    }

    public BigDecimal getMntHT2O() {
        return mntHT2O;
    }

    public void setMntHT2O(BigDecimal mntHT2O) {
        this.mntHT2O = mntHT2O;
    }

    public BigDecimal getMntHT7() {
        return mntHT7;
    }

    public void setMntHT7(BigDecimal mntHT7) {
        this.mntHT7 = mntHT7;
    }

    public BigDecimal getTva7() {
        return tva7;
    }

    public void setTva7(BigDecimal tva7) {
        this.tva7 = tva7;
    }

    public BigDecimal getTva20() {
        return tva20;
    }

    public void setTva20(BigDecimal tva20) {
        this.tva20 = tva20;
    }

    public String getNumCheque() {
        return numCheque;
    }

    public void setNumCheque(String numCheque) {
        this.numCheque = numCheque;
    }

    public String getNumCheque2() {
        return numCheque2;
    }

    public void setNumCheque2(String numCheque2) {
        this.numCheque2 = numCheque2;
    }

    public String getNumCheque3() {
        return numCheque3;
    }

    public void setNumCheque3(String numCheque3) {
        this.numCheque3 = numCheque3;
    }

    public String getNumCheque4() {
        return numCheque4;
    }

    public void setNumCheque4(String numCheque4) {
        this.numCheque4 = numCheque4;
    }

    public String getNumRemise() {
        return numRemise;
    }

    public void setNumRemise(String numRemise) {
        this.numRemise = numRemise;
    }

    public String getNumRemise2() {
        return numRemise2;
    }

    public void setNumRemise2(String numRemise2) {
        this.numRemise2 = numRemise2;
    }

    public String getNumRemise3() {
        return numRemise3;
    }

    public void setNumRemise3(String numRemise3) {
        this.numRemise3 = numRemise3;
    }

    public String getNumRemise4() {
        return numRemise4;
    }

    public void setNumRemise4(String numRemise4) {
        this.numRemise4 = numRemise4;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getFacturer100() {
        return facturer100;
    }

    public void setFacturer100(Boolean facturer100) {
        this.facturer100 = facturer100;
    }

    public Boolean getCalculer() {
        return calculer;
    }

    public void setCalculer(Boolean calculer) {
        this.calculer = calculer;
    }

    public Boolean getDisableMontant() {
        return disableMontant;
    }

    public void setDisableMontant(Boolean disableMontant) {
        this.disableMontant = disableMontant;
    }

    public String getTypeTVA() {
        return typeTVA;
    }

    public void setTypeTVA(String typeTVA) {
        this.typeTVA = typeTVA;
    }

    public Boolean getDateReglementIn() {
        return dateReglementIn;
    }

    public void setDateReglementIn(Boolean dateReglementIn) {
        this.dateReglementIn = dateReglementIn;
    }

    public Boolean getDateReglement2In() {
        return dateReglement2In;
    }

    public void setDateReglement2In(Boolean dateReglement2In) {
        this.dateReglement2In = dateReglement2In;
    }

    public Boolean getDateReglement3In() {
        return dateReglement3In;
    }

    public void setDateReglement3In(Boolean dateReglement3In) {
        this.dateReglement3In = dateReglement3In;
    }

    public Boolean getDateReglement4In() {
        return dateReglement4In;
    }

    public void setDateReglement4In(Boolean dateReglement4In) {
        this.dateReglement4In = dateReglement4In;
    }

    public BigDecimal getTva20Reglement1() {
        return tva20Reglement1;
    }

    public void setTva20Reglement1(BigDecimal tva20Reglement1) {
        this.tva20Reglement1 = tva20Reglement1;
    }

    public BigDecimal getTva20Reglement2() {
        return tva20Reglement2;
    }

    public void setTva20Reglement2(BigDecimal tva20Reglement2) {
        this.tva20Reglement2 = tva20Reglement2;
    }

    public BigDecimal getTva20Reglement3() {
        return tva20Reglement3;
    }

    public void setTva20Reglement3(BigDecimal tva20Reglement3) {
        this.tva20Reglement3 = tva20Reglement3;
    }

    public BigDecimal getTva20Reglement4() {
        return tva20Reglement4;
    }

    public void setTva20Reglement4(BigDecimal tva20Reglement4) {
        this.tva20Reglement4 = tva20Reglement4;
    }

    public BigDecimal getMntHT20Reglement1() {
        return mntHT20Reglement1;
    }

    public void setMntHT20Reglement1(BigDecimal mntHT20Reglement1) {
        this.mntHT20Reglement1 = mntHT20Reglement1;
    }

    public BigDecimal getMntHT20Reglement2() {
        return mntHT20Reglement2;
    }

    public void setMntHT20Reglement2(BigDecimal mntHT20Reglement2) {
        this.mntHT20Reglement2 = mntHT20Reglement2;
    }

}