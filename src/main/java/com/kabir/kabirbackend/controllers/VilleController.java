package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.dto.VilleDTO;
import com.kabir.kabirbackend.service.VilleService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ville")
class VilleController {
    private static final Logger logger = LoggerFactory.getLogger(VilleController.class);
    private final VilleService villeService;

    public VilleController(VilleService villeService) {
        this.villeService = villeService;
    }

    /*
    getAll: `${BASE_URL}/ville`,
    getById: (id: bigint) => `${BASE_URL}/ville/${id}`,
    create: `${BASE_URL}/ville`,
    update: (id: bigint) => `${BASE_URL}/ville/${id}`,
    delete: (id: bigint) => `${BASE_URL}/ville/${id}`,
    exist: `${BASE_URL}/ville/exist`,

    id: bigint | null;
  nomVille: string;
     */

    @GetMapping
    public ResponseEntity<List<VilleDTO>> getAll() {
        logger.info("Fetching all villes");
        try {
            List<VilleDTO> villes = villeService.findAll();
            return ResponseEntity.ok(villes);
        } catch (Exception e) {
            logger.error("Error fetching all villes: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<VilleDTO> getById(@PathVariable Long id) {
        logger.info("Fetching ville with id: {}", id);
        try {
            VilleDTO ville = villeService.findById(id);
            return ResponseEntity.ok(ville);
        } catch (Exception e) {
            logger.error("Error fetching ville with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VilleDTO> create(@PathVariable Long id, @RequestBody VilleDTO villeDTO) {
        logger.info("Creating ville: {}", villeDTO);
        try {
            VilleDTO createdVille = villeService.save(villeDTO);
            return ResponseEntity.ok(createdVille);
        } catch (Exception e) {
            logger.error("Error creating ville: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VilleDTO> delete(@PathVariable Long id) {
        logger.info("Deleting ville with id: {}", id);
        try {
            villeService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting ville with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody VilleDTO villeDTO) {
        logger.info("Checking if ville exists: " + villeDTO);
        try {
            List<VilleDTO> villes = villeService.search(villeDTO);
            return ResponseEntity.ok(CollectionUtils.isNotEmpty(villes));
        } catch (Exception e) {
            logger.error("Error checking if ville exists: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<VilleDTO>> search(@RequestBody VilleDTO villeDTO) {
        logger.info("Searching villes: " + villeDTO);
        try {
            List<VilleDTO> villes = villeService.search(villeDTO);
            return ResponseEntity.ok(villes);
        } catch (Exception e) {
            logger.error("Error searching villes: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

}
