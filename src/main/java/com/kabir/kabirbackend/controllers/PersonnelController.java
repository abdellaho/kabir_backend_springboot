package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.dto.PersonnelDTO;
import com.kabir.kabirbackend.service.PersonnelService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personnel")
class PersonnelController {

    private final Logger logger = LoggerFactory.getLogger(PersonnelController.class);
    private final PersonnelService personnelService;

    public PersonnelController(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    /*
    getAll: `${BASE_URL}/personnel`,
    getAllAlowed: `${BASE_URL}/personnel/allowed`,
    getById: (id: bigint) => `${BASE_URL}/personnel/${id}`,
    create: `${BASE_URL}/personnel`,
    update: (id: bigint) => `${BASE_URL}/personnel/${id}`,
    delete: (id: bigint) => `${BASE_URL}/personnel/${id}`,
    exist: `${BASE_URL}/personnel/exist`,
    search: `${BASE_URL}/personnel/search`,
    present: `${BASE_URL}/personnel/present`

    present(dateAbsence: Date): Observable<Personnel[]> {
    let body = { dateAbsence };
    return this.http.post<Personnel[]>(ENDPOINTS.PERSONNEL.present, body);
  }

    id: bigint | null;
    designation: string;
    cin: string;
    login: string;
    password: string;
    typePersonnel: number;
    etatComptePersonnel: boolean;
    tel1: string;
    tel2: string;
    adresse: string;
    email: string;
    dateEntrer: Date;
    dateSuppression: Date | null;
    salaire: number;
    archiver: boolean;
    supprimer: boolean;
    consulterStock: boolean;
    ajouterStock: boolean;
    modifierStock: boolean;
    supprimerStock: boolean;
    consulterRepertoire: boolean;
    ajouterRepertoire: boolean;
    modifierRepertoire: boolean;
    supprimerRepertoire: boolean;
     */

    @GetMapping
    public ResponseEntity<List<PersonnelDTO>> getAll() {
        logger.info("Fetching all personnel");
        try {
            List<PersonnelDTO> personnel = personnelService.findAll();
            return ResponseEntity.ok(personnel);
        } catch (Exception e) {
            logger.error("Error fetching all personnel: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/allowed")
    public ResponseEntity<List<PersonnelDTO>> getAllAllowed(@RequestBody PersonnelDTO personnelDTO) {
        logger.info("Fetching all allowed personnel");
        try {
            List<PersonnelDTO> personnel = personnelService.search(personnelDTO);
            return ResponseEntity.ok(personnel);
        } catch (Exception e) {
            logger.error("Error fetching all allowed personnel: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonnelDTO> getById(@PathVariable Long id) {
        logger.info("Fetching personnel with id: {}", id);
        try {
            PersonnelDTO personnel = personnelService.findById(id);
            return ResponseEntity.ok(personnel);
        } catch (Exception e) {
            logger.error("Error fetching personnel with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<PersonnelDTO> create(@RequestBody PersonnelDTO personnelDTO) {
        logger.info("Creating personnel: {}", personnelDTO);
        try {
            PersonnelDTO createdPersonnel = personnelService.save(personnelDTO);
            return ResponseEntity.ok(createdPersonnel);
        } catch (Exception e) {
            logger.error("Error creating personnel: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PersonnelDTO> update(@PathVariable Long id, @RequestBody PersonnelDTO personnelDTO) {
        logger.info("Updating personnel with id: {}", id);
        try {
            PersonnelDTO updatedPersonnel = personnelService.save(personnelDTO);
            return ResponseEntity.ok(updatedPersonnel);
        } catch (Exception e) {
            logger.error("Error updating personnel with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonnelDTO> delete(@PathVariable Long id) {
        logger.info("Deleting personnel with id: {}", id);
        try {
            personnelService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting personnel with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody PersonnelDTO personnelDTO) {
        logger.info("Checking if personnel exists: " + personnelDTO);
        try {
            List<PersonnelDTO> personnel = personnelService.search(personnelDTO);
            return ResponseEntity.ok(CollectionUtils.isNotEmpty(personnel));
        } catch (Exception e) {
            logger.error("Error checking if personnel exists: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<PersonnelDTO>> search(@RequestBody PersonnelDTO personnelDTO) {
        logger.info("Searching personnel: " + personnelDTO);
        try {
            List<PersonnelDTO> personnel = personnelService.searchBySupprimerOrArchiver(personnelDTO);
            return ResponseEntity.ok(personnel);
        } catch (Exception e) {
            logger.error("Error searching personnel: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/present")
    public ResponseEntity<List<PersonnelDTO>> present(@RequestBody PersonnelDTO personnelDTO) {
        logger.info("Presenting personnel: " + personnelDTO);
        try {
            List<PersonnelDTO> personnel = personnelService.search(personnelDTO);
            return ResponseEntity.ok(personnel);
        } catch (Exception e) {
            logger.error("Error presenting personnel: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

}
