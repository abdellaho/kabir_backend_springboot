package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "droit")
public class Droit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "listRepertoire", nullable = false)
    private Boolean listRepertoire = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "listStock", nullable = false)
    private Boolean listStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "listLivraison", nullable = false)
    private Boolean listLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "listFacture", nullable = false)
    private Boolean listFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "listAchatLivraison", nullable = false)
    private Boolean listAchatLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "listAchatFacture", nullable = false)
    private Boolean listAchatFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "listAchatLivraisonEtranger", nullable = false)
    private Boolean listAchatLivraisonEtranger = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ajouterRepertoire", nullable = false)
    private Boolean ajouterRepertoire = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ajouterStock", nullable = false)
    private Boolean ajouterStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ajouterLivraison", nullable = false)
    private Boolean ajouterLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ajouterFacture", nullable = false)
    private Boolean ajouterFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ajouterAchatLivraison", nullable = false)
    private Boolean ajouterAchatLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ajouterAchatFacture", nullable = false)
    private Boolean ajouterAchatFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ajouterAchatLivraisonEtranger", nullable = false)
    private Boolean ajouterAchatLivraisonEtranger = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "modifRepertoire", nullable = false)
    private Boolean modifRepertoire = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "modifStock", nullable = false)
    private Boolean modifStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "modifLivraison", nullable = false)
    private Boolean modifLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "modifFacture", nullable = false)
    private Boolean modifFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "modifAchatLivraison", nullable = false)
    private Boolean modifAchatLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "modifAchatFacture", nullable = false)
    private Boolean modifAchatFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "modifAchatLivraisonEtranger", nullable = false)
    private Boolean modifAchatLivraisonEtranger = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "suppRepertoire", nullable = false)
    private Boolean suppRepertoire = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "suppStock", nullable = false)
    private Boolean suppStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "suppLivraison", nullable = false)
    private Boolean suppLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "suppFacture", nullable = false)
    private Boolean suppFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "suppAchatLivraison", nullable = false)
    private Boolean suppAchatLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "suppAchatFacture", nullable = false)
    private Boolean suppAchatFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "suppAchatLivraisonEtranger", nullable = false)
    private Boolean suppAchatLivraisonEtranger = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatRepertoire", nullable = false)
    private Boolean etatRepertoire = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatStock", nullable = false)
    private Boolean etatStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatLivraison", nullable = false)
    private Boolean etatLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatFacture", nullable = false)
    private Boolean etatFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatAchatLivraison", nullable = false)
    private Boolean etatAchatLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatAchatFacture", nullable = false)
    private Boolean etatAchatFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatAchatLivraisonEtranger", nullable = false)
    private Boolean etatAchatLivraisonEtranger = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "imprimeRepertoire", nullable = false)
    private Boolean imprimeRepertoire = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "imprimeStock", nullable = false)
    private Boolean imprimeStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "imprimeLivraison", nullable = false)
    private Boolean imprimeLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "imprimeFacture", nullable = false)
    private Boolean imprimeFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "imprimerAchatLivraison", nullable = false)
    private Boolean imprimerAchatLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "imprimerAchatFacture", nullable = false)
    private Boolean imprimerAchatFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "imprimerAchatLivraisonEtranger", nullable = false)
    private Boolean imprimerAchatLivraisonEtranger = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getListRepertoire() {
        return listRepertoire;
    }

    public void setListRepertoire(Boolean listRepertoire) {
        this.listRepertoire = listRepertoire;
    }

    public Boolean getListStock() {
        return listStock;
    }

    public void setListStock(Boolean listStock) {
        this.listStock = listStock;
    }

    public Boolean getListLivraison() {
        return listLivraison;
    }

    public void setListLivraison(Boolean listLivraison) {
        this.listLivraison = listLivraison;
    }

    public Boolean getListFacture() {
        return listFacture;
    }

    public void setListFacture(Boolean listFacture) {
        this.listFacture = listFacture;
    }

    public Boolean getListAchatLivraison() {
        return listAchatLivraison;
    }

    public void setListAchatLivraison(Boolean listAchatLivraison) {
        this.listAchatLivraison = listAchatLivraison;
    }

    public Boolean getListAchatFacture() {
        return listAchatFacture;
    }

    public void setListAchatFacture(Boolean listAchatFacture) {
        this.listAchatFacture = listAchatFacture;
    }

    public Boolean getListAchatLivraisonEtranger() {
        return listAchatLivraisonEtranger;
    }

    public void setListAchatLivraisonEtranger(Boolean listAchatLivraisonEtranger) {
        this.listAchatLivraisonEtranger = listAchatLivraisonEtranger;
    }

    public Boolean getAjouterRepertoire() {
        return ajouterRepertoire;
    }

    public void setAjouterRepertoire(Boolean ajouterRepertoire) {
        this.ajouterRepertoire = ajouterRepertoire;
    }

    public Boolean getAjouterStock() {
        return ajouterStock;
    }

    public void setAjouterStock(Boolean ajouterStock) {
        this.ajouterStock = ajouterStock;
    }

    public Boolean getAjouterLivraison() {
        return ajouterLivraison;
    }

    public void setAjouterLivraison(Boolean ajouterLivraison) {
        this.ajouterLivraison = ajouterLivraison;
    }

    public Boolean getAjouterFacture() {
        return ajouterFacture;
    }

    public void setAjouterFacture(Boolean ajouterFacture) {
        this.ajouterFacture = ajouterFacture;
    }

    public Boolean getAjouterAchatLivraison() {
        return ajouterAchatLivraison;
    }

    public void setAjouterAchatLivraison(Boolean ajouterAchatLivraison) {
        this.ajouterAchatLivraison = ajouterAchatLivraison;
    }

    public Boolean getAjouterAchatFacture() {
        return ajouterAchatFacture;
    }

    public void setAjouterAchatFacture(Boolean ajouterAchatFacture) {
        this.ajouterAchatFacture = ajouterAchatFacture;
    }

    public Boolean getAjouterAchatLivraisonEtranger() {
        return ajouterAchatLivraisonEtranger;
    }

    public void setAjouterAchatLivraisonEtranger(Boolean ajouterAchatLivraisonEtranger) {
        this.ajouterAchatLivraisonEtranger = ajouterAchatLivraisonEtranger;
    }

    public Boolean getModifRepertoire() {
        return modifRepertoire;
    }

    public void setModifRepertoire(Boolean modifRepertoire) {
        this.modifRepertoire = modifRepertoire;
    }

    public Boolean getModifStock() {
        return modifStock;
    }

    public void setModifStock(Boolean modifStock) {
        this.modifStock = modifStock;
    }

    public Boolean getModifLivraison() {
        return modifLivraison;
    }

    public void setModifLivraison(Boolean modifLivraison) {
        this.modifLivraison = modifLivraison;
    }

    public Boolean getModifFacture() {
        return modifFacture;
    }

    public void setModifFacture(Boolean modifFacture) {
        this.modifFacture = modifFacture;
    }

    public Boolean getModifAchatLivraison() {
        return modifAchatLivraison;
    }

    public void setModifAchatLivraison(Boolean modifAchatLivraison) {
        this.modifAchatLivraison = modifAchatLivraison;
    }

    public Boolean getModifAchatFacture() {
        return modifAchatFacture;
    }

    public void setModifAchatFacture(Boolean modifAchatFacture) {
        this.modifAchatFacture = modifAchatFacture;
    }

    public Boolean getModifAchatLivraisonEtranger() {
        return modifAchatLivraisonEtranger;
    }

    public void setModifAchatLivraisonEtranger(Boolean modifAchatLivraisonEtranger) {
        this.modifAchatLivraisonEtranger = modifAchatLivraisonEtranger;
    }

    public Boolean getSuppRepertoire() {
        return suppRepertoire;
    }

    public void setSuppRepertoire(Boolean suppRepertoire) {
        this.suppRepertoire = suppRepertoire;
    }

    public Boolean getSuppStock() {
        return suppStock;
    }

    public void setSuppStock(Boolean suppStock) {
        this.suppStock = suppStock;
    }

    public Boolean getSuppLivraison() {
        return suppLivraison;
    }

    public void setSuppLivraison(Boolean suppLivraison) {
        this.suppLivraison = suppLivraison;
    }

    public Boolean getSuppFacture() {
        return suppFacture;
    }

    public void setSuppFacture(Boolean suppFacture) {
        this.suppFacture = suppFacture;
    }

    public Boolean getSuppAchatLivraison() {
        return suppAchatLivraison;
    }

    public void setSuppAchatLivraison(Boolean suppAchatLivraison) {
        this.suppAchatLivraison = suppAchatLivraison;
    }

    public Boolean getSuppAchatFacture() {
        return suppAchatFacture;
    }

    public void setSuppAchatFacture(Boolean suppAchatFacture) {
        this.suppAchatFacture = suppAchatFacture;
    }

    public Boolean getSuppAchatLivraisonEtranger() {
        return suppAchatLivraisonEtranger;
    }

    public void setSuppAchatLivraisonEtranger(Boolean suppAchatLivraisonEtranger) {
        this.suppAchatLivraisonEtranger = suppAchatLivraisonEtranger;
    }

    public Boolean getEtatRepertoire() {
        return etatRepertoire;
    }

    public void setEtatRepertoire(Boolean etatRepertoire) {
        this.etatRepertoire = etatRepertoire;
    }

    public Boolean getEtatStock() {
        return etatStock;
    }

    public void setEtatStock(Boolean etatStock) {
        this.etatStock = etatStock;
    }

    public Boolean getEtatLivraison() {
        return etatLivraison;
    }

    public void setEtatLivraison(Boolean etatLivraison) {
        this.etatLivraison = etatLivraison;
    }

    public Boolean getEtatFacture() {
        return etatFacture;
    }

    public void setEtatFacture(Boolean etatFacture) {
        this.etatFacture = etatFacture;
    }

    public Boolean getEtatAchatLivraison() {
        return etatAchatLivraison;
    }

    public void setEtatAchatLivraison(Boolean etatAchatLivraison) {
        this.etatAchatLivraison = etatAchatLivraison;
    }

    public Boolean getEtatAchatFacture() {
        return etatAchatFacture;
    }

    public void setEtatAchatFacture(Boolean etatAchatFacture) {
        this.etatAchatFacture = etatAchatFacture;
    }

    public Boolean getEtatAchatLivraisonEtranger() {
        return etatAchatLivraisonEtranger;
    }

    public void setEtatAchatLivraisonEtranger(Boolean etatAchatLivraisonEtranger) {
        this.etatAchatLivraisonEtranger = etatAchatLivraisonEtranger;
    }

    public Boolean getImprimeRepertoire() {
        return imprimeRepertoire;
    }

    public void setImprimeRepertoire(Boolean imprimeRepertoire) {
        this.imprimeRepertoire = imprimeRepertoire;
    }

    public Boolean getImprimeStock() {
        return imprimeStock;
    }

    public void setImprimeStock(Boolean imprimeStock) {
        this.imprimeStock = imprimeStock;
    }

    public Boolean getImprimeLivraison() {
        return imprimeLivraison;
    }

    public void setImprimeLivraison(Boolean imprimeLivraison) {
        this.imprimeLivraison = imprimeLivraison;
    }

    public Boolean getImprimeFacture() {
        return imprimeFacture;
    }

    public void setImprimeFacture(Boolean imprimeFacture) {
        this.imprimeFacture = imprimeFacture;
    }

    public Boolean getImprimerAchatLivraison() {
        return imprimerAchatLivraison;
    }

    public void setImprimerAchatLivraison(Boolean imprimerAchatLivraison) {
        this.imprimerAchatLivraison = imprimerAchatLivraison;
    }

    public Boolean getImprimerAchatFacture() {
        return imprimerAchatFacture;
    }

    public void setImprimerAchatFacture(Boolean imprimerAchatFacture) {
        this.imprimerAchatFacture = imprimerAchatFacture;
    }

    public Boolean getImprimerAchatLivraisonEtranger() {
        return imprimerAchatLivraisonEtranger;
    }

    public void setImprimerAchatLivraisonEtranger(Boolean imprimerAchatLivraisonEtranger) {
        this.imprimerAchatLivraisonEtranger = imprimerAchatLivraisonEtranger;
    }

}