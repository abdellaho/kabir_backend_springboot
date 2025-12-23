package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.dto.EmployeDTO;
import com.kabir.kabirbackend.entities.Employe;
import com.kabir.kabirbackend.mapper.EmployeMapper;
import com.kabir.kabirbackend.repository.EmployeRepository;
import com.kabir.kabirbackend.service.interfaces.IEmployeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeService implements IEmployeService {

    private final Logger logger = LoggerFactory.getLogger(EmployeService.class);

    private final EmployeRepository employeRepository;
    private final EmployeMapper employeMapper;

    public EmployeService(EmployeRepository employeRepository, EmployeMapper employeMapper) {
        this.employeRepository = employeRepository;
        this.employeMapper = employeMapper;
    }

    @Override
    public EmployeDTO save(EmployeDTO employeDTO) {
        logger.info("Saving employe: {}", employeDTO);
        try {
            Employe employe = employeMapper.toEmploye(employeDTO);
            employe = employeRepository.save(employe);
            logger.info("Saved employe: {}", employe);
            return employeMapper.toEmployeDTO(employe);
        } catch (Exception e) {
            logger.error("Error saving employe: {}", employeDTO, e);
            throw new RuntimeException("Error saving employe", e);
        }
    }

    @Override
    public List<EmployeDTO> findAll() {
        logger.info("Finding all employes");
        try {
            List<Employe> employes = employeRepository.findAll();
            logger.info("Found all employes: {}", employes);
            return employes.stream().map(employeMapper::toEmployeDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all employes", e);
            throw new RuntimeException("Error finding all employes", e);
        }
    }

    @Override
    public EmployeDTO findById(Long id) {
        logger.info("Finding employe by id: {}", id);
        try {
            Employe employe = employeRepository.findById(id).orElse(null);
            logger.info("Found employe by id: {}", employe);
            return employeMapper.toEmployeDTO(employe);
        } catch (Exception e) {
            logger.error("Error finding employe by id: {}", id, e);
            throw new RuntimeException("Error finding employe by id: " + id, e);
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting employe by id: {}", id);
        try {
            employeRepository.deleteById(id);
            logger.info("Deleted employe by id: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting employe by id: {}", id, e);
            throw new RuntimeException("Error deleting employe by id: " + id, e);
        }
    }

    @Override
    public List<EmployeDTO> search(EmployeDTO employeDTO) {
        logger.info("Searching employes by example: {}", employeDTO);
        try {
            List<Employe> employes = employeRepository.findAll();
            logger.info("Found employes by example: {}", employes);
            return employes.stream().map(employeMapper::toEmployeDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding employes by example: {}", employeDTO, e);
            throw new RuntimeException("Error finding employes by example: " + employeDTO, e);
        }
    }

}
