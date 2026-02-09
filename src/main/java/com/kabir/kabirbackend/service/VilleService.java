package com.kabir.kabirbackend.service;

import com.kabir.kabirbackend.dto.VilleDTO;
import com.kabir.kabirbackend.entities.Ville;
import com.kabir.kabirbackend.mapper.VilleMapper;
import com.kabir.kabirbackend.repository.VilleRepository;
import com.kabir.kabirbackend.service.interfaces.IVilleService;
import com.kabir.kabirbackend.specifications.VilleSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService implements IVilleService {
    private static final Logger logger = LoggerFactory.getLogger(VilleService.class);
    private final VilleRepository villeRepository;
    private final VilleMapper villeMapper;

    public VilleService(VilleRepository villeRepository, VilleMapper villeMapper) {
        this.villeRepository = villeRepository;
        this.villeMapper = villeMapper;
    }

    @Override
    public VilleDTO save(VilleDTO villeDTO) {
        logger.info("Saving ville: {}", villeDTO);
        try {
            Ville ville = villeMapper.toVille(villeDTO);
            ville = villeRepository.save(ville);
            return villeMapper.toVilleDTO(ville);
        } catch (Exception e) {
            logger.error("Error saving ville", e);
            throw new RuntimeException("Error saving ville", e);
        }
    }

    @Override
    public VilleDTO findById(Long id) {
        logger.info("Finding ville by id: {}", id);
        try {
            Ville ville = villeRepository.findById(id).orElseThrow(() -> new RuntimeException("Ville not found"));
            return villeMapper.toVilleDTO(ville);
        } catch (Exception e) {
            logger.error("Error finding ville by id", e);
            throw new RuntimeException("Error finding ville by id", e);
        }
    }

    @Override
    public List<VilleDTO> findAll() {
        logger.info("Finding all villes");
        try {
            List<Ville> villes = villeRepository.findAll(Sort.by(Sort.Direction.ASC, "nomVille"));
            return villes.stream().map(villeMapper::toVilleDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all villes", e);
            throw new RuntimeException("Error finding all villes", e);
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting ville: {}", id);
        try {
            villeRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting ville", e);
            throw new RuntimeException("Error deleting ville", e);
        }
    }

    @Override
    public List<VilleDTO> search(VilleDTO villeDTO) {
        logger.info("Searching villes: {}", villeDTO);
        return villeRepository.findAll(VilleSpecification.builder().villeDTO(villeDTO).build()).stream().map(villeMapper::toVilleDTO).toList();
    }
}
