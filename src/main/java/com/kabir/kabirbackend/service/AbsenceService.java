package com.kabir.kabirbackend.service;

import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.AbsenceDTO;
import com.kabir.kabirbackend.entities.Absence;
import com.kabir.kabirbackend.mapper.AbsenceMapper;
import com.kabir.kabirbackend.repository.AbsenceRepository;
import com.kabir.kabirbackend.service.interfaces.IAbsenceService;
import com.kabir.kabirbackend.specifications.AbsenceSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbsenceService implements IAbsenceService {

    private static final Logger logger = LoggerFactory.getLogger(AbsenceService.class);
    private final AbsenceRepository absenceRepository;
    private final AbsenceMapper absenceMapper;

    public AbsenceService(AbsenceRepository absenceRepository, AbsenceMapper absenceMapper) {
        this.absenceRepository = absenceRepository;
        this.absenceMapper = absenceMapper;
    }

    @Override
    public AbsenceDTO save(AbsenceDTO absenceDTO) {
        logger.info("Saving absence: {}", absenceDTO);
        try {
            Absence absence = absenceRepository.save(absenceMapper.toAbsence(absenceDTO));
            return absenceMapper.toAbsenceDTO(absence);
        } catch (Exception e) {
            logger.error("Error saving absence", e);
            throw new RuntimeException("Error saving absence", e);
        }
    }


    @Override
    public AbsenceDTO findById(Long id) {
        logger.info("Getting absence by id: {}", id);
        Absence absence = absenceRepository.findById(id).orElseThrow(() -> new RuntimeException("Absence not found"));
        return absenceMapper.toAbsenceDTO(absence);
    }

    @Override
    public List<AbsenceDTO> findAll() {
        logger.info("Getting all absences");
        return absenceRepository.findAll().stream()
                .map(absenceMapper::toAbsenceDTO)
                .toList();
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting absence with id: {}", id);
        absenceRepository.deleteById(id);
    }

    @Override
    public List<AbsenceDTO> search(AbsenceDTO absenceDTO) {
        logger.info("Searching absence by date and personnel id with AbsenceSpecification builder {}", absenceDTO);
        return absenceRepository.findAll(AbsenceSpecification.builder().absenceDTO(absenceDTO).build()).stream()
                .map(absenceMapper::toAbsenceDTO)
                .toList();
    }

    @Override
    public List<AbsenceDTO> searchByCommon(CommonSearchModel commonSearchModel) {
        logger.info("Searching absence by common: {}", commonSearchModel);
        return absenceRepository.findAll(AbsenceSpecification.builder().build().searchByCommon(commonSearchModel)).stream()
                .map(absenceMapper::toAbsenceDTO)
                .toList();
    }
}
