package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.config.responses.AchatFactureResponse;
import com.kabir.kabirbackend.config.responses.StockDepotResponse;
import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.AbsenceDTO;
import com.kabir.kabirbackend.dto.AchatFactureDTO;
import com.kabir.kabirbackend.dto.AchatFactureDTO;
import com.kabir.kabirbackend.dto.LivraisonDTO;
import com.kabir.kabirbackend.service.AchatFactureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/achat-facture")
class AchatFactureController {

    private static final Logger logger = LoggerFactory.getLogger(AchatFactureController.class);

    private final AchatFactureService achatFactureService;

    public AchatFactureController(AchatFactureService achatFactureService) {
        this.achatFactureService = achatFactureService;
    }

    @GetMapping
    public ResponseEntity<List<AchatFactureDTO>> getAll() {
        logger.info("Finding all achat factures");
        try {
            return ResponseEntity.ok(achatFactureService.findAll());
        } catch (Exception e) {
            logger.error("Error finding all achat factures", e);
            throw new RuntimeException("Error finding all achat factures", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchatFactureDTO> getById(@PathVariable Long id) {
        logger.info("Finding achat facture by id: {}", id);
        try {
            return ResponseEntity.ok(achatFactureService.findById(id));
        } catch (Exception e) {
            logger.error("Error finding achat facture by id: {}", id, e);
            throw new RuntimeException("Error finding achat facture by id: " + id, e);
        }
    }

    @GetMapping("/response/{id}")
    public ResponseEntity<AchatFactureResponse> getByIdStockDepotResponse(@PathVariable Long id) {
        logger.info("Finding achat facture response by id: {}", id);
        try {
            return ResponseEntity.ok(achatFactureService.findByIdAchatSimpleResponse(id));
        } catch (Exception e) {
            logger.error("Error finding achat facture response by id: {}", id, e);
            throw new RuntimeException("Error finding achat facture response by id: " + id, e);
        }
    }

    @PostMapping
    public ResponseEntity<AchatFactureDTO> create(@RequestBody AchatFactureResponse achatFactureResponse) {
        logger.info("Creating achat facture response : {}", achatFactureResponse);
        try {
            return ResponseEntity.ok(achatFactureService.save(achatFactureResponse));
        } catch (Exception e) {
            logger.error("Error creating achat facture response : {}", achatFactureResponse, e);
            throw new RuntimeException("Error creating achat facture response : " + achatFactureResponse, e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AchatFactureDTO> update(@PathVariable Long id, @RequestBody AchatFactureResponse achatFactureResponse) {
        logger.info("Updating achat facture response : {}", achatFactureResponse);
        try {
            return ResponseEntity.ok(achatFactureService.save(achatFactureResponse));
        } catch (Exception e) {
            logger.error("Error updating achat facture response : {}", achatFactureResponse, e);
            throw new RuntimeException("Error updating achat facture response : " + achatFactureResponse, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting achat facture by id: {}", id);
        try {
            achatFactureService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting achat facture by id: {}", id, e);
            throw new RuntimeException("Error deleting achat facture by id: " + id, e);
        }
    }

    @PostMapping("/last-num-achat-facture")
    public ResponseEntity<Integer> getLastNumLivraison(@RequestBody AchatFactureDTO achatFactureDTO) {
        logger.info("Getting last num achat facture");
        try {
            int lastNumAchatFacture = achatFactureService.getLastNumAchatFacture(achatFactureDTO);
            return ResponseEntity.ok(lastNumAchatFacture);
        } catch (Exception e) {
            logger.error("Error getting last num achat facture: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody AchatFactureDTO achatFactureDTO) {
        logger.info("Searching if exist");
        try {
            return ResponseEntity.ok(achatFactureService.exist(achatFactureDTO));
        } catch (Exception e) {
            logger.error("Error searching if an existing achat facture: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/searchByCommon")
    public ResponseEntity<List<AchatFactureDTO>> searchByCommon(@RequestBody CommonSearchModel commonSearchModel) {
        logger.info("Searching achat facture by common: {}", commonSearchModel);
        try {
            return ResponseEntity.ok(achatFactureService.searchByCommon(commonSearchModel));
        } catch (Exception e) {
            logger.error("Error searching achat facture by common: {}", commonSearchModel, e);
            throw new RuntimeException("Error searching achat facture by common: " + commonSearchModel, e);
        }
    }

}
