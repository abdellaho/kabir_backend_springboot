package com.kabir.kabirbackend.mapper;


import com.kabir.kabirbackend.dto.BulletinPaiDTO;
import com.kabir.kabirbackend.entities.BulletinPai;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BulletinPaiMapper {

    private final ModelMapper modelMapper;


    public BulletinPaiMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BulletinPaiDTO toDto(BulletinPai entity) {
        return modelMapper.map(entity, BulletinPaiDTO.class);
    }

    public BulletinPai toEntity(BulletinPaiDTO dto) {
        return modelMapper.map(dto, BulletinPai.class);
    }

    public List<BulletinPaiDTO> toDto(List<BulletinPai> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<BulletinPai> toEntity(List<BulletinPaiDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
