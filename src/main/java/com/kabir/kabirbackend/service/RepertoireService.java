package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.dto.RepertoireDTO;
import com.kabir.kabirbackend.entities.Repertoire;
import com.kabir.kabirbackend.mapper.RepertoireMapper;
import com.kabir.kabirbackend.repository.RepertoireRepository;
import com.kabir.kabirbackend.service.interfaces.IRepertoireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepertoireService implements IRepertoireService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RepertoireRepository repertoireRepository;
    private final RepertoireMapper repertoireMapper;

    public RepertoireService(RepertoireRepository repertoireRepository, RepertoireMapper repertoireMapper) {
        this.repertoireRepository = repertoireRepository;
        this.repertoireMapper = repertoireMapper;
    }

    @Override
    public RepertoireDTO save(RepertoireDTO repertoireDTO) {
        logger.info("Saving repertoire: {}", repertoireDTO);
        try {
            Repertoire repertoire = repertoireMapper.toRepertoire(repertoireDTO);
            repertoire = repertoireRepository.save(repertoire);
            return repertoireMapper.toRepertoireDTO(repertoire);
        } catch (Exception e) {
            logger.error("Error saving repertoire", e);
            throw new RuntimeException("Error saving repertoire", e);
        }
    }

    @Override
    public List<RepertoireDTO> findAll() {
        logger.info("Finding all repertoires");
        try {
            List<Repertoire> repertoires = repertoireRepository.findAll();
            return repertoires.stream().map(repertoireMapper::toRepertoireDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all repertoires", e);
            throw new RuntimeException("Error finding all repertoires", e);
        }
    }

    @Override
    public RepertoireDTO findById(Long id) {
        logger.info("Finding repertoire by id: {}", id);
        try {
            Repertoire repertoire = repertoireRepository.findById(id).orElseThrow(() -> new RuntimeException("Repertoire not found"));
            return repertoireMapper.toRepertoireDTO(repertoire);
        } catch (Exception e) {
            logger.error("Error finding repertoire by id", e);
            throw new RuntimeException("Error finding repertoire by id", e);
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting repertoire: {}", id);
        try {
            repertoireRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting repertoire", e);
            throw new RuntimeException("Error deleting repertoire", e);
        }
    }

    @Override
    public List<RepertoireDTO> search(RepertoireDTO absenceDTO) {
        return repertoireRepository.findAll().stream().map(repertoireMapper::toRepertoireDTO).toList();
    }
}
