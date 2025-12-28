package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.DetAchatSimpleDTO;
import com.kabir.kabirbackend.entities.DetAchatSimple;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DetAchatSimpleMapper {

    private final ModelMapper modelMapper;

    public DetAchatSimpleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DetAchatSimpleDTO toDTO(DetAchatSimple achatSimple) {
        return modelMapper.map(achatSimple, DetAchatSimpleDTO.class);
    }

    public DetAchatSimple toEntity(DetAchatSimpleDTO dto) {
        return modelMapper.map(dto, DetAchatSimple.class);
    }
}

