package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.config.requests.ComptaRequest;
import com.kabir.kabirbackend.config.responses.ComptaResponse;
import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.config.searchEntities.ComptaSearch;
import com.kabir.kabirbackend.dto.ComptaDTO;
import com.kabir.kabirbackend.entities.Compta;
import com.kabir.kabirbackend.mapper.ComptaMapper;
import com.kabir.kabirbackend.repository.AchatFactureRepository;
import com.kabir.kabirbackend.repository.ComptaRepository;
import com.kabir.kabirbackend.repository.FactureRepository;
import com.kabir.kabirbackend.service.interfaces.IComptaService;
import com.kabir.kabirbackend.specifications.ComptaSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComptaService implements IComptaService {

    private static final Logger logger = LoggerFactory.getLogger(ComptaService.class);
    private final ComptaRepository comptaRepository;
    private final ComptaMapper comptaMapper;
    private final FactureRepository factureRepository;
    private final AchatFactureRepository achatFactureRepository;

    public ComptaService(ComptaRepository comptaRepository,
                         ComptaMapper comptaMapper,
                         FactureRepository factureRepository,
                         AchatFactureRepository achatFactureRepository
    ) {
        this.comptaRepository = comptaRepository;
        this.comptaMapper = comptaMapper;
        this.factureRepository = factureRepository;
        this.achatFactureRepository = achatFactureRepository;
    }

    @Override
    public ComptaDTO save(ComptaDTO comptaDTO) {
        logger.info("Saving compta: {}", comptaDTO);
        try {
            Compta compta = comptaMapper.toCompta(comptaDTO);
            return comptaMapper.toComptaDTO(comptaRepository.save(compta));
        } catch (Exception e) {
            logger.error("Error saving compta", e);
            throw new RuntimeException("Error saving compta", e);
        }
    }


    @Override
    public ComptaDTO findById(Long id) {
        logger.info("Getting compta by id: {}", id);
        Compta compta = comptaRepository.findById(id).orElseThrow(() -> new RuntimeException("Compta not found"));
        return comptaMapper.toComptaDTO(compta);
    }

    @Override
    public List<ComptaDTO> findAll() {
        logger.info("Getting all absences");
        return comptaRepository.findAll().stream()
                .map(comptaMapper::toComptaDTO)
                .toList();
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting compta with id: {}", id);
        comptaRepository.deleteById(id);
    }

    @Override
    public List<ComptaDTO> search(ComptaSearch comptaSearch) {
        logger.info("Searching compta by Compta Search builder {}", comptaSearch);
        return comptaRepository.findAll(ComptaSpecification.builder().comptaSearch(comptaSearch).build()).stream()
                .map(comptaMapper::toComptaDTO)
                .toList();
    }

    @Override
    public ComptaDTO getLastCompta() {
        logger.info("Getting last compta");
        return comptaRepository.getLastCompta().map(comptaMapper::toComptaDTO).orElse(null);
    }

    @Override
    public ComptaResponse getGlobalSums(ComptaRequest comptaRequest) {
        logger.info("Getting global sums");
        Double sum = achatFactureRepository.getSumMantantTotHTVA(comptaRequest.dateDebut(), comptaRequest.dateFin());

        ComptaResponse comptaResponse = factureRepository.getGlobalSums(comptaRequest.dateDebut(), comptaRequest.dateFin());

        comptaResponse.setAchatTvaSum(null != sum ? sum : 0.0);
        comptaResponse.setTva7Sum(null != comptaResponse.getTva7Sum() ? comptaResponse.getTva7Sum() : 0.0);
        comptaResponse.setTva20Sum(null != comptaResponse.getTva20Sum() ? comptaResponse.getTva20Sum() : 0.0);

        return comptaResponse;
    }

    @Override
    public boolean checkIsLast(ComptaSearch comptaSearch) {
        logger.info("Checking if compta is last");
        return comptaRepository.findAll(ComptaSpecification.builder().build().isLast(comptaSearch)).isEmpty();
    }

    @Override
    public List<ComptaDTO> searchByCommon(CommonSearchModel commonSearchModel) {
        logger.info("Searching compta by common: {}", commonSearchModel);
        return comptaRepository.findAll(ComptaSpecification.builder().build().searchByCommon(commonSearchModel)).stream()
                .map(comptaMapper::toComptaDTO)
                .toList();
    }
}
