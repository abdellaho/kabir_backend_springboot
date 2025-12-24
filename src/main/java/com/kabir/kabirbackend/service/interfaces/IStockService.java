package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.requests.RequestStockQte;
import com.kabir.kabirbackend.dto.StockDTO;

import java.util.List;

public interface IStockService {

    public StockDTO save(StockDTO absenceDTO);
    public StockDTO findById(Long id);
    public List<StockDTO> findAll();
    public void delete(Long id);
    public List<StockDTO> search(StockDTO absenceDTO);

    List<StockDTO> searchBySupprimerOrArchiver(StockDTO stockDTO);

    void updateQteStock(Long id, RequestStockQte requestStockQte);
    void updateQteStockImport(Long id, RequestStockQte requestStockQte);
    void updateQteStockFacturer(Long id, RequestStockQte requestStockQte);
}
