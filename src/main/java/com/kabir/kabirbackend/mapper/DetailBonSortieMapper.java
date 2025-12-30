package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.DetailBonSortieDTO;
import com.kabir.kabirbackend.entities.DetailBonSortie;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DetailBonSortieMapper {
    
    private final ModelMapper modelMapper;
    
    public DetailBonSortieMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public DetailBonSortieDTO toDTO(DetailBonSortie detailBonSortie) {
        return modelMapper.map(detailBonSortie, DetailBonSortieDTO.class);
    }
    
    public DetailBonSortie toEntity(DetailBonSortieDTO dto) {
        return modelMapper.map(dto, DetailBonSortie.class);
    }
}
