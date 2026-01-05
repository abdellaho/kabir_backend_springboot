package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.config.enums.StockOperation;
import com.kabir.kabirbackend.config.enums.TypeOperation;
import com.kabir.kabirbackend.config.requests.RequestStockQte;
import com.kabir.kabirbackend.config.responses.LivraisonResponse;
import com.kabir.kabirbackend.dto.DetLivraisonDTO;
import com.kabir.kabirbackend.dto.LivraisonDTO;
import com.kabir.kabirbackend.entities.*;
import com.kabir.kabirbackend.mapper.DetLivraisonMapper;
import com.kabir.kabirbackend.mapper.LivraisonMapper;
import com.kabir.kabirbackend.repository.*;
import com.kabir.kabirbackend.service.interfaces.ILivraisonService;
import com.kabir.kabirbackend.specifications.LivraisonSpecification;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivraisonService implements ILivraisonService {
    private static final Logger logger = LoggerFactory.getLogger(LivraisonService.class);

    private final LivraisonRepository livraisonRepository;
    private final DetLivraisonRepository detLivraisonRepository;
    private final RepertoireRepository repertoireRepository;
    private final PersonnelRepository personnelRepository;
    private final StockRepository stockRepository;
    private final StockService stockService;
    private final LivraisonMapper livraisonMapper;
    private final DetLivraisonMapper detLivraisonMapper;

    public LivraisonService(LivraisonRepository livraisonRepository, 
                            LivraisonMapper livraisonMapper, 
                            RepertoireRepository repertoireRepository, 
                            DetLivraisonRepository detLivraisonRepository,
                            DetLivraisonMapper detLivraisonMapper,
                            StockRepository stockRepository,
                            PersonnelRepository personnelRepository, 
                            StockService stockService) {
        this.livraisonRepository = livraisonRepository;
        this.repertoireRepository = repertoireRepository;
        this.personnelRepository = personnelRepository;
        this.detLivraisonRepository = detLivraisonRepository;
        this.stockRepository = stockRepository;
        this.stockService = stockService;
        this.livraisonMapper = livraisonMapper;
        this.detLivraisonMapper = detLivraisonMapper;
    }

    @Override
    public LivraisonDTO save(LivraisonResponse livraisonResponse) {
        logger.info("Saving livraison response: {}", livraisonResponse);

        LivraisonDTO livraisonDTO = livraisonResponse.livraison();
        boolean isSave = livraisonDTO.getId() == null;
        Repertoire oldRepertoire = null;
        Personnel personnelOperation = null;
        String repertoireObservation = livraisonDTO.getRepertoireObservation();

        try {
            if(!isSave && null != livraisonDTO.getRepertoireIdOld()) {
                oldRepertoire = repertoireRepository.findById(livraisonDTO.getRepertoireIdOld()).orElse(null);
            }
            if(null != livraisonDTO.getEmployeOperateurId() && livraisonDTO.getEmployeOperateurId() != 0) {
                personnelOperation = personnelRepository.findById(livraisonDTO.getEmployeOperateurId()).orElse(null);
            }

            Optional<Repertoire> optionalRepertoire = repertoireRepository.findById(livraisonResponse.livraison().getRepertoireId());
            Optional<Personnel> optionalPersonnel = personnelRepository.findById(livraisonResponse.livraison().getPersonnelId());

            Livraison livraison = livraisonMapper.toLivraison(livraisonDTO);

            livraison.setEmployeOperateur(personnelOperation);
            livraison.setRepertoire(optionalRepertoire.orElse(null));
            livraison.setPersonnel(optionalPersonnel.orElse(null));

            livraisonDTO = livraisonMapper.toLivraisonDTO(livraisonRepository.save(livraison));

            enregistrerDetLivraison(livraison, isSave, livraisonResponse.detLivraisons());

            logger.info("Livraison repertoire observation: {}", repertoireObservation);
            livraison.getRepertoire().setObservation(repertoireObservation);

            updateRepertoire(oldRepertoire, livraison.getRepertoire());

            logger.info("Stock livraison saved successfully: {}", livraisonDTO);
        } catch (Exception e) {
            logger.error("Failed to save livraison: {}", e.getMessage());
        }

        return livraisonDTO;
    }

    @Override
    public LivraisonDTO findById(Long id) {
        logger.info("Finding livraison by id: {}", id);
        try {
            return livraisonMapper.toLivraisonDTO(livraisonRepository.findById(id).orElse(null));
        } catch (Exception e) {
            logger.error("Error finding livraison by id: {}", id, e);
            throw new RuntimeException("Error finding livraison by id: " + id, e);
        }
    }

    @Override
    public LivraisonResponse findByIdWithDetLivraison(Long id) {
        logger.info("Finding livraison response by id: {}", id);
        try {
            LivraisonDTO livraisonDTO = livraisonMapper.toLivraisonDTO(livraisonRepository.findById(id).orElse(null));
            if(null != livraisonDTO && null != livraisonDTO.getId()) {
                List<DetLivraisonDTO> list = detLivraisonRepository.findByLivraisonId(id).stream()
                        .map(detLivraisonMapper::toDetLivraisonDTO)
                        .toList();

                return new LivraisonResponse(livraisonDTO, list);
            } else {
                logger.info("Livraison not found with ID: {}", id);
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to find livraison response by id: {}", id);
            return null;
        }
    }

    @Override
    public List<LivraisonDTO> findAll() {
        logger.info("Finding all livraisons");
        try {
            List<Livraison> livraisons = livraisonRepository.findAll();
            return livraisons.stream().map(livraisonMapper::toLivraisonDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all livraisons", e);
            throw new RuntimeException("Error finding all livraisons", e);
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting livraison by id: {}", id);
        try {
            Livraison livraison = livraisonRepository.findById(id).orElse(null);
            List<DetLivraison> detLivraisonDTOOld = detLivraisonRepository.findByLivraisonId(id);
            logger.info("List of DetLivraison to delete: {}", detLivraisonDTOOld.size());

            if(CollectionUtils.isNotEmpty(detLivraisonDTOOld)) {
                for(DetLivraison detStockDepot : detLivraisonDTOOld) {
                    if(null != detStockDepot.getStock() && null != detStockDepot.getStock().getId()) {
                        stockService.updateQteStock(detStockDepot.getStock().getId(), new RequestStockQte(detStockDepot.getQteLivrer(), StockOperation.ADD_TO_STOCK.getValue(), null));

                        logger.info("Deleting detail livraison by id: {}", detStockDepot.getId());
                        detLivraisonRepository.deleteById(detStockDepot.getId());
                    }
                }
            }

            livraisonRepository.deleteById(id);
            updateRepertoire(livraison.getRepertoire(), null);
            logger.info("Livraison deleted successfully");
        } catch (Exception e) {
            logger.error("Failed to delete livraison: {}", e.getMessage());
        }
    }

    @Override
    public List<LivraisonDTO> search(LivraisonDTO livraisonDTO) {
        return livraisonRepository.findAll(LivraisonSpecification.builder().livraisonDTO(livraisonDTO).build()).stream().map(livraisonMapper::toLivraisonDTO).toList();
    }

    @Override
    public int getLastNumLivraison(LivraisonDTO livraisonDTO) {
        return livraisonRepository.findMaxNumLivraisonInYearDateBL(livraisonDTO.getDateBl().getYear()).map(l -> l + 1).orElse(1);
    }

    public void deleteStockInDetLivraison(List<DetLivraisonDTO> detLivraisons) {
        if(CollectionUtils.isNotEmpty(detLivraisons)) {
            for (DetLivraisonDTO detLivraisonDTO : detLivraisons) {
                if(null != detLivraisonDTO.getStockId() && detLivraisonDTO.getQteLivrer() != 0) {
                    stockService.updateQteStock(detLivraisonDTO.getStockId(), new RequestStockQte(detLivraisonDTO.getQteLivrer(), 2, null));
                }
            }
        }
    }

    public List<DetLivraison> getDetStockDepotOldNotAnymore(List<DetLivraison> detLivraisonDTOOld, List<DetLivraisonDTO> detLivraisonDTOs) {
        if(CollectionUtils.isEmpty(detLivraisonDTOOld)) {
            return new ArrayList<>();
        }

        return detLivraisonDTOOld.stream()
                .filter(detStockDepotOld -> detLivraisonDTOs.stream()
                        .noneMatch(detLivraisonDTO -> detLivraisonDTO.getId() != null && detLivraisonDTO.getId().equals(detStockDepotOld.getId())))
                .collect(Collectors.toList());
    }

    public void enregistrerDetLivraison(Livraison livraison, boolean isSave, List<DetLivraisonDTO> detLivraisonDTOs) {
        List<DetLivraison> detLivraisonDTOOld = new ArrayList<>();
        if(!isSave) {
            detLivraisonDTOOld = detLivraisonRepository.findByLivraisonId(livraison.getId());
        }

        List<DetLivraison> listToDelete = getDetStockDepotOldNotAnymore(detLivraisonDTOOld, detLivraisonDTOs);
        if(CollectionUtils.isNotEmpty(listToDelete)) {
            logger.info("Deleting det stock depot: {}", listToDelete.size());
            for(DetLivraison detLivraison : listToDelete) {
                if(null != detLivraison.getStock() && null != detLivraison.getStock().getId()) {
                    stockService.updateQteStock(detLivraison.getStock().getId(), new RequestStockQte(detLivraison.getQteLivrer(), StockOperation.ADD_TO_STOCK.getValue(), null));
                }

                detLivraisonRepository.delete(detLivraison);
            }
        }

        List<DetLivraison> listToSave = detLivraisonDTOs.stream()
                .map(detLivraisonDTO -> {
                    DetLivraison detLivraison = detLivraisonMapper.toDetLivraison(detLivraisonDTO);

                    Optional<Stock> optionalStock =  stockRepository.findById(detLivraisonDTO.getStockId());
                    detLivraison.setStock(optionalStock.orElse(null));

                    detLivraison.setLivraison(livraison);

                    return detLivraison;
                })
                .toList();
        updateStockAndSaveDetStockDepot(listToSave, detLivraisonDTOOld);
    }

    public void updateStockAndSaveDetStockDepot(List<DetLivraison> listToSave, List<DetLivraison> detLivraisonDTOOld) {
        if(CollectionUtils.isNotEmpty(listToSave)) {
            for(DetLivraison detLivraison : listToSave) {
                if(null == detLivraison.getId()) {
                    stockService.updateQteStock(detLivraison.getStock().getId(), new RequestStockQte(detLivraison.getQteLivrer(),StockOperation.DELETE_FROM_STOCK.getValue(), null));
                } else {
                    int qte = detLivraison.getQteLivrer();
                    int operation = 1;//Supprimer from qte stock
                    qte = updateQte(detLivraisonDTOOld, detLivraison, qte);

                    if(qte != 0) {
                        if(qte < 0) {
                            qte = Math.abs(qte);
                            operation = 2;//Ajouter from qte stock
                        }
                        stockService.updateQteStock(detLivraison.getStock().getId(), new RequestStockQte(qte, operation, null));
                    }
                }

                detLivraisonRepository.save(detLivraison);
            }
        }
    }

    public int updateQte(List<DetLivraison> detLivraisonDTOOld, DetLivraison detLivraison, int qte) {
        if(CollectionUtils.isNotEmpty(detLivraisonDTOOld)) {
            Optional<DetLivraison> optionalDetStockDepotOld = detLivraisonDTOOld.stream()
                    .filter(detStockDepotOld -> detStockDepotOld.getId().equals(detLivraison.getId()))
                    .findFirst();

            if(optionalDetStockDepotOld.isPresent()) {
                qte -= optionalDetStockDepotOld.get().getQteLivrer();
            }
        }

        return qte;
    }

    public void updateRepertoire(Repertoire oldRepertoire, Repertoire repertoire) {
        logger.info("Updating repertoire Old: {}, New: {}", oldRepertoire, repertoire);
        if(null != oldRepertoire && null != oldRepertoire.getId()) {
            if(null != repertoire && null != repertoire.getId()) {
                logger.info("Updating repertoire Old: {}, New: {}", oldRepertoire, repertoire);
                if(oldRepertoire.getId().equals(repertoire.getId())) {
                    logger.info("Updating repertoire but just the observation: {}", oldRepertoire);
                    updateRepertoireInformation(repertoire, false, TypeOperation.UPDATE, true);
                } else {
                    logger.info("Updating repertoire but just the number of operations: {}", oldRepertoire);
                    updateRepertoireInformation(oldRepertoire, true, TypeOperation.REMOVE, false);
                    logger.info("Updating repertoire (the number of operations and observation): {}", repertoire);
                    updateRepertoireInformation(repertoire, true, TypeOperation.ADD, true);
                }
            } else {
                logger.info("Updating repertoire minus one of the number of operations: {}", oldRepertoire);
                updateRepertoireInformation(oldRepertoire, true, TypeOperation.REMOVE, false);
            }
        } else {
            logger.info("Updating repertoire plus one of the number of operations: {}", repertoire);
            updateRepertoireInformation(repertoire, true, TypeOperation.ADD, true);
        }
    }

    public void updateRepertoireInformation(Repertoire repertoire, boolean updateNBrOperation, TypeOperation typeOperation, boolean updateObservation) {
        if(updateNBrOperation || updateObservation) {
            if(null != repertoire && null != repertoire.getId()) {
                repertoire = repertoireRepository.findById(repertoire.getId()).orElse(null);
                if(null != repertoire && null != repertoire.getId()) {
                    if(updateNBrOperation) {
                        if(typeOperation == TypeOperation.ADD) {
                            repertoire.setNbrOperationClient(repertoire.getNbrOperationClient() + 1);
                        } else {
                            repertoire.setNbrOperationClient(repertoire.getNbrOperationClient() - 1);
                            if(repertoire.getNbrOperationClient() < 0) repertoire.setNbrOperationClient(0);
                        }
                    }

                    if(updateObservation) {
                        repertoire.setObservation(repertoire.getObservation());
                    }

                    repertoireRepository.save(repertoire);
                }
            }
        }
    }
}
