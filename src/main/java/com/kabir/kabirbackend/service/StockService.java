package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.config.enums.TypeQteToUpdate;
import com.kabir.kabirbackend.config.requests.RequestStockQte;
import com.kabir.kabirbackend.dto.StockDTO;
import com.kabir.kabirbackend.entities.Stock;
import com.kabir.kabirbackend.mapper.StockMapper;
import com.kabir.kabirbackend.repository.StockRepository;
import com.kabir.kabirbackend.service.interfaces.IStockService;
import com.kabir.kabirbackend.specifications.StockSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService implements IStockService {
    private final Logger logger = LoggerFactory.getLogger(StockService.class);
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    public StockService(StockRepository stockRepository, StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

    @Override
    public StockDTO save(StockDTO stockDTO) {
        logger.info("Saving stock: {}", stockDTO);
        try {
            Stock stock = stockMapper.toStock(stockDTO);
            stock = stockRepository.save(stock);
            return stockMapper.toStockDTO(stock);
        } catch (Exception e) {
            logger.error("Error saving stock", e);
            throw new RuntimeException("Error saving stock", e);
        }
    }

    @Override
    public List<StockDTO> findAll() {
        logger.info("Finding all stocks");
        try {
            List<Stock> stocks = stockRepository.findAll();
            return stocks.stream().map(stockMapper::toStockDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all stocks", e);
            throw new RuntimeException("Error finding all stocks", e);
        }
    }

    @Override
    public StockDTO findById(Long id) {
        logger.info("Finding stock DTO by id: {}", id);
        try {
            Stock stock = stockRepository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
            return stockMapper.toStockDTO(stock);
        } catch (Exception e) {
            logger.error("Error finding stock by id", e);
            throw new RuntimeException("Error finding stock by id", e);
        }
    }

    @Override
    public Stock findByIdStock(Long id) {
        logger.info("Finding stock by id: {}", id);
        try {
            return stockRepository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
        } catch (Exception e) {
            logger.error("Error finding stock by id", e);
            throw new RuntimeException("Error finding stock by id", e);
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting stock: {}", id);
        try {
            stockRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting stock", e);
            throw new RuntimeException("Error deleting stock", e);
        }
    }

    @Override
    public List<StockDTO> search(StockDTO stockDTO) {
        logger.info("Searching stocks: {}", stockDTO);
        return stockRepository.findAll(StockSpecification.builder().stockDTO(stockDTO).build()).stream().map(stockMapper::toStockDTO).toList();
    }

    @Override
    public List<StockDTO> searchBySupprimerOrArchiver(StockDTO stockDTO) {
        return stockRepository.findAll(StockSpecification.searchBySupprimerOrArchiver(stockDTO)).stream().map(stockMapper::toStockDTO).toList();
    }

    @Override
    public void updateQteStock(Long id, TypeQteToUpdate typeQteToUpdate, RequestStockQte requestStockQte) {
        logger.info("Updating stock qte stock: {}", requestStockQte);
        try {
            if (null != id) {
                StockDTO stockDTO = findById(id);
                if(null != stockDTO && null != stockDTO.getId()) {
                    if (typeQteToUpdate == TypeQteToUpdate.QTE_STOCK) {
                        logger.info("Updating stock qte stock: {}", requestStockQte.qte());
                    } else if (typeQteToUpdate == TypeQteToUpdate.QTE_STOCK_IMPORT) {
                        logger.info("Updating stock qte stock import: {}", requestStockQte.qte());
                    } else if (typeQteToUpdate == TypeQteToUpdate.QTE_STOCK_FACTURER) {
                        logger.info("Updating stock qte stock facturer: {}", requestStockQte.qte());
                    } else if (typeQteToUpdate == TypeQteToUpdate.QTE_STOCK_SORTIE) {
                        logger.info("Updating stock qte stock sortie: {}", requestStockQte.qte());
                    }

                    updateStock(typeQteToUpdate, stockDTO, requestStockQte);
                } else {
                    throw new RuntimeException("Stock not found");
                }
            } else {
                throw new RuntimeException("Id is null");
            }
        } catch (Exception e) {
            logger.error("Error updating stock qte stock", e);
            throw new RuntimeException("Error updating stock qte stock", e);
        }
    }

    public void updateStock(TypeQteToUpdate typeQteToUpdate, StockDTO stockDTO, RequestStockQte requestStockQte) {
        int operator = requestStockQte.typeOperation();
        int qte = requestStockQte.qte();
        int uniteGrat = (null != requestStockQte.uniteGratuite()) ? requestStockQte.uniteGratuite() : 0;

        switch (typeQteToUpdate) {
            case QTE_STOCK:
                stockDTO.setQteStock(operator == 1 ? stockDTO.getQteStock() - qte : stockDTO.getQteStock() + qte);
                break;
            case QTE_STOCK_IMPORT:
                stockDTO.setQteStock(operator == 1 ? stockDTO.getQteStock() - (qte + uniteGrat) : stockDTO.getQteStock() + (qte + uniteGrat));
                stockDTO.setQteStockImport(operator == 1 ? stockDTO.getQteStockImport() + qte : stockDTO.getQteStockImport() - qte);
                break;
            case QTE_STOCK_FACTURER:
                stockDTO.setQteFacturer(operator == 1 ? stockDTO.getQteFacturer() - qte : stockDTO.getQteFacturer() + qte);
                break;
            case QTE_STOCK_SORTIE:
                stockDTO.setQteSortie(operator == 1 ? stockDTO.getQteSortie() - qte : stockDTO.getQteSortie() + qte);
                break;
        }

        save(stockDTO);
    }
}
