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
	private AchatFactureDTO AchatFactureDTO;
	private Importation importation;
	private RepertoireInfo repertoireInfo;

	public AchatFactureImport(AchatFactureDTO AchatFactureDTO, Importation importation) {
		this.AchatFactureDTO = AchatFactureDTO;
		this.importation = importation;
	}
	
	public AchatFactureImport(int type, AchatFactureDTO AchatFactureDTO, Importation importation) {
		this.type = type;
		this.AchatFactureDTO = AchatFactureDTO;
		this.importation = importation;
	}
	
	

}
