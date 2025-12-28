package com.kabir.kabirbackend.service;

import com.kabir.kabirbackend.config.requests.RequestStockQte;
import com.kabir.kabirbackend.config.responses.StockDepotResponse;
import com.kabir.kabirbackend.dto.DetStockDepotDTO;
import com.kabir.kabirbackend.dto.StockDepotDTO;
import com.kabir.kabirbackend.entities.DetStockDepot;
import com.kabir.kabirbackend.entities.Stock;
import com.kabir.kabirbackend.entities.StockDepot;
import com.kabir.kabirbackend.mapper.DetStockDepotMapper;
import com.kabir.kabirbackend.mapper.StockDepotMapper;
import com.kabir.kabirbackend.repository.DetStockDepotRepository;
import com.kabir.kabirbackend.repository.StockDepotRepository;
import com.kabir.kabirbackend.repository.StockRepository;
import com.kabir.kabirbackend.service.interfaces.IStockDepotService;
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
public class StockDepotService implements IStockDepotService {

    private final Logger logger = LoggerFactory.getLogger(StockDepotService.class);

    private final StockDepotRepository stockDepotRepository;
    private final DetStockDepotRepository detStockDepotRepository;
    private final StockDepotMapper stockDepotMapper;
    private final DetStockDepotMapper detStockDepotMapper;
    private final StockService stockService;
    private final StockRepository stockRepository;


    public StockDepotService(
            StockDepotRepository stockDepotRepository,
            DetStockDepotRepository detStockDepotRepository,
            StockDepotMapper stockDepotMapper,
            DetStockDepotMapper detStockDepotMapper,
            StockService stockService,
            StockRepository stockRepository
    ) {
        this.stockDepotRepository = stockDepotRepository;
        this.stockDepotMapper = stockDepotMapper;
        this.detStockDepotMapper = detStockDepotMapper;
        this.stockService = stockService;
        this.stockRepository = stockRepository;
        this.detStockDepotRepository = detStockDepotRepository;
    }

    @Override
    public StockDepotDTO save(StockDepotResponse stockDepotResponse) {
        logger.info("Saving stock depot: {}", stockDepotResponse);
        StockDepotDTO stockDepotDTO = stockDepotResponse.stockDepot();
        boolean isSave = stockDepotDTO.getId() == null;
        try {
            StockDepot stockDepot = stockDepotMapper.toStockDepot(stockDepotDTO);
            stockDepotDTO = stockDepotMapper.toStockDepotDTO(stockDepotRepository.save(stockDepot));

            enregistrerDetStockDepot(stockDepot, isSave, stockDepotResponse.detStockDepots());

            logger.info("Stock depot saved successfully: {}", stockDepotDTO);
        } catch (Exception e) {
            logger.error("Failed to save stock depot: {}", e.getMessage());
        }
        return stockDepotDTO;
    }

    @Override
    public StockDepotDTO findById(Long id) {
        logger.info("Finding stock depot by id: {}", id);
        StockDepotDTO stockDepotDTO = stockDepotMapper.toStockDepotDTO(stockDepotRepository.findById(id).orElse(null));
        logger.info("Stock depot found: {}", stockDepotDTO);
        return stockDepotDTO;
    }

