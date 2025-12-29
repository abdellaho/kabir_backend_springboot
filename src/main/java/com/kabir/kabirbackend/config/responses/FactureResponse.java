package com.kabir.kabirbackend.config.responses;

import com.kabir.kabirbackend.dto.DetFactureDTO;
import com.kabir.kabirbackend.dto.FactureDTO;

import java.util.List;

public record FactureResponse(FactureDTO facture, List<DetFactureDTO> detFactures ) {
}
