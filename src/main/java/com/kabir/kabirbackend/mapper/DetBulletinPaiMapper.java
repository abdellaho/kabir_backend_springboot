package com.kabir.kabirbackend.mapper;


import com.kabir.kabirbackend.dto.DetBulletinPaiDTO;
import com.kabir.kabirbackend.entities.DetBulletinPai;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetBulletinPaiMapper {

    private final ModelMapper modelMapper;


    public DetBulletinPaiMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DetBulletinPaiDTO toDto(DetBulletinPai entity) {
        return modelMapper.map(entity, DetBulletinPaiDTO.class);
    }

    public DetBulletinPai toEntity(DetBulletinPaiDTO dto) {
        return modelMapper.map(dto, DetBulletinPai.class);
    }

    public List<DetBulletinPaiDTO> toDtos(List<DetBulletinPai> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<DetBulletinPai> toEntitys(List<DetBulletinPaiDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}