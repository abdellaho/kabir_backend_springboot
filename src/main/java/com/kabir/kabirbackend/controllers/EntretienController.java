package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.config.enums.ReportTypeEnum;
import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.config.util.JasperReportsUtil;
import com.kabir.kabirbackend.config.util.StaticVariables;
import com.kabir.kabirbackend.dto.EntretienDTO;
import com.kabir.kabirbackend.service.EntretienService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/entretien")
public class EntretienController {

    private final Logger logger = LoggerFactory.getLogger(EntretienController.class);
    private final EntretienService entretienService;
    private final JasperReportsUtil jasperReportsUtil;

    public EntretienController(EntretienService entretienService, JasperReportsUtil jasperReportsUtil) {
        this.entretienService = entretienService;
        this.jasperReportsUtil = jasperReportsUtil;
    }

    @GetMapping
    public ResponseEntity<List<EntretienDTO>> getAll() {
        logger.info("Fetching all entretiens");
        try {
            List<EntretienDTO> entretiens = entretienService.findAll();
            return ResponseEntity.ok(entretiens);
        } catch (Exception e) {
            logger.error("Error fetching all entretiens: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntretienDTO> getById(@PathVariable Long id) {
        logger.info("Fetching entretien with id: {}", id);
        try {
            EntretienDTO entretien = entretienService.findById(id);
            return ResponseEntity.ok(entretien);
        } catch (Exception e) {
            logger.error("Error fetching entretien with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<EntretienDTO> create(@RequestBody EntretienDTO entretienDTO) {
        logger.info("Creating entretien: {}", entretienDTO);
        try {
            EntretienDTO createdVoiture = entretienService.save(entretienDTO);
            return ResponseEntity.ok(createdVoiture);
        } catch (Exception e) {
            logger.error("Error creating entretien: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntretienDTO> update(@PathVariable Long id, @RequestBody EntretienDTO entretienDTO) {
        logger.info("Updating entretien with id: {}", id);
        try {
            EntretienDTO updatedVoiture = entretienService.save(entretienDTO);
            return ResponseEntity.ok(updatedVoiture);
        } catch (Exception e) {
            logger.error("Error updating entretien with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntretienDTO> delete(@PathVariable Long id) {
        logger.info("Deleting entretien with id: {}", id);
        try {
            entretienService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting entretien with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody EntretienDTO entretienDTO) {
        logger.info("Checking if entretien exists: {}", entretienDTO);
        try {
            return ResponseEntity.ok(entretienService.exists(entretienDTO));
        } catch (Exception e) {
            logger.error("Error checking if entretien exists: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<EntretienDTO>> search(@RequestBody CommonSearchModel commonSearchModel) {
        logger.info("Searching entretiens: {}", commonSearchModel);
        try {
            List<EntretienDTO> entretiens = entretienService.search(commonSearchModel);
            return ResponseEntity.ok(entretiens);
        } catch (Exception e) {
            logger.error("Error searching entretiens: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/imprimer")
    public ResponseEntity<?> imprimer(@RequestBody EntretienDTO entretienDTO) {
        logger.info("imprimer entretien: {}", entretienDTO);
        try {
            Map<String, Object> params = new HashMap<>();
            try {
                List<EntretienDTO> list = new ArrayList<>();
                EntretienDTO entretien = entretienService.findById(entretienDTO.getId());
                StringBuilder etatName = new StringBuilder("etatEntretien");

                if (null == entretien || null == entretien.getId()) {
                    byte[] bytes = JasperReportsUtil.anullerImpr("Aucun entretien trouvé");
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

                list.add(entretien);

                byte[] checkedBytes = new ClassPathResource("images/checked.png").getInputStream().readAllBytes();
                byte[] notCheckedBytes = new ClassPathResource("images/notChecked.png").getInputStream().readAllBytes();

                params.put("fichier", StaticVariables.bundleFR.getBaseBundleName());
                params.put("checked", checkedBytes);
                params.put("notChecked", notCheckedBytes);
                params.put("titre", "");

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
                logger.debug("Exception while trying to print entretien : {}", e.getMessage());
                return ResponseEntity.internalServerError().build();
            }
        } catch (Exception e) {
            logger.error("Error printing : {}", entretienDTO, e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