    @Override
    public StockDepotResponse findByIdStockDepotResponse(Long id) {
        logger.info("Finding stock depot response by id: {}", id);
        try {
            StockDepotDTO stockDepotDTO = stockDepotMapper.toStockDepotDTO(stockDepotRepository.findById(id).orElse(null));
            if(null != stockDepotDTO && null != stockDepotDTO.getId()) {
                List<DetStockDepotDTO> list = detStockDepotRepository.findAllByStockDepotId(id).stream()
                    .map(detStockDepotMapper::toDetStockDepotDTO)
                    .toList();

                return new StockDepotResponse(stockDepotDTO, list);
            } else {
                logger.info("Stock depot not found with ID: {}", id);
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to find stock depot response by id: {}", id);
            return null;
        }
    }

    @Override
    public List<StockDepotDTO> findAll() {
        logger.info("Finding all stock depots");
        List<StockDepotDTO> stockDepotDTOs = stockDepotRepository.findAll(Sort.by(Sort.Direction.DESC, "dateOperation")).stream()
                .map(stockDepotMapper::toStockDepotDTO)
                .collect(Collectors.toList());
        logger.info("Stock depots found: {}", stockDepotDTOs.size());
        return stockDepotDTOs;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting stock depot by id: {}", id);
        try {
            List<DetStockDepot> detStockDepotDTOOld = detStockDepotRepository.findAllByStockDepotId(id);
            logger.info("List of DetStockDepot to delete: {}", detStockDepotDTOOld.size());

            if(CollectionUtils.isNotEmpty(detStockDepotDTOOld)) {
                for(DetStockDepot detStockDepot : detStockDepotDTOOld) {
                    if(null != detStockDepot.getStock() && null != detStockDepot.getStock().getId()) {
                        stockService.updateQteStock(detStockDepot.getStock().getId(), new RequestStockQte(detStockDepot.getQte(), 1));

                        logger.info("Deleting detail stock depot by id: {}", detStockDepot.getId());
                        detStockDepotRepository.deleteById(detStockDepot.getId());
                    }
                }
            }

            stockDepotRepository.deleteById(id);
            logger.info("Stock depot deleted successfully");
        } catch (Exception e) {
            logger.error("Failed to delete stock depot: {}", e.getMessage());
        }
    }

    @Override
    public List<StockDepotDTO> search(StockDepotDTO stockDepot) {
        return this.findAll();
    }


    public List<DetStockDepot> getDetStockDepotOldNotAnymore(List<DetStockDepot> detStockDepotDTOOld, List<DetStockDepotDTO> detStockDepotDTOs) {
        if(CollectionUtils.isEmpty(detStockDepotDTOOld)) {
            return new ArrayList<>();
        }

        return detStockDepotDTOOld.stream()
                .filter(detStockDepotOld -> detStockDepotDTOs.stream()
                        .noneMatch(detStockDepotDTO -> detStockDepotDTO.getId() != null && detStockDepotDTO.getId().equals(detStockDepotOld.getId())))
                .collect(Collectors.toList());
    }

    public void enregistrerDetStockDepot(StockDepot stockDepot, boolean isSave, List<DetStockDepotDTO> detStockDepotDTOs) {
        List<DetStockDepot> detStockDepotDTOOld = new ArrayList<>();
        if(!isSave) {
            detStockDepotDTOOld = detStockDepotRepository.findAllByStockDepotId(stockDepot.getId());
        }

        List<DetStockDepot> listToDelete = getDetStockDepotOldNotAnymore(detStockDepotDTOOld, detStockDepotDTOs);
        if(CollectionUtils.isNotEmpty(listToDelete)) {
            logger.info("Deleting det stock depot: {}", listToDelete.size());
            for(DetStockDepot detStockDepot : listToDelete) {
                if(null != detStockDepot.getStock() && null != detStockDepot.getStock().getId()) {
                    stockService.updateQteStock(detStockDepot.getStock().getId(), new RequestStockQte(detStockDepot.getQte(), 1));
                }

                detStockDepotRepository.delete(detStockDepot);
            }
        }

        List<DetStockDepot> listToSave = detStockDepotDTOs.stream()
                .map(detStockDepotDTO -> {
                    DetStockDepot detStockDepot = detStockDepotMapper.toDetStockDepot(detStockDepotDTO);

                    Optional<Stock> optionalStock =  stockRepository.findById(detStockDepotDTO.getStockId());
                    detStockDepot.setStock(optionalStock.orElse(null));

                    detStockDepot.setStockDepot(stockDepot);

                    return detStockDepot;
                })
                .toList();
        updateStockAndSaveDetStockDepot(listToSave, detStockDepotDTOOld);
    }

    public void updateStockAndSaveDetStockDepot(List<DetStockDepot> listToSave, List<DetStockDepot> detStockDepotDTOOld) {
        if(CollectionUtils.isNotEmpty(listToSave)) {
            for(DetStockDepot detStockDepot : listToSave) {
                if(null == detStockDepot.getId()) {
                    stockService.updateQteStock(detStockDepot.getStock().getId(), new RequestStockQte(detStockDepot.getQte(),2));
                } else {
                    int qte = detStockDepot.getQte();
                    int operation = 2;
                    qte = updateQte(detStockDepotDTOOld, detStockDepot, qte);

                    if(qte != 0) {
                        if(qte < 0) {
                            qte = Math.abs(qte);
                            operation = 1;
                        }
                        stockService.updateQteStock(detStockDepot.getStock().getId(), new RequestStockQte(qte, operation));
                    }
                }

                detStockDepotRepository.save(detStockDepot);
            }
        }
    }

    public int updateQte(List<DetStockDepot> detStockDepotDTOOld, DetStockDepot detStockDepot, int qte) {
        if(CollectionUtils.isNotEmpty(detStockDepotDTOOld)) {
            Optional<DetStockDepot> optionalDetStockDepotOld = detStockDepotDTOOld.stream()
                    .filter(detStockDepotOld -> detStockDepotOld.getId().equals(detStockDepot.getId()))
                    .findFirst();

            if(optionalDetStockDepotOld.isPresent()) {
                qte -= optionalDetStockDepotOld.get().getQte();
            }
        }

        return qte;
    }

}
