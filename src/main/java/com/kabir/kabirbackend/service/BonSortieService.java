package com.kabir.kabirbackend.service;

import com.kabir.kabirbackend.config.enums.TypeQteToUpdate;
import com.kabir.kabirbackend.config.requests.RequestStockQte;
import com.kabir.kabirbackend.config.responses.BonSortieResponse;
import com.kabir.kabirbackend.dto.DetailBonSortieDTO;
import com.kabir.kabirbackend.dto.BonSortieDTO;
import com.kabir.kabirbackend.dto.FactureDTO;
import com.kabir.kabirbackend.dto.LivraisonDTO;
import com.kabir.kabirbackend.entities.*;
import com.kabir.kabirbackend.mapper.DetailBonSortieMapper;
import com.kabir.kabirbackend.mapper.BonSortieMapper;
import com.kabir.kabirbackend.repository.*;
import com.kabir.kabirbackend.service.interfaces.IBonSortieService;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class BonSortieService implements IBonSortieService {

    private final Logger logger = LoggerFactory.getLogger(BonSortieService.class);

    private final BonSortieRepository bonSortieRepository;
    private final DetailBonSortieRepository detailBonSortieRepository;
    private final BonSortieMapper bonSortieMapper;
    private final DetailBonSortieMapper detailBonSortieMapper;
    private final StockService stockService;
    private final StockRepository stockRepository;
    private final RepertoireRepository repertoireRepository;
    private final PersonnelRepository personnelRepository;


    public BonSortieService(
            BonSortieRepository bonSortieRepository,
            DetailBonSortieRepository detailBonSortieRepository,
            BonSortieMapper bonSortieMapper,
            DetailBonSortieMapper detailBonSortieMapper,
            StockService stockService,
            StockRepository stockRepository,
            RepertoireRepository repertoireRepository,
            PersonnelRepository personnelRepository
    ) {
        this.bonSortieRepository = bonSortieRepository;
        this.bonSortieMapper = bonSortieMapper;
        this.detailBonSortieMapper = detailBonSortieMapper;
        this.stockService = stockService;
        this.stockRepository = stockRepository;
        this.detailBonSortieRepository = detailBonSortieRepository;
        this.repertoireRepository = repertoireRepository;
        this.personnelRepository = personnelRepository;
    }

    @Override
    public BonSortieDTO save(BonSortieResponse bonSortieResponse) {
        logger.info("Saving bon sortie Response: {}", bonSortieResponse);
        BonSortieDTO bonSortieDTO = bonSortieResponse.bonSortie();
        boolean isSave = bonSortieDTO.getId() == null;
        try {
            Optional<Repertoire> optionalRepertoire = repertoireRepository.findById(bonSortieResponse.bonSortie().getRepertoireId());
            Optional<Personnel> optionalPersonnel = personnelRepository.findById(bonSortieResponse.bonSortie().getPersonnelId());

            BonSortie bonSortie = bonSortieMapper.toEntity(bonSortieDTO);
            bonSortie.setRepertoire(optionalRepertoire.orElse(null));
            bonSortie.setPersonnel(optionalPersonnel.orElse(null));
            bonSortieDTO = bonSortieMapper.toDTO(bonSortieRepository.save(bonSortie));

            enregistrerDetBonSortie(bonSortie, isSave, bonSortieResponse.detailBonSorties());

            logger.info("Bon sortie saved successfully: {}", bonSortieDTO);
        } catch (Exception e) {
            logger.error("Failed to save bon sortie: {}", e.getMessage());
        }
        return bonSortieDTO;
    }

    @Override
    public BonSortieDTO findById(Long id) {
        logger.info("Finding bon sortie by id: {}", id);
        BonSortieDTO bonSortieDTO = bonSortieMapper.toDTO(bonSortieRepository.findById(id).orElse(null));
        logger.info("Bon sortie found: {}", bonSortieDTO);
        return bonSortieDTO;
    }

    @Override
    public BonSortieResponse findByIdBonSortieResponse(Long id) {
        logger.info("Finding bon Sortie response by id: {}", id);
        try {
            BonSortieDTO bonSortieDTO = bonSortieMapper.toDTO(bonSortieRepository.findById(id).orElse(null));
            if(null != bonSortieDTO && null != bonSortieDTO.getId()) {
                List<DetailBonSortieDTO> list = detailBonSortieRepository.findAllByBonSortieId(id).stream()
                    .map(detailBonSortieMapper::toDTO)
                    .toList();

                return new BonSortieResponse(bonSortieDTO, list);
            } else {
                logger.info("Bon sortie not found with ID: {}", id);
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to find bon sortie response by id: {}", id);
            return null;
        }
    }

    @Override
    public List<BonSortieDTO> findAll() {
        logger.info("Finding all bon sorties");
        List<BonSortieDTO> stockDepotDTOs = bonSortieRepository.findAll(Sort.by(Sort.Direction.DESC, "dateOperation")).stream()
                .map(bonSortieMapper::toDTO)
                .collect(Collectors.toList());
        logger.info("Bon sorties found: {}", stockDepotDTOs.size());
        return stockDepotDTOs;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting bon sortie by id: {}", id);
        try {
            List<DetailBonSortie> detailBonSortieDTOOld = detailBonSortieRepository.findAllByBonSortieId(id);
            logger.info("List of Detail Bon Sortie to delete: {}", detailBonSortieDTOOld.size());

            if(CollectionUtils.isNotEmpty(detailBonSortieDTOOld)) {
                for(DetailBonSortie detStockDepot : detailBonSortieDTOOld) {
                    if(null != detStockDepot.getStock() && null != detStockDepot.getStock().getId()) {
                        stockService.updateQteStock(detStockDepot.getStock().getId(), TypeQteToUpdate.QTE_STOCK, new RequestStockQte(detStockDepot.getQteSortie(), 1, null));

                        logger.info("Deleting detail bon sortie by id: {}", detStockDepot.getId());
                        detailBonSortieRepository.deleteById(detStockDepot.getId());
                    }
                }
            }

            bonSortieRepository.deleteById(id);
            logger.info("Bon sortie deleted successfully");
        } catch (Exception e) {
            logger.error("Failed to delete bon sortie: {}", e.getMessage());
        }
    }

    @Override
    public List<BonSortieDTO> search(BonSortieDTO bonSortieDTO) {
        return this.findAll();
    }


    public List<DetailBonSortie> getDetStockDepotOldNotAnymore(List<DetailBonSortie> detailBonSortieDTOOld, List<DetailBonSortieDTO> detStockDepotDTOs) {
        if(CollectionUtils.isEmpty(detailBonSortieDTOOld)) {
            return new ArrayList<>();
        }

        return detailBonSortieDTOOld.stream()
                .filter(detStockDepotOld -> detStockDepotDTOs.stream()
                        .noneMatch(detailBonSortieDTO -> detailBonSortieDTO.getId() != null && detailBonSortieDTO.getId().equals(detStockDepotOld.getId())))
                .collect(Collectors.toList());
    }

    public void enregistrerDetBonSortie(BonSortie bonSortie, boolean isSave, List<DetailBonSortieDTO> detStockDepotDTOs) {
        List<DetailBonSortie> detailBonSortieDTOOld = new ArrayList<>();
        if(!isSave) {
            detailBonSortieDTOOld = detailBonSortieRepository.findAllByBonSortieId(bonSortie.getId());
        }

        List<DetailBonSortie> listToDelete = getDetStockDepotOldNotAnymore(detailBonSortieDTOOld, detStockDepotDTOs);
        if(CollectionUtils.isNotEmpty(listToDelete)) {
            logger.info("Deleting detail bon sortie: {}", listToDelete.size());
            for(DetailBonSortie detStockDepot : listToDelete) {
                if(null != detStockDepot.getStock() && null != detStockDepot.getStock().getId()) {
                    stockService.updateQteStock(detStockDepot.getStock().getId(), TypeQteToUpdate.QTE_STOCK_SORTIE, new RequestStockQte(detStockDepot.getQteSortie(), 1, null));
                }

                detailBonSortieRepository.delete(detStockDepot);
            }
        }

        List<DetailBonSortie> listToSave = detStockDepotDTOs.stream()
                .map(detailBonSortieDTO -> {
                    DetailBonSortie detailBonSortie = detailBonSortieMapper.toEntity(detailBonSortieDTO);

                    Optional<Stock> optionalStock =  stockRepository.findById(detailBonSortieDTO.getStockId());
                    detailBonSortie.setStock(optionalStock.orElse(null));

                    detailBonSortie.setBonSortie(bonSortie);

                    return detailBonSortie;
                })
                .toList();
        updateStockAndSaveDetStockDepot(listToSave, detailBonSortieDTOOld);
    }

    public void updateStockAndSaveDetStockDepot(List<DetailBonSortie> listToSave, List<DetailBonSortie> detailBonSortieDTOOld) {
        if(CollectionUtils.isNotEmpty(listToSave)) {
            for(DetailBonSortie detStockDepot : listToSave) {
                if(null == detStockDepot.getId()) {
                    stockService.updateQteStock(detStockDepot.getStock().getId(), TypeQteToUpdate.QTE_STOCK_SORTIE, new RequestStockQte(detStockDepot.getQteSortie(),2, null));
                } else {
                    int qte = detStockDepot.getQteSortie();
                    int operation = 2;
                    qte = updateQte(detailBonSortieDTOOld, detStockDepot, qte);

                    if(qte != 0) {
                        if(qte < 0) {
                            qte = Math.abs(qte);
                            operation = 1;
                        }
                        stockService.updateQteStock(detStockDepot.getStock().getId(), TypeQteToUpdate.QTE_STOCK_SORTIE, new RequestStockQte(qte, operation, null));
                    }
                }

                detailBonSortieRepository.save(detStockDepot);
            }
        }
    }

    public int updateQte(List<DetailBonSortie> detailBonSortieDTOOld, DetailBonSortie detStockDepot, int qte) {
        if(CollectionUtils.isNotEmpty(detailBonSortieDTOOld)) {
            Optional<DetailBonSortie> optionalDetStockDepotOld = detailBonSortieDTOOld.stream()
                    .filter(detStockDepotOld -> detStockDepotOld.getId().equals(detStockDepot.getId()))
                    .findFirst();

            if(optionalDetStockDepotOld.isPresent()) {
                qte -= optionalDetStockDepotOld.get().getQteSortie();
            }
        }

        return qte;
    }

    @Override
    public int getLastNumBonSortie(BonSortieDTO bonSortieDTO) {
        logger.info("Getting last num bon sortie");
        //LocalDate localDate = bonSortieDTO.getDateOperation().atZone(ZoneId.systemDefault()).toLocalDate();
        return bonSortieRepository.findMaxNumBonSortie().map(l -> l + 1).orElse(1);
    }

}
