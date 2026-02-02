package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.config.requests.BulletinPaiRequest;
import com.kabir.kabirbackend.config.responses.BulletinPaiResponse;
import com.kabir.kabirbackend.dto.DetBulletinLivraisonDTO;
import com.kabir.kabirbackend.dto.DetBulletinPaiDTO;
import com.kabir.kabirbackend.dto.BulletinPaiDTO;
import com.kabir.kabirbackend.entities.*;
import com.kabir.kabirbackend.mapper.*;
import com.kabir.kabirbackend.repository.*;
import com.kabir.kabirbackend.service.interfaces.IBulletinPaiService;
import com.kabir.kabirbackend.specifications.BulletinPaiSpecification;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BulletinPaiService implements IBulletinPaiService {

    private final Logger logger = LoggerFactory.getLogger(BulletinPaiService.class);

    private final BulletinPaiRepository bulletinPaiRepository;
    private final DetBulletinPaiRepository detBulletinPaiRepository;
    private final DetBulletinLivraisonRepository detBulletinLivraisonRepository;
    private final BulletinPaiMapper bulletinPaiMapper;
    private final DetBulletinPaiMapper detBulletinPaiMapper;
    private final DetBulletinLivraisonMapper detBulletinLivraisonMapper;
    private final LivraisonRepository livraisonRepository;
    private final StockRepository stockRepository;
    private final PersonnelRepository personnelRepository;


    public BulletinPaiService(
            BulletinPaiRepository bulletinPaiRepository,
            DetBulletinPaiRepository detBulletinPaiRepository,
            DetBulletinLivraisonRepository detBulletinLivraisonRepository,
            BulletinPaiMapper bulletinPaiMapper,
            DetBulletinPaiMapper detBulletinPaiMapper,
            DetBulletinLivraisonMapper detBulletinLivraisonMapper,
            LivraisonRepository livraisonRepository,
            StockRepository stockRepository,
            PersonnelRepository personnelRepository
    ) {
        this.bulletinPaiMapper = bulletinPaiMapper;
        this.detBulletinPaiMapper = detBulletinPaiMapper;
        this.detBulletinLivraisonMapper = detBulletinLivraisonMapper;
        this.livraisonRepository = livraisonRepository;
        this.stockRepository = stockRepository;
        this.bulletinPaiRepository = bulletinPaiRepository;
        this.detBulletinPaiRepository = detBulletinPaiRepository;
        this.detBulletinLivraisonRepository = detBulletinLivraisonRepository;
        this.personnelRepository = personnelRepository;
    }

    @Override
    public List<BulletinPaiDTO> findAll() {
        logger.info("Finding all bulletinPais");
        try {
            List<BulletinPai> bulletinPais = bulletinPaiRepository.findAll();
            return bulletinPaiMapper.toDto(bulletinPais);
        } catch (Exception e) {
            logger.error("Error finding all bulletinPais", e);
            throw new RuntimeException("Error finding all bulletinPais", e);
        }
    }

    @Override
    public BulletinPaiDTO findById(Long id) {
        logger.info("Finding bulletinPai by id: {}", id);
        try {
            BulletinPai bulletinPai = bulletinPaiRepository.findById(id).orElseThrow(() -> new RuntimeException("BulletinPai not found"));
            return bulletinPaiMapper.toDto(bulletinPai);
        } catch (Exception e) {
            logger.error("Error finding bulletinPai by id: {}", id, e);
            throw new RuntimeException("Error finding bulletinPai by id: " + id, e);
        }
    }

    @Override
    public BulletinPaiResponse findByIdBulletinPaiResponse(Long id) {
        logger.info("Finding bulletinPai response by id: {}", id);
        try {
            if (null != id) {
                BulletinPaiDTO bulletinPaiDTO = bulletinPaiRepository.findById(id).map(bulletinPaiMapper::toDto).orElseThrow(() -> new RuntimeException("BulletinPai not found"));
                List<DetBulletinPaiDTO> list = detBulletinPaiRepository.findByBulletinPaiId(id).stream().map(detBulletinPaiMapper::toDto).toList();
                List<DetBulletinLivraisonDTO> listLivraison = detBulletinLivraisonRepository.findByBulletinPaiId(id).stream().map(detBulletinLivraisonMapper::toDTO).toList();
                return new BulletinPaiResponse(bulletinPaiDTO, list,null, listLivraison);
            } else {
                throw new RuntimeException("BulletinPai id cannot be null");
            }
        } catch (Exception e) {
            logger.error("Error finding bulletinPai response by id: {}", id, e);
            throw new RuntimeException("Error finding bulletinPai response by id: " + id, e);
        }
    }

    @Override
    public BulletinPaiDTO save(BulletinPaiResponse bulletinPaiResponse) {
        logger.info("Saving bulletinPai from BulletinPaiResponse: {}", bulletinPaiResponse);
        BulletinPaiDTO bulletinPaiDTO = bulletinPaiResponse.bulletinPai();
        boolean isSave = bulletinPaiDTO.getId() == null;
        try {
            Optional<Personnel> optionalCommercial = personnelRepository.findById(bulletinPaiDTO.getCommercialId());
            Optional<Personnel> optionalOperateur = personnelRepository.findById(bulletinPaiDTO.getOperateurId());

            BulletinPai bulletinPai = bulletinPaiMapper.toEntity(bulletinPaiDTO);
            bulletinPai.setCommercial(optionalCommercial.orElse(null));
            bulletinPai.setOperateur(optionalOperateur.orElse(null));

            bulletinPaiDTO = bulletinPaiMapper.toDto(bulletinPaiRepository.save(bulletinPai));

            enregistrerDetBulletinPai(bulletinPai, isSave, bulletinPaiResponse.detBulletinPais());
            enregistrerDetBulletinLivraison(bulletinPai, isSave, bulletinPaiResponse.detBulletinLivraisons());

            logger.info("BulletinPai saved successfully: {}", bulletinPaiDTO);
            return bulletinPaiDTO;
        } catch (Exception e) {
            logger.error("Failed to save bulletinPai: {}", e.getMessage());
            throw new RuntimeException("Failed to save bulletinPai", e);
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting bulletinPai: {}", id);
        try {
            List<DetBulletinPai> list = detBulletinPaiRepository.findByBulletinPaiId(id);
            List<DetBulletinLivraison> listLivraison = detBulletinLivraisonRepository.findByBulletinPaiId(id);

            if(CollectionUtils.isNotEmpty(list)) {
                logger.info("deleting All DetBulletinPai of bulletinPai: {}", id);
                detBulletinPaiRepository.deleteAll(list);
            }
            if(CollectionUtils.isNotEmpty(listLivraison)) {
                logger.info("deleting All DetBulletinLivraison of bulletinPai: {}", id);
                detBulletinLivraisonRepository.deleteAll(listLivraison);
            }

            bulletinPaiRepository.deleteById(id);
        } catch (Exception e) {

            logger.error("Error deleting bulletinPai: {}", id, e);
            throw new RuntimeException("Error deleting bulletinPai: " + id, e);
        }
    }

    @Override
    public boolean searchIfExist(BulletinPaiDTO bulletinPaiDTO) {
        return !bulletinPaiRepository.findAll(BulletinPaiSpecification.builder().build().searchIfExist(bulletinPaiDTO)).isEmpty();
    }

    @Override
    public List<BulletinPaiDTO> search(BulletinPaiDTO bulletinPaiDTO) {
        logger.info("Searching bulletinPais: {}", bulletinPaiDTO);
        return bulletinPaiRepository.findAll(BulletinPaiSpecification.builder().bulletinPaiDTO(bulletinPaiDTO).build()).stream().map(bulletinPaiMapper::toDto).toList();
    }


    public List<DetBulletinPai> getDetBulletinOldNotAnymore(List<DetBulletinPai> detBulletinPaiDtoOld, List<DetBulletinPaiDTO> detBulletinPaiDTOs) {
        if (CollectionUtils.isEmpty(detBulletinPaiDtoOld)) {
            return new ArrayList<>();
        }

        return detBulletinPaiDtoOld.stream()
                .filter(detStockDepotOld -> detBulletinPaiDTOs.stream()
                        .noneMatch(detStockDepotDTO -> detStockDepotDTO.getId() != null && detStockDepotDTO.getId().equals(detStockDepotOld.getId())))
                .collect(Collectors.toList());
    }

    public void enregistrerDetBulletinPai(BulletinPai bulletinPai, boolean isSave, List<DetBulletinPaiDTO> detBulletinPaiDTOs) {
        List<DetBulletinPai> detBulletinPaiDtoOld = new ArrayList<>();
        if (!isSave) {
            detBulletinPaiDtoOld = detBulletinPaiRepository.findByBulletinPaiId(bulletinPai.getId());
        }

        List<DetBulletinPai> listToDelete = getDetBulletinOldNotAnymore(detBulletinPaiDtoOld, detBulletinPaiDTOs);
        if (CollectionUtils.isNotEmpty(listToDelete)) {
            logger.info("Deleting det bulletinPai : {}", listToDelete.size());
            detBulletinPaiRepository.deleteAll(listToDelete);
        }

        List<DetBulletinPai> listToSave = detBulletinPaiDTOs.stream()
                .map(bulletinPaiDTO -> {
                    DetBulletinPai detBulletinPai = detBulletinPaiMapper.toEntity(bulletinPaiDTO);

                    Optional<Stock> optionalStock = stockRepository.findById(bulletinPaiDTO.getProduitId());
                    detBulletinPai.setProduit(optionalStock.orElse(null));

                    detBulletinPai.setBulletinPai(bulletinPai);

                    return detBulletinPai;
                })
                .toList();
        saveDetBulletinPai(listToSave, detBulletinPaiDtoOld);
    }

    public void saveDetBulletinPai(List<DetBulletinPai> listToSave, List<DetBulletinPai> detBulletinPaiDtoOld) {
        if (CollectionUtils.isNotEmpty(listToSave)) {
            detBulletinPaiRepository.saveAll(listToSave);
        }
    }

    public void saveDetBulletinPaiLivraison(List<DetBulletinLivraison> listToSave, List<DetBulletinLivraison> detBulletinLivraisonDtoOld) {
        if (CollectionUtils.isNotEmpty(listToSave)) {
            detBulletinLivraisonRepository.saveAll(listToSave);
        }
    }

    public void enregistrerDetBulletinLivraison(BulletinPai bulletinPai, boolean isSave, List<DetBulletinLivraisonDTO> detBulletinLivraisonDTOs) {
        List<DetBulletinLivraison> detBulletinLivraisonDtoOld = new ArrayList<>();
        if (!isSave) {
            detBulletinLivraisonDtoOld = detBulletinLivraisonRepository.findByBulletinPaiId(bulletinPai.getId());
        }

        List<DetBulletinLivraison> listToDelete = getDetBulletinLivraisonOldNotAnymore(detBulletinLivraisonDtoOld, detBulletinLivraisonDTOs);
        if (CollectionUtils.isNotEmpty(listToDelete)) {
            logger.info("Deleting det bulletinLivraison : {}", listToDelete.size());
            detBulletinLivraisonRepository.deleteAll(listToDelete);
        }

        List<DetBulletinLivraison> listToSave = detBulletinLivraisonDTOs.stream()
                .map(bulletinLivraisonDTO -> {
                    DetBulletinLivraison detBulletinLivraison = detBulletinLivraisonMapper.toEntity(bulletinLivraisonDTO);

                    Optional<Livraison> optionalLivraison = livraisonRepository.findById(bulletinLivraisonDTO.getLivraisonId());
                    detBulletinLivraison.setLivraison(optionalLivraison.orElse(null));

                    detBulletinLivraison.setBulletinPai(bulletinPai);

                    return detBulletinLivraison;
                })
                .toList();
        saveDetBulletinPaiLivraison(listToSave, detBulletinLivraisonDtoOld);
    }

    public List<DetBulletinLivraison> getDetBulletinLivraisonOldNotAnymore(List<DetBulletinLivraison> detBulletinLivraisonDtoOld, List<DetBulletinLivraisonDTO> detBulletinLivraisonDTOs) {
        if (CollectionUtils.isEmpty(detBulletinLivraisonDtoOld)) {
            return new ArrayList<>();
        }

        return detBulletinLivraisonDtoOld.stream()
                .filter(detBulletinLivraisonOld -> detBulletinLivraisonDTOs.stream()
                        .noneMatch(detBulletinLivraisonDTO -> detBulletinLivraisonDTO.getId() != null && detBulletinLivraisonDTO.getId().equals(detBulletinLivraisonOld.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public int getLast() {
        return bulletinPaiRepository.findMaxNum().map(num -> num + 1).orElse(1);
    }

    @Override
    public BulletinPaiResponse getDetails(BulletinPaiRequest bulletinPaiRequest) {
        logger.info("Getting details of bulletinPai based on dateDebut and dateFin and personnelId(Commercial): {}", bulletinPaiRequest);
        List<DetBulletinPaiDTO> listDetBulletinPaiAvecMontant = detBulletinPaiRepository.getDetBulletinPai(bulletinPaiRequest.getDateDebut(), bulletinPaiRequest.getDateFin(), bulletinPaiRequest.getCommercialId(), false, null);
        List<DetBulletinPaiDTO> listDetBulletinPaiSansMontant = detBulletinPaiRepository.getDetBulletinPai(bulletinPaiRequest.getDateDebut(), bulletinPaiRequest.getDateFin(), bulletinPaiRequest.getCommercialId(), true, null);
        List<DetBulletinLivraisonDTO> listDetBulletinLivraison = livraisonRepository.findByDateBlBetweenAndPersonnelId(bulletinPaiRequest.getDateDebut(), bulletinPaiRequest.getDateFin(), bulletinPaiRequest.getCommercialId());

        listDetBulletinPaiAvecMontant.addAll(listDetBulletinPaiSansMontant);

        return new BulletinPaiResponse(null, listDetBulletinPaiAvecMontant, listDetBulletinPaiSansMontant, listDetBulletinLivraison);
    }

    @Override
    public BulletinPaiResponse getDetailsOfLivraison(BulletinPaiRequest bulletinPaiRequest) {
        logger.info("Getting details of bulletinPai based on dateDebut and dateFin and personnelId(Commercial) and livraisonId : {}", bulletinPaiRequest);
        List<DetBulletinPaiDTO> listDetBulletinPaiAvecMontant = detBulletinPaiRepository.getDetBulletinPai(bulletinPaiRequest.getDateDebut(), bulletinPaiRequest.getDateFin(), bulletinPaiRequest.getCommercialId(), false, bulletinPaiRequest.getLivraisonId());
        List<DetBulletinPaiDTO> listDetBulletinPaiSansMontant = detBulletinPaiRepository.getDetBulletinPai(bulletinPaiRequest.getDateDebut(), bulletinPaiRequest.getDateFin(), bulletinPaiRequest.getCommercialId(), true, bulletinPaiRequest.getLivraisonId());

        listDetBulletinPaiAvecMontant.addAll(listDetBulletinPaiSansMontant);

        return new BulletinPaiResponse(null, listDetBulletinPaiAvecMontant, listDetBulletinPaiSansMontant, List.of());
    }
}