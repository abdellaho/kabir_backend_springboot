package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.dto.CaisseDTO;
import com.kabir.kabirbackend.service.CaisseService;
import jakarta.validation.Valid;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/caisse")
class CaisseController {

    private final Logger logger = LoggerFactory.getLogger(CaisseController.class);
    private final CaisseService absenceService;

    public CaisseController(CaisseService absenceService) {
        this.absenceService = absenceService;
    }

    @GetMapping
    public ResponseEntity<List<CaisseDTO>> findAll() {
        logger.info("Finding all caisses");
        try {
            return ResponseEntity.ok(absenceService.findAll());
        } catch (Exception e) {
            logger.error("Error finding all caisses", e);
            throw new RuntimeException("Error finding all caisses", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaisseDTO> findById(@PathVariable Long id) {
        logger.info("Finding caisse by id: {}", id);
        try {
            CaisseDTO caisseDTO = absenceService.findById(id);
            if (caisseDTO == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(caisseDTO);
            }
        } catch (Exception e) {
            logger.error("Error finding caisse by id: {}", id, e);
            throw new RuntimeException("Error finding caisse by id: " + id, e);
        }
    }

    @PostMapping
    public ResponseEntity<CaisseDTO> save(@Valid @RequestBody CaisseDTO caisseDTO) {
        logger.info("Saving caisse: {}", caisseDTO);
        try {
            CaisseDTO updatedAbsenceDTO = absenceService.save(caisseDTO);
            return ResponseEntity.ok(updatedAbsenceDTO);
        } catch (Exception e) {
            logger.error("Error saving caisse: {}", caisseDTO, e);
            throw new RuntimeException("Error saving caisse: " + caisseDTO, e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CaisseDTO> update(@PathVariable Long id, @Valid @RequestBody CaisseDTO caisseDTO) {
        logger.info("Updating caisse: {}, with ID {} : ", caisseDTO, id);
        try {
            CaisseDTO updatedAbsenceDTO = absenceService.save(caisseDTO);
            return ResponseEntity.ok(updatedAbsenceDTO);
        } catch (Exception e) {
            logger.error("Error updating caisse: {}, with ID: {}", caisseDTO, id, e);
            throw new RuntimeException("Error updating caisse: " + caisseDTO, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting caisse: {}", id);
        try {
            absenceService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting caisse: {}", id, e);
            throw new RuntimeException("Error deleting caisse: " + id, e);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<CaisseDTO>> search(@RequestBody CaisseDTO caisseDTO) {
        logger.info("Searching caisse: {}", caisseDTO);
        try {
            return ResponseEntity.ok(absenceService.search(caisseDTO));
        } catch (Exception e) {
            logger.error("Error searching caisse: {}", caisseDTO, e);
            throw new RuntimeException("Error searching caisse: " + caisseDTO, e);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody CaisseDTO caisseDTO) {
        logger.info("Searching caisse if exist: {}", caisseDTO);
        try {
            return ResponseEntity.ok(CollectionUtils.isNotEmpty(absenceService.search(caisseDTO)));
        } catch (Exception e) {
            logger.error("Error searching caisse if exist: {}", caisseDTO, e);
            throw new RuntimeException("Error searching caisse if exist: " + caisseDTO, e);
        }
    }

}
