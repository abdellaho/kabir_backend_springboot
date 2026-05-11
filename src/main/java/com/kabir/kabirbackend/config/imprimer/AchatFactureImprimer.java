package com.kabir.kabirbackend.config.imprimer;

import com.kabir.kabirbackend.dto.AchatFactureDTO;
import com.kabir.kabirbackend.dto.DetAchatFactureDTO;
import com.kabir.kabirbackend.dto.DetAchatFactureTVADTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchatFactureImprimer {
    private int type;
    private AchatFactureDTO achatFactureDTO;
    private DetAchatFactureTVADTO detAchatFactureTVADTO;
    private DetAchatFactureDTO detAchatFactureDTO;
}
