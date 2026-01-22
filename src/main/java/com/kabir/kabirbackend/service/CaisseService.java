package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.dto.CaisseDTO;
import com.kabir.kabirbackend.entities.Caisse;
import com.kabir.kabirbackend.mapper.CaisseMapper;
import com.kabir.kabirbackend.repository.CaisseRepository;
import com.kabir.kabirbackend.service.interfaces.ICaisseService;
import com.kabir.kabirbackend.specifications.CaisseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaisseService implements ICaisseService {

    private static final Logger logger = LoggerFactory.getLogger(CaisseService.class);
    private final CaisseRepository caisseRepository;
    private final CaisseMapper caisseMapper;

    public CaisseService(CaisseRepository caisseRepository, CaisseMapper caisseMapper) {
        this.caisseRepository = caisseRepository;
        this.caisseMapper = caisseMapper;
    }

    @Override
    public CaisseDTO save(CaisseDTO caisseDTO) {
        logger.info("Saving caisse: {}", caisseDTO);
        try {
            Caisse caisse = caisseRepository.save(caisseMapper.toEntity(caisseDTO));
            return caisseMapper.toDto(caisse);
        } catch (Exception e) {
            logger.error("Error saving caisse", e);
            throw new RuntimeException("Error saving caisse", e);
        }
    }


    @Override
    public CaisseDTO findById(Long id) {
        logger.info("Getting caisse by id: {}", id);
        Caisse caisse = caisseRepository.findById(id).orElseThrow(() -> new RuntimeException("Caisse not found"));
        return caisseMapper.toDto(caisse);
    }

    @Override
    public List<CaisseDTO> findAll() {
        logger.info("Getting all absences");
        return caisseRepository.findAll().stream()
                .map(caisseMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting caisse with id: {}", id);
        caisseRepository.deleteById(id);
    }

    @Override
    public List<CaisseDTO> search(CaisseDTO caisseDTO) {
        logger.info("Searching caisse by date and personnel id with AbsenceSpecification builder {}", caisseDTO);
        return caisseRepository.findAll(CaisseSpecification.builder().caisseDTO(caisseDTO).build()).stream()
                .map(caisseMapper::toDto)
                .toList();
    }
}
