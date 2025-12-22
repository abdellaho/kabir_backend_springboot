package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.VoitureDTO;
import com.kabir.kabirbackend.entities.Voiture;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VoitureMapper {

    private ModelMapper modelMapper;

    public VoitureMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VoitureDTO toVoitureDTO(Voiture voiture) {
        return Objects.isNull(voiture) ? null : modelMapper.map(voiture, VoitureDTO.class);
    }

    public Voiture toVoiture(VoitureDTO voitureDTO) {
        return Objects.isNull(voitureDTO) ? null : modelMapper.map(voitureDTO, Voiture.class);
    }
}
