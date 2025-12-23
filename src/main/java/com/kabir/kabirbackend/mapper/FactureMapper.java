package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.FactureDTO;
import com.kabir.kabirbackend.entities.Facture;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FactureMapper {
    private final ModelMapper modelMapper;

    public FactureMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FactureDTO toFactureDTO(Facture facture) {
        return modelMapper.map(facture, FactureDTO.class);
    }

    public Facture toFacture(FactureDTO factureDTO) {
        return modelMapper.map(factureDTO, Facture.class);
    }
}
