package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.enums.TypeQteToUpdate;
import com.kabir.kabirbackend.config.requests.PrintRequest;
import com.kabir.kabirbackend.config.requests.PrintResponse;
import com.kabir.kabirbackend.config.requests.RequestStockQte;
import com.kabir.kabirbackend.dto.StockDTO;
import com.kabir.kabirbackend.entities.Stock;

import java.util.List;

public interface IStockService {

    public StockDTO save(StockDTO absenceDTO);

    StockDTO findById(Long id);
    Stock findByIdStock(Long id);
    public List<StockDTO> findAll();
    public void delete(Long id);
    public List<StockDTO> search(StockDTO absenceDTO);

    List<StockDTO> searchBySupprimerOrArchiver(StockDTO stockDTO);

    void updateQteStock(Long id, TypeQteToUpdate typeQteToUpdate, RequestStockQte requestStockQte);

    PrintResponse imprimer(PrintRequest printRequest) throws Exception;
    //void updateQteStockImport(Long id, RequestStockQte requestStockQte);
    //void updateQteStockFacturer(Long id, RequestStockQte requestStockQte);
}
