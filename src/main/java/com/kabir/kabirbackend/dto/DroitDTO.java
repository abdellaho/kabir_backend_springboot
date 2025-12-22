package com.kabir.kabirbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.kabir.kabirbackend.entities.Droit}
 */
@Value
public class DroitDTO implements Serializable {
    Long id;
    
    boolean listRepertoire;
    boolean listStock;
    boolean listLivraison;
    boolean listFacture;
    boolean listAchatLivraison;
    boolean listAchatFacture;
    boolean listAchatLivraisonEtranger;
    boolean ajouterRepertoire;
    boolean ajouterStock;
    boolean ajouterLivraison;
    boolean ajouterFacture;
    boolean ajouterAchatLivraison;
    boolean ajouterAchatFacture;
    boolean ajouterAchatLivraisonEtranger;
    boolean modifRepertoire;
    boolean modifStock;
    boolean modifLivraison;
    boolean modifFacture;
    boolean modifAchatLivraison;
    boolean modifAchatFacture;
    boolean modifAchatLivraisonEtranger;
    boolean suppRepertoire;
    boolean suppStock;
    boolean suppLivraison;
    boolean suppFacture;
    boolean suppAchatLivraison;
    boolean suppAchatFacture;
    boolean suppAchatLivraisonEtranger;
    boolean etatRepertoire;
    boolean etatStock;
    boolean etatLivraison;
    boolean etatFacture;
    boolean etatAchatLivraison;
    boolean etatAchatFacture;
    boolean etatAchatLivraisonEtranger;
    boolean imprimeRepertoire;
    boolean imprimeStock;
    boolean imprimeLivraison;
    boolean imprimeFacture;
    boolean imprimerAchatLivraison;
    boolean imprimerAchatFacture;
    boolean imprimerAchatLivraisonEtranger;
}