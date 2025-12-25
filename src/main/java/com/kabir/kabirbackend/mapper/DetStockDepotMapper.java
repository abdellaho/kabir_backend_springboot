package com.kabir.kabirbackend.mapper;


import com.kabir.kabirbackend.dto.DetStockDepotDTO;
import com.kabir.kabirbackend.entities.DetStockDepot;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DetStockDepotMapper {

    private ModelMapper modelMapper;

    public DetStockDepotMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DetStockDepot toDetStockDepot(DetStockDepotDTO detStockDepotDTO) {
        return Objects.isNull(detStockDepotDTO) ? null : modelMapper.map(detStockDepotDTO, DetStockDepot.class);
    }

    public DetStockDepotDTO toDetStockDepotDTO(DetStockDepot detStockDepot) {
        return Objects.isNull(detStockDepot) ? null : modelMapper.map(detStockDepot, DetStockDepotDTO.class);
    }
}
