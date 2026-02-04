package com.kabir.kabirbackend.controllers;


import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.ChequeDTO;
import com.kabir.kabirbackend.service.ChequeService;
import jakarta.validation.Valid;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cheque")
public class ChequeController {

    private final Logger logger = LoggerFactory.getLogger(ChequeController.class);
    private final ChequeService chequeService;

    public ChequeController(ChequeService chequeService) {
        this.chequeService = chequeService;
    }

    @GetMapping
    public ResponseEntity<List<ChequeDTO>> findAll() {
        logger.info("Finding all cheques");
        try {
            return ResponseEntity.ok(chequeService.findAll());
        } catch (Exception e) {
            logger.error("Error finding all cheques", e);
            throw new RuntimeException("Error finding all cheques", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChequeDTO> findById(@PathVariable Long id) {
        logger.info("Finding cheque by id: {}", id);
        try {
            ChequeDTO chequeDTO = chequeService.findById(id);
            if (chequeDTO == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(chequeDTO);
            }
        } catch (Exception e) {
            logger.error("Error finding cheque by id: {}", id, e);
            throw new RuntimeException("Error finding cheque by id: " + id, e);
        }
    }

    @PostMapping
    public ResponseEntity<ChequeDTO> save(@Valid @RequestBody ChequeDTO chequeDTO) {
        logger.info("Saving cheque: {}", chequeDTO);
        try {
            ChequeDTO updatedAbsenceDTO = chequeService.save(chequeDTO);
            return ResponseEntity.ok(updatedAbsenceDTO);
        } catch (Exception e) {
            logger.error("Error saving cheque: {}", chequeDTO, e);
            throw new RuntimeException("Error saving cheque: " + chequeDTO, e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ChequeDTO> update(@PathVariable Long id, @Valid @RequestBody ChequeDTO chequeDTO) {
        logger.info("Updating cheque: {}, with ID {} : ", chequeDTO, id);
        try {
            ChequeDTO updatedAbsenceDTO = chequeService.save(chequeDTO);
            return ResponseEntity.ok(updatedAbsenceDTO);
        } catch (Exception e) {
            logger.error("Error updating cheque: {}, with ID: {}", chequeDTO, id, e);
            throw new RuntimeException("Error updating cheque: " + chequeDTO, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting cheque: {}", id);
        try {
            chequeService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting cheque: {}", id, e);
            throw new RuntimeException("Error deleting cheque: " + id, e);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<ChequeDTO>> search(@RequestBody ChequeDTO chequeDTO) {
        logger.info("Searching cheque: {}", chequeDTO);
        try {
            return ResponseEntity.ok(chequeService.search(chequeDTO));
        } catch (Exception e) {
            logger.error("Error searching cheque: {}", chequeDTO, e);
            throw new RuntimeException("Error searching cheque: " + chequeDTO, e);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody ChequeDTO chequeDTO) {
        logger.info("Searching cheque if exist: {}", chequeDTO);
        try {
            return ResponseEntity.ok(CollectionUtils.isNotEmpty(chequeService.search(chequeDTO)));
        } catch (Exception e) {
            logger.error("Error searching cheque if exist: {}", chequeDTO, e);
            throw new RuntimeException("Error searching cheque if exist: " + chequeDTO, e);
        }
    }

    @GetMapping("/last-num")
    public ResponseEntity<Integer> getLastNum() {
        logger.info("Getting last num cheque");
        try {
            return ResponseEntity.ok(chequeService.getLastNum());
        } catch (Exception e) {
            logger.error("Error getting last num cheque", e);
            throw new RuntimeException("Error getting last num cheque", e);
        }
    }

    @PostMapping("/searchByCommon")
    public ResponseEntity<List<ChequeDTO>> searchByCommon(@RequestBody CommonSearchModel commonSearchModel) {
        logger.info("Searching cheque by common: {}", commonSearchModel);
        try {
            return ResponseEntity.ok(chequeService.searchByCommon(commonSearchModel));
        } catch (Exception e) {
            logger.error("Error searching cheque by common: {}", commonSearchModel, e);
            throw new RuntimeException("Error searching cheque by common: " + commonSearchModel, e);
        }
    }

}
