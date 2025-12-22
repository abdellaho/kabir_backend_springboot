package com.kabir.kabirbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private boolean listRepertoire = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "listStock", nullable = false)
    private boolean listStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "listLivraison", nullable = false)
    private boolean listLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "listFacture", nullable = false)
    private boolean listFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "listAchatLivraison", nullable = false)
    private boolean listAchatLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "listAchatFacture", nullable = false)
    private boolean listAchatFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "listAchatLivraisonEtranger", nullable = false)
    private boolean listAchatLivraisonEtranger = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ajouterRepertoire", nullable = false)
    private boolean ajouterRepertoire = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ajouterStock", nullable = false)
    private boolean ajouterStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ajouterLivraison", nullable = false)
    private boolean ajouterLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ajouterFacture", nullable = false)
    private boolean ajouterFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ajouterAchatLivraison", nullable = false)
    private boolean ajouterAchatLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ajouterAchatFacture", nullable = false)
    private boolean ajouterAchatFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ajouterAchatLivraisonEtranger", nullable = false)
    private boolean ajouterAchatLivraisonEtranger = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "modifRepertoire", nullable = false)
    private boolean modifRepertoire = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "modifStock", nullable = false)
    private boolean modifStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "modifLivraison", nullable = false)
    private boolean modifLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "modifFacture", nullable = false)
    private boolean modifFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "modifAchatLivraison", nullable = false)
    private boolean modifAchatLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "modifAchatFacture", nullable = false)
    private boolean modifAchatFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "modifAchatLivraisonEtranger", nullable = false)
    private boolean modifAchatLivraisonEtranger = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "suppRepertoire", nullable = false)
    private boolean suppRepertoire = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "suppStock", nullable = false)
    private boolean suppStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "suppLivraison", nullable = false)
    private boolean suppLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "suppFacture", nullable = false)
    private boolean suppFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "suppAchatLivraison", nullable = false)
    private boolean suppAchatLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "suppAchatFacture", nullable = false)
    private boolean suppAchatFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "suppAchatLivraisonEtranger", nullable = false)
    private boolean suppAchatLivraisonEtranger = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatRepertoire", nullable = false)
    private boolean etatRepertoire = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatStock", nullable = false)
    private boolean etatStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatLivraison", nullable = false)
    private boolean etatLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatFacture", nullable = false)
    private boolean etatFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatAchatLivraison", nullable = false)
    private boolean etatAchatLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatAchatFacture", nullable = false)
    private boolean etatAchatFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "etatAchatLivraisonEtranger", nullable = false)
    private boolean etatAchatLivraisonEtranger = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "imprimeRepertoire", nullable = false)
    private boolean imprimeRepertoire = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "imprimeStock", nullable = false)
    private boolean imprimeStock = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "imprimeLivraison", nullable = false)
    private boolean imprimeLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "imprimeFacture", nullable = false)
    private boolean imprimeFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "imprimerAchatLivraison", nullable = false)
    private boolean imprimerAchatLivraison = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "imprimerAchatFacture", nullable = false)
    private boolean imprimerAchatFacture = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "imprimerAchatLivraisonEtranger", nullable = false)
    private boolean imprimerAchatLivraisonEtranger = false;

}