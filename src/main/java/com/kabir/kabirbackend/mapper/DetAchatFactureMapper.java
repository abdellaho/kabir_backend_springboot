package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.DetAchatFactureDTO;
import com.kabir.kabirbackend.entities.DetAchatFacture;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DetAchatFactureMapper {

    private final ModelMapper modelMapper;

    public DetAchatFactureMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DetAchatFactureDTO toDTO(DetAchatFacture achatFacture) {
        return modelMapper.map(achatFacture, DetAchatFactureDTO.class);
    }

    public DetAchatFacture toEntity(DetAchatFactureDTO dto) {
        return modelMapper.map(dto, DetAchatFacture.class);
    }


}
