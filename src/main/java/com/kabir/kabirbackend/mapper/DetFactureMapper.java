package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.DetFactureDTO;
import com.kabir.kabirbackend.entities.DetFacture;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DetFactureMapper {

    private final ModelMapper modelMapper;


    public DetFactureMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public DetFactureDTO toDTO(DetFacture detFacture) {
        return modelMapper.map(detFacture, DetFactureDTO.class);
    }

    public DetFacture toEntity(DetFactureDTO dto) {
        return modelMapper.map(dto, DetFacture.class);
    }


}
