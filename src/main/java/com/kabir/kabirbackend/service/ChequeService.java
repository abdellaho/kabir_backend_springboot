package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.dto.ChequeDTO;
import com.kabir.kabirbackend.entities.Cheque;
import com.kabir.kabirbackend.entities.Fournisseur;
import com.kabir.kabirbackend.entities.Personnel;
import com.kabir.kabirbackend.mapper.ChequeMapper;
import com.kabir.kabirbackend.repository.ChequeRepository;
import com.kabir.kabirbackend.repository.FournisseurRepository;
import com.kabir.kabirbackend.repository.PersonnelRepository;
import com.kabir.kabirbackend.service.interfaces.IChequeService;
import com.kabir.kabirbackend.specifications.ChequeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChequeService implements IChequeService {

    private static final Logger logger = LoggerFactory.getLogger(ChequeService.class);
    private final ChequeRepository chequeRepository;
    private final ChequeMapper chequeMapper;
    private final PersonnelRepository personnelRepository;
    private final FournisseurRepository fournisseurRepository;

    public ChequeService(ChequeRepository chequeRepository, ChequeMapper chequeMapper, PersonnelRepository personnelRepository, FournisseurRepository fournisseurRepository) {
        this.chequeRepository = chequeRepository;
        this.chequeMapper = chequeMapper;
        this.personnelRepository = personnelRepository;
        this.fournisseurRepository = fournisseurRepository;
    }

    @Override
    public ChequeDTO save(ChequeDTO chequeDTO) {
        logger.info("Saving cheque: {}", chequeDTO);
        try {
            Optional<Fournisseur> optionalFournisseur = null != chequeDTO.getFournisseurId() ? fournisseurRepository.findById(chequeDTO.getFournisseurId()) : Optional.empty();
            Optional<Personnel> optionalPersonnel = null != chequeDTO.getOperateurId() ? personnelRepository.findById(chequeDTO.getOperateurId()) : Optional.empty();

            Cheque cheque = chequeMapper.toEntity(chequeDTO);
            cheque.setFournisseur(optionalFournisseur.orElse(null));
            cheque.setOperateur(optionalPersonnel.orElse(null));

            return chequeMapper.toDto(chequeRepository.save(cheque));
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

    @Override
    public int getLastNum() {
        logger.info("Getting last num Cheque");
        return chequeRepository.findMaxNumCheque().map(l -> l + 1).orElse(1);
    }


}
