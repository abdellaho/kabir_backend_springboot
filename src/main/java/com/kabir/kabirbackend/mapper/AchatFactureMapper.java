package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.AchatFactureDTO;
import com.kabir.kabirbackend.entities.AchatFacture;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AchatFactureMapper {

    private final ModelMapper modelMapper;

    public AchatFactureMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AchatFacture toAchatFacture(AchatFactureDTO achatFactureDTO) {
        return modelMapper.map(achatFactureDTO, AchatFacture.class);
    }

    public AchatFactureDTO toAchatFactureDTO(AchatFacture achatFacture) {
        return modelMapper.map(achatFacture, AchatFactureDTO.class);
    }
}
