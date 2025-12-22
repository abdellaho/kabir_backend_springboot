package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.LivraisonDTO;
import com.kabir.kabirbackend.entities.Livraison;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LivraisonMapper {


    private ModelMapper modelMapper;

    public LivraisonMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public LivraisonDTO toLivraisonDTO(Livraison livraison) {
        return Objects.isNull(livraison) ? null : modelMapper.map(livraison, LivraisonDTO.class);
    }

    public Livraison toLivraison(LivraisonDTO livraisonDTO) {
        return Objects.isNull(livraisonDTO) ? null : modelMapper.map(livraisonDTO, Livraison.class);
    }
}
