package com.kabir.kabirbackend.config.responses;

import com.kabir.kabirbackend.dto.DetLivraisonDTO;
import com.kabir.kabirbackend.dto.LivraisonDTO;

import java.util.List;

public record LivraisonResponse(LivraisonDTO livraison, List<DetLivraisonDTO> detLivraisons) {
}
