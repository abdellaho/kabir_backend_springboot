package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.dto.VoitureDTO;
import com.kabir.kabirbackend.service.VoitureService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voiture")
class VoitureController {

    private final Logger logger = LoggerFactory.getLogger(VoitureController.class);
    private final VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    /*
    getAll: `${BASE_URL}/voiture`,
    getById: (id: bigint) => `${BASE_URL}/voiture/${id}`,
    create: `${BASE_URL}/voiture`,
    update: (id: bigint) => `${BASE_URL}/voiture/${id}`,
    delete: (id: bigint) => `${BASE_URL}/voiture/${id}`,
    exist: `${BASE_URL}/voiture/exist`,
    search: `${BASE_URL}/voiture/search`,

    id: bigint | null;
    numVoiture: string;
    kmMax: number;
     */

    @GetMapping
    public ResponseEntity<List<VoitureDTO>> getAll() {
        logger.info("Fetching all voitures");
        try {
            List<VoitureDTO> voitures = voitureService.findAll();
            return ResponseEntity.ok(voitures);
        } catch (Exception e) {
            logger.error("Error fetching all voitures: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoitureDTO> getById(@PathVariable Long id) {
        logger.info("Fetching voiture with id: {}", id);
        try {
            VoitureDTO voiture = voitureService.findById(id);
            return ResponseEntity.ok(voiture);
        } catch (Exception e) {
            logger.error("Error fetching voiture with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<VoitureDTO> create(@RequestBody VoitureDTO voitureDTO) {
        logger.info("Creating voiture: {}", voitureDTO);
        try {
            VoitureDTO createdVoiture = voitureService.save(voitureDTO);
            return ResponseEntity.ok(createdVoiture);
        } catch (Exception e) {
            logger.error("Error creating voiture: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VoitureDTO> update(@PathVariable Long id, @RequestBody VoitureDTO voitureDTO) {
        logger.info("Updating voiture with id: {}", id);
        try {
            VoitureDTO updatedVoiture = voitureService.save(voitureDTO);
            return ResponseEntity.ok(updatedVoiture);
        } catch (Exception e) {
            logger.error("Error updating voiture with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VoitureDTO> delete(@PathVariable Long id) {
        logger.info("Deleting voiture with id: {}", id);
        try {
            voitureService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting voiture with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody VoitureDTO voitureDTO) {
        logger.info("Checking if voiture exists: {}", voitureDTO);
        try {
            List<VoitureDTO> voitures = voitureService.search(voitureDTO);
            return ResponseEntity.ok(CollectionUtils.isNotEmpty(voitures));
        } catch (Exception e) {
            logger.error("Error checking if voiture exists: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<VoitureDTO>> search(@RequestBody VoitureDTO voitureDTO) {
        logger.info("Searching voitures: {}", voitureDTO);
        try {
            List<VoitureDTO> voitures = voitureService.search(voitureDTO);
            return ResponseEntity.ok(voitures);
        } catch (Exception e) {
            logger.error("Error searching voitures: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

}
