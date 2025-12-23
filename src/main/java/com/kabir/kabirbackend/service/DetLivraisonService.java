package com.kabir.kabirbackend.service;

import com.kabir.kabirbackend.dto.DetLivraisonDTO;
import com.kabir.kabirbackend.entities.DetLivraison;
import com.kabir.kabirbackend.mapper.DetLivraisonMapper;
import com.kabir.kabirbackend.repository.DetLivraisonRepository;
import com.kabir.kabirbackend.service.interfaces.IDetLivraisonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetLivraisonService implements IDetLivraisonService {
    private static final Logger logger = LoggerFactory.getLogger(DetLivraisonService.class);
    private final DetLivraisonRepository detLivraisonRepository;
    private final DetLivraisonMapper detLivraisonMapper;

    public DetLivraisonService(DetLivraisonRepository detLivraisonRepository, DetLivraisonMapper detLivraisonMapper) {
        this.detLivraisonRepository = detLivraisonRepository;
        this.detLivraisonMapper = detLivraisonMapper;
    }

    @Override
    public DetLivraisonDTO save(DetLivraisonDTO detLivraisonDTO) {
        logger.info("Saving DetLivraison: {}", detLivraisonDTO);
        try {
            DetLivraison detLivraison = detLivraisonMapper.toDetLivraison(detLivraisonDTO);
            logger.info("DetLivraison saved: {}", detLivraison);
            return detLivraisonMapper.toDetLivraisonDTO(detLivraisonRepository.save(detLivraison));
        } catch (Exception e) {
            logger.error("Error saving DetLivraison", e);
            throw new RuntimeException("Error saving DetLivraison", e);
        }
    }

    @Override
    public List<DetLivraisonDTO> findAll() {
        logger.info("Finding all DetLivraison");
        try {
            return detLivraisonRepository.findAll().stream()
                    .map(detLivraisonMapper::toDetLivraisonDTO)
                    .toList();
        } catch (Exception e) {
            logger.error("Error finding all DetLivraison", e);
            throw new RuntimeException("Error finding all DetLivraison", e);
        }
    }

    @Override
    public DetLivraisonDTO findById(Long id) {
        logger.info("Finding DetLivraison by id: {}", id);
        try {
            return detLivraisonMapper.toDetLivraisonDTO(detLivraisonRepository.findById(id).orElse(null));
        } catch (Exception e) {
            logger.error("Error finding DetLivraison by id", e);
            throw new RuntimeException("Error finding DetLivraison by id", e);
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting DetLivraison by id: {}", id);
        try {
            detLivraisonRepository.deleteById(id);
            logger.info("DetLivraison deleted by id: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting DetLivraison by id", e);
            throw new RuntimeException("Error deleting DetLivraison by id", e);
        }
    }

    @Override
    public List<DetLivraisonDTO> search(DetLivraisonDTO detLivraisonDTO) {
        logger.info("Searching DetLivraison by DetLivraisonDTO: {}", detLivraisonDTO);
        try {
            return detLivraisonRepository.findAll().stream()
                    .map(detLivraisonMapper::toDetLivraisonDTO)
                    .toList();
        } catch (Exception e) {
            logger.error("Error searching DetLivraison by keyword", e);
            throw new RuntimeException("Error searching DetLivraison by keyword", e);
        }
    }
}
