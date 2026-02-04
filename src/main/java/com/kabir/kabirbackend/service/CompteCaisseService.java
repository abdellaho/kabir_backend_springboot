package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.config.searchEntities.CompteCaisseSearch;
import com.kabir.kabirbackend.dto.CompteCaisseDTO;
import com.kabir.kabirbackend.entities.CompteCaisse;
import com.kabir.kabirbackend.mapper.CompteCaisseMapper;
import com.kabir.kabirbackend.repository.CompteCaisseRepository;
import com.kabir.kabirbackend.service.interfaces.ICompteCaisseService;
import com.kabir.kabirbackend.specifications.CompteCaisseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompteCaisseService implements ICompteCaisseService {

    private static final Logger logger = LoggerFactory.getLogger(CompteCaisseService.class);
    private final CompteCaisseRepository compteCaisseRepository;
    private final CompteCaisseMapper compteCaisseMapper;

    public CompteCaisseService(CompteCaisseRepository compteCaisseRepository, CompteCaisseMapper compteCaisseMapper) {
        this.compteCaisseRepository = compteCaisseRepository;
        this.compteCaisseMapper = compteCaisseMapper;
    }

    @Override
    public CompteCaisseDTO save(CompteCaisseDTO compteCaisseDTO) {
        logger.info("Saving compteCaisse: {}", compteCaisseDTO);
        try {
            CompteCaisse compteCaisse = compteCaisseRepository.save(compteCaisseMapper.toCompteCaisse(compteCaisseDTO));
            return compteCaisseMapper.toCompteCaisseDTO(compteCaisse);
        } catch (Exception e) {
            logger.error("Error saving compteCaisse", e);
            throw new RuntimeException("Error saving compteCaisse", e);
        }
    }


    @Override
    public CompteCaisseDTO findById(Long id) {
        logger.info("Getting compteCaisse by id: {}", id);
        CompteCaisse compteCaisse = compteCaisseRepository.findById(id).orElseThrow(() -> new RuntimeException("CompteCaisse not found"));
        return compteCaisseMapper.toCompteCaisseDTO(compteCaisse);
    }

    @Override
    public List<CompteCaisseDTO> findAll() {
        logger.info("Getting all absences");
        return compteCaisseRepository.findAll().stream()
                .map(compteCaisseMapper::toCompteCaisseDTO)
                .toList();
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting compteCaisse with id: {}", id);
        compteCaisseRepository.deleteById(id);
    }

    @Override
    public List<CompteCaisseDTO> search(CompteCaisseSearch compteCaisseSearch) {
        logger.info("Searching compteCaisse by compteCaisseSearch builder {}", compteCaisseSearch);
        return compteCaisseRepository.findAll(CompteCaisseSpecification.builder().compteCaisseSearch(compteCaisseSearch).build()).stream()
                .map(compteCaisseMapper::toCompteCaisseDTO)
                .toList();
    }

    @Override
    public List<CompteCaisseDTO> searchByCommon(CommonSearchModel commonSearchModel) {
        logger.info("Searching compte caisse by common: {}", commonSearchModel);
        return compteCaisseRepository.findAll(CompteCaisseSpecification.builder().build().searchByCommon(commonSearchModel)).stream()
                .map(compteCaisseMapper::toCompteCaisseDTO)
                .toList();
    }
}
