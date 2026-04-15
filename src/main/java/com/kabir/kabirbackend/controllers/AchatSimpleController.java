package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.config.enums.ReportTypeEnum;
import com.kabir.kabirbackend.config.responses.AchatSimpleResponse;
import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.config.util.JasperReportsUtil;
import com.kabir.kabirbackend.config.util.StaticVariables;
import com.kabir.kabirbackend.dto.AchatFactureDTO;
import com.kabir.kabirbackend.dto.AchatSimpleDTO;
import com.kabir.kabirbackend.dto.DetAchatSimpleDTO;
import com.kabir.kabirbackend.dto.DetStockDepotDTO;
import com.kabir.kabirbackend.service.AchatSimpleService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/achat-simple")
class AchatSimpleController {
    
    private static final Logger logger = LoggerFactory.getLogger(AchatSimpleController.class);

    private final AchatSimpleService achatSimpleService;
    private final JasperReportsUtil jasperReportsUtil;

    public AchatSimpleController(AchatSimpleService achatSimpleService, JasperReportsUtil jasperReportsUtil) {
        this.achatSimpleService = achatSimpleService;
        this.jasperReportsUtil = jasperReportsUtil;
    }

    @GetMapping
    public ResponseEntity<List<AchatSimpleDTO>> getAll() {
        logger.info("Finding all achat simples");
        try {
            return ResponseEntity.ok(achatSimpleService.findAll());
        } catch (Exception e) {
            logger.error("Error finding all achat simples", e);
            throw new RuntimeException("Error finding all stock depots", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchatSimpleDTO> getById(@PathVariable Long id) {
        logger.info("Finding achat simple by id: {}", id);
        try {
            return ResponseEntity.ok(achatSimpleService.findById(id));
        } catch (Exception e) {
            logger.error("Error finding achat simple by id: {}", id, e);
            throw new RuntimeException("Error finding achat simple by id: " + id, e);
        }
    }

    @GetMapping("/response/{id}")
    public ResponseEntity<AchatSimpleResponse> getByIdStockDepotResponse(@PathVariable Long id) {
        logger.info("Finding achat simple response by id: {}", id);
        try {
            return ResponseEntity.ok(achatSimpleService.findByIdAchatSimpleResponse(id));
        } catch (Exception e) {
            logger.error("Error finding achat simple response by id: {}", id, e);
            throw new RuntimeException("Error finding achat simple response by id: " + id, e);
        }
    }

    @PostMapping
    public ResponseEntity<AchatSimpleDTO> create(@RequestBody AchatSimpleResponse achatSimpleResponse) {
        logger.info("Creating achat simple response : {}", achatSimpleResponse);
        try {
            return ResponseEntity.ok(achatSimpleService.save(achatSimpleResponse));
        } catch (Exception e) {
            logger.error("Error creating achat simple response : {}", achatSimpleResponse, e);
            throw new RuntimeException("Error creating achat simple response : " + achatSimpleResponse, e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AchatSimpleDTO> update(@PathVariable Long id, @RequestBody AchatSimpleResponse achatSimpleResponse) {
        logger.info("Updating achat simple response : {}", achatSimpleResponse);
        try {
            return ResponseEntity.ok(achatSimpleService.save(achatSimpleResponse));
        } catch (Exception e) {
            logger.error("Error updating achat simple response : {}", achatSimpleResponse, e);
            throw new RuntimeException("Error updating achat simple response : " + achatSimpleResponse, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting achat simple by id: {}", id);
        try {
            achatSimpleService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting achat simple by id: {}", id, e);
            throw new RuntimeException("Error deleting achat simple by id: " + id, e);
        }
    }

    @GetMapping("/searchByCommon")
    public ResponseEntity<List<AchatSimpleDTO>> search(@RequestBody CommonSearchModel commonSearchModel) {
        logger.info("Searching achat simple by common: {}", commonSearchModel);
        try {
            return ResponseEntity.ok(achatSimpleService.searchByCommon(commonSearchModel));
        } catch (Exception e) {
            logger.error("Error searching achat simple by common: {}", commonSearchModel, e);
            throw new RuntimeException("Error searching achat simple by common: " + commonSearchModel, e);
        }
    }

    @PostMapping("/imprimer")
    public ResponseEntity<?> imprimer(@RequestBody AchatSimpleDTO achatSimpleDTO) {
        logger.info("imprimer achat simple : {}", achatSimpleDTO);
        try {
            Map<String, Object> params = new HashMap<>();
            try {
                AchatSimpleResponse achatSimpleResponse = achatSimpleService.findByIdAchatSimpleResponse(achatSimpleDTO.getId());
                StringBuilder etatName = new StringBuilder("etatAchatSimple");

                if (null == achatSimpleResponse || null == achatSimpleResponse.achatSimple() || CollectionUtils.isEmpty(achatSimpleResponse.detAchatSimples())) {
                    byte[] bytes = JasperReportsUtil.anullerImpr("Aucune Achat simple trouvé");
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

                params.put("fournisseur", achatSimpleResponse.achatSimple().getFournisseurDesignation());
                params.put("fichier", StaticVariables.bundleFR.getBaseBundleName());
                params.put("dateBL", StaticVariables.dateFormatDayFirst.format(achatSimpleResponse.achatSimple().getDateOperation()));
                params.put("normalUser", true + "");

                String logo = "";
                byte[] bytes = jasperReportsUtil.jasperReportInBytes(achatSimpleResponse.detAchatSimples(), params, etatName.toString(), ReportTypeEnum.PDF, logo);
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
                logger.debug("Exception while trying to print achat simple : {}", e.getMessage());
                return ResponseEntity.internalServerError().build();
            }
        } catch (Exception e) {
            logger.error("Error printing achat simple : {}", achatSimpleDTO, e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
