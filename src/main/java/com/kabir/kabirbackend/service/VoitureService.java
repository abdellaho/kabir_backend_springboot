package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.dto.VoitureDTO;
import com.kabir.kabirbackend.entities.Voiture;
import com.kabir.kabirbackend.mapper.VoitureMapper;
import com.kabir.kabirbackend.repository.VoitureRepository;
import com.kabir.kabirbackend.service.interfaces.IVoitureService;
import com.kabir.kabirbackend.specifications.VoitureSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoitureService implements IVoitureService {

    private final Logger logger = LoggerFactory.getLogger(VoitureService.class);
    private final VoitureRepository voitureRepository;
    private final VoitureMapper voitureMapper;

    public VoitureService(VoitureRepository voitureRepository, VoitureMapper voitureMapper) {
        this.voitureRepository = voitureRepository;
        this.voitureMapper = voitureMapper;
    }

    @Override
    public VoitureDTO save(VoitureDTO voitureDTO) {
        logger.info("Saving voiture: {}", voitureDTO);
        try {
            Voiture voiture = voitureMapper.toVoiture(voitureDTO);
            voiture = voitureRepository.save(voiture);
            return voitureMapper.toVoitureDTO(voiture);
        } catch (Exception e) {
            logger.error("Error saving voiture: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<VoitureDTO> findAll() {
        logger.info("Finding all voitures");
        try {
            List<Voiture> voitures = voitureRepository.findAll();
            return voitures.stream().map(voitureMapper::toVoitureDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all voitures: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public VoitureDTO findById(Long id) {
        logger.info("Finding voiture by id: {}", id);
        try {
            Voiture voiture = voitureRepository.findById(id).orElseThrow(() -> new RuntimeException("Voiture not found"));
            return voitureMapper.toVoitureDTO(voiture);
        } catch (Exception e) {
            logger.error("Error finding voiture by id: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting voiture: {}", id);
        try {
            voitureRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting voiture: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<VoitureDTO> search(VoitureDTO absenceDTO) {
        logger.info("Searching voitures: {}", absenceDTO);
        return voitureRepository.findAll(VoitureSpecification.builder().voitureDTO(absenceDTO).build()).stream().map(voitureMapper::toVoitureDTO).toList();
    }

}
