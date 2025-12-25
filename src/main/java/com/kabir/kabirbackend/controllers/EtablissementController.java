package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.dto.EtablissementDTO;
import com.kabir.kabirbackend.service.EtablissementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/etablissement")
public class EtablissementController {

    private final Logger logger = LoggerFactory.getLogger(EtablissementController.class);
    private final EtablissementService etablissementService;

    public EtablissementController(EtablissementService etablissementService) {
        this.etablissementService = etablissementService;
    }



    /*
    getAll: `${BASE_URL}/etablissement`,
    getById: (id: bigint) => `${BASE_URL}/etablissement/${id}`,
    create: `${BASE_URL}/etablissement`,
    update: (id: bigint) => `${BASE_URL}/etablissement/${id}`,
    delete: (id: bigint) => `${BASE_URL}/etablissement/${id}`,

    id: bigint | null;
  nom: string;
  cheminBD: string;
  tel1: string;
  tel2: string;
  tel3: string;
  fax: string;
  gsm: string;
  email: string;
  siteweb: string;
  cnss: string;
  patente: string;
  adresse: string;
  raisonSocial: string;
  ice: string;
  ife: string;
  rc: string;
  port: number;
  hostMail: string;
  paswordMail: string;
  paswordMailFake: string;
  fromMail: string;
  userMail: string;
  capitale: number;
  pourcentageLiv: number;
  lienDbDump: string;
  lienBackupDB: string;
  lundi: boolean;
  mardi: boolean;
  mercredi: boolean;
  jeudi: boolean;
  vendredi: boolean;
  samedi: boolean;
  dimanche: boolean;
  dateTime: Date;
  typeExec: number;
  numJour: number;
  villeId: bigint | null;
  ville?: Ville | null;
     */

    @GetMapping
    public ResponseEntity<List<EtablissementDTO>> getAll() {
        logger.info("Fetching all etablissements");
        try {
            List<EtablissementDTO> etablissements = etablissementService.findAll();
            return ResponseEntity.ok(etablissements);
        } catch (Exception e) {
            logger.error("Error fetching all etablissements: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtablissementDTO> getById(@PathVariable Long id) {
        logger.info("Fetching etablissement with id: {}", id);
        try {
            EtablissementDTO etablissement = etablissementService.findById(id);
            return ResponseEntity.ok(etablissement);
        } catch (Exception e) {
            logger.error("Error fetching etablissement with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<EtablissementDTO> create(@RequestBody EtablissementDTO etablissementDTO) {
        logger.info("Creating etablissement: {}", etablissementDTO);
        try {
            EtablissementDTO createdEtablissement = etablissementService.save(etablissementDTO);
            return ResponseEntity.ok(createdEtablissement);
        } catch (Exception e) {
            logger.error("Error creating etablissement: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EtablissementDTO> update(@PathVariable Long id, @RequestBody EtablissementDTO etablissementDTO) {
        logger.info("Updating etablissement with id: {}", id);
        try {
            EtablissementDTO updatedEtablissement = etablissementService.save(etablissementDTO);
            return ResponseEntity.ok(updatedEtablissement);
        } catch (Exception e) {
            logger.error("Error updating etablissement with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting etablissement with id: {}", id);
        try {
            etablissementService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting etablissement with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }
}
