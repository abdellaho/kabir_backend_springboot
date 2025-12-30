package com.kabir.kabirbackend.config.responses;

import com.kabir.kabirbackend.dto.BonSortieDTO;
import com.kabir.kabirbackend.dto.DetailBonSortieDTO;

import java.util.List;

public record BonSortieResponse(BonSortieDTO bonSortie, List<DetailBonSortieDTO> detailBonSorties) {
}
