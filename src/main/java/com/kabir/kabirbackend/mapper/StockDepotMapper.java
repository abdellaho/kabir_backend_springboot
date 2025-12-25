package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.StockDepotDTO;
import com.kabir.kabirbackend.entities.StockDepot;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StockDepotMapper {

    private ModelMapper modelMapper;

    public StockDepotMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public StockDepot toStockDepot(StockDepotDTO stockDepotDTO) {
        return Objects.isNull(stockDepotDTO) ? null : modelMapper.map(stockDepotDTO, StockDepot.class);
    }

    public StockDepotDTO toStockDepotDTO(StockDepot stockDepot) {
        return Objects.isNull(stockDepot) ? null : modelMapper.map(stockDepot, StockDepotDTO.class);
    }
}
