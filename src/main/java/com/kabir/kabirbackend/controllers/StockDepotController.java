package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.config.responses.StockDepotResponse;
import com.kabir.kabirbackend.dto.DetStockDepotDTO;
import com.kabir.kabirbackend.dto.StockDepotDTO;
import com.kabir.kabirbackend.service.StockDepotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock-depot")
public class StockDepotController {

    private final Logger logger = LoggerFactory.getLogger(StockDepotController.class);

    private final StockDepotService stockDepotService;

    public StockDepotController(StockDepotService stockDepotService) {
        this.stockDepotService = stockDepotService;
    }

    /*
    getAll: `${BASE_URL}/stock-depot`,
        getById: (id: bigint) => `${BASE_URL}/stock-depot/${id}`,
        create: `${BASE_URL}/stock-depot`,
        update: (id: bigint) => `${BASE_URL}/stock-depot/${id}`,
        delete: (id: bigint) => `${BASE_URL}/stock-depot/${id}`,
     */

    @GetMapping
    public ResponseEntity<List<StockDepotDTO>> getAll() {
        logger.info("Finding all stock depots");
        try {
            return ResponseEntity.ok(stockDepotService.findAll());
        } catch (Exception e) {
            logger.error("Error finding all stock depots", e);
            throw new RuntimeException("Error finding all stock depots", e);
        }
    }

    @GetMapping("/details")
    public ResponseEntity<List<DetStockDepotDTO>> getAllDetails() {
        logger.info("Finding all det stock depots");
        try {
            return ResponseEntity.ok(stockDepotService.findAllDetails());
        } catch (Exception e) {
            logger.error("Error finding all det stock depots", e);
            throw new RuntimeException("Error finding all det stock depots", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDepotDTO> getById(@PathVariable Long id) {
        logger.info("Finding stock depot by id: {}", id);
        try {
            return ResponseEntity.ok(stockDepotService.findById(id));
        } catch (Exception e) {
            logger.error("Error finding stock depot by id: {}", id, e);
            throw new RuntimeException("Error finding stock depot by id: " + id, e);
        }
    }

    @GetMapping("/response/{id}")
    public ResponseEntity<StockDepotResponse> getByIdStockDepotResponse(@PathVariable Long id) {
        logger.info("Finding stock depot response by id: {}", id);
        try {
            return ResponseEntity.ok(stockDepotService.findByIdStockDepotResponse(id));
        } catch (Exception e) {
            logger.error("Error finding stock depot response by id: {}", id, e);
            throw new RuntimeException("Error finding stock depot response by id: " + id, e);
        }
    }

    @PostMapping
    public ResponseEntity<StockDepotResponse> create(@RequestBody StockDepotResponse stockDepotResponse) {
        logger.info("Creating stock depot response : {}", stockDepotResponse);
        try {
            return ResponseEntity.ok(stockDepotService.save(stockDepotResponse));
        } catch (Exception e) {
            logger.error("Error creating stock depot response : {}", stockDepotResponse, e);
            throw new RuntimeException("Error creating stock depot response : " + stockDepotResponse, e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StockDepotResponse> update(@PathVariable Long id, @RequestBody StockDepotResponse stockDepotResponse) {
        logger.info("Updating stock depot response : {}", stockDepotResponse);
        try {
            return ResponseEntity.ok(stockDepotService.save(stockDepotResponse));
        } catch (Exception e) {
            logger.error("Error updating stock depot response : {}", stockDepotResponse, e);
            throw new RuntimeException("Error updating stock depot response : " + stockDepotResponse, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting stock depot by id: {}", id);
        try {
            stockDepotService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting stock depot by id: {}", id, e);
            throw new RuntimeException("Error deleting stock depot by id: " + id, e);
        }
    }
}
