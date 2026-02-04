package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.config.searchEntities.CompteCaisseSearch;
import com.kabir.kabirbackend.dto.CompteCaisseDTO;
import com.kabir.kabirbackend.service.CompteCaisseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/compte-caisse")
class CompteCaisseController {

    private final Logger logger = LoggerFactory.getLogger(CompteCaisseController.class);
    private final CompteCaisseService compteCaisseService;

    public CompteCaisseController(CompteCaisseService compteCaisseService) {
        this.compteCaisseService = compteCaisseService;
    }

    @GetMapping
    public ResponseEntity<List<CompteCaisseDTO>> findAll() {
        logger.info("Finding all compteCaisses");
        try {
            return ResponseEntity.ok(compteCaisseService.findAll());
        } catch (Exception e) {
            logger.error("Error finding all compteCaisses", e);
            throw new RuntimeException("Error finding all compteCaisses", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompteCaisseDTO> findById(@PathVariable Long id) {
        logger.info("Finding compteCaisse by id: {}", id);
        try {
            CompteCaisseDTO compteCaisseDTO = compteCaisseService.findById(id);
            if (compteCaisseDTO == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(compteCaisseDTO);
            }
        } catch (Exception e) {
            logger.error("Error finding compteCaisse by id: {}", id, e);
            throw new RuntimeException("Error finding compteCaisse by id: " + id, e);
        }
    }

    @PostMapping
    public ResponseEntity<CompteCaisseDTO> save(@Valid @RequestBody CompteCaisseDTO compteCaisseDTO) {
        logger.info("Saving compteCaisse: {}", compteCaisseDTO);
        try {
            CompteCaisseDTO updatedCompteCaisse = compteCaisseService.save(compteCaisseDTO);
            return ResponseEntity.ok(updatedCompteCaisse);
        } catch (Exception e) {
            logger.error("Error saving compteCaisse: {}", compteCaisseDTO, e);
            throw new RuntimeException("Error saving compteCaisse: " + compteCaisseDTO, e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CompteCaisseDTO> update(@PathVariable Long id, @Valid @RequestBody CompteCaisseDTO compteCaisseDTO) {
        logger.info("Updating compteCaisse: {}, with ID {} : ", compteCaisseDTO, id);
        try {
            CompteCaisseDTO updatedCompteCaisse = compteCaisseService.save(compteCaisseDTO);
            return ResponseEntity.ok(updatedCompteCaisse);
        } catch (Exception e) {
            logger.error("Error updating compteCaisse: {}, with ID: {}", compteCaisseDTO, id, e);
            throw new RuntimeException("Error updating compteCaisse: " + compteCaisseDTO, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting compteCaisse: {}", id);
        try {
            compteCaisseService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting compteCaisse: {}", id, e);
            throw new RuntimeException("Error deleting compteCaisse: " + id, e);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<CompteCaisseDTO>> search(@RequestBody CompteCaisseSearch compteCaisseSearch) {
        logger.info("Searching compteCaisse: {}", compteCaisseSearch);
        try {
            return ResponseEntity.ok(compteCaisseService.search(compteCaisseSearch));
        } catch (Exception e) {
            logger.error("Error searching compteCaisse: {}", compteCaisseSearch, e);
            throw new RuntimeException("Error searching compteCaisse: " + compteCaisseSearch, e);
        }
    }

    @PostMapping("/searchByCommon")
    public ResponseEntity<List<CompteCaisseDTO>> searchByCommon(@RequestBody CommonSearchModel commonSearchModel) {
        logger.info("Searching compte caisse by common: {}", commonSearchModel);
        try {
            return ResponseEntity.ok(compteCaisseService.searchByCommon(commonSearchModel));
        } catch (Exception e) {
            logger.error("Error searching compte caisse by common: {}", commonSearchModel, e);
            throw new RuntimeException("Error searching compte caisse by common: " + commonSearchModel, e);
        }
    }
}
