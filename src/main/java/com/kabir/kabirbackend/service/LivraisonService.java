package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.config.requests.RequestStockQte;
import com.kabir.kabirbackend.dto.DetLivraisonDTO;
import com.kabir.kabirbackend.dto.LivraisonDTO;
import com.kabir.kabirbackend.entities.Fournisseur;
import com.kabir.kabirbackend.entities.Livraison;
import com.kabir.kabirbackend.entities.Personnel;
import com.kabir.kabirbackend.entities.Repertoire;
import com.kabir.kabirbackend.mapper.LivraisonMapper;
import com.kabir.kabirbackend.repository.*;
import com.kabir.kabirbackend.service.interfaces.ILivraisonService;
import com.kabir.kabirbackend.specifications.LivraisonSpecification;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivraisonService implements ILivraisonService {
    private static final Logger logger = LoggerFactory.getLogger(LivraisonService.class);

    private final LivraisonRepository livraisonRepository;
    private final RepertoireRepository repertoireRepository;
    private final PersonnelRepository personnelRepository;
    private final StockService stockService;
    private final LivraisonMapper livraisonMapper;

    public LivraisonService(LivraisonRepository livraisonRepository, LivraisonMapper livraisonMapper, RepertoireRepository repertoireRepository, PersonnelRepository personnelRepository, StockService stockService) {
        this.livraisonRepository = livraisonRepository;
        this.repertoireRepository = repertoireRepository;
        this.personnelRepository = personnelRepository;
        this.stockService = stockService;
        this.livraisonMapper = livraisonMapper;
    }

    @Override
    public LivraisonDTO save(LivraisonDTO livraisonDTO) {
        logger.info("Saving livraison: {}", livraisonDTO);
        try {
            Optional<Repertoire> optionalRepertoire = repertoireRepository.findById(livraisonDTO.getRepertoireId());
            Optional<Personnel> optionalPersonnel = personnelRepository.findById(livraisonDTO.getPersonnelId());
            Livraison livraison = livraisonMapper.toLivraison(livraisonDTO);

            livraison.setRepertoire(optionalRepertoire.orElse(null));
            livraison.setPersonnel(optionalPersonnel.orElse(null));
            return livraisonMapper.toLivraisonDTO(livraisonRepository.save(livraison));
        } catch (Exception e) {
            logger.error("Error saving livraison", e);
            throw new RuntimeException("Error saving livraison", e);
        }
    }

    @Override
    public LivraisonDTO findById(Long id) {
        logger.info("Finding livraison by id: {}", id);
        try {
            return livraisonMapper.toLivraisonDTO(livraisonRepository.findById(id).orElse(null));
        } catch (Exception e) {
            logger.error("Error finding livraison by id: {}", id, e);
            throw new RuntimeException("Error finding livraison by id: " + id, e);
        }
    }

    @Override
    public List<LivraisonDTO> findAll() {
        logger.info("Finding all livraisons");
        try {
            List<Livraison> livraisons = livraisonRepository.findAll();
            return livraisons.stream().map(livraisonMapper::toLivraisonDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all livraisons", e);
            throw new RuntimeException("Error finding all livraisons", e);
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting livraison: {}", id);
        try {
            livraisonRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting livraison", e);
            throw new RuntimeException("Error deleting livraison", e);
        }
    }

    @Override
    public List<LivraisonDTO> search(LivraisonDTO livraisonDTO) {
        return livraisonRepository.findAll(LivraisonSpecification.builder().livraisonDTO(livraisonDTO).build()).stream().map(livraisonMapper::toLivraisonDTO).toList();
    }

    @Override
    public int getLastNumLivraison(LivraisonDTO livraisonDTO) {
        return livraisonRepository.findMaxNumLivraisonInYearDateBL(livraisonDTO.getDateBl().getYear()).map(l -> l + 1).orElse(1);
    }

    public void deleteStockInDetLivraison(List<DetLivraisonDTO> detLivraisons) {
        if(CollectionUtils.isNotEmpty(detLivraisons)) {
            for (DetLivraisonDTO detLivraisonDTO : detLivraisons) {
                if(null != detLivraisonDTO.getStockId() && detLivraisonDTO.getQteLivrer() != 0) {
                    stockService.updateQteStock(detLivraisonDTO.getStockId(), new RequestStockQte(detLivraisonDTO.getQteLivrer(), 2));
                }
            }
        }
    }
}
