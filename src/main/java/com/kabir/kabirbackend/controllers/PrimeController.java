package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.dto.PrimeDTO;
import com.kabir.kabirbackend.service.PrimeService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prime")
class PrimeController {
    private final Logger logger = LoggerFactory.getLogger(PrimeController.class);
    private final PrimeService primeService;

    public PrimeController(PrimeService primeService) {
        this.primeService = primeService;
    }

    /*
    getAll: `${BASE_URL}/prime`,
    getById: (id: bigint) => `${BASE_URL}/prime/${id}`,
    create: `${BASE_URL}/prime`,
    update: (id: bigint) => `${BASE_URL}/prime/${id}`,
    delete: (id: bigint) => `${BASE_URL}/prime/${id}`,
    search: `${BASE_URL}/prime/search`,

    id: bigint | null;
    montant: number;
    prime: number;
     */

    @GetMapping
    public ResponseEntity<List<PrimeDTO>> getAll() {
        logger.info("Fetching all primes");
        try {
            List<PrimeDTO> primes = primeService.findAll();
            return ResponseEntity.ok(primes);
        } catch (Exception e) {
            logger.error("Error fetching all primes: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrimeDTO> getById(@PathVariable Long id) {
        logger.info("Fetching prime with id: {}", id);
        try {
            PrimeDTO prime = primeService.findById(id);
            return ResponseEntity.ok(prime);
        } catch (Exception e) {
            logger.error("Error fetching prime with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<PrimeDTO> create(@RequestBody PrimeDTO primeDTO) {
        logger.info("Creating prime: {}", primeDTO);
        try {
            PrimeDTO createdPrime = primeService.save(primeDTO);
            return ResponseEntity.ok(createdPrime);
        } catch (Exception e) {
            logger.error("Error creating prime: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PrimeDTO> update(@PathVariable Long id, @RequestBody PrimeDTO primeDTO) {
        logger.info("Updating prime: {}", primeDTO);
        try {
            PrimeDTO createdPrime = primeService.save(primeDTO);
            return ResponseEntity.ok(createdPrime);
        } catch (Exception e) {
            logger.error("Error updating prime: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PrimeDTO> delete(@PathVariable Long id) {
        logger.info("Deleting prime with id: {}", id);
        try {
            primeService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting prime with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody PrimeDTO primeDTO) {
        logger.info("Checking if prime exists: {}", primeDTO);
        try {
            List<PrimeDTO> primes = primeService.search(primeDTO);
            return ResponseEntity.ok(CollectionUtils.isNotEmpty(primes));
        } catch (Exception e) {
            logger.error("Error checking if prime exists: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/search")
    public ResponseEntity<Boolean> search(@RequestBody PrimeDTO primeDTO) {
        logger.info("Searching primes: {}", primeDTO);
        try {
            List<PrimeDTO> primes = primeService.search(primeDTO);
            return ResponseEntity.ok(CollectionUtils.isNotEmpty(primes));
        } catch (Exception e) {
            logger.error("Error searching primes: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

}
