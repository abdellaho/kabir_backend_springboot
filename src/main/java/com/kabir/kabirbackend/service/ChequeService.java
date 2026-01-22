package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.dto.ChequeDTO;
import com.kabir.kabirbackend.entities.Cheque;
import com.kabir.kabirbackend.mapper.ChequeMapper;
import com.kabir.kabirbackend.repository.ChequeRepository;
import com.kabir.kabirbackend.service.interfaces.IChequeService;
import com.kabir.kabirbackend.specifications.ChequeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChequeService implements IChequeService {

    private static final Logger logger = LoggerFactory.getLogger(ChequeService.class);
    private final ChequeRepository chequeRepository;
    private final ChequeMapper chequeMapper;

    public ChequeService(ChequeRepository chequeRepository, ChequeMapper chequeMapper) {
        this.chequeRepository = chequeRepository;
        this.chequeMapper = chequeMapper;
    }

    @Override
    public ChequeDTO save(ChequeDTO chequeDTO) {
        logger.info("Saving cheque: {}", chequeDTO);
        try {
            Cheque cheque = chequeRepository.save(chequeMapper.toEntity(chequeDTO));
            return chequeMapper.toDto(cheque);
        } catch (Exception e) {
            logger.error("Error saving cheque", e);
            throw new RuntimeException("Error saving cheque", e);
        }
    }


    @Override
    public ChequeDTO findById(Long id) {
        logger.info("Getting cheque by id: {}", id);
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() -> new RuntimeException("Cheque not found"));
        return chequeMapper.toDto(cheque);
    }

    @Override
    public List<ChequeDTO> findAll() {
        logger.info("Getting all absences");
        return chequeRepository.findAll().stream()
                .map(chequeMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting cheque with id: {}", id);
        chequeRepository.deleteById(id);
    }

    @Override
    public List<ChequeDTO> search(ChequeDTO chequeDTO) {
        logger.info("Searching cheque by date and personnel id with AbsenceSpecification builder {}", chequeDTO);
        return chequeRepository.findAll(ChequeSpecification.builder().chequeDTO(chequeDTO).build()).stream()
                .map(chequeMapper::toDto)
                .toList();
    }
}
