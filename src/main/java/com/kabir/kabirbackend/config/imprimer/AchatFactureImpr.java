package com.kabir.kabirbackend.config.imprimer;

import java.io.Serializable;

public class AchatFactureImpr implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	double mntTva7;
	double mntTva10;
	double mntTva14;
	double mntTva20;
	
	double mntHT7;
	double mntHT10;
	double mntHT14;
	double mntHT20;
	
	
	public AchatFactureImpr(double mntTva7, double mntTva10, double mntTva14, double mntTva20, double mntHT7,
			double mntHT10, double mntHT14, double mntHT20) {
		super();
		this.mntTva7 = mntTva7;
		this.mntTva10 = mntTva10;
		this.mntTva14 = mntTva14;
		this.mntTva20 = mntTva20;
		this.mntHT7 = mntHT7;
		this.mntHT10 = mntHT10;
		this.mntHT14 = mntHT14;
		this.mntHT20 = mntHT20;
	}


	public double getMntTva7() {
		return mntTva7;
	}

	public void setMntTva7(double mntTva7) {
		this.mntTva7 = mntTva7;
	}

	public double getMntTva10() {
		return mntTva10;
	}

	public void setMntTva10(double mntTva10) {
		this.mntTva10 = mntTva10;
	}

	public double getMntTva14() {
		return mntTva14;
	}

	public void setMntTva14(double mntTva14) {
		this.mntTva14 = mntTva14;
	}

	public double getMntTva20() {
		return mntTva20;
	}

	public void setMntTva20(double mntTva20) {
		this.mntTva20 = mntTva20;
	}

	public double getMntHT7() {
		return mntHT7;
	}

	public void setMntHT7(double mntHT7) {
		this.mntHT7 = mntHT7;
	}

	public double getMntHT10() {
		return mntHT10;
	}

	public void setMntHT10(double mntHT10) {
		this.mntHT10 = mntHT10;
	}

	public double getMntHT14() {
		return mntHT14;
	}

	public void setMntHT14(double mntHT14) {
		this.mntHT14 = mntHT14;
	}

	public double getMntHT20() {
		return mntHT20;
	}

	public void setMntHT20(double mntHT20) {
		this.mntHT20 = mntHT20;
	}

}
