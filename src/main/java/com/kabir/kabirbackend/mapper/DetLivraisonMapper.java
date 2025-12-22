package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.DetLivraisonDTO;
import com.kabir.kabirbackend.entities.DetLivraison;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DetLivraisonMapper {

    private ModelMapper modelMapper;

    public DetLivraisonMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DetLivraisonDTO toDetLivraisonDTO(DetLivraison detLivraison) {
        return Objects.isNull(detLivraison) ? null : modelMapper.map(detLivraison, DetLivraisonDTO.class);
    }

    public DetLivraison toDetLivraison(DetLivraisonDTO detLivraisonDTO) {
        return Objects.isNull(detLivraisonDTO) ? null : modelMapper.map(detLivraisonDTO, DetLivraison.class);
    }
}
