package com.kabir.kabirbackend.service;

import com.kabir.kabirbackend.dto.FournisseurDTO;
import com.kabir.kabirbackend.entities.Fournisseur;
import com.kabir.kabirbackend.mapper.FournisseurMapper;
import com.kabir.kabirbackend.repository.FournisseurRepository;
import com.kabir.kabirbackend.service.interfaces.IFournisseurService;
import com.kabir.kabirbackend.specifications.FournisseurSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FournisseurService implements IFournisseurService {

    private final Logger logger = LoggerFactory.getLogger(FournisseurService.class);

    private final FournisseurRepository fournisseurRepository;
    private final FournisseurMapper fournisseurMapper;

    @Autowired
    public FournisseurService(FournisseurRepository fournisseurRepository, FournisseurMapper fournisseurMapper) {
        this.fournisseurRepository = fournisseurRepository;
        this.fournisseurMapper = fournisseurMapper;
    }

    @Override
    public FournisseurDTO save(FournisseurDTO fournisseurDTO) {
        try {
            Fournisseur fournisseur = fournisseurMapper.toFournisseur(fournisseurDTO);
            Fournisseur savedFournisseur = fournisseurRepository.save(fournisseur);
            return fournisseurMapper.toFournisseurDTO(savedFournisseur);
        } catch (Exception e) {
            logger.error("Error saving Fournisseur", e);
            throw new RuntimeException("Error saving Fournisseur", e);
        }
    }

    @Override
    public List<FournisseurDTO> findAll() {
        try {
            List<Fournisseur> fournisseurs = fournisseurRepository.findAll(Sort.by(Sort.Direction.ASC, "designation"));
            return fournisseurs.stream().map(fournisseurMapper::toFournisseurDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all Fournisseurs", e);
            throw new RuntimeException("Error finding all Fournisseurs", e);
        }
    }

    @Override
    public FournisseurDTO findById(Long id) {
        try {
            Fournisseur fournisseur = fournisseurRepository.findById(id).orElse(null);
            return fournisseurMapper.toFournisseurDTO(fournisseur);
        } catch (Exception e) {
            logger.error("Error finding Fournisseur by id", e);
            throw new RuntimeException("Error finding Fournisseur by id", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            fournisseurRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting Fournisseur", e);
            throw new RuntimeException("Error deleting Fournisseur", e);
        }
    }

    @Override
    public List<FournisseurDTO> search(FournisseurDTO fournisseurDTO) {
        return fournisseurRepository.findAll(FournisseurSpecification.builder().fournisseurDTO(fournisseurDTO).build()).stream().map(fournisseurMapper::toFournisseurDTO).toList();
    }

    @Override
    public List<FournisseurDTO> searchBySupprimerOrArchiver(FournisseurDTO fournisseurDTO) {
        return fournisseurRepository.findAll(FournisseurSpecification.searchBySupprimerOrArchiver(fournisseurDTO)).stream().map(fournisseurMapper::toFournisseurDTO).toList();
    }

    public FournisseurDTO updateNbrOperation(Long id, Integer type) {
        if(null != id && null != type) {
            FournisseurDTO fournisseurDTO = this.findById(id);

            if(null != fournisseurDTO) {
                int numberToAdd = type == 1 ? 1 : -1;
                fournisseurDTO.setNbrOperationClient(fournisseurDTO.getNbrOperationClient() + numberToAdd);
                return this.save(fournisseurDTO);
            } else {
                logger.error("Fournisseur not found with id: {}", id);
                throw new RuntimeException("Fournisseur not found with id: " + id);
            }
        }

        return null;
    }

}
