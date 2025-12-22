package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.FournisseurDTO;
import com.kabir.kabirbackend.entities.Fournisseur;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FournisseurMapper {

    private ModelMapper modelMapper;

    public FournisseurMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FournisseurDTO toFournisseurDTO(Fournisseur fournisseur) {
        return Objects.isNull(fournisseur) ? null : modelMapper.map(fournisseur, FournisseurDTO.class);
    }

    public Fournisseur toFournisseur(FournisseurDTO fournisseurDTO) {
        return Objects.isNull(fournisseurDTO) ? null : modelMapper.map(fournisseurDTO, Fournisseur.class);
    }
}
