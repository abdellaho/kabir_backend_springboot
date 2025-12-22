package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.VilleDTO;
import com.kabir.kabirbackend.entities.Ville;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VilleMapper {


    private ModelMapper modelMapper;

    public VilleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VilleDTO toVilleDTO(Ville ville) {
        return Objects.isNull(ville) ? null : modelMapper.map(ville, VilleDTO.class);
    }

    public Ville toVille(VilleDTO villeDTO) {
        return Objects.isNull(villeDTO) ? null : modelMapper.map(villeDTO, Ville.class);
    }
}
