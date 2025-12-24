package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.dto.DetLivraisonDTO;
import com.kabir.kabirbackend.service.DetLivraisonService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/det-livraison")
class DetLivraisonController {

    private final Logger logger = LoggerFactory.getLogger(DetLivraisonController.class);
    private final DetLivraisonService detLivraisonService;

    public DetLivraisonController(DetLivraisonService detLivraisonService) {
        this.detLivraisonService = detLivraisonService;
    }

    /*
    getAll: `${BASE_URL}/det-livraison`,
    getById: (id: bigint) => `${BASE_URL}/det-livraison/${id}`,
    create: `${BASE_URL}/det-livraison`,
    update: (id: bigint) => `${BASE_URL}/det-livraison/${id}`,
    delete: (id: bigint) => `${BASE_URL}/det-livraison/${id}`,
    getByLivraison: (idLivraison: bigint) => `${BASE_URL}/det-livraison/livraison/${idLivraison}`,
    exist: `${BASE_URL}/det-livraison/exist`,
    search: `${BASE_URL}/det-livraison/search`,
    present: `${BASE_URL}/det-livraison/present`
     */

    @GetMapping
    public ResponseEntity<List<DetLivraisonDTO>> getAll() {
        logger.info("Fetching all detLivraisons");
        try {
            List<DetLivraisonDTO> detLivraisons = detLivraisonService.findAll();
            return ResponseEntity.ok(detLivraisons);
        } catch (Exception e) {
            logger.error("Error fetching all detLivraisons: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetLivraisonDTO> getById(@PathVariable Long id) {
        logger.info("Fetching detLivraison with id: {}", id);
        try {
            DetLivraisonDTO detLivraison = detLivraisonService.findById(id);
            return ResponseEntity.ok(detLivraison);
        } catch (Exception e) {
            logger.error("Error fetching detLivraison with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<DetLivraisonDTO> create(@RequestBody DetLivraisonDTO detLivraisonDTO) {
        logger.info("Creating detLivraison: {}", detLivraisonDTO);
        try {
            DetLivraisonDTO createdDetLivraison = detLivraisonService.saveWithoutLivraison(detLivraisonDTO);
            return ResponseEntity.ok(createdDetLivraison);
        } catch (Exception e) {
            logger.error("Error creating detLivraison: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DetLivraisonDTO> update(@PathVariable Long id, @RequestBody DetLivraisonDTO detLivraisonDTO) {
        logger.info("Updating detLivraison with id: {}", id);
        try {
            DetLivraisonDTO updatedDetLivraison = detLivraisonService.saveWithoutLivraison(detLivraisonDTO);
            return ResponseEntity.ok(updatedDetLivraison);
        } catch (Exception e) {
            logger.error("Error updating detLivraison with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting detLivraison with id: {}", id);
        try {
            detLivraisonService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting detLivraison with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping("/livraison/{id}")
    public ResponseEntity<List<DetLivraisonDTO>> getByLivraisonId(@PathVariable Long id) {
        logger.info("Fetching detLivraisons by livraison id: {}", id);
        try {
            List<DetLivraisonDTO> detLivraisons = detLivraisonService.DetLivraisonByLivraisonId(id);
            return ResponseEntity.ok(detLivraisons);
        } catch (Exception e) {
            logger.error("Error fetching detLivraisons by livraison id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping("/search")
    public ResponseEntity<List<DetLivraisonDTO>> search(@RequestBody DetLivraisonDTO detLivraisonDTO) {
        logger.info("Searching detLivraison: {}", detLivraisonDTO);
        try {
            List<DetLivraisonDTO> detLivraisons = detLivraisonService.search(detLivraisonDTO);
            return ResponseEntity.ok(detLivraisons);
        } catch (Exception e) {
            logger.error("Error searching detLivraison: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody DetLivraisonDTO detLivraisonDTO) {
        logger.info("Checking if detLivraison exists: {}", detLivraisonDTO);
        try {
            List<DetLivraisonDTO> detLivraisons = detLivraisonService.search(detLivraisonDTO);
            return ResponseEntity.ok(CollectionUtils.isNotEmpty(detLivraisons));
        } catch (Exception e) {
            logger.error("Error checking if detLivraison exists: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }
}
