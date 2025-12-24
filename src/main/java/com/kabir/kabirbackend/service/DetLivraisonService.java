package com.kabir.kabirbackend.service;

import com.kabir.kabirbackend.dto.DetLivraisonDTO;
import com.kabir.kabirbackend.entities.DetLivraison;
import com.kabir.kabirbackend.entities.Livraison;
import com.kabir.kabirbackend.entities.Stock;
import com.kabir.kabirbackend.mapper.DetLivraisonMapper;
import com.kabir.kabirbackend.repository.DetLivraisonRepository;
import com.kabir.kabirbackend.repository.StockRepository;
import com.kabir.kabirbackend.service.interfaces.IDetLivraisonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetLivraisonService implements IDetLivraisonService {

    private static final Logger logger = LoggerFactory.getLogger(DetLivraisonService.class);
    private final DetLivraisonRepository detLivraisonRepository;
    private final StockRepository stockRepository;
    private final DetLivraisonMapper detLivraisonMapper;

    public DetLivraisonService(DetLivraisonRepository detLivraisonRepository, DetLivraisonMapper detLivraisonMapper, StockRepository stockRepository) {
        this.detLivraisonRepository = detLivraisonRepository;
        this.stockRepository = stockRepository;
        this.detLivraisonMapper = detLivraisonMapper;
    }

    @Override
    public DetLivraisonDTO saveWithoutLivraison(DetLivraisonDTO detLivraisonDTO) {
        logger.info("Saving DetLivraison without Livraison: {}", detLivraisonDTO);
        try {
            Optional<Stock> optionalStock = stockRepository.findById(detLivraisonDTO.getStockId());

            DetLivraison detLivraison = detLivraisonMapper.toDetLivraison(detLivraisonDTO);
            detLivraison.setStock(optionalStock.orElse(null));

            detLivraison = detLivraisonRepository.save(detLivraison);

            logger.info("DetLivraison saved without Livraison: {}", detLivraison);

            return detLivraisonMapper.toDetLivraisonDTO(detLivraison);
        } catch (Exception e) {
            logger.error("Error saving DetLivraison", e);
            throw new RuntimeException("Error saving DetLivraison", e);
        }
    }

    @Override
    public DetLivraisonDTO save(DetLivraisonDTO detLivraisonDTO, Livraison livraison) {
        logger.info("Saving DetLivraison: {}", detLivraisonDTO);
        try {
            if(null != livraison && null != livraison.getId()) {
                Optional<Stock> optionalStock = stockRepository.findById(detLivraisonDTO.getStockId());

                DetLivraison detLivraison = detLivraisonMapper.toDetLivraison(detLivraisonDTO);
                detLivraison.setStock(optionalStock.orElse(null));
                detLivraison.setLivraison(livraison);

                detLivraison = detLivraisonRepository.save(detLivraison);

                logger.info("DetLivraison saved: {}", detLivraison);

                return detLivraisonMapper.toDetLivraisonDTO(detLivraison);
            } else {
                logger.error("Livraison is null or Livraison id is null");
                throw new RuntimeException("Livraison is null or Livraison id is null");
            }
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
    public List<DetLivraisonDTO> DetLivraisonByLivraisonId(Long idLivraison) {
        logger.info("Finding DetLivraison by LivraisonId: {}", idLivraison);
        try {
            return detLivraisonRepository.findByLivraisonId(idLivraison).stream()
                    .map(detLivraisonMapper::toDetLivraisonDTO)
                    .toList();
        } catch (Exception e) {
            logger.error("Error finding DetLivraison by LivraisonId", e);
            throw new RuntimeException("Error finding DetLivraison by LivraisonId", e);
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
