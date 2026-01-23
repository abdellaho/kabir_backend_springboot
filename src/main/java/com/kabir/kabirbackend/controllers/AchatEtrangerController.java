package com.kabir.kabirbackend.controllers;


import com.kabir.kabirbackend.config.responses.AchatEtrangerResponse;
import com.kabir.kabirbackend.dto.AchatEtrangerDTO;
import com.kabir.kabirbackend.service.AchatEtrangerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/achat-etranger")
public class AchatEtrangerController {

    private static final Logger logger = LoggerFactory.getLogger(AchatEtrangerController.class);

    private final AchatEtrangerService achatEtrangerService;

    public AchatEtrangerController(AchatEtrangerService achatEtrangerService) {
        this.achatEtrangerService = achatEtrangerService;
    }

    @GetMapping
    public ResponseEntity<List<AchatEtrangerDTO>> getAll() {
        logger.info("Finding all achat etrangers");
        try {
            return ResponseEntity.ok(achatEtrangerService.findAll());
        } catch (Exception e) {
            logger.error("Error finding all achat etrangers", e);
            throw new RuntimeException("Error finding all achat etrangers", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchatEtrangerDTO> getById(@PathVariable Long id) {
        logger.info("Finding achat etranger by id: {}", id);
        try {
            return ResponseEntity.ok(achatEtrangerService.findById(id));
        } catch (Exception e) {
            logger.error("Error finding achat etranger by id: {}", id, e);
            throw new RuntimeException("Error finding achat etranger by id: " + id, e);
        }
    }

    @GetMapping("/response/{id}")
    public ResponseEntity<AchatEtrangerResponse> getByIdAchatEtrangerResponse(@PathVariable Long id) {
        logger.info("Finding achat etranger response by id: {}", id);
        try {
            return ResponseEntity.ok(achatEtrangerService.findByIdAchatEtrangerResponse(id));
        } catch (Exception e) {
            logger.error("Error finding achat etranger response by id: {}", id, e);
            throw new RuntimeException("Error finding achat etranger response by id: " + id, e);
        }
    }

    @PostMapping
    public ResponseEntity<AchatEtrangerDTO> create(@RequestBody AchatEtrangerResponse achatEtrangerResponse) {
        logger.info("Creating achat etranger response : {}", achatEtrangerResponse);
        try {
            return ResponseEntity.ok(achatEtrangerService.save(achatEtrangerResponse));
        } catch (Exception e) {
            logger.error("Error creating achat etranger response : {}", achatEtrangerResponse, e);
            throw new RuntimeException("Error creating achat etranger response : " + achatEtrangerResponse, e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AchatEtrangerDTO> update(@PathVariable Long id, @RequestBody AchatEtrangerResponse achatEtrangerResponse) {
        logger.info("Updating achat etranger response : {}", achatEtrangerResponse);
        try {
            return ResponseEntity.ok(achatEtrangerService.save(achatEtrangerResponse));
        } catch (Exception e) {
            logger.error("Error updating achat etranger response : {}", achatEtrangerResponse, e);
            throw new RuntimeException("Error updating achat etranger response : " + achatEtrangerResponse, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting achat etranger by id: {}", id);
        try {
            achatEtrangerService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting achat etranger by id: {}", id, e);
            throw new RuntimeException("Error deleting achat etranger by id: " + id, e);
        }
    }

    @PostMapping("/last-num-achat-etranger")
    public ResponseEntity<Integer> getLastNumAchatEtranger(@RequestBody AchatEtrangerDTO achatFactureDTO) {
        logger.info("Getting last num achat etranger");
        try {
            int lastNumAchatFacture = achatEtrangerService.getLastNumAchatEtranger(achatFactureDTO);
            return ResponseEntity.ok(lastNumAchatFacture);
        } catch (Exception e) {
            logger.error("Error getting last num achat etranger: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody AchatEtrangerDTO achatFactureDTO) {
        logger.info("Searching if exist");
        try {
            return ResponseEntity.ok(achatEtrangerService.exist(achatFactureDTO));
        } catch (Exception e) {
            logger.error("Error searching if an existing achat etranger: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }
}
