package com.kabir.kabirbackend.mapper;


import com.kabir.kabirbackend.dto.ComptaDTO;
import com.kabir.kabirbackend.entities.Compta;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ComptaMapper {

    private final ModelMapper modelMapper;

    public ComptaMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Compta toCompta(ComptaDTO comptaDTO) {
        return modelMapper.map(comptaDTO, Compta.class);
    }

    public ComptaDTO toComptaDTO(Compta compta) {
        return modelMapper.map(compta, ComptaDTO.class);
    }
}
