package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.config.responses.AchatSimpleResponse;
import com.kabir.kabirbackend.config.responses.StockDepotResponse;
import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.AchatSimpleDTO;
import com.kabir.kabirbackend.dto.AchatSimpleDTO;
import com.kabir.kabirbackend.service.AchatSimpleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/achat-simple")
class AchatSimpleController {
    
    private static final Logger logger = LoggerFactory.getLogger(AchatSimpleController.class);

    private final AchatSimpleService achatSimpleService;

    public AchatSimpleController(AchatSimpleService achatSimpleService) {
        this.achatSimpleService = achatSimpleService;
    }

    @GetMapping
    public ResponseEntity<List<AchatSimpleDTO>> getAll() {
        logger.info("Finding all achat simples");
        try {
            return ResponseEntity.ok(achatSimpleService.findAll());
        } catch (Exception e) {
            logger.error("Error finding all achat simples", e);
            throw new RuntimeException("Error finding all stock depots", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchatSimpleDTO> getById(@PathVariable Long id) {
        logger.info("Finding achat simple by id: {}", id);
        try {
            return ResponseEntity.ok(achatSimpleService.findById(id));
        } catch (Exception e) {
            logger.error("Error finding achat simple by id: {}", id, e);
            throw new RuntimeException("Error finding achat simple by id: " + id, e);
        }
    }

    @GetMapping("/response/{id}")
    public ResponseEntity<AchatSimpleResponse> getByIdStockDepotResponse(@PathVariable Long id) {
        logger.info("Finding achat simple response by id: {}", id);
        try {
            return ResponseEntity.ok(achatSimpleService.findByIdAchatSimpleResponse(id));
        } catch (Exception e) {
            logger.error("Error finding achat simple response by id: {}", id, e);
            throw new RuntimeException("Error finding achat simple response by id: " + id, e);
        }
    }

    @PostMapping
    public ResponseEntity<AchatSimpleDTO> create(@RequestBody AchatSimpleResponse achatSimpleResponse) {
        logger.info("Creating achat simple response : {}", achatSimpleResponse);
        try {
            return ResponseEntity.ok(achatSimpleService.save(achatSimpleResponse));
        } catch (Exception e) {
            logger.error("Error creating achat simple response : {}", achatSimpleResponse, e);
            throw new RuntimeException("Error creating achat simple response : " + achatSimpleResponse, e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AchatSimpleDTO> update(@PathVariable Long id, @RequestBody AchatSimpleResponse achatSimpleResponse) {
        logger.info("Updating achat simple response : {}", achatSimpleResponse);
        try {
            return ResponseEntity.ok(achatSimpleService.save(achatSimpleResponse));
        } catch (Exception e) {
            logger.error("Error updating achat simple response : {}", achatSimpleResponse, e);
            throw new RuntimeException("Error updating achat simple response : " + achatSimpleResponse, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting achat simple by id: {}", id);
        try {
            achatSimpleService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting achat simple by id: {}", id, e);
            throw new RuntimeException("Error deleting achat simple by id: " + id, e);
        }
    }

    @GetMapping("/searchByCommon")
    public ResponseEntity<List<AchatSimpleDTO>> search(@RequestBody CommonSearchModel commonSearchModel) {
        logger.info("Searching achat simple by common: {}", commonSearchModel);
        try {
            return ResponseEntity.ok(achatSimpleService.searchByCommon(commonSearchModel));
        } catch (Exception e) {
            logger.error("Error searching achat simple by common: {}", commonSearchModel, e);
            throw new RuntimeException("Error searching achat simple by common: " + commonSearchModel, e);
        }
    }

}
