package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.config.responses.BulletinPaiResponse;
import com.kabir.kabirbackend.dto.BulletinPaiDTO;
import com.kabir.kabirbackend.dto.FactureDTO;
import com.kabir.kabirbackend.service.BulletinPaiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bulletin-pai")
class BulletinPaiController {

    private static final Logger logger = LoggerFactory.getLogger(BulletinPaiController.class);

    private final BulletinPaiService bulletinPaiService;

    public BulletinPaiController(BulletinPaiService bulletinPaiService) {
        this.bulletinPaiService = bulletinPaiService;
    }

    @GetMapping
    public ResponseEntity<List<BulletinPaiDTO>> getAll() {
        logger.info("Finding all factures");
        try {
            return ResponseEntity.ok(bulletinPaiService.findAll());
        } catch (Exception e) {
            logger.error("Error finding all factures", e);
            throw new RuntimeException("Error finding all factures", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BulletinPaiDTO> getById(@PathVariable Long id) {
        logger.info("Finding bulletinPai by id: {}", id);
        try {
            return ResponseEntity.ok(bulletinPaiService.findById(id));
        } catch (Exception e) {
            logger.error("Error finding bulletinPai by id: {}", id, e);
            throw new RuntimeException("Error finding bulletinPai by id: " + id, e);
        }
    }

    @GetMapping("/response/{id}")
    public ResponseEntity<BulletinPaiResponse> getByIdBulletinPaiResponse(@PathVariable Long id) {
        logger.info("Finding bulletinPai response by id: {}", id);
        try {
            return ResponseEntity.ok(bulletinPaiService.findByIdBulletinPaiResponse(id));
        } catch (Exception e) {
            logger.error("Error finding bulletinPai response by id: {}", id, e);
            throw new RuntimeException("Error finding bulletinPai response by id: " + id, e);
        }
    }

    @PostMapping
    public ResponseEntity<BulletinPaiDTO> create(@RequestBody BulletinPaiResponse stockDepotResponse) {
        logger.info("Creating bulletinPai response : {}", stockDepotResponse);
        try {
            return ResponseEntity.ok(bulletinPaiService.save(stockDepotResponse));
        } catch (Exception e) {
            logger.error("Error creating bulletinPai response : {}", stockDepotResponse, e);
            throw new RuntimeException("Error creating bulletinPai response : " + stockDepotResponse, e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BulletinPaiDTO> update(@PathVariable Long id, @RequestBody BulletinPaiResponse stockDepotResponse) {
        logger.info("Updating bulletinPai response : {}", stockDepotResponse);
        try {
            return ResponseEntity.ok(bulletinPaiService.save(stockDepotResponse));
        } catch (Exception e) {
            logger.error("Error updating bulletinPai response : {}", stockDepotResponse, e);
            throw new RuntimeException("Error updating bulletinPai response : " + stockDepotResponse, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting bulletinPai by id: {}", id);
        try {
            bulletinPaiService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting bulletinPai by id: {}", id, e);
            throw new RuntimeException("Error deleting bulletinPai by id: " + id, e);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody BulletinPaiDTO bulletinPaiDTO) {
        logger.info("Search if bulletinPai exist : {}", bulletinPaiDTO);
        return ResponseEntity.ok(bulletinPaiService.searchIfExist(bulletinPaiDTO));
    }

    @GetMapping("/last-num")
    public ResponseEntity<Integer> getLastNum() {
        logger.info("Getting last num bulletinPai");
        try {
            int lastNum = bulletinPaiService.getLast();
            return ResponseEntity.ok(lastNum);
        } catch (Exception e) {
            logger.error("Error getting last num facture: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/details")
    public ResponseEntity<BulletinPaiResponse> getDetails(@RequestBody BulletinPaiDTO bulletinPaiDTO) {
        logger.info("Getting details bulletinPai : {}", bulletinPaiDTO);
        try {
            return ResponseEntity.ok(bulletinPaiService.getDetails(bulletinPaiDTO));
        } catch (Exception e) {
            logger.error("Error getting details bulletinPai : {}", bulletinPaiDTO, e);
            throw new RuntimeException("Error getting details bulletinPai : " + bulletinPaiDTO, e);
        }
    }

}