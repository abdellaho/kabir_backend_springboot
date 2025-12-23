package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.dto.AchatFactureDTO;
import com.kabir.kabirbackend.entities.AchatFacture;
import com.kabir.kabirbackend.mapper.AchatFactureMapper;
import com.kabir.kabirbackend.repository.AchatFactureRepository;
import com.kabir.kabirbackend.service.interfaces.IAchatFactureService;
import com.kabir.kabirbackend.specifications.AchatFactureSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchatFactureService implements IAchatFactureService {

    private final Logger logger = LoggerFactory.getLogger(AchatFactureService.class);
    private final AchatFactureRepository achatFactureRepository;
    private final AchatFactureMapper achatFactureMapper;

    public AchatFactureService(AchatFactureRepository achatFactureRepository, AchatFactureMapper achatFactureMapper) {
        this.achatFactureRepository = achatFactureRepository;
        this.achatFactureMapper = achatFactureMapper;
    }

    @Override
    public AchatFactureDTO save(AchatFactureDTO achatFactureDTO) {
        logger.info("Saving achatFacture: {}", achatFactureDTO);
        try {
            AchatFacture achatFacture = achatFactureMapper.toAchatFacture(achatFactureDTO);
            return achatFactureMapper.toAchatFactureDTO(achatFactureRepository.save(achatFacture));
        } catch (Exception e) {
            logger.error("Error saving achatFacture: {}", achatFactureDTO, e);
            throw new RuntimeException("Error saving achatFacture: " + achatFactureDTO, e);
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting achatFacture: {}", id);
        try {
            achatFactureRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting achatFacture: {}", id, e);
            throw new RuntimeException("Error deleting achatFacture: " + id, e);
        }
    }

    @Override
    public List<AchatFactureDTO> findAll() {
        logger.info("Finding all achatFactures");
        try {
            List<AchatFacture> achatFactures = achatFactureRepository.findAll();
            return achatFactures.stream().map(achatFactureMapper::toAchatFactureDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all achatFactures", e);
            throw new RuntimeException("Error finding all achatFactures", e);
        }
    }

    @Override
    public AchatFactureDTO findById(Long id) {
        logger.info("Finding achatFacture by id: {}", id);
        try {
            AchatFacture achatFacture = achatFactureRepository.findById(id).orElseThrow(() -> new RuntimeException("AchatFacture not found"));
            return achatFactureMapper.toAchatFactureDTO(achatFacture);
        } catch (Exception e) {
            logger.error("Error finding achatFacture by id: {}", id, e);
            throw new RuntimeException("Error finding achatFacture by id: " + id, e);
        }
    }

    @Override
    public List<AchatFactureDTO> search(AchatFactureDTO achatFactureDTO) {
        logger.info("Searching achatFacture: {}", achatFactureDTO);
        try {
            List<AchatFacture> achatFactures = achatFactureRepository.findAll(AchatFactureSpecification.builder().achatFactureDTO(achatFactureDTO).build());
            return achatFactures.stream().map(achatFactureMapper::toAchatFactureDTO).toList();
        } catch (Exception e) {
            logger.error("Error searching achatFacture: {}", achatFactureDTO, e);
            throw new RuntimeException("Error searching achatFacture: " + achatFactureDTO, e);
        }
    }
}
