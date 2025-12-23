package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.dto.DroitDTO;
import com.kabir.kabirbackend.entities.Droit;
import com.kabir.kabirbackend.mapper.DroitMapper;
import com.kabir.kabirbackend.repository.DroitRepository;
import com.kabir.kabirbackend.service.interfaces.IDroitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroitService implements IDroitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DroitService.class);

    private final DroitRepository droitRepository;
    private final DroitMapper droitMapper;

    public DroitService(DroitRepository droitRepository, DroitMapper droitMapper) {
        this.droitRepository = droitRepository;
        this.droitMapper = droitMapper;
    }

    @Override
    public DroitDTO save(DroitDTO droitDTO) {
        LOGGER.info("Saving droit: {}", droitDTO);
        try {
            Droit droit = droitMapper.toDroit(droitDTO);
            droit = droitRepository.save(droit);
            LOGGER.info("Droit saved: {}", droit);
            return droitMapper.toDroitDTO(droit);
        } catch (Exception e) {
            LOGGER.error("Error while saving droit", e);
            throw new RuntimeException("Error while saving droit", e);
        }
    }

    @Override
    public List<DroitDTO> findAll() {
        LOGGER.info("Finding all droits");
        try {
            List<Droit> droits = droitRepository.findAll();
            LOGGER.info("Found droits: {}", droits);
            return droits.stream().map(droitMapper::toDroitDTO).toList();
        } catch (Exception e) {
            LOGGER.error("Error while finding all droits", e);
            throw new RuntimeException("Error while finding all droits", e);
        }
    }

    @Override
    public DroitDTO findById(Long id) {
        LOGGER.info("Finding droit by id: {}", id);
        try {
            Droit droit = droitRepository.findById(id).orElseThrow(() -> new RuntimeException("Droit not found"));
            LOGGER.info("Found droit: {}", droit);
            return droitMapper.toDroitDTO(droit);
        } catch (Exception e) {
            LOGGER.error("Error while finding droit by id", e);
            throw new RuntimeException("Error while finding droit by id", e);
        }
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Deleting droit by id: {}", id);
        try {
            droitRepository.deleteById(id);
            LOGGER.info("Droit deleted by id: {}", id);
        } catch (Exception e) {
            LOGGER.error("Error while deleting droit by id", e);
            throw new RuntimeException("Error while deleting droit by id", e);
        }
    }

    @Override
    public List<DroitDTO> search(DroitDTO droitDTO) {
        LOGGER.info("Searching droits: {}", droitDTO);
        try {
            List<Droit> droits = droitRepository.findAll();
            LOGGER.info("Found droits: {}", droits);
            return droits.stream().map(droitMapper::toDroitDTO).toList();
        } catch (Exception e) {
            LOGGER.error("Error while searching droits", e);
            throw new RuntimeException("Error while searching droits", e);
        }
    }
}
