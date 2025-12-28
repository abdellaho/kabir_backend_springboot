package com.kabir.kabirbackend.service;

import com.kabir.kabirbackend.config.requests.RequestStockQte;
import com.kabir.kabirbackend.config.responses.AchatSimpleResponse;
import com.kabir.kabirbackend.dto.DetAchatSimpleDTO;
import com.kabir.kabirbackend.dto.AchatSimpleDTO;
import com.kabir.kabirbackend.entities.DetAchatSimple;
import com.kabir.kabirbackend.entities.Fournisseur;
import com.kabir.kabirbackend.entities.Stock;
import com.kabir.kabirbackend.entities.AchatSimple;
import com.kabir.kabirbackend.mapper.AchatSimpleMapper;
import com.kabir.kabirbackend.mapper.DetAchatSimpleMapper;
import com.kabir.kabirbackend.repository.*;
import com.kabir.kabirbackend.service.interfaces.IAchatSimpleService;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class AchatSimpleService implements IAchatSimpleService {

    private final Logger logger = LoggerFactory.getLogger(AchatSimpleService.class);

    private final AchatSimpleRepository achatSimpleRepository;
    private final DetAchatSimpleRepository detAchatSimpleRepository;
    private final AchatSimpleMapper achatSimpleMapper;
    private final DetAchatSimpleMapper detAchatSimpleMapper;
    private final StockService stockService;
    private final StockRepository stockRepository;
    private final FournisseurRepository fournisseurRepository;


    public AchatSimpleService(
            AchatSimpleRepository achatSimpleRepository,
            DetAchatSimpleRepository detAchatSimpleRepository,
            AchatSimpleMapper achatSimpleMapper,
            DetAchatSimpleMapper detAchatSimpleMapper,
            StockService stockService,
            StockRepository stockRepository,
            FournisseurRepository fournisseurRepository
    ) {
        this.achatSimpleMapper = achatSimpleMapper;
        this.detAchatSimpleMapper = detAchatSimpleMapper;
        this.stockService = stockService;
        this.stockRepository = stockRepository;
        this.achatSimpleRepository = achatSimpleRepository;
        this.detAchatSimpleRepository = detAchatSimpleRepository;
        this.fournisseurRepository = fournisseurRepository;
    }

    @Override
    public AchatSimpleDTO save(AchatSimpleResponse achatSimpleResponse) {
        logger.info("Saving achat simple: {}", achatSimpleResponse);
        AchatSimpleDTO achatSimpleDTO = achatSimpleResponse.achatSimple();
        boolean isSave = achatSimpleDTO.getId() == null;
        try {
            Optional<Fournisseur> optionalFournisseur = fournisseurRepository.findById(achatSimpleDTO.getFournisseurId());
            AchatSimple achatSimple = achatSimpleMapper.toEntity(achatSimpleDTO);
            achatSimple.setFournisseur(optionalFournisseur.orElse(null));
            achatSimpleDTO = achatSimpleMapper.toDTO(achatSimpleRepository.save(achatSimple));

            enregistrerDetAchatSimple(achatSimple, isSave, achatSimpleResponse.detAchatSimples());

            logger.info("Achat simple saved successfully: {}", achatSimpleDTO);
        } catch (Exception e) {
            logger.error("Failed to save achat simple: {}", e.getMessage());
        }
        return achatSimpleDTO;
    }

    @Override
    public AchatSimpleDTO findById(Long id) {
        logger.info("Finding achat simple by id: {}", id);
        AchatSimpleDTO achatSimpleDTO = achatSimpleMapper.toDTO(achatSimpleRepository.findById(id).orElse(null));
        logger.info("Achat simple found: {}", achatSimpleDTO);
        return achatSimpleDTO;
    }

    @Override
    public AchatSimpleResponse findByIdAchatSimpleResponse(Long id) {
        logger.info("Finding achat simple response by id: {}", id);
        try {
            AchatSimpleDTO achatSimpleDTO = achatSimpleMapper.toDTO(achatSimpleRepository.findById(id).orElse(null));
            if(null != achatSimpleDTO && null != achatSimpleDTO.getId()) {
                List<DetAchatSimpleDTO> list = detAchatSimpleRepository.findAllByAchatSimpleId(id).stream()
                        .map(detAchatSimpleMapper::toDTO)
                        .toList();

                return new AchatSimpleResponse(achatSimpleDTO, list);
            } else {
                logger.info("Achat simple not found with ID: {}", id);
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to find achat simple response by id: {}", id);
            return null;
        }
    }

    @Override
    public List<AchatSimpleDTO> findAll() {
        logger.info("Finding all achat simples");
        List<AchatSimpleDTO> achatSimpleDTOs = achatSimpleRepository.findAll(Sort.by(Sort.Direction.DESC, "dateOperation")).stream()
                .map(achatSimpleMapper::toDTO)
                .toList();
        logger.info("Achat simples found: {}", achatSimpleDTOs.size());
        return achatSimpleDTOs;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting achat simple by id: {}", id);
        try {
            List<DetAchatSimple> detAchatSimpleDTOOld = detAchatSimpleRepository.findAllByAchatSimpleId(id);
            logger.info("List of det Achat Simple to delete: {}", detAchatSimpleDTOOld.size());

            if(CollectionUtils.isNotEmpty(detAchatSimpleDTOOld)) {
                for(DetAchatSimple detAchatSimple : detAchatSimpleDTOOld) {
                    if(null != detAchatSimple.getStock() && null != detAchatSimple.getStock().getId()) {
                        stockService.updateQteStock(detAchatSimple.getStock().getId(), new RequestStockQte(detAchatSimple.getQte(), 1, detAchatSimple.getUniteGratuite()));

                        logger.info("Deleting detail achat simple by id: {}", detAchatSimple.getId());
                        detAchatSimpleRepository.deleteById(detAchatSimple.getId());
                    }
                }
            }

            achatSimpleRepository.deleteById(id);
            logger.info("Achat simple deleted successfully");
        } catch (Exception e) {
            logger.error("Failed to delete achat simple: {}", e.getMessage());
        }
    }

    @Override
    public List<AchatSimpleDTO> search(AchatSimpleDTO stockDepot) {
        return this.findAll();
    }


    public List<DetAchatSimple> getDetStockDepotOldNotAnymore(List<DetAchatSimple> detAchatSimpleDTOOld, List<DetAchatSimpleDTO> detAchatSimpleDTOs) {
        if(CollectionUtils.isEmpty(detAchatSimpleDTOOld)) {
            return new ArrayList<>();
        }

        return detAchatSimpleDTOOld.stream()
                .filter(detStockDepotOld -> detAchatSimpleDTOs.stream()
                        .noneMatch(detAchatSimpleDTO -> detAchatSimpleDTO.getId() != null && detAchatSimpleDTO.getId().equals(detStockDepotOld.getId())))
                .collect(Collectors.toList());
    }

    public void enregistrerDetAchatSimple(AchatSimple achatSimple, boolean isSave, List<DetAchatSimpleDTO> detAchatSimpleDTOs) {
        List<DetAchatSimple> detAchatSimpleOld = new ArrayList<>();
        if(!isSave) {
            detAchatSimpleOld = detAchatSimpleRepository.findAllByAchatSimpleId(achatSimple.getId());
        }

        List<DetAchatSimple> listToDelete = getDetStockDepotOldNotAnymore(detAchatSimpleOld, detAchatSimpleDTOs);
        if(CollectionUtils.isNotEmpty(listToDelete)) {
            logger.info("Deleting det achat simple: {}", listToDelete.size());
            for(DetAchatSimple detAchatSimple : listToDelete) {
                if(null != detAchatSimple.getStock() && null != detAchatSimple.getStock().getId()) {
                    stockService.updateQteStockImport(detAchatSimple.getStock().getId(), new RequestStockQte(detAchatSimple.getQte(), 1, detAchatSimple.getUniteGratuite()));
                }

                detAchatSimpleRepository.delete(detAchatSimple);
            }
        }

        List<DetAchatSimple> listToSave = detAchatSimpleDTOs.stream()
                .map(detAchatSimpleDTO -> {
                    DetAchatSimple detAchatSimple = detAchatSimpleMapper.toEntity(detAchatSimpleDTO);

                    Optional<Stock> optionalStock =  stockRepository.findById(detAchatSimpleDTO.getStockId());
                    detAchatSimple.setStock(optionalStock.orElse(null));

                    detAchatSimple.setAchatSimple(achatSimple);

                    return detAchatSimple;
                })
                .toList();
        updateStockAndSaveDetStockDepot(listToSave, detAchatSimpleOld);
    }

    public void updateStockAndSaveDetStockDepot(List<DetAchatSimple> listToSave, List<DetAchatSimple> detAchatSimpleDTOOld) {
        if(CollectionUtils.isNotEmpty(listToSave)) {
            for(DetAchatSimple detAchatSimple : listToSave) {
                if(null == detAchatSimple.getId()) {
                    stockService.updateQteStock(detAchatSimple.getStock().getId(), new RequestStockQte(detAchatSimple.getQte(),2, detAchatSimple.getUniteGratuite()));
                } else {
                    int qte = detAchatSimple.getQte();
                    int operation = 2;
                    qte = updateQte(detAchatSimpleDTOOld, detAchatSimple, qte);

                    if(qte != 0) {
                        if(qte < 0) {
                            qte = Math.abs(qte);
                            operation = 1;
                        }
                        stockService.updateQteStockImport(detAchatSimple.getStock().getId(), new RequestStockQte(qte, operation, detAchatSimple.getUniteGratuite()));
                    }
                }

                detAchatSimpleRepository.save(detAchatSimple);
            }
        }
    }

    public int updateQte(List<DetAchatSimple> detAchatSimpleDTOOld, DetAchatSimple detStockDepot, int qte) {
        if(CollectionUtils.isNotEmpty(detAchatSimpleDTOOld)) {
            Optional<DetAchatSimple> optionalDetStockDepotOld = detAchatSimpleDTOOld.stream()
                    .filter(detStockDepotOld -> detStockDepotOld.getId().equals(detStockDepot.getId()))
                    .findFirst();

            if(optionalDetStockDepotOld.isPresent()) {
                qte -= optionalDetStockDepotOld.get().getQte();
            }
        }

        return qte;
    }

}
