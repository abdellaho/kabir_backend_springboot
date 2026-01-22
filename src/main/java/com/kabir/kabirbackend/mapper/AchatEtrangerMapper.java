package com.kabir.kabirbackend.mapper;


import com.kabir.kabirbackend.dto.AchatEtrangerDTO;
import com.kabir.kabirbackend.entities.AchatEtranger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AchatEtrangerMapper {

    private final ModelMapper modelMapper;

    public AchatEtrangerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AchatEtranger toEntity(AchatEtrangerDTO dto) {
        return modelMapper.map(dto, AchatEtranger.class);
    }

    public AchatEtrangerDTO toDto(AchatEtranger entity) {
        return modelMapper.map(entity, AchatEtrangerDTO.class);
    }
}
