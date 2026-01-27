package com.kabir.kabirbackend.mapper;


import com.kabir.kabirbackend.dto.CompteCaisseDTO;
import com.kabir.kabirbackend.entities.CompteCaisse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CompteCaisseMapper {

    private final ModelMapper modelMapper;

    public CompteCaisseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CompteCaisse toCompteCaisse(CompteCaisseDTO compteCaisseDTO) {
        return modelMapper.map(compteCaisseDTO, CompteCaisse.class);
    }

    public CompteCaisseDTO toCompteCaisseDTO(CompteCaisse compteCaisse) {
        return modelMapper.map(compteCaisse, CompteCaisseDTO.class);
    }
}
