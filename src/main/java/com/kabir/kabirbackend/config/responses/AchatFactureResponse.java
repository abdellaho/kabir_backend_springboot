package com.kabir.kabirbackend.config.responses;

import com.kabir.kabirbackend.dto.AchatFactureDTO;
import com.kabir.kabirbackend.dto.DetAchatFactureDTO;

import java.util.List;

public record AchatFactureResponse(
        AchatFactureDTO achatFacture,
        List<DetAchatFactureDTO> detAchatFactures
) {
}
