package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.dto.FactureDTO;
import com.kabir.kabirbackend.entities.Facture;
import com.kabir.kabirbackend.mapper.FactureMapper;
import com.kabir.kabirbackend.repository.FactureRepository;
import com.kabir.kabirbackend.service.interfaces.IFactureService;
import com.kabir.kabirbackend.specifications.FactureSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactureService implements IFactureService {

    private final Logger logger = LoggerFactory.getLogger(FactureService.class);
    private final FactureRepository factureRepository;
    private final FactureMapper factureMapper;

    public FactureService(FactureRepository factureRepository, FactureMapper factureMapper) {
        this.factureRepository = factureRepository;
        this.factureMapper = factureMapper;
    }

    @Override
    public List<FactureDTO> findAll() {
        logger.info("Finding all factures");
        try {
            List<Facture> factures = factureRepository.findAll();
            return factures.stream().map(factureMapper::toFactureDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all factures", e);
            throw new RuntimeException("Error finding all factures", e);
        }
    }

    @Override
    public FactureDTO findById(Long id) {
        logger.info("Finding facture by id: {}", id);
        try {
            Facture facture = factureRepository.findById(id).orElseThrow(() -> new RuntimeException("Facture not found"));
            return factureMapper.toFactureDTO(facture);
        } catch (Exception e) {
            logger.error("Error finding facture by id: {}", id, e);
            throw new RuntimeException("Error finding facture by id: " + id, e);
        }
    }

    @Override
    public FactureDTO save(FactureDTO factureDTO) {
        logger.info("Saving facture: {}", factureDTO);
        try {
            Facture facture = factureMapper.toFacture(factureDTO);
            return factureMapper.toFactureDTO(factureRepository.save(facture));
        } catch (Exception e) {
            logger.error("Error saving facture: {}", factureDTO, e);
            throw new RuntimeException("Error saving facture: " + factureDTO, e);
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting facture: {}", id);
        try {
            factureRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting facture: {}", id, e);
            throw new RuntimeException("Error deleting facture: " + id, e);
        }
    }

    @Override
    public List<FactureDTO> search(FactureDTO factureDTO) {
        logger.info("Searching factures: {}", factureDTO);
        return factureRepository.findAll(FactureSpecification.builder().factureDTO(factureDTO).build()).stream().map(factureMapper::toFactureDTO).toList();
    }
}
