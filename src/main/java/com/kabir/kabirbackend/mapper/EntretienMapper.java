package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.EntretienDTO;
import com.kabir.kabirbackend.entities.Entretien;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EntretienMapper {

    private ModelMapper modelMapper;

    public EntretienMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EntretienDTO toDTO(Entretien entretien) {
        return this.modelMapper.map(entretien, EntretienDTO.class);
    }

    public Entretien toEntity(EntretienDTO entretienDTO) {
        return this.modelMapper.map(entretienDTO, Entretien.class);
    }
}
