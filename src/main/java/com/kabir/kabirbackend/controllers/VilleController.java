package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.config.enums.ReportTypeEnum;
import com.kabir.kabirbackend.config.util.JasperReportsUtil;
import com.kabir.kabirbackend.config.util.StaticVariables;
import com.kabir.kabirbackend.dto.VilleDTO;
import com.kabir.kabirbackend.service.VilleService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ville")
class VilleController {
    private static final Logger logger = LoggerFactory.getLogger(VilleController.class);
    private final VilleService villeService;
    private final JasperReportsUtil jasperReportsUtil;

    public VilleController(VilleService villeService, JasperReportsUtil jasperReportsUtil) {
        this.villeService = villeService;
        this.jasperReportsUtil = jasperReportsUtil;
    }

    /*
    getAll: `${BASE_URL}/ville`,
    getById: (id: bigint) => `${BASE_URL}/ville/${id}`,
    create: `${BASE_URL}/ville`,
    update: (id: bigint) => `${BASE_URL}/ville/${id}`,
    delete: (id: bigint) => `${BASE_URL}/ville/${id}`,
    exist: `${BASE_URL}/ville/exist`,

    id: bigint | null;
  nomVille: string;
     */

    @GetMapping
    public ResponseEntity<List<VilleDTO>> getAll() {
        logger.info("Fetching all villes");
        try {
            List<VilleDTO> villes = villeService.findAll();
            return ResponseEntity.ok(villes);
        } catch (Exception e) {
            logger.error("Error fetching all villes: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<VilleDTO> getById(@PathVariable Long id) {
        logger.info("Fetching ville with id: {}", id);
        try {
            VilleDTO ville = villeService.findById(id);
            return ResponseEntity.ok(ville);
        } catch (Exception e) {
            logger.error("Error fetching ville with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<VilleDTO> create(@RequestBody VilleDTO villeDTO) {
        logger.info("Creating ville: {}", villeDTO);
        try {
            VilleDTO createdVille = villeService.save(villeDTO);
            return ResponseEntity.ok(createdVille);
        } catch (Exception e) {
            logger.error("Error creating ville: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VilleDTO> update(@PathVariable Long id, @RequestBody VilleDTO villeDTO) {
        logger.info("Updating ville: {}", villeDTO);
        try {
            VilleDTO createdVille = villeService.save(villeDTO);
            return ResponseEntity.ok(createdVille);
        } catch (Exception e) {
            logger.error("Error updating ville: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VilleDTO> delete(@PathVariable Long id) {
        logger.info("Deleting ville with id: {}", id);
        try {
            villeService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting ville with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody VilleDTO villeDTO) {
        logger.info("Checking if ville exists: {}", villeDTO);
        try {
            List<VilleDTO> villes = villeService.search(villeDTO);
            return ResponseEntity.ok(CollectionUtils.isNotEmpty(villes));
        } catch (Exception e) {
            logger.error("Error checking if ville exists: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<VilleDTO>> search(@RequestBody VilleDTO villeDTO) {
        logger.info("Searching villes: {}", villeDTO);
        try {
            List<VilleDTO> villes = villeService.search(villeDTO);
            return ResponseEntity.ok(villes);
        } catch (Exception e) {
            logger.error("Error searching villes: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/imprimer")
    public ResponseEntity<?> imprimer() {
        logger.info("imprimer villes");
        try {
            Map<String, Object> params = new HashMap<>();
            try {
                List<VilleDTO> list = villeService.findAll();
                StringBuilder etatName = new StringBuilder("etatVilles");

                if (CollectionUtils.isEmpty(list)) {
                    byte[] bytes = JasperReportsUtil.anullerImpr("Aucune ville trouvé");
                    ByteArrayResource resource;
                    if (bytes != null) {
                        resource = new ByteArrayResource(bytes);

                        return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, MessageFormat.format("attachment; filename=\"{0}\"", etatName.toString()))
                                .contentLength(resource.contentLength())
                                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                .body(resource);
                    } else {
                        return ResponseEntity.noContent().build();
                    }
                }

                params.put("fichier", StaticVariables.bundleFR.getBaseBundleName());

                byte[] bytes = jasperReportsUtil.jasperReportInBytes(list, params, etatName.toString(), ReportTypeEnum.PDF, "");
                if (null != bytes) {
                    ByteArrayResource resource = new ByteArrayResource(bytes);
                    String fileName = MessageFormat.format(etatName + "_{0}.{1}", LocalDateTime.now(), "pdf");
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, MessageFormat.format("attachment; filename=\"{0}\"", fileName))
                            .contentLength(resource.contentLength())
                            .contentType(MediaType.APPLICATION_OCTET_STREAM)
                            .body(resource);
                } else {
                    throw new Exception("File Download Failed");
                }
            } catch (Exception e) {
                logger.debug("Exception while trying to print villes : {}", e.getMessage());
                return ResponseEntity.internalServerError().build();
            }
        } catch (Exception e) {
            logger.error("Error printing villes ", e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
