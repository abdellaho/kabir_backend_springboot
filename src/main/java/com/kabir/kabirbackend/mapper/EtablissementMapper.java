package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.EtablissementDTO;
import com.kabir.kabirbackend.entities.Etablissement;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EtablissementMapper {

    private ModelMapper modelMapper;

    public EtablissementMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EtablissementDTO toEtablissementDTO(Etablissement etablissement) {
        return Objects.isNull(etablissement) ? null : modelMapper.map(etablissement, EtablissementDTO.class);
    }

    public Etablissement toEtablissement(EtablissementDTO etablissementDTO) {
        return Objects.isNull(etablissementDTO) ? null : modelMapper.map(etablissementDTO, Etablissement.class);
    }
}
