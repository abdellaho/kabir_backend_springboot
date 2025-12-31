package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.config.responses.BonSortieResponse;
import com.kabir.kabirbackend.dto.BonSortieDTO;
import com.kabir.kabirbackend.dto.FactureDTO;
import com.kabir.kabirbackend.service.BonSortieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bon-sortie")
public class BonSortieController {

    private final Logger logger = LoggerFactory.getLogger(BonSortieController.class);

    private final BonSortieService bonSortieService;

    public BonSortieController(BonSortieService bonSortieService) {
        this.bonSortieService = bonSortieService;
    }

    /*
    getAll: `${BASE_URL}/stock-depot`,
        getById: (id: bigint) => `${BASE_URL}/stock-depot/${id}`,
        create: `${BASE_URL}/stock-depot`,
        update: (id: bigint) => `${BASE_URL}/stock-depot/${id}`,
        delete: (id: bigint) => `${BASE_URL}/stock-depot/${id}`,
     */

    @GetMapping
    public ResponseEntity<List<BonSortieDTO>> getAll() {
        logger.info("Finding all stock depots");
        try {
            return ResponseEntity.ok(bonSortieService.findAll());
        } catch (Exception e) {
            logger.error("Error finding all stock depots", e);
            throw new RuntimeException("Error finding all stock depots", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BonSortieDTO> getById(@PathVariable Long id) {
        logger.info("Finding bon sortie by id: {}", id);
        try {
            return ResponseEntity.ok(bonSortieService.findById(id));
        } catch (Exception e) {
            logger.error("Error finding bon sortie by id: {}", id, e);
            throw new RuntimeException("Error finding bon sortie by id: " + id, e);
        }
    }

    @GetMapping("/response/{id}")
    public ResponseEntity<BonSortieResponse> getByIdStockDepotResponse(@PathVariable Long id) {
        logger.info("Finding bon sortie response by id: {}", id);
        try {
            return ResponseEntity.ok(bonSortieService.findByIdBonSortieResponse(id));
        } catch (Exception e) {
            logger.error("Error finding bon sortie response by id: {}", id, e);
            throw new RuntimeException("Error finding bon sortie response by id: " + id, e);
        }
    }

    @PostMapping
    public ResponseEntity<BonSortieDTO> create(@RequestBody BonSortieResponse bonSortieResponse) {
        logger.info("Creating bon sortie response : {}", bonSortieResponse);
        try {
            return ResponseEntity.ok(bonSortieService.save(bonSortieResponse));
        } catch (Exception e) {
            logger.error("Error creating bon sortie response : {}", bonSortieResponse, e);
            throw new RuntimeException("Error creating bon sortie response : " + bonSortieResponse, e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BonSortieDTO> update(@PathVariable Long id, @RequestBody BonSortieResponse bonSortieResponse) {
        logger.info("Updating bon sortie response : {}", bonSortieResponse);
        try {
            return ResponseEntity.ok(bonSortieService.save(bonSortieResponse));
        } catch (Exception e) {
            logger.error("Error updating bon sortie response : {}", bonSortieResponse, e);
            throw new RuntimeException("Error updating bon sortie response : " + bonSortieResponse, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting bon sortie by id: {}", id);
        try {
            bonSortieService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting bon sortie by id: {}", id, e);
            throw new RuntimeException("Error deleting bon sortie by id: " + id, e);
        }
    }

    @PostMapping("/last-num-bon-sortie")
    public ResponseEntity<Integer> getLastNumBonSortie(@RequestBody BonSortieDTO bonSortieDTO) {
        logger.info("Getting last num bon sortie");
        try {
            int lastNum = bonSortieService.getLastNumBonSortie(bonSortieDTO);
            return ResponseEntity.ok(lastNum);
        } catch (Exception e) {
            logger.error("Error getting last num bon sortie: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }
}
