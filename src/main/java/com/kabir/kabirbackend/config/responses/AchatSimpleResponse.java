package com.kabir.kabirbackend.config.responses;

import com.kabir.kabirbackend.dto.AchatSimpleDTO;
import com.kabir.kabirbackend.dto.DetAchatSimpleDTO;

import java.util.List;

public record AchatSimpleResponse(AchatSimpleDTO achatSimple, List<DetAchatSimpleDTO> detAchatSimples) {
}
