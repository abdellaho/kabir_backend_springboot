package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.dto.RepertoireDTO;
import com.kabir.kabirbackend.entities.Personnel;
import com.kabir.kabirbackend.entities.Repertoire;
import com.kabir.kabirbackend.entities.Ville;
import com.kabir.kabirbackend.mapper.RepertoireMapper;
import com.kabir.kabirbackend.repository.PersonnelRepository;
import com.kabir.kabirbackend.repository.RepertoireRepository;
import com.kabir.kabirbackend.repository.VilleRepository;
import com.kabir.kabirbackend.service.interfaces.IRepertoireService;
import com.kabir.kabirbackend.specifications.RepertoireSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepertoireService implements IRepertoireService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RepertoireRepository repertoireRepository;
    private final VilleRepository villeRepository;
    private final PersonnelRepository personnelRepository;
    private final RepertoireMapper repertoireMapper;

    public RepertoireService(RepertoireRepository repertoireRepository, RepertoireMapper repertoireMapper, VilleRepository villeRepository, PersonnelRepository personnelRepository) {
        this.repertoireRepository = repertoireRepository;
        this.villeRepository = villeRepository;
        this.personnelRepository = personnelRepository;
        this.repertoireMapper = repertoireMapper;
    }

    @Override
    public RepertoireDTO save(RepertoireDTO repertoireDTO) {
        logger.info("Saving repertoire: {}", repertoireDTO);
        try {
            Optional<Ville> villeOptional = villeRepository.findById(repertoireDTO.getVilleId());
            if (villeOptional.isEmpty()) {
                throw new RuntimeException("Ville not found");
            }

            Optional<Personnel> personnelOptional = null != repertoireDTO.getPersonnelId() ? personnelRepository.findById(repertoireDTO.getPersonnelId()) : Optional.empty();
            Repertoire repertoire = repertoireMapper.toRepertoire(repertoireDTO);
            repertoire.setVille(villeOptional.get());
            repertoire.setPersonnel(personnelOptional.orElse(null));
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
    public List<RepertoireDTO> search(RepertoireDTO repertoireDTO) {
        return repertoireRepository.findAll(RepertoireSpecification.searchBySupprimerOrArchiver(repertoireDTO)).stream().map(repertoireMapper::toRepertoireDTO).toList();
    }

    @Override
    public List<RepertoireDTO> exist(RepertoireDTO repertoireDTO) {
        return repertoireRepository.findAll(RepertoireSpecification.builder().repertoireDTO(repertoireDTO).build()).stream().map(repertoireMapper::toRepertoireDTO).toList();
    }

    @Override
    public RepertoireDTO updateNbrOperation(Long id, Integer type) {
        if(null != id && null != type) {
            RepertoireDTO repertoireDTO = this.findById(id);

            if(null != repertoireDTO) {
                int numberToAdd = type == 1 ? 1 : -1;
                repertoireDTO.setNbrOperationClient(repertoireDTO.getNbrOperationClient() + numberToAdd);
                return this.save(repertoireDTO);
            } else {
                logger.error("Fournisseur not found with id: {}", id);
                throw new RuntimeException("Fournisseur not found with id: " + id);
            }
        }

        return null;
    }
}
