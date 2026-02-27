package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.responses.StockDepotResponse;
import com.kabir.kabirbackend.dto.DetStockDepotDTO;
import com.kabir.kabirbackend.dto.StockDepotDTO;

import java.util.List;

public interface IStockDepotService {

    StockDepotResponse save(StockDepotResponse stockDepotResponse);
    StockDepotDTO findById(Long id);
    StockDepotResponse findByIdStockDepotResponse(Long id);
    List<StockDepotDTO> findAll();

    List<DetStockDepotDTO> findAllDetails();

    List<DetStockDepotDTO> findAllDetails(DetStockDepotDTO detStockDepotDTO);

    void delete(Long id);
    List<StockDepotDTO> search(StockDepotDTO stockDepot);
}
