package com.kabir.kabirbackend.config.responses;

import com.kabir.kabirbackend.dto.DetStockDepotDTO;
import com.kabir.kabirbackend.dto.StockDepotDTO;

import java.util.List;

public record StockDepotResponse(StockDepotDTO stockDepot, List<DetStockDepotDTO> detStockDepots) {
}
