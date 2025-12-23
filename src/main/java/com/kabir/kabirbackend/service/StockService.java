package com.kabir.kabirbackend.service;


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
        logger.info("Finding stock by id: {}", id);
        try {
            Stock stock = stockRepository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
            return stockMapper.toStockDTO(stock);
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
}
