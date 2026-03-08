package com.kabir.kabirbackend.service;

import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.EntretienDTO;
import com.kabir.kabirbackend.entities.Entretien;
import com.kabir.kabirbackend.entities.Voiture;
import com.kabir.kabirbackend.mapper.EntretienMapper;
import com.kabir.kabirbackend.repository.EntretienRepository;
import com.kabir.kabirbackend.repository.VoitureRepository;
import com.kabir.kabirbackend.service.interfaces.IEntretienService;
import com.kabir.kabirbackend.specifications.EntretienSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntretienService implements IEntretienService {

    private final Logger logger = LoggerFactory.getLogger(EntretienService.class);

    private final EntretienRepository entretienRepository;
    private final EntretienMapper entretienMapper;
    private final VoitureRepository voitureRepository;

    public EntretienService(EntretienRepository entretienRepository, EntretienMapper entretienMapper, VoitureRepository voitureRepository) {
        this.entretienRepository = entretienRepository;
        this.entretienMapper = entretienMapper;
        this.voitureRepository = voitureRepository;
    }

    @Override
    public EntretienDTO save(EntretienDTO entretienDTO) {
        try {
            Voiture voiture = null;
            if(null != entretienDTO.getVoitureId() && entretienDTO.getVoitureId() != 0) {
                voiture = voitureRepository.findById(entretienDTO.getVoitureId()).orElse(null);
            }

            Entretien entretien = entretienMapper.toEntity(entretienDTO);
            entretien.setVoiture(voiture);

            Entretien savedEntretien = entretienRepository.save(entretien);
            return entretienMapper.toDTO(savedEntretien);
        } catch (Exception e) {
            logger.error("Error while saving entretien", e);
            throw new RuntimeException("Error while saving entretien", e);
        }
    }

    @Override
    public List<EntretienDTO> findAll() {
        try {
            List<Entretien> entretienList = entretienRepository.findAll();
            return entretienList.stream().map(entretienMapper::toDTO).toList();
        } catch (Exception e) {
            logger.error("Error while finding all entretien", e);
            throw new RuntimeException("Error while finding all entretien", e);
        }
    }

    @Override
    public EntretienDTO findById(Long id) {
        try {
            Optional<Entretien> entretienOptional = entretienRepository.findById(id);
            if (entretienOptional.isPresent()) {
                return entretienMapper.toDTO(entretienOptional.get());
            } else {
                throw new RuntimeException("Entretien with id " + id + " not found");
            }
        } catch (Exception e) {
            logger.error("Error while finding entretien by id: {}", id, e);
            throw new RuntimeException("Error while finding entretien by id: " + id, e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            entretienRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error while deleting entretien with id: {}", id, e);
            throw new RuntimeException("Error deleting entretien by id: " + id, e);
        }
    }

    @Override
    public List<EntretienDTO> search(CommonSearchModel commonSearchModel) {
        logger.info("Searching entretien : {}", commonSearchModel);
        return entretienRepository.findAll(EntretienSpecification.searchByCommon(commonSearchModel)).stream().map(entretienMapper::toDTO).toList();
    }

}
