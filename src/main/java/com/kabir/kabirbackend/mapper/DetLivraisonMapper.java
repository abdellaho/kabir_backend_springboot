package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.DetLivraisonDTO;
import com.kabir.kabirbackend.entities.DetLivraison;
import com.kabir.kabirbackend.entities.Livraison;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DetLivraisonMapper {

    private ModelMapper modelMapper;

    public DetLivraisonMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DetLivraisonDTO toDetLivraisonDTO(DetLivraison detLivraison) {
        return Objects.isNull(detLivraison) ? null : modelMapper.map(detLivraison, DetLivraisonDTO.class);
    }

    public DetLivraison toDetLivraison(DetLivraisonDTO dto) {
        if (dto == null) {
            return null;
        }
        DetLivraison entity = new DetLivraison();
        entity.setId(dto.getId());
        entity.setQteLivrer(dto.getQteLivrer());
        entity.setChampsRouge(dto.getChampsRouge());
        entity.setRemiseLivraison(dto.getRemiseLivraison());
        entity.setPrixVente(dto.getPrixVente());
        entity.setMontantProduit(dto.getMontantProduit());
        entity.setBeneficeDH(dto.getBeneficeDH());
        entity.setBenepourcentage(dto.getBenepourcentage());
        entity.setInfinity(dto.getInfinity());
        entity.setAvecRemise(dto.isAvecRemise());
        if (dto.getLivraisonId() != null) {
            Livraison livraison = new Livraison();
            livraison.setId(dto.getLivraisonId());
            entity.setLivraison(livraison);
        }
        return entity;
    }
}
