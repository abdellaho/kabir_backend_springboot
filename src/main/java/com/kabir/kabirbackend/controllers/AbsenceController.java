package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.dto.AbsenceDTO;
import com.kabir.kabirbackend.service.AbsenceService;
import jakarta.validation.Valid;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/absence")
public class AbsenceController {

    /*
    getAll: `${BASE_URL}/absence`,
    getById: (id: bigint) => `${BASE_URL}/absence/${id}`,
    create: `${BASE_URL}/absence`,
    update: (id: bigint) => `${BASE_URL}/absence/${id}`,
    delete: (id: bigint) => `${BASE_URL}/absence/${id}`,
    exist: `${BASE_URL}/absence/exist`,
    search: `${BASE_URL}/absence/search`,

    id: bigint | null;
  dateAbsence: Date;
  matin: boolean;
  apresMidi: boolean;
  dateOperation: Date;
  personnelOperationId: bigint | null;
  personnelOperation?: Personnel | null;
  personnelId: bigint;
  personnel?: Personnel | null;
  dateAbsenceStr?: string;
     */

    private final Logger logger = LoggerFactory.getLogger(AbsenceController.class);
    private final AbsenceService absenceService;

    public AbsenceController(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @GetMapping
    public ResponseEntity<List<AbsenceDTO>> findAll() {
        logger.info("Finding all absences");
        try {
            return ResponseEntity.ok(absenceService.findAll());
        } catch (Exception e) {
            logger.error("Error finding all absences", e);
            throw new RuntimeException("Error finding all absences", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbsenceDTO> findById(@PathVariable Long id) {
        logger.info("Finding absence by id: {}", id);
        try {
            AbsenceDTO absenceDTO = absenceService.findById(id);
            if (absenceDTO == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(absenceDTO);
            }
        } catch (Exception e) {
            logger.error("Error finding absence by id: {}", id, e);
            throw new RuntimeException("Error finding absence by id: " + id, e);
        }
    }

    @PostMapping
    public ResponseEntity<AbsenceDTO> save(@Valid @RequestBody AbsenceDTO absenceDTO) {
        logger.info("Saving absence: {}", absenceDTO);
        try {
            AbsenceDTO updatedAbsenceDTO = absenceService.save(absenceDTO);
            return ResponseEntity.ok(updatedAbsenceDTO);
        } catch (Exception e) {
            logger.error("Error saving absence: {}", absenceDTO, e);
            throw new RuntimeException("Error saving absence: " + absenceDTO, e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AbsenceDTO> update(@PathVariable Long id, @Valid @RequestBody AbsenceDTO absenceDTO) {
        logger.info("Updating absence: {}, with ID {} : ", absenceDTO, id);
        try {
            AbsenceDTO updatedAbsenceDTO = absenceService.save(absenceDTO);
            return ResponseEntity.ok(updatedAbsenceDTO);
        } catch (Exception e) {
            logger.error("Error updating absence: {}, with ID: {}", absenceDTO, id, e);
            throw new RuntimeException("Error updating absence: " + absenceDTO, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting absence: {}", id);
        try {
            absenceService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting absence: {}", id, e);
            throw new RuntimeException("Error deleting absence: " + id, e);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<AbsenceDTO>> search(@RequestBody AbsenceDTO absenceDTO) {
        logger.info("Searching absence: {}", absenceDTO);
        try {
            return ResponseEntity.ok(absenceService.search(absenceDTO));
        } catch (Exception e) {
            logger.error("Error searching absence: {}", absenceDTO, e);
            throw new RuntimeException("Error searching absence: " + absenceDTO, e);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody AbsenceDTO absenceDTO) {
        logger.info("Searching absence if exist: {}", absenceDTO);
        try {
            return ResponseEntity.ok(CollectionUtils.isNotEmpty(absenceService.search(absenceDTO)));
        } catch (Exception e) {
            logger.error("Error searching absence if exist: {}", absenceDTO, e);
            throw new RuntimeException("Error searching absence if exist: " + absenceDTO, e);
        }
    }
}
