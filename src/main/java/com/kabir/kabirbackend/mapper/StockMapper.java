package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.StockDTO;
import com.kabir.kabirbackend.entities.Stock;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StockMapper {

    private ModelMapper modelMapper;

    public StockMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public StockDTO toStockDTO(Stock stock) {
        return Objects.isNull(stock) ? null : modelMapper.map(stock, StockDTO.class);
    }

    public Stock toStock(StockDTO stockDTO) {
        return Objects.isNull(stockDTO) ? null : modelMapper.map(stockDTO, Stock.class);
    }
}
