package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.config.enums.StockOperation;
import com.kabir.kabirbackend.config.enums.TypeQteToUpdate;
import com.kabir.kabirbackend.config.requests.RequestStockQte;
import com.kabir.kabirbackend.config.responses.FactureResponse;
import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.DetFactureDTO;
import com.kabir.kabirbackend.dto.FactureDTO;
import com.kabir.kabirbackend.entities.*;
import com.kabir.kabirbackend.mapper.DetFactureMapper;
import com.kabir.kabirbackend.mapper.FactureMapper;
import com.kabir.kabirbackend.repository.*;
import com.kabir.kabirbackend.service.interfaces.IFactureService;
import com.kabir.kabirbackend.specifications.FactureSpecification;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FactureService implements IFactureService {

    private final Logger logger = LoggerFactory.getLogger(FactureService.class);
    private final FactureRepository factureRepository;
    private final DetFactureRepository detFactureRepository;
    private final FactureMapper factureMapper;
    private final DetFactureMapper detFactureMapper;
    private final StockService stockService;
    private final StockRepository stockRepository;
    private final PersonnelRepository personnelRepository;
    private final RepertoireRepository repertoireRepository;
    

    public FactureService(FactureRepository factureRepository,
                          DetFactureRepository detFactureRepository,
                          FactureMapper factureMapper,
                          DetFactureMapper detFactureMapper,
                            StockRepository stockRepository,
                            PersonnelRepository personnelRepository,
                            RepertoireRepository repertoireRepository,
                            StockService stockService) {
        this.factureRepository = factureRepository;
        this.factureMapper = factureMapper;
        this.detFactureRepository = detFactureRepository;
        this.detFactureMapper = detFactureMapper;
        this.stockService = stockService;
        this.stockRepository = stockRepository;
        this.personnelRepository = personnelRepository;
        this.repertoireRepository = repertoireRepository;
    }

    @Override
    public List<FactureDTO> findAll() {
        logger.info("Finding all factures");
        try {
            List<Facture> factures = factureRepository.findAll();
            return factures.stream().map(factureMapper::toFactureDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all factures", e);
            throw new RuntimeException("Error finding all factures", e);
        }
    }

    @Override
    public FactureDTO findById(Long id) {
        logger.info("Finding facture by id: {}", id);
        try {
            Facture facture = factureRepository.findById(id).orElseThrow(() -> new RuntimeException("Facture not found"));
            return factureMapper.toFactureDTO(facture);
        } catch (Exception e) {
            logger.error("Error finding facture by id: {}", id, e);
            throw new RuntimeException("Error finding facture by id: " + id, e);
        }
    }

    @Override
    public FactureResponse findByIdFactureResponse(Long id) {
        logger.info("Finding facture response by id: {}", id);
        try {
            if(null != id) {
                FactureDTO factureDTO = factureRepository.findById(id).map(factureMapper::toFactureDTO).orElseThrow(() -> new RuntimeException("Facture not found"));
                List<DetFactureDTO>  list = detFactureRepository.findByFactureId(id).stream().map(detFactureMapper::toDTO).toList();
                return new FactureResponse(factureDTO, list);
            } else {
                throw new RuntimeException("Facture id cannot be null");
            }
        } catch (Exception e) {
            logger.error("Error finding facture response by id: {}", id, e);
            throw new RuntimeException("Error finding facture response by id: " + id, e);
        }
    }

    @Override
    public FactureDTO save(FactureResponse factureResponse) {
        logger.info("Saving facture response: {}", factureResponse);

        FactureDTO factureDTO = factureResponse.facture();
        boolean isSave = factureDTO.getId() == null;
        Personnel personnelOperation = null;

        try {
            if(null != factureDTO.getEmployeOperateurId() && factureDTO.getEmployeOperateurId() != 0) {
                personnelOperation = personnelRepository.findById(factureDTO.getEmployeOperateurId()).orElse(null);
            }

            Optional<Repertoire> optionalRepertoire = repertoireRepository.findById(factureResponse.facture().getRepertoireId());
            Optional<Personnel> optionalPersonnel = personnelRepository.findById(factureResponse.facture().getPersonnelId());

            Facture facture = factureMapper.toFacture(factureDTO);

            facture.setEmployeOperateur(personnelOperation);
            facture.setRepertoire(optionalRepertoire.orElse(null));
            facture.setPersonnel(optionalPersonnel.orElse(null));

            factureDTO = factureMapper.toFactureDTO(factureRepository.save(facture));

            enregistrerDetFacture(facture, isSave, factureResponse.detFactures());

            logger.info("Stock facture saved successfully: {}", factureDTO);
        } catch (Exception e) {
            logger.error("Failed to save facture: {}", e.getMessage());
        }

        return factureDTO;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting facture by id: {}", id);
        try {
            List<DetFacture> detFactureDTOOld = detFactureRepository.findByFactureId(id);
            logger.info("List of DetFacture to delete: {}", detFactureDTOOld.size());

            if(CollectionUtils.isNotEmpty(detFactureDTOOld)) {
                for(DetFacture detStockDepot : detFactureDTOOld) {
                    if(null != detStockDepot.getStock() && null != detStockDepot.getStock().getId()) {
                        stockService.updateQteStock(detStockDepot.getStock().getId(), TypeQteToUpdate.QTE_STOCK_FACTURER, new RequestStockQte(detStockDepot.getQteFacturer(), StockOperation.ADD_TO_STOCK.getValue(), null));

                        logger.info("Deleting detail facture by id: {}", detStockDepot.getId());
                        detFactureRepository.deleteById(detStockDepot.getId());
                    }
                }
            }

            factureRepository.deleteById(id);
            logger.info("Facture deleted successfully");
        } catch (Exception e) {
            logger.error("Failed to delete facture: {}", e.getMessage());
        }
    }

    @Override
    public List<FactureDTO> search(FactureDTO factureDTO) {
        logger.info("Searching factures: {}", factureDTO);
        return factureRepository.findAll(FactureSpecification.builder().factureDTO(factureDTO).build()).stream().map(factureMapper::toFactureDTO).toList();
    }

    @Override
    public int getLastNumFacture(FactureDTO factureDTO) {
        logger.info("Getting last num facture");
        return factureRepository.findMaxNumFactureInYearDateBL(factureDTO.getDateBF().getYear()).map(l -> l + 1).orElse(1);
    }

    public List<DetFacture> getDetStockDepotOldNotAnymore(List<DetFacture> detFactureDTOOld, List<DetFactureDTO> detFactureDTOs) {
        if(CollectionUtils.isEmpty(detFactureDTOOld)) {
            return new ArrayList<>();
        }

        return detFactureDTOOld.stream()
                .filter(detStockDepotOld -> detFactureDTOs.stream()
                        .noneMatch(detStockDepotDTO -> detStockDepotDTO.getId() != null && detStockDepotDTO.getId().equals(detStockDepotOld.getId())))
                .collect(Collectors.toList());
    }

    public void enregistrerDetFacture(Facture facture, boolean isSave, List<DetFactureDTO> detFactureDTOs) {
        List<DetFacture> detFactureDTOOld = new ArrayList<>();
        if(!isSave) {
            detFactureDTOOld = detFactureRepository.findByFactureId(facture.getId());
        }

        List<DetFacture> listToDelete = getDetStockDepotOldNotAnymore(detFactureDTOOld, detFactureDTOs);
        if(CollectionUtils.isNotEmpty(listToDelete)) {
            logger.info("Deleting det stock depot: {}", listToDelete.size());
            for(DetFacture detFacture : listToDelete) {
                if(null != detFacture.getStock() && null != detFacture.getStock().getId()) {
                    stockService.updateQteStock(detFacture.getStock().getId(), TypeQteToUpdate.QTE_STOCK_FACTURER, new RequestStockQte(detFacture.getQteFacturer(), 1, null));
                }

                detFactureRepository.delete(detFacture);
            }
        }

        List<DetFacture> listToSave = detFactureDTOs.stream()
                .map(detFactureDTO -> {
                    DetFacture detFacture = detFactureMapper.toEntity(detFactureDTO);

                    Optional<Stock> optionalStock =  stockRepository.findById(detFactureDTO.getStockId());
                    detFacture.setStock(optionalStock.orElse(null));

                    detFacture.setFacture(facture);

                    return detFacture;
                })
                .toList();
        updateStockAndSaveDetStockDepot(listToSave, detFactureDTOOld);
    }

    public void updateStockAndSaveDetStockDepot(List<DetFacture> listToSave, List<DetFacture> detFactureDTOOld) {
        if(CollectionUtils.isNotEmpty(listToSave)) {
            for(DetFacture detFacture : listToSave) {
                if(null == detFacture.getId()) {
                    stockService.updateQteStock(detFacture.getStock().getId(), TypeQteToUpdate.QTE_STOCK_FACTURER, new RequestStockQte(detFacture.getQteFacturer(),2, null));
                } else {
                    int qte = detFacture.getQteFacturer();
                    int operation = 2;
                    qte = updateQte(detFactureDTOOld, detFacture, qte);

                    if(qte != 0) {
                        if(qte < 0) {
                            qte = Math.abs(qte);
                            operation = 1;
                        }
                        stockService.updateQteStock(detFacture.getStock().getId(), TypeQteToUpdate.QTE_STOCK_FACTURER, new RequestStockQte(qte, operation, null));
                    }
                }

                detFactureRepository.save(detFacture);
            }
        }
    }

    public int updateQte(List<DetFacture> detFactureDTOOld, DetFacture detFacture, int qte) {
        if(CollectionUtils.isNotEmpty(detFactureDTOOld)) {
            Optional<DetFacture> optionalDetStockDepotOld = detFactureDTOOld.stream()
                    .filter(detStockDepotOld -> detStockDepotOld.getId().equals(detFacture.getId()))
                    .findFirst();

            if(optionalDetStockDepotOld.isPresent()) {
                qte -= optionalDetStockDepotOld.get().getQteFacturer();
            }
        }

        return qte;
    }

    public List<FactureDTO> searchByCommon(CommonSearchModel commonSearchModel) {
        logger.info("Searching facture by common: {}", commonSearchModel);
        return factureRepository.findAll(FactureSpecification.builder().build().searchByCommon(commonSearchModel)).stream().map(factureMapper::toFactureDTO).toList();
    }
}
