package com.kabir.kabirbackend.mapper;


import com.kabir.kabirbackend.dto.BonSortieDTO;
import com.kabir.kabirbackend.entities.BonSortie;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BonSortieMapper {

    private final ModelMapper modelMapper;

    public BonSortieMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BonSortieDTO toDTO(BonSortie bonSortie) {
        return modelMapper.map(bonSortie, BonSortieDTO.class);
    }

    public BonSortie toEntity(BonSortieDTO dto) {
        return modelMapper.map(dto, BonSortie.class);
    }
}
