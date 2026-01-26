package com.kabir.kabirbackend.service;

import com.kabir.kabirbackend.dto.PersonnelDTO;
import com.kabir.kabirbackend.entities.Personnel;
import com.kabir.kabirbackend.mapper.PersonnelMapper;
import com.kabir.kabirbackend.repository.PersonnelRepository;
import com.kabir.kabirbackend.service.interfaces.IPersonnelService;
import com.kabir.kabirbackend.specifications.PersonnelSpecification;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonnelService implements IPersonnelService {

    private final Logger logger = LoggerFactory.getLogger(PersonnelService.class);

    private final PersonnelRepository personnelRepository;
    private final PersonnelMapper personnelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonnelService(PersonnelRepository personnelRepository,
                            PersonnelMapper personnelMapper,
                            PasswordEncoder passwordEncoder) {
        this.personnelRepository = personnelRepository;
        this.personnelMapper = personnelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public long count() {
        return personnelRepository.count();
    }

    @Override
    public PersonnelDTO save(PersonnelDTO personnelDTO) {
        logger.info("Saving personnel: {}", personnelDTO);
        try {
            Personnel personnel = personnelMapper.toEntity(personnelDTO);
            if(StringUtils.isNotBlank(personnelDTO.getPasswordFake()) && StringUtils.isNotBlank(personnelDTO.getEmail())) {
                personnel.setPassword(passwordEncoder.encode(personnelDTO.getPasswordFake()));
            }
            personnel = personnelRepository.save(personnel);
            return personnelMapper.toDTO(personnel);
        } catch (Exception e) {
            logger.error("Error saving personnel", e);
            throw new RuntimeException("Error saving personnel", e);
        }
    }

    @Override
    public List<PersonnelDTO> findAll() {
        logger.info("Finding all personnels");
        try {
            List<Personnel> personnels = personnelRepository.findAll();
            return personnels.stream().map(personnelMapper::toDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all personnels", e);
            throw new RuntimeException("Error finding all personnels", e);
        }
    }

    @Override
    public PersonnelDTO findById(Long id) {
        logger.info("Finding personnel by id: {}", id);
        try {
            Personnel personnel = personnelRepository.findById(id).orElse(null);
            if (personnel == null) {
                throw new RuntimeException("Personnel not found");
            }
            return personnelMapper.toDTO(personnel);
        } catch (Exception e) {
            logger.error("Error finding personnel by id", e);
            throw new RuntimeException("Error finding personnel by id", e);
        }
    }

    @Override
    public PersonnelDTO findByEmail(String email) {
        logger.info("Finding personnel by email: {}", email);
        try {
            Personnel personnel = personnelRepository.findByEmail(email).orElse(null);
            if (personnel == null) {
                throw new RuntimeException("Personnel not found");
            }
            return personnelMapper.toDTO(personnel);
        } catch (Exception e) {
            logger.error("Error finding personnel by id", e);
            throw new RuntimeException("Error finding personnel by id", e);
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting personnel: {}", id);
        try {
            personnelRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting personnel", e);
            throw new RuntimeException("Error deleting personnel", e);
        }
    }

    @Override
    public List<PersonnelDTO> search(PersonnelDTO personnelDTO) {
        return personnelRepository.findAll(PersonnelSpecification.builder().personnelDTO(personnelDTO).build()).stream().map(personnelMapper::toDTO).toList();
    }

    @Override
    public List<PersonnelDTO> searchBySupprimerOrArchiver(PersonnelDTO personnelDTO) {
        return personnelRepository.findAll(PersonnelSpecification.searchBySupprimerOrArchiver(personnelDTO)).stream().map(personnelMapper::toDTO).toList();
    }
}
