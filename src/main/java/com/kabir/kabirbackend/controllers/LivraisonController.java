package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.config.responses.LivraisonResponse;
import com.kabir.kabirbackend.dto.DetLivraisonDTO;
import com.kabir.kabirbackend.dto.LivraisonDTO;
import com.kabir.kabirbackend.service.DetLivraisonService;
import com.kabir.kabirbackend.service.LivraisonService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/livraison")
class LivraisonController {

    private final Logger logger = LoggerFactory.getLogger(LivraisonController.class);
    private final LivraisonService livraisonService;
    private final DetLivraisonService detLivraisonService;

    public LivraisonController(LivraisonService livraisonService, DetLivraisonService detLivraisonService) {
        this.livraisonService = livraisonService;
        this.detLivraisonService = detLivraisonService;
    }

    /*
    getAll: `${BASE_URL}/livraison`,
    getById: (id: bigint) => `${BASE_URL}/livraison/${id}`,
    getByIdWithDetLivraison: (id: bigint) => `${BASE_URL}/livraison/${id}/with-det-livraison`,
    create: `${BASE_URL}/livraison`,
    update: (id: bigint) => `${BASE_URL}/livraison/${id}`,
    delete: (id: bigint) => `${BASE_URL}/livraison/${id}`,
    exist: `${BASE_URL}/livraison/exist`,
    search: `${BASE_URL}/livraison/search`,
    present: `${BASE_URL}/livraison/present`,
    getLastNumLivraison: `${BASE_URL}/livraison/last-num-livraison`

    export interface LivraisonRequest {
        livraison: Livraison;
        detLivraisons: DetLivraison[];
    }

    id: bigint | null;
  numLivraison: number;
  codeBl: string;
  dateBl: Date;
  dateReglement: Date;
  dateReglement2: Date | null;
  dateReglement3: Date | null;
  dateReglement4: Date | null;
  typeReglment: number;
  typeReglment2: number;
  typeReglment3: number;
  typeReglment4: number;
  mantantBL: number;
  mantantBLReel: number;
  mantantBLBenefice: number;
  typePaiement: string;
  mantantBLPourcent: number;
  reglerNonRegler: number;
  sysDate: Date;
  infinity: number;
  etatBultinPaie: number;
  livrernonlivrer: number;
  avecRemise: boolean;
  mntReglement: number;
  mntReglement2: number;
  mntReglement3: number;
  mntReglement4: number;
  facturer100: boolean;
  codeTransport: string;
  employeOperateurId: bigint | null;
  employeOperateur?: Employe | null;
  personnel: Personnel | null;
  personnelId: bigint;
  personnelAncien: Personnel | null;
  personnelAncienId?: bigint | null;
  fournisseur: Fournisseur | null;
  fournisseurId: bigint;

     */

    @GetMapping
    public ResponseEntity<List<LivraisonDTO>> getAll() {
        logger.info("Fetching all livraisons");
        try {
            List<LivraisonDTO> livraisons = livraisonService.findAll();
            return ResponseEntity.ok(livraisons);
        } catch (Exception e) {
            logger.error("Error fetching all livraisons: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivraisonDTO> getById(@PathVariable Long id) {
        logger.info("Fetching livraison with id: {}", id);
        try {
            LivraisonDTO livraison = livraisonService.findById(id);
            return ResponseEntity.ok(livraison);
        } catch (Exception e) {
            logger.error("Error fetching livraison with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}/with-det-livraison")
    public ResponseEntity<LivraisonResponse> getByIdWithDetLivraison(@PathVariable Long id) {
        logger.info("Fetching livraison and detLivraison with id: {}", id);
        try {
            LivraisonDTO livraison = livraisonService.findById(id);
            List<DetLivraisonDTO> detLivraisons = detLivraisonService.DetLivraisonByLivraisonId(id);
            return ResponseEntity.ok(new LivraisonResponse(livraison, detLivraisons));
        } catch (Exception e) {
            logger.error("Error fetching livraison and detLivraison with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<LivraisonDTO> create(@RequestBody LivraisonDTO livraisonDTO) {
        logger.info("Creating livraison: {}", livraisonDTO);
        try {
            LivraisonDTO createdLivraison = livraisonService.save(livraisonDTO);
            return ResponseEntity.ok(createdLivraison);
        } catch (Exception e) {
            logger.error("Error creating livraison: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody LivraisonDTO livraisonDTO) {
        logger.info("Checking if livraison exists: " + livraisonDTO);
        try {
            List<LivraisonDTO> livraisons = livraisonService.search(livraisonDTO);
            return ResponseEntity.ok(CollectionUtils.isNotEmpty(livraisons));
        } catch (Exception e) {
            logger.error("Error checking if livraison exists: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<LivraisonDTO>> search(@RequestBody LivraisonDTO livraisonDTO) {
        logger.info("Searching livraison: " + livraisonDTO);
        try {
            List<LivraisonDTO> livraisons = livraisonService.search(livraisonDTO);
            return ResponseEntity.ok(livraisons);
        } catch (Exception e) {
            logger.error("Error searching livraison: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/present")
    public ResponseEntity<List<LivraisonDTO>> present(@RequestBody LivraisonDTO livraisonDTO) {
        logger.info("Presenting livraison: " + livraisonDTO);
        try {
            List<LivraisonDTO> livraisons = livraisonService.search(livraisonDTO);
            return ResponseEntity.ok(livraisons);
        } catch (Exception e) {
            logger.error("Error presenting livraison: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/last-num-livraison")
    public ResponseEntity<Integer> getLastNumLivraison(@RequestBody LivraisonDTO livraisonDTO) {
        logger.info("Getting last num livraison");
        try {
            int lastNumLivraison = livraisonService.getLastNumLivraison(livraisonDTO);
            return ResponseEntity.ok(lastNumLivraison);
        } catch (Exception e) {
            logger.error("Error getting last num livraison: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting livraison id: {}", id);
        try {
            List<DetLivraisonDTO> detLivraisons = detLivraisonService.DetLivraisonByLivraisonId(id);
            if (CollectionUtils.isNotEmpty(detLivraisons)) {
                for (DetLivraisonDTO detLivraisonDTO : detLivraisons) {
                    detLivraisonService.delete(detLivraisonDTO.getId());
                }
            }
            livraisonService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting livraison: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }
}
