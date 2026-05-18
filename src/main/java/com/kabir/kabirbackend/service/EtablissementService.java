package com.kabir.kabirbackend.service;

import com.kabir.kabirbackend.config.security.encryption.Encryption;
import com.kabir.kabirbackend.dto.EtablissementDTO;
import com.kabir.kabirbackend.entities.Etablissement;
import com.kabir.kabirbackend.mapper.EtablissementMapper;
import com.kabir.kabirbackend.repository.EtablissementRepository;
import com.kabir.kabirbackend.service.interfaces.IEtablissementService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtablissementService implements IEtablissementService {

    private static final Logger logger = LoggerFactory.getLogger(EtablissementService.class);
    private final EtablissementRepository etablissementRepository;
    private final EtablissementMapper etablissementMapper;

    public EtablissementService(EtablissementRepository etablissementRepository, EtablissementMapper etablissementMapper) {
        this.etablissementRepository = etablissementRepository;
        this.etablissementMapper = etablissementMapper;
    }

    @Override
    public EtablissementDTO save(EtablissementDTO etablissementDTO) {
        try {
            logger.info("Saving new EtablissementDTO: {}", etablissementDTO);
            Etablissement etablissement = etablissementMapper.toEtablissement(etablissementDTO);
            etablissement.setPaswordMail("");

            if(StringUtils.isNotBlank(etablissementDTO.getPaswordMailFake())) {
                etablissement.setPaswordMail(Encryption.strEncrypt(etablissementDTO.getPaswordMailFake(), 7));
            }
            etablissement = etablissementRepository.save(etablissement);
            return etablissementMapper.toEtablissementDTO(etablissement);
        } catch (Exception e) {
            logger.error("Error saving EtablissementDTO: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EtablissementDTO> findAll() {
        try {
            logger.info("Finding all EtablissementDTOs");
            List<Etablissement> etablissements = etablissementRepository.findAll();
            etablissements.stream().filter(x -> StringUtils.isNotBlank(x.getPaswordMail())).forEach(x -> x.setPaswordMailFake(Encryption.strDecrypt(x.getPaswordMail(), 7)));
            return etablissements.stream().map(etablissementMapper::toEtablissementDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all EtablissementDTOs: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public EtablissementDTO findById(Long id) {
        try {
            logger.info("Finding EtablissementDTO by id: {}", id);
            Etablissement etablissement = etablissementRepository.findById(id).orElse(null);
            if(null != etablissement && StringUtils.isNotBlank(etablissement.getPaswordMail())) {
                etablissement.setPaswordMailFake(Encryption.strDecrypt(etablissement.getPaswordMailFake(), 7));
            }
            return etablissementMapper.toEtablissementDTO(etablissement);
        } catch (Exception e) {
            logger.error("Error finding EtablissementDTO by id: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            logger.info("Deleting EtablissementDTO by id: {}", id);
            etablissementRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting EtablissementDTO by id: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EtablissementDTO> search(EtablissementDTO etablissementDTO) {
        try {
            logger.info("Searching EtablissementDTOs by criteria: {}", etablissementDTO);
            Etablissement etablissement = etablissementMapper.toEtablissement(etablissementDTO);
            List<Etablissement> etablissements = etablissementRepository.findAll();
            return etablissements.stream().map(etablissementMapper::toEtablissementDTO).toList();
        } catch (Exception e) {
            logger.error("Error searching EtablissementDTOs by criteria: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
