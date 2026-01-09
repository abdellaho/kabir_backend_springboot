package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.config.enums.StockOperation;
import com.kabir.kabirbackend.config.responses.AchatFactureResponse;
import com.kabir.kabirbackend.config.requests.RequestStockQte;
import com.kabir.kabirbackend.dto.AchatFactureDTO;
import com.kabir.kabirbackend.dto.DetAchatFactureDTO;
import com.kabir.kabirbackend.entities.*;
import com.kabir.kabirbackend.mapper.AchatFactureMapper;
import com.kabir.kabirbackend.mapper.DetAchatFactureMapper;
import com.kabir.kabirbackend.repository.AchatFactureRepository;
import com.kabir.kabirbackend.repository.DetAchatFactureRepository;
import com.kabir.kabirbackend.repository.StockRepository;
import com.kabir.kabirbackend.service.interfaces.IAchatFactureService;
import com.kabir.kabirbackend.specifications.AchatFactureSpecification;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AchatFactureService implements IAchatFactureService {

    private final Logger logger = LoggerFactory.getLogger(AchatFactureService.class);
    private final AchatFactureRepository achatFactureRepository;
    private final AchatFactureMapper achatFactureMapper;
    private final DetAchatFactureRepository detAchatFactureRepository;
    private final DetAchatFactureMapper detAchatFactureMapper;
    private final StockService stockService;
    private final StockRepository stockRepository;

    public AchatFactureService(AchatFactureRepository achatFactureRepository,
                               AchatFactureMapper achatFactureMapper,
                               DetAchatFactureRepository detAchatFactureRepository, 
                               DetAchatFactureMapper detAchatFactureMapper, 
                               StockService stockService, 
                               StockRepository stockRepository
                               ) {
        this.achatFactureRepository = achatFactureRepository;
        this.achatFactureMapper = achatFactureMapper;
        this.detAchatFactureRepository = detAchatFactureRepository;
        this.detAchatFactureMapper = detAchatFactureMapper;
        this.stockService = stockService;
        this.stockRepository = stockRepository;
    }

    @Override
    public AchatFactureDTO save(AchatFactureResponse achatFactureResponse) {
        logger.info("Saving achat facture: {}", achatFactureResponse);
        AchatFactureDTO achatFactureDTO = achatFactureResponse.achatFacture();
        boolean isSave = achatFactureDTO.getId() == null;

        try {
            AchatFacture achatFacture = achatFactureMapper.toAchatFacture(achatFactureDTO);
            achatFactureDTO = achatFactureMapper.toAchatFactureDTO(achatFactureRepository.save(achatFacture));

            enregistrerDetAchatFacture(achatFacture, isSave, achatFactureResponse.detAchatFactures());

            logger.info("Achat facture saved successfully: {}", achatFactureDTO);
        } catch (Exception e) {
            logger.error("Failed to save achat facture: {}", e.getMessage());
        }

        return achatFactureDTO;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting achat facture by id: {}", id);
        try {
            List<DetAchatFacture> detAchatFactureDTOOld = detAchatFactureRepository.findAllByAchatFactureId(id);
            logger.info("List of det Achat Simple to delete: {}", detAchatFactureDTOOld.size());

            if(CollectionUtils.isNotEmpty(detAchatFactureDTOOld)) {
                for(DetAchatFacture detAchatFacture : detAchatFactureDTOOld) {
                    if(null != detAchatFacture.getStock() && null != detAchatFacture.getStock().getId()) {
                        stockService.updateQteStockImport(detAchatFacture.getStock().getId(), new RequestStockQte(detAchatFacture.getQteAcheter(), 1, detAchatFacture.getUniteGratuit()));

                        logger.info("Deleting detail achat facture by id: {}", detAchatFacture.getId());
                        detAchatFactureRepository.deleteById(detAchatFacture.getId());
                    }
                }
            }

            achatFactureRepository.deleteById(id);
            logger.info("Achat facture deleted successfully");
        } catch (Exception e) {
            logger.error("Failed to delete achat facture: {}", e.getMessage());
        }
    }

    @Override
    public AchatFactureResponse findByIdAchatSimpleResponse(Long id) {
        logger.info("Finding achat facture response by id: {}", id);
        try {
            AchatFactureDTO achatFactureDTO = achatFactureMapper.toAchatFactureDTO(achatFactureRepository.findById(id).orElse(null));
            if(null != achatFactureDTO && null != achatFactureDTO.getId()) {
                List<DetAchatFactureDTO> list = detAchatFactureRepository.findAllByAchatFactureId(id).stream()
                        .map(detAchatFactureMapper::toDTO)
                        .toList();

                return new AchatFactureResponse(achatFactureDTO, list);
            } else {
                logger.info("Achat facture not found with ID: {}", id);
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to find achat facture response by id: {}", id);
            return null;
        }
    }

    @Override
    public List<AchatFactureDTO> findAll() {
        logger.info("Finding all achatFactures");
        try {
            List<AchatFacture> achatFactures = achatFactureRepository.findAll();
            return achatFactures.stream().map(achatFactureMapper::toAchatFactureDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all achatFactures", e);
            throw new RuntimeException("Error finding all achatFactures", e);
        }
    }

    @Override
    public AchatFactureDTO findById(Long id) {
        logger.info("Finding achatFacture by id: {}", id);
        try {
            AchatFacture achatFacture = achatFactureRepository.findById(id).orElseThrow(() -> new RuntimeException("AchatFacture not found"));
            return achatFactureMapper.toAchatFactureDTO(achatFacture);
        } catch (Exception e) {
            logger.error("Error finding achatFacture by id: {}", id, e);
            throw new RuntimeException("Error finding achatFacture by id: " + id, e);
        }
    }

    @Override
    public List<AchatFactureDTO> search(AchatFactureDTO achatFactureDTO) {
        logger.info("Searching achatFacture: {}", achatFactureDTO);
        try {
            List<AchatFacture> achatFactures = achatFactureRepository.findAll(AchatFactureSpecification.builder().achatFactureDTO(achatFactureDTO).build());
            return achatFactures.stream().map(achatFactureMapper::toAchatFactureDTO).toList();
        } catch (Exception e) {
            logger.error("Error searching achatFacture: {}", achatFactureDTO, e);
            throw new RuntimeException("Error searching achatFacture: " + achatFactureDTO, e);
        }
    }

    public void enregistrerDetAchatFacture(AchatFacture achatFacture, boolean isSave, List<DetAchatFactureDTO> detAchatFactureDTOs) {
        List<DetAchatFacture> detAchatFactureOld = new ArrayList<>();
        if(!isSave) {
            detAchatFactureOld = detAchatFactureRepository.findAllByAchatFactureId(achatFacture.getId());
        }

        List<DetAchatFacture> listToDelete = getDetStockDepotOldNotAnymore(detAchatFactureOld, detAchatFactureDTOs);
        if(CollectionUtils.isNotEmpty(listToDelete)) {
            logger.info("Deleting det achat facture: {}", listToDelete.size());
            for(DetAchatFacture detAchatFacture : listToDelete) {
                if(null != detAchatFacture.getStock() && null != detAchatFacture.getStock().getId()) {
                    stockService.updateQteStockFacturer(detAchatFacture.getStock().getId(), new RequestStockQte(detAchatFacture.getQteAcheter(), StockOperation.ADD_TO_STOCK.getValue(), detAchatFacture.getUniteGratuit()));
                }

                detAchatFactureRepository.delete(detAchatFacture);
            }
        }

        List<DetAchatFacture> listToSave = detAchatFactureDTOs.stream()
                .map(detAchatFactureDTO -> {
                    DetAchatFacture detAchatFacture = detAchatFactureMapper.toEntity(detAchatFactureDTO);

                    Optional<Stock> optionalStock =  stockRepository.findById(detAchatFactureDTO.getStockId());
                    detAchatFacture.setStock(optionalStock.orElse(null));

                    detAchatFacture.setAchatFacture(achatFacture);

                    return detAchatFacture;
                })
                .toList();
        updateStockAndSaveDetStockDepot(listToSave, detAchatFactureOld);
    }

    public void updateStockAndSaveDetStockDepot(List<DetAchatFacture> listToSave, List<DetAchatFacture> detAchatFactureDTOOld) {
        if(CollectionUtils.isNotEmpty(listToSave)) {
            for(DetAchatFacture detAchatFacture : listToSave) {
                if(null == detAchatFacture.getId()) {
                    stockService.updateQteStockFacturer(detAchatFacture.getStock().getId(), new RequestStockQte(detAchatFacture.getQteAcheter(),StockOperation.DELETE_FROM_STOCK.getValue(), detAchatFacture.getUniteGratuit()));
                } else {
                    int qte = detAchatFacture.getQteAcheter();
                    int operation = StockOperation.ADD_TO_STOCK.getValue();
                    qte = updateQte(detAchatFactureDTOOld, detAchatFacture, qte);

                    if(qte != 0) {
                        if(qte < 0) {
                            qte = Math.abs(qte);
                            operation = StockOperation.DELETE_FROM_STOCK.getValue();
                        }

                        stockService.updateQteStockFacturer(detAchatFacture.getStock().getId(), new RequestStockQte(qte, operation, detAchatFacture.getUniteGratuit()));
                    }
                }

                detAchatFactureRepository.save(detAchatFacture);
            }
        }
    }

    public int updateQte(List<DetAchatFacture> detAchatFactureDTOOld, DetAchatFacture detStockDepot, int qte) {
        if(CollectionUtils.isNotEmpty(detAchatFactureDTOOld)) {
            Optional<DetAchatFacture> optionalDetStockDepotOld = detAchatFactureDTOOld.stream()
                    .filter(detStockDepotOld -> detStockDepotOld.getId().equals(detStockDepot.getId()))
                    .findFirst();

            if(optionalDetStockDepotOld.isPresent()) {
                qte -= optionalDetStockDepotOld.get().getQteAcheter();
            }
        }

        return qte;
    }

    public List<DetAchatFacture> getDetStockDepotOldNotAnymore(List<DetAchatFacture> detAchatFactureDTOOld, List<DetAchatFactureDTO> detAchatSimpleDTOs) {
        if(CollectionUtils.isEmpty(detAchatFactureDTOOld)) {
            return new ArrayList<>();
        }

        return detAchatFactureDTOOld.stream()
                .filter(detStockDepotOld -> detAchatSimpleDTOs.stream()
                        .noneMatch(detAchatFactureDTO -> detAchatFactureDTO.getId() != null && detAchatFactureDTO.getId().equals(detStockDepotOld.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public Integer getLastNumAchatFacture(AchatFactureDTO achatFactureDTO) {
        return achatFactureRepository.findMaxNumAchatFactureInYearDateAF(achatFactureDTO.getDateAF().getYear()).map(l -> l + 1).orElse(1);
    }
}
