package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.config.requests.ComptaRequest;
import com.kabir.kabirbackend.config.responses.ComptaResponse;
import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.config.searchEntities.ComptaSearch;
import com.kabir.kabirbackend.dto.ChequeDTO;
import com.kabir.kabirbackend.dto.ComptaDTO;
import com.kabir.kabirbackend.service.ComptaService;
import jakarta.validation.Valid;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/compta")
class ComptaController {

    private final Logger logger = LoggerFactory.getLogger(ComptaController.class);
    private final ComptaService comptaService;

    public ComptaController(ComptaService comptaService) {
        this.comptaService = comptaService;
    }

    @GetMapping
    public ResponseEntity<List<ComptaDTO>> findAll() {
        logger.info("Finding all compta");
        try {
            return ResponseEntity.ok(comptaService.findAll());
        } catch (Exception e) {
            logger.error("Error finding all compta", e);
            throw new RuntimeException("Error finding all compta", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComptaDTO> findById(@PathVariable Long id) {
        logger.info("Finding compta by id: {}", id);
        try {
            ComptaDTO comptaDTO = comptaService.findById(id);
            if (comptaDTO == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(comptaDTO);
            }
        } catch (Exception e) {
            logger.error("Error finding compta by id: {}", id, e);
            throw new RuntimeException("Error finding compta by id: " + id, e);
        }
    }

    @PostMapping
    public ResponseEntity<ComptaDTO> save(@Valid @RequestBody ComptaDTO comptaDTO) {
        logger.info("Saving compta: {}", comptaDTO);
        try {
            ComptaDTO updatedAbsenceDTO = comptaService.save(comptaDTO);
            return ResponseEntity.ok(updatedAbsenceDTO);
        } catch (Exception e) {
            logger.error("Error saving compta: {}", comptaDTO, e);
            throw new RuntimeException("Error saving compta: " + comptaDTO, e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ComptaDTO> update(@PathVariable Long id, @Valid @RequestBody ComptaDTO comptaDTO) {
        logger.info("Updating compta: {}, with ID {} : ", comptaDTO, id);
        try {
            ComptaDTO updatedAbsenceDTO = comptaService.save(comptaDTO);
            return ResponseEntity.ok(updatedAbsenceDTO);
        } catch (Exception e) {
            logger.error("Error updating compta: {}, with ID: {}", comptaDTO, id, e);
            throw new RuntimeException("Error updating compta: " + comptaDTO, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting compta: {}", id);
        try {
            comptaService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting compta: {}", id, e);
            throw new RuntimeException("Error deleting compta: " + id, e);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<ComptaDTO>> search(@RequestBody ComptaSearch comptaSearch) {
        logger.info("Searching compta: {}", comptaSearch);
        try {
            return ResponseEntity.ok(comptaService.search(comptaSearch));
        } catch (Exception e) {
            logger.error("Error searching compta: {}", comptaSearch, e);
            throw new RuntimeException("Error searching compta: " + comptaSearch, e);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody ComptaSearch comptaSearch) {
        logger.info("Searching compta if exist: {}", comptaSearch);
        try {
            return ResponseEntity.ok(CollectionUtils.isNotEmpty(comptaService.search(comptaSearch)));
        } catch (Exception e) {
            logger.error("Error searching compta if exist: {}", comptaSearch, e);
            throw new RuntimeException("Error searching compta if exist: " + comptaSearch, e);
        }
    }

    @GetMapping("/last")
    public ResponseEntity<ComptaDTO> getLastCompta() {
        logger.info("Getting last compta");
        try {
            ComptaDTO comptaDTO = comptaService.getLastCompta();
            if(null != comptaDTO) return ResponseEntity.ok(comptaDTO);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error getting last compta", e);
            throw new RuntimeException("Error getting last compta", e);
        }
    }

    @PostMapping("/global-sums")
    public ResponseEntity<ComptaResponse> getGlobalSums(@RequestBody ComptaRequest comptaRequest) {
        logger.info("Getting global sums");
        try {
            return ResponseEntity.ok(comptaService.getGlobalSums(comptaRequest));
        } catch (Exception e) {
            logger.error("Error getting global sums", e);
            throw new RuntimeException("Error getting global sums", e);
        }
    }

    @PostMapping("/check-is-last")
    public ResponseEntity<Boolean> checkIsLast(@RequestBody ComptaSearch comptaSearch) {
        logger.info("Checking if compta is last {}", comptaSearch);
        try {
            return ResponseEntity.ok(comptaService.checkIsLast(comptaSearch));
        } catch (Exception e) {
            logger.error("Error checking if compta is last", e);
            throw new RuntimeException("Error checking if compta is last", e);
        }
    }

    @PostMapping("/searchByCommon")
    public ResponseEntity<List<ComptaDTO>> searchByCommon(@RequestBody CommonSearchModel commonSearchModel) {
        logger.info("Searching compta by common: {}", commonSearchModel);
        try {
            return ResponseEntity.ok(comptaService.searchByCommon(commonSearchModel));
        } catch (Exception e) {
            logger.error("Error searching compta by common: {}", commonSearchModel, e);
            throw new RuntimeException("Error searching compta by common: " + commonSearchModel, e);
        }
    }

}
