package com.kabir.kabirbackend.config.imprimer;

import com.kabir.kabirbackend.dto.FournisseurDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepertoireInfo implements Cloneable {
	
	private FournisseurDTO repertoire;
	private double montantTva;
	private double montantHT;
	private double montantTTC;
	private String annee;
	
	public RepertoireInfo(FournisseurDTO repertoire, double montantTva, double montantHT, double montantTTC) {
		this.repertoire = repertoire;
		this.montantTva = montantTva;
		this.montantHT = montantHT;
		this.montantTTC = montantTTC;
	}

	@Override
	public Object clone() {
        Object o = null;
        try {
              o = super.clone();
        } catch(CloneNotSupportedException cnse) {
              cnse.printStackTrace(System.err);
        }
        return o;
	}
	

}
