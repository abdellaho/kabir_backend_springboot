package com.kabir.kabirbackend.config.responses;

import com.kabir.kabirbackend.dto.AchatEtrangerDTO;
import com.kabir.kabirbackend.dto.DetAchatEtrangerDTO;

import java.util.List;

public record AchatEtrangerResponse(AchatEtrangerDTO achatEtranger, List<DetAchatEtrangerDTO> detAchatEtrangers) {
}
