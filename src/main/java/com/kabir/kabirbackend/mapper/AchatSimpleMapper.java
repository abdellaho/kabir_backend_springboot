package com.kabir.kabirbackend.mapper;


import com.kabir.kabirbackend.dto.AchatSimpleDTO;
import com.kabir.kabirbackend.entities.AchatSimple;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AchatSimpleMapper {

    private final ModelMapper modelMapper;

    public AchatSimpleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AchatSimpleDTO toDTO(AchatSimple achatSimple) {
        return modelMapper.map(achatSimple, AchatSimpleDTO.class);
    }

    public AchatSimple toEntity(AchatSimpleDTO dto) {
        return modelMapper.map(dto, AchatSimple.class);
    }
}
