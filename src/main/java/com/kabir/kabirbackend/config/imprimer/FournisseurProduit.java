package com.kabir.kabirbackend.config.imprimer;

import java.io.Serializable;
import java.util.Comparator;

import ma.kabir.modele.Repertoire;
import ma.kabir.modele.Stock;

public class FournisseurProduit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Repertoire repertoire;
	private Stock stock;
	private int qteLivre;
	private double montant;
	private double beneficeDH;
	private double sumBeneficeDH;
	private int type;
	
	
	
	public FournisseurProduit() {
		super();
	}
	
	public FournisseurProduit(Repertoire repertoire, Stock stock,
			int qteLivre, double montant, double beneficeDH) {
		super();
		this.repertoire = repertoire;
		this.stock =  stock;
		this.qteLivre = qteLivre;
		this.montant = montant;
		this.beneficeDH = beneficeDH;
	}
	
	public FournisseurProduit(Repertoire repertoire, Stock stock, int qteLivre, double montant, double beneficeDH, int type) {
		super();
		this.repertoire = repertoire;
		this.stock =  stock;
		this.qteLivre = qteLivre;
		this.montant = montant;
		this.beneficeDH = beneficeDH;
		this.type = type;
	}
	
	
	
	public double getSumBeneficeDH() {
		return sumBeneficeDH;
	}
	
	public void setSumBeneficeDH(double sumBeneficeDH) {
		this.sumBeneficeDH = sumBeneficeDH;
	}
	
	public Repertoire getRepertoire() {
		return repertoire;
	}
	
	public void setRepertoire(Repertoire repertoire) {
		this.repertoire = repertoire;
	}
	 
	public Stock getStock() {
		return stock;
	}
	
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	
	public int getQteLivre() {
		return qteLivre;
	}
	
	public void setQteLivre(int qteLivre) {
		this.qteLivre = qteLivre;
	}
	
	public double getMontant() {
		return montant;
	}
	
	public void setMontant(double montant) {
		this.montant = montant;
	}
	
	public double getBeneficeDH() {
		return beneficeDH;
	}
	
	public void setBeneficeDH(double beneficeDH) {
		this.beneficeDH = beneficeDH;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	public static final Comparator<FournisseurProduit> parOrdreAlphabetiqueStock = new Comparator<FournisseurProduit>() {
		@Override
		public int compare(FournisseurProduit arg0, FournisseurProduit arg1) {
			int nameCompare = arg0.getStock().getDesignation().toUpperCase().compareTo(arg1.getStock().getDesignation().toUpperCase());
			return nameCompare;
		}
    };
	
	public static final Comparator<FournisseurProduit> ID_COMPARATOR = new Comparator<FournisseurProduit>() {
		@Override
		public int compare(FournisseurProduit arg0, FournisseurProduit arg1) {
			// TODO Auto-generated method stub
			FournisseurProduit p=arg0;
			FournisseurProduit q=arg1;
			  if(p.sumBeneficeDH == q.sumBeneficeDH){
				  return p.repertoire.getDesignation().compareTo(q.repertoire.getDesignation());
			  } else if (p.sumBeneficeDH < q.sumBeneficeDH)  return 1;
			  else return -1;
		}
    };
    
    
    public static final Comparator<FournisseurProduit> ID_COMPARATORProdui = new Comparator<FournisseurProduit>() {
		@Override
		public int compare(FournisseurProduit arg0, FournisseurProduit arg1) {
			FournisseurProduit p=arg0;
			FournisseurProduit q=arg1;
			  if(p.beneficeDH == q.beneficeDH){
				  return p.stock.getDesignation().compareTo(q.stock.getDesignation());
			  } else if (p.beneficeDH < q.beneficeDH)  return 1;
			  else return -1;
		}
    };

	  

}
