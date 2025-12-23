package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.dto.EtablissementDTO;
import com.kabir.kabirbackend.entities.Etablissement;
import com.kabir.kabirbackend.mapper.EtablissementMapper;
import com.kabir.kabirbackend.repository.EtablissementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtablissementService implements IEtablissementService {

    private static final Logger logger = LoggerFactory.getLogger(EtablissementService.class);
    private final EtablissementRepository etablissementRepository;
    private final EtablissementMapper etablissementMapper;

    public EtablissementService(EtablissementRepository etablissementRepository, EtablissementMapper etablissementMapper) {
        this.etablissementRepository = etablissementRepository;
        this.etablissementMapper = etablissementMapper;
    }

    @Override
    public EtablissementDTO save(EtablissementDTO etablissementDTO) {
        try {
            logger.info("Saving new EtablissementDTO: {}", etablissementDTO);
            Etablissement etablissement = etablissementMapper.toEtablissement(etablissementDTO);
            etablissement = etablissementRepository.save(etablissement);
            return etablissementMapper.toEtablissementDTO(etablissement);
        } catch (Exception e) {
            logger.error("Error saving EtablissementDTO: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EtablissementDTO> findAll() {
        try {
            logger.info("Finding all EtablissementDTOs");
            List<Etablissement> etablissements = etablissementRepository.findAll();
            return etablissements.stream().map(etablissementMapper::toEtablissementDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all EtablissementDTOs: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public EtablissementDTO findById(Long id) {
        try {
            logger.info("Finding EtablissementDTO by id: {}", id);
            Etablissement etablissement = etablissementRepository.findById(id).orElse(null);
            return etablissementMapper.toEtablissementDTO(etablissement);
        } catch (Exception e) {
            logger.error("Error finding EtablissementDTO by id: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            logger.info("Deleting EtablissementDTO by id: {}", id);
            etablissementRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting EtablissementDTO by id: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EtablissementDTO> search(EtablissementDTO etablissementDTO) {
        try {
            logger.info("Searching EtablissementDTOs by criteria: {}", etablissementDTO);
            Etablissement etablissement = etablissementMapper.toEtablissement(etablissementDTO);
            List<Etablissement> etablissements = etablissementRepository.findAll();
            return etablissements.stream().map(etablissementMapper::toEtablissementDTO).toList();
        } catch (Exception e) {
            logger.error("Error searching EtablissementDTOs by criteria: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
