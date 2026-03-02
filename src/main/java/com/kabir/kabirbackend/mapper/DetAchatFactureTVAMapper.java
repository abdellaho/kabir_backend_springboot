package com.kabir.kabirbackend.mapper;


import com.kabir.kabirbackend.dto.DetAchatFactureTVADTO;
import com.kabir.kabirbackend.entities.DetAchatFactureTVA;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DetAchatFactureTVAMapper {

    private final ModelMapper modelMapper;

    public DetAchatFactureTVAMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DetAchatFactureTVA toEntity(DetAchatFactureTVADTO dto) {
        return modelMapper.map(dto, DetAchatFactureTVA.class);
    }

    public DetAchatFactureTVADTO toDTO(DetAchatFactureTVA entity) {
        return modelMapper.map(entity, DetAchatFactureTVADTO.class);
    }
}
