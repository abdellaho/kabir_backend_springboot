package com.kabir.kabirbackend.config.imprimer;

import com.kabir.kabirbackend.dto.AchatFactureDTO;
import com.kabir.kabirbackend.entities.Importation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchatFactureImport {
	
	private int type;
	private AchatFactureDTO achatFacture;
	private Importation importation;
	private RepertoireInfo repertoireInfo;

	public AchatFactureImport(AchatFactureDTO achatFacture, Importation importation) {
		this.achatFacture = achatFacture;
		this.importation = importation;
	}
	
	public AchatFactureImport(int type, AchatFactureDTO achatFacture, Importation importation) {
		this.type = type;
		this.achatFacture = achatFacture;
		this.importation = importation;
	}
	
	

}
