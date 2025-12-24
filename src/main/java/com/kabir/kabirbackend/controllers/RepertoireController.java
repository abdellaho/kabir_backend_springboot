package com.kabir.kabirbackend.controllers;


import com.kabir.kabirbackend.dto.RepertoireDTO;
import com.kabir.kabirbackend.service.RepertoireService;
import jakarta.validation.Valid;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repertoire")
public class RepertoireController {

    private final Logger logger = LoggerFactory.getLogger(RepertoireController.class);
    private final RepertoireService repertoireService;

    public RepertoireController(RepertoireService repertoireService) {
        this.repertoireService = repertoireService;
    }

    /*
    getAll: `${BASE_URL}/repertoire`,
    getById: (id: bigint) => `${BASE_URL}/repertoire/${id}`,
    create: `${BASE_URL}/repertoire`,
    update: (id: bigint) => `${BASE_URL}/repertoire/${id}`,
    delete: (id: bigint) => `${BASE_URL}/repertoire/${id}`,
    exist: `${BASE_URL}/repertoire/exist`,
    search: `${BASE_URL}/repertoire/search`,
    searchPersonnel: `${BASE_URL}/repertoire/search-personnel`,
     */

    @GetMapping
    public ResponseEntity<List<RepertoireDTO>> findAll() {
        logger.info("Finding all repertoires");
        try {
            return ResponseEntity.ok(repertoireService.findAll());
        } catch (Exception e) {
            logger.error("Error finding all repertoires", e);
            throw new RuntimeException("Error finding all repertoires", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepertoireDTO> findById(@PathVariable Long id) {
        logger.info("Finding repertoire by id: {}", id);
        try {
            RepertoireDTO repertoireDTO = repertoireService.findById(id);
            if (repertoireDTO == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(repertoireDTO);
            }
        } catch (Exception e) {
            logger.error("Error finding repertoire by id: {}", id, e);
            throw new RuntimeException("Error finding repertoire by id: " + id, e);
        }
    }

    @PostMapping
    public ResponseEntity<RepertoireDTO> save(@Valid @RequestBody RepertoireDTO repertoireDTO) {
        logger.info("Saving repertoire: {}", repertoireDTO);
        try {
            RepertoireDTO updatedRepertoireDTO = repertoireService.save(repertoireDTO);
            return ResponseEntity.ok(updatedRepertoireDTO);
        } catch (Exception e) {
            logger.error("Error saving repertoire: {}", repertoireDTO, e);
            throw new RuntimeException("Error saving repertoire: " + repertoireDTO, e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RepertoireDTO> update(@PathVariable Long id, @Valid @RequestBody RepertoireDTO repertoireDTO) {
        logger.info("Updating repertoire: {}, with ID {} : ", repertoireDTO, id);
        try {
            RepertoireDTO updatedRepertoireDTO = repertoireService.save(repertoireDTO);
            return ResponseEntity.ok(updatedRepertoireDTO);
        } catch (Exception e) {
            logger.error("Error updating repertoire: {}, with ID: {}", repertoireDTO, id, e);
            throw new RuntimeException("Error updating repertoire: " + repertoireDTO, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting repertoire: {}", id);
        try {
            repertoireService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting repertoire: {}", id, e);
            throw new RuntimeException("Error deleting repertoire: " + id, e);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<RepertoireDTO>> search(@RequestBody RepertoireDTO repertoireDTO) {
        logger.info("Searching repertoire: {}", repertoireDTO);
        try {
            return ResponseEntity.ok(repertoireService.search(repertoireDTO));
        } catch (Exception e) {
            logger.error("Error searching repertoire: {}", repertoireDTO, e);
            throw new RuntimeException("Error searching repertoire: " + repertoireDTO, e);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody RepertoireDTO repertoireDTO) {
        logger.info("Searching repertoire if exist: {}", repertoireDTO);
        try {
            return ResponseEntity.ok(CollectionUtils.isNotEmpty(repertoireService.exist(repertoireDTO)));
        } catch (Exception e) {
            logger.error("Error searching repertoire if exist: {}", repertoireDTO, e);
            throw new RuntimeException("Error searching repertoire if exist: " + repertoireDTO, e);
        }
    }

    @PostMapping("/search-personnel")
    public ResponseEntity<List<RepertoireDTO>> searchPersonnel(@RequestBody RepertoireDTO repertoireDTO) {
        logger.info("Searching repertoire by personnel: {}", repertoireDTO);
        try {
            return ResponseEntity.ok(repertoireService.search(repertoireDTO));
        } catch (Exception e) {
            logger.error("Error searching repertoire by personnel: {}", repertoireDTO, e);
            throw new RuntimeException("Error searching repertoire by personnel: " + repertoireDTO, e);
        }
    }
}
