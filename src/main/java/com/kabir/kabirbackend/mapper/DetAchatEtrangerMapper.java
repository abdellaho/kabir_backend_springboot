package com.kabir.kabirbackend.mapper;


import com.kabir.kabirbackend.dto.DetAchatEtrangerDTO;
import com.kabir.kabirbackend.entities.DetAchatEtranger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class DetAchatEtrangerMapper {

    private final ModelMapper modelMapper;

    public DetAchatEtrangerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DetAchatEtranger toEntity(DetAchatEtrangerDTO dto) {
        return modelMapper.map(dto, DetAchatEtranger.class);
    }

    public DetAchatEtrangerDTO toDto(DetAchatEtranger entity) {
        return modelMapper.map(entity, DetAchatEtrangerDTO.class);
    }
}
