package com.kabir.kabirbackend.mapper;


import com.kabir.kabirbackend.dto.DetBulletinLivraisonDTO;
import com.kabir.kabirbackend.entities.DetBulletinLivraison;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetBulletinLivraisonMapper {

    private final ModelMapper modelMapper;


    public DetBulletinLivraisonMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DetBulletinLivraisonDTO toDTO(DetBulletinLivraison entity) {
        return modelMapper.map(entity, DetBulletinLivraisonDTO.class);
    }

    public DetBulletinLivraison toEntity(DetBulletinLivraisonDTO dto) {
        return modelMapper.map(dto, DetBulletinLivraison.class);
    }

    public List<DetBulletinLivraisonDTO> toDTO(List<DetBulletinLivraison> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<DetBulletinLivraison> toEntity(List<DetBulletinLivraisonDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
