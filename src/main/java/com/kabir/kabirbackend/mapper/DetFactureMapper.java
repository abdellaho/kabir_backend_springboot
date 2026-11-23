package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.DetFactureDTO;
import com.kabir.kabirbackend.entities.DetFacture;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DetFactureMapper {

    private final ModelMapper modelMapper;


    public DetFactureMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public DetFactureDTO toDTO(DetFacture detFacture) {
        return modelMapper.map(detFacture, DetFactureDTO.class);
    }

    public DetFacture toEntity(DetFactureDTO dto) {
        DetFacture entity = new DetFacture();
        entity.setId(dto.getId());
        entity.setQteFacturer(dto.getQteFacturer());
        entity.setRemiseFacture(dto.getRemiseFacture());
        entity.setPrixVente(dto.getPrixVente());
        entity.setMontantProduit(dto.getMontantProduit());
        entity.setBeneficeDH(dto.getBeneficeDH());
        entity.setBenepourcentage(dto.getBenepourcentage());
        entity.setTva7(dto.getTva7());
        entity.setTva20(dto.getTva20());
        entity.setMontantProduitHT(dto.getMontantProduitHT());
        entity.setDirectFacture(dto.getDirectFacture());
        entity.setAvecRemise(dto.isAvecRemise());
        return entity;
    }


}
