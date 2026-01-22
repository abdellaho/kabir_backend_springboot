package com.kabir.kabirbackend.mapper;


import com.kabir.kabirbackend.dto.CaisseDTO;
import com.kabir.kabirbackend.entities.Caisse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CaisseMapper {

    private final ModelMapper modelMapper;

    public CaisseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CaisseDTO toDto(Caisse caisse) {
        return modelMapper.map(caisse, CaisseDTO.class);
    }

    public Caisse toEntity(CaisseDTO caisseDTO) {
        return modelMapper.map(caisseDTO, Caisse.class);
    }


}
