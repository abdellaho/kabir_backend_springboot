package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.config.enums.StockOperation;
import com.kabir.kabirbackend.config.enums.TypeQteToUpdate;
import com.kabir.kabirbackend.config.requests.RequestStockQte;
import com.kabir.kabirbackend.config.responses.AchatEtrangerResponse;
import com.kabir.kabirbackend.dto.AchatEtrangerDTO;
import com.kabir.kabirbackend.dto.DetAchatEtrangerDTO;
import com.kabir.kabirbackend.entities.*;
import com.kabir.kabirbackend.mapper.AchatEtrangerMapper;
import com.kabir.kabirbackend.mapper.DetAchatEtrangerMapper;
import com.kabir.kabirbackend.repository.*;
import com.kabir.kabirbackend.service.interfaces.IAchatEtrangerService;
import com.kabir.kabirbackend.specifications.AchatEtrangerSpecification;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AchatEtrangerService implements IAchatEtrangerService {

    private final Logger logger = LoggerFactory.getLogger(AchatEtrangerService.class);
    private final AchatEtrangerRepository achatEtrangerRepository;
    private final AchatEtrangerMapper achatEtrangerMapper;
    private final DetAchatEtrangerRepository detAchatEtrangerRepository;
    private final DetAchatEtrangerMapper detAchatEtrangerMapper;
    private final StockService stockService;
    private final StockRepository stockRepository;
    private final FournisseurRepository fournisseurRepository;
    private final PersonnelRepository personnelRepository;

    public AchatEtrangerService(AchatEtrangerRepository achatEtrangerRepository,
                               AchatEtrangerMapper achatEtrangerMapper,
                               DetAchatEtrangerRepository detAchatEtrangerRepository,
                               DetAchatEtrangerMapper detAchatEtrangerMapper,
                               StockService stockService,
                               StockRepository stockRepository,
                               FournisseurRepository fournisseurRepository,
                               PersonnelRepository personnelRepository
    ) {
        this.achatEtrangerRepository = achatEtrangerRepository;
        this.achatEtrangerMapper = achatEtrangerMapper;
        this.detAchatEtrangerRepository = detAchatEtrangerRepository;
        this.detAchatEtrangerMapper = detAchatEtrangerMapper;
        this.stockService = stockService;
        this.stockRepository = stockRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.personnelRepository = personnelRepository;
    }

    @Override
    public AchatEtrangerDTO save(AchatEtrangerResponse achatEtrangerResponse) {
        logger.info("Saving achat etranger: {}", achatEtrangerResponse);
        AchatEtrangerDTO achatEtrangerDTO = achatEtrangerResponse.achatEtranger();
        boolean isSave = achatEtrangerDTO.getId() == null;

        try {
            Optional<Fournisseur> optionalFournisseur = null != achatEtrangerDTO.getFournisseurId() ? fournisseurRepository.findById(achatEtrangerDTO.getFournisseurId()) : Optional.empty();
            Optional<Personnel> optionalPersonnel = null != achatEtrangerDTO.getOperateurId() ? personnelRepository.findById(achatEtrangerDTO.getOperateurId()) : Optional.empty();

            AchatEtranger achatEtranger = achatEtrangerMapper.toEntity(achatEtrangerDTO);
            achatEtranger.setFournisseur(optionalFournisseur.orElse(null));
            achatEtranger.setOperateur(optionalPersonnel.orElse(null));

            achatEtrangerDTO = achatEtrangerMapper.toDto(achatEtrangerRepository.save(achatEtranger));

            enregistrerDetAchatEtranger(achatEtranger, isSave, achatEtrangerResponse.detAchatEtrangers());

            logger.info("Achat etranger saved successfully: {}", achatEtrangerDTO);
        } catch (Exception e) {
            logger.error("Failed to save achat etranger: {}", e.getMessage());
        }

        return achatEtrangerDTO;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting achat etranger by id: {}", id);
        try {
            List<DetAchatEtranger> detAchatFactureDTOOld = detAchatEtrangerRepository.findAllByAchatEtrangerId(id);
            logger.info("List of det Achat Simple to delete: {}", detAchatFactureDTOOld.size());

            if(CollectionUtils.isNotEmpty(detAchatFactureDTOOld)) {
                for(DetAchatEtranger detAchatEtranger : detAchatFactureDTOOld) {
                    if(null != detAchatEtranger.getStock() && null != detAchatEtranger.getStock().getId()) {
                        stockService.updateQteStock(detAchatEtranger.getStock().getId(), TypeQteToUpdate.QTE_STOCK_IMPORT, new RequestStockQte(detAchatEtranger.getQteAchat(), StockOperation.ADD_TO_STOCK.getValue(), null));

                        logger.info("Deleting detail achat etranger by id: {}", detAchatEtranger.getId());
                        detAchatEtrangerRepository.deleteById(detAchatEtranger.getId());
                    }
                }
            }

            achatEtrangerRepository.deleteById(id);
            logger.info("Achat etranger deleted successfully");
        } catch (Exception e) {
            logger.error("Failed to delete achat etranger: {}", e.getMessage());
        }
    }

    @Override
    public AchatEtrangerResponse findByIdAchatEtrangerResponse(Long id) {
        logger.info("Finding achat etranger response by id: {}", id);
        try {
            AchatEtrangerDTO achatEtrangerDTO = achatEtrangerMapper.toDto(achatEtrangerRepository.findById(id).orElse(null));
            if(null != achatEtrangerDTO && null != achatEtrangerDTO.getId()) {
                List<DetAchatEtrangerDTO> list = detAchatEtrangerRepository.findAllByAchatEtrangerId(id).stream()
                        .map(detAchatEtrangerMapper::toDto)
                        .toList();

                return new AchatEtrangerResponse(achatEtrangerDTO, list);
            } else {
                logger.info("Achat etranger not found with ID: {}", id);
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to find achat etranger response by id: {}", id);
            return null;
        }
    }

    @Override
    public List<AchatEtrangerDTO> findAll() {
        logger.info("Finding all achatFactures");
        try {
            List<AchatEtranger> achatFactures = achatEtrangerRepository.findAll();
            return achatFactures.stream().map(achatEtrangerMapper::toDto).toList();
        } catch (Exception e) {
            logger.error("Error finding all achatFactures", e);
            throw new RuntimeException("Error finding all achatFactures", e);
        }
    }

    @Override
    public AchatEtrangerDTO findById(Long id) {
        logger.info("Finding achatEtranger by id: {}", id);
        try {
            AchatEtranger achatEtranger = achatEtrangerRepository.findById(id).orElseThrow(() -> new RuntimeException("AchatEtranger not found"));
            return achatEtrangerMapper.toDto(achatEtranger);
        } catch (Exception e) {
            logger.error("Error finding achatEtranger by id: {}", id, e);
            throw new RuntimeException("Error finding achatEtranger by id: " + id, e);
        }
    }

    @Override
    public List<AchatEtrangerDTO> search(AchatEtrangerDTO achatEtrangerDTO) {
        logger.info("Searching achatEtranger: {}", achatEtrangerDTO);
        try {
            List<AchatEtranger> achatFactures = achatEtrangerRepository.findAll(AchatEtrangerSpecification.builder().achatEtrangerDTO(achatEtrangerDTO).build());
            return achatFactures.stream().map(achatEtrangerMapper::toDto).toList();
        } catch (Exception e) {
            logger.error("Error searching achatEtranger: {}", achatEtrangerDTO, e);
            throw new RuntimeException("Error searching achatEtranger: " + achatEtrangerDTO, e);
        }
    }

    public void enregistrerDetAchatEtranger(AchatEtranger achatEtranger, boolean isSave, List<DetAchatEtrangerDTO> detAchatFactureDTOs) {
        List<DetAchatEtranger> detAchatFactureOld = new ArrayList<>();
        if(!isSave) {
            detAchatFactureOld = detAchatEtrangerRepository.findAllByAchatEtrangerId(achatEtranger.getId());
        }

        List<DetAchatEtranger> listToDelete = getDetStockDepotOldNotAnymore(detAchatFactureOld, detAchatFactureDTOs);
        if(CollectionUtils.isNotEmpty(listToDelete)) {
            logger.info("Deleting det achat etranger: {}", listToDelete.size());
            for(DetAchatEtranger detAchatEtranger : listToDelete) {
                if(null != detAchatEtranger.getStock() && null != detAchatEtranger.getStock().getId()) {
                    stockService.updateQteStock(detAchatEtranger.getStock().getId(), TypeQteToUpdate.QTE_STOCK_IMPORT, new RequestStockQte(detAchatEtranger.getQteAchat(), StockOperation.ADD_TO_STOCK.getValue(), null));
                }

                detAchatEtrangerRepository.delete(detAchatEtranger);
            }
        }

        List<DetAchatEtranger> listToSave = detAchatFactureDTOs.stream()
                .map(detAchatEtrangerDTO -> {
                    DetAchatEtranger detAchatEtranger = detAchatEtrangerMapper.toEntity(detAchatEtrangerDTO);

                    Optional<Stock> optionalStock =  stockRepository.findById(detAchatEtrangerDTO.getStockId());
                    detAchatEtranger.setStock(optionalStock.orElse(null));

                    detAchatEtranger.setAchatEtranger(achatEtranger);

                    return detAchatEtranger;
                })
                .toList();
        updateStockAndSaveDetAchatEtranger(listToSave, detAchatFactureOld);
    }

    public void updateStockAndSaveDetAchatEtranger(List<DetAchatEtranger> listToSave, List<DetAchatEtranger> detAchatFactureDTOOld) {
        if(CollectionUtils.isNotEmpty(listToSave)) {
            for(DetAchatEtranger detAchatEtranger : listToSave) {
                if(null == detAchatEtranger.getId()) {
                    stockService.updateQteStock(detAchatEtranger.getStock().getId(), TypeQteToUpdate.QTE_STOCK_IMPORT, new RequestStockQte(detAchatEtranger.getQteAchat(),StockOperation.DELETE_FROM_STOCK.getValue(), null));
                } else {
                    int qte = detAchatEtranger.getQteAchat();
                    int operation = StockOperation.ADD_TO_STOCK.getValue();
                    qte = updateQte(detAchatFactureDTOOld, detAchatEtranger, qte);

                    if(qte != 0) {
                        if(qte < 0) {
                            qte = Math.abs(qte);
                            operation = StockOperation.DELETE_FROM_STOCK.getValue();
                        }

                        stockService.updateQteStock(detAchatEtranger.getStock().getId(), TypeQteToUpdate.QTE_STOCK_IMPORT, new RequestStockQte(qte, operation, null));
                    }
                }

                detAchatEtrangerRepository.save(detAchatEtranger);
            }
        }
    }

    public int updateQte(List<DetAchatEtranger> detAchatFactureDTOOld, DetAchatEtranger detStockDepot, int qte) {
        if(CollectionUtils.isNotEmpty(detAchatFactureDTOOld)) {
            Optional<DetAchatEtranger> optionalDetStockDepotOld = detAchatFactureDTOOld.stream()
                    .filter(detStockDepotOld -> detStockDepotOld.getId().equals(detStockDepot.getId()))
                    .findFirst();

            if(optionalDetStockDepotOld.isPresent()) {
                qte -= optionalDetStockDepotOld.get().getQteAchat();
            }
        }

        return qte;
    }

    public List<DetAchatEtranger> getDetStockDepotOldNotAnymore(List<DetAchatEtranger> detAchatFactureDTOOld, List<DetAchatEtrangerDTO> detAchatSimpleDTOs) {
        if(CollectionUtils.isEmpty(detAchatFactureDTOOld)) {
            return new ArrayList<>();
        }

        return detAchatFactureDTOOld.stream()
                .filter(detStockDepotOld -> detAchatSimpleDTOs.stream()
                        .noneMatch(detAchatEtrangerDTO -> detAchatEtrangerDTO.getId() != null && detAchatEtrangerDTO.getId().equals(detStockDepotOld.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public Integer getLastNumAchatEtranger(AchatEtrangerDTO achatEtrangerDTO) {
        return achatEtrangerRepository.findMaxNumAchatEtrangerInYearDateFacture(achatEtrangerDTO.getDateFacture().getYear()).map(l -> l + 1).orElse(1);
    }

    @Override
    public boolean exist(AchatEtrangerDTO achatEtrangerDTO) {
        List<AchatEtranger> list = achatEtrangerRepository.findAll(AchatEtrangerSpecification.builder().achatEtrangerDTO(achatEtrangerDTO).build());
        return CollectionUtils.isNotEmpty(list);
    }
}
