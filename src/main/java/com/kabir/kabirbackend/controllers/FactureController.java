package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.config.responses.FactureResponse;
import com.kabir.kabirbackend.dto.FactureDTO;
import com.kabir.kabirbackend.dto.LivraisonDTO;
import com.kabir.kabirbackend.service.FactureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facture")
public class FactureController {

    private final Logger logger = LoggerFactory.getLogger(FactureController.class);

    private final FactureService factureService;

    public FactureController(FactureService factureService) {
        this.factureService = factureService;
    }

    /*
    getAll: `${BASE_URL}/stock-depot`,
        getById: (id: bigint) => `${BASE_URL}/stock-depot/${id}`,
        create: `${BASE_URL}/stock-depot`,
        update: (id: bigint) => `${BASE_URL}/stock-depot/${id}`,
        delete: (id: bigint) => `${BASE_URL}/stock-depot/${id}`,
     */

    @GetMapping
    public ResponseEntity<List<FactureDTO>> getAll() {
        logger.info("Finding all factures");
        try {
            return ResponseEntity.ok(factureService.findAll());
        } catch (Exception e) {
            logger.error("Error finding all factures", e);
            throw new RuntimeException("Error finding all factures", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FactureDTO> getById(@PathVariable Long id) {
        logger.info("Finding facture by id: {}", id);
        try {
            return ResponseEntity.ok(factureService.findById(id));
        } catch (Exception e) {
            logger.error("Error finding facture by id: {}", id, e);
            throw new RuntimeException("Error finding facture by id: " + id, e);
        }
    }

    @GetMapping("/response/{id}")
    public ResponseEntity<FactureResponse> getByIdStockDepotResponse(@PathVariable Long id) {
        logger.info("Finding facture response by id: {}", id);
        try {
            return ResponseEntity.ok(factureService.findByIdFactureResponse(id));
        } catch (Exception e) {
            logger.error("Error finding facture response by id: {}", id, e);
            throw new RuntimeException("Error finding facture response by id: " + id, e);
        }
    }

    @PostMapping
    public ResponseEntity<FactureDTO> create(@RequestBody FactureResponse stockDepotResponse) {
        logger.info("Creating facture response : {}", stockDepotResponse);
        try {
            return ResponseEntity.ok(factureService.save(stockDepotResponse));
        } catch (Exception e) {
            logger.error("Error creating facture response : {}", stockDepotResponse, e);
            throw new RuntimeException("Error creating facture response : " + stockDepotResponse, e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FactureDTO> update(@PathVariable Long id, @RequestBody FactureResponse stockDepotResponse) {
        logger.info("Updating facture response : {}", stockDepotResponse);
        try {
            return ResponseEntity.ok(factureService.save(stockDepotResponse));
        } catch (Exception e) {
            logger.error("Error updating facture response : {}", stockDepotResponse, e);
            throw new RuntimeException("Error updating facture response : " + stockDepotResponse, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting facture by id: {}", id);
        try {
            factureService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting facture by id: {}", id, e);
            throw new RuntimeException("Error deleting facture by id: " + id, e);
        }
    }

    @PostMapping("/last-num-facture")
    public ResponseEntity<Integer> getLastNumFacture(@RequestBody FactureDTO factureDTO) {
        logger.info("Getting last num facture");
        try {
            int lastNum = factureService.getLastNumFacture(factureDTO);
            return ResponseEntity.ok(lastNum);
        } catch (Exception e) {
            logger.error("Error getting last num facture: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }
}
