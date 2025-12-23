package com.kabir.kabirbackend.mapper;


import com.kabir.kabirbackend.dto.DroitDTO;
import com.kabir.kabirbackend.entities.Droit;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DroitMapper {

    private final ModelMapper modelMapper;

    public DroitMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DroitDTO toDroitDTO(Droit droit) {
        return modelMapper.map(droit, DroitDTO.class);
    }

    public Droit toDroit(DroitDTO droitDTO) {
        return modelMapper.map(droitDTO, Droit.class);
    }
}
