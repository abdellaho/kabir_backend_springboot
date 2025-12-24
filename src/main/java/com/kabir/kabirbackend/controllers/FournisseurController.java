package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.dto.FournisseurDTO;
import com.kabir.kabirbackend.service.FournisseurService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fournisseur")
class FournisseurController {

    private final Logger logger = LoggerFactory.getLogger(FournisseurController.class);
    private final FournisseurService fournisseurService;

    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    /*
    getAll: `${BASE_URL}/fournisseur`,
    getById: (id: bigint) => `${BASE_URL}/fournisseur/${id}`,
    create: `${BASE_URL}/fournisseur`,
    update: (id: bigint) => `${BASE_URL}/fournisseur/${id}`,
    delete: (id: bigint) => `${BASE_URL}/fournisseur/${id}`,
    exist: `${BASE_URL}/fournisseur/exist`,
    search: `${BASE_URL}/fournisseur/search`,

    id: bigint | null;
    designation: string;
    type: number;
    tel1: string;
    tel2: string;
    ice: string;
    adresse: string;
    archiver: boolean;
    supprimer: boolean;
    dateSuppression: Date | null;
    ville: Ville | null;
    villeId: bigint | null;
     */

    @GetMapping
    public ResponseEntity<List<FournisseurDTO>> getAll() {
        logger.info("Fetching all fournisseurs");
        try {
            List<FournisseurDTO> fournisseurs = fournisseurService.findAll();
            return ResponseEntity.ok(fournisseurs);
        } catch (Exception e) {
            logger.error("Error fetching all fournisseurs: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FournisseurDTO> getById(@PathVariable Long id) {
        logger.info("Fetching fournisseur with id: {}", id);
        try {
            FournisseurDTO fournisseur = fournisseurService.findById(id);
            return ResponseEntity.ok(fournisseur);
        } catch (Exception e) {
            logger.error("Error fetching fournisseur with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<FournisseurDTO> create(@RequestBody FournisseurDTO fournisseurDTO) {
        logger.info("Creating fournisseur: {}", fournisseurDTO);
        try {
            FournisseurDTO createdFournisseur = fournisseurService.save(fournisseurDTO);
            return ResponseEntity.ok(createdFournisseur);
        } catch (Exception e) {
            logger.error("Error creating fournisseur: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FournisseurDTO> update(@PathVariable Long id, @RequestBody FournisseurDTO fournisseurDTO) {
        logger.info("Updating fournisseur: {}, with ID {}: ", fournisseurDTO, id);
        try {
            FournisseurDTO updatedFournisseur = fournisseurService.save(fournisseurDTO);
            return ResponseEntity.ok(updatedFournisseur);
        } catch (Exception e) {
            logger.error("Error updating fournisseur: {}, with ID {}: ", e.getMessage(), id);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody FournisseurDTO fournisseurDTO) {
        logger.info("Checking if fournisseur exists: {}", fournisseurDTO);
        try {
            List<FournisseurDTO> fournisseurs = fournisseurService.search(fournisseurDTO);
            return ResponseEntity.ok(CollectionUtils.isNotEmpty(fournisseurs));
        } catch (Exception e) {
            logger.error("Error checking if fournisseur exists: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<FournisseurDTO>> search(@RequestBody FournisseurDTO fournisseurDTO) {
        logger.info("Searching fournisseur: {}", fournisseurDTO);
        try {
            List<FournisseurDTO> fournisseurs = fournisseurService.searchBySupprimerOrArchiver(fournisseurDTO);
            return ResponseEntity.ok(fournisseurs);
        } catch (Exception e) {
            logger.error("Error searching fournisseur: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting fournisseur id: {}", id);
        try {
            fournisseurService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting fournisseur: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

}
