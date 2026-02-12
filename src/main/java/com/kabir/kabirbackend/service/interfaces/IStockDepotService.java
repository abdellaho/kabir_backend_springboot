package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.responses.StockDepotResponse;
import com.kabir.kabirbackend.dto.DetStockDepotDTO;
import com.kabir.kabirbackend.dto.StockDepotDTO;

import java.util.List;

public interface IStockDepotService {

    public StockDepotResponse save(StockDepotResponse stockDepotResponse);
    public StockDepotDTO findById(Long id);
    public StockDepotResponse findByIdStockDepotResponse(Long id);
    public List<StockDepotDTO> findAll();

    List<DetStockDepotDTO> findAllDetails();

    public void delete(Long id);
    public List<StockDepotDTO> search(StockDepotDTO stockDepot);
}
