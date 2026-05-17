package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.config.enums.ReportTypeEnum;
import com.kabir.kabirbackend.config.imprimer.AchatFactureImprimer;
import com.kabir.kabirbackend.config.requests.PrintResponse;
import com.kabir.kabirbackend.config.responses.AchatFactureResponse;
import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.config.util.JasperReportsUtil;
import com.kabir.kabirbackend.config.util.StaticVariables;
import com.kabir.kabirbackend.dto.AchatFactureDTO;
import com.kabir.kabirbackend.service.AchatFactureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/achat-facture")
class AchatFactureController {

    private static final Logger logger = LoggerFactory.getLogger(AchatFactureController.class);

    private final AchatFactureService achatFactureService;
    private final JasperReportsUtil jasperReportsUtil;

    public AchatFactureController(AchatFactureService achatFactureService, JasperReportsUtil jasperReportsUtil) {
        this.achatFactureService = achatFactureService;
        this.jasperReportsUtil = jasperReportsUtil;
    }

    @GetMapping
    public ResponseEntity<List<AchatFactureDTO>> getAll() {
        logger.info("Finding all achat factures");
        try {
            return ResponseEntity.ok(achatFactureService.findAll());
        } catch (Exception e) {
            logger.error("Error finding all achat factures", e);
            throw new RuntimeException("Error finding all achat factures", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchatFactureDTO> getById(@PathVariable Long id) {
        logger.info("Finding achat facture by id: {}", id);
        try {
            return ResponseEntity.ok(achatFactureService.findById(id));
        } catch (Exception e) {
            logger.error("Error finding achat facture by id: {}", id, e);
            throw new RuntimeException("Error finding achat facture by id: " + id, e);
        }
    }

    @GetMapping("/response/{id}")
    public ResponseEntity<AchatFactureResponse> getByIdStockDepotResponse(@PathVariable Long id) {
        logger.info("Finding achat facture response by id: {}", id);
        try {
            return ResponseEntity.ok(achatFactureService.findByIdAchatSimpleResponse(id));
        } catch (Exception e) {
            logger.error("Error finding achat facture response by id: {}", id, e);
            throw new RuntimeException("Error finding achat facture response by id: " + id, e);
        }
    }

    @PostMapping
    public ResponseEntity<AchatFactureDTO> create(@RequestBody AchatFactureResponse achatFactureResponse) {
        logger.info("Creating achat facture response : {}", achatFactureResponse);
        try {
            return ResponseEntity.ok(achatFactureService.save(achatFactureResponse));
        } catch (Exception e) {
            logger.error("Error creating achat facture response : {}", achatFactureResponse, e);
            throw new RuntimeException("Error creating achat facture response : " + achatFactureResponse, e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AchatFactureDTO> update(@PathVariable Long id, @RequestBody AchatFactureResponse achatFactureResponse) {
        logger.info("Updating achat facture response : {}", achatFactureResponse);
        try {
            return ResponseEntity.ok(achatFactureService.save(achatFactureResponse));
        } catch (Exception e) {
            logger.error("Error updating achat facture response : {}", achatFactureResponse, e);
            throw new RuntimeException("Error updating achat facture response : " + achatFactureResponse, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting achat facture by id: {}", id);
        try {
            achatFactureService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting achat facture by id: {}", id, e);
            throw new RuntimeException("Error deleting achat facture by id: " + id, e);
        }
    }

    @PostMapping("/last-num-achat-facture")
    public ResponseEntity<Integer> getLastNumLivraison(@RequestBody AchatFactureDTO achatFactureDTO) {
        logger.info("Getting last num achat facture");
        try {
            int lastNumAchatFacture = achatFactureService.getLastNumAchatFacture(achatFactureDTO);
            return ResponseEntity.ok(lastNumAchatFacture);
        } catch (Exception e) {
            logger.error("Error getting last num achat facture: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody AchatFactureDTO achatFactureDTO) {
        logger.info("Searching if exist");
        try {
            return ResponseEntity.ok(achatFactureService.exist(achatFactureDTO));
        } catch (Exception e) {
            logger.error("Error searching if an existing achat facture: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/searchByCommon")
    public ResponseEntity<List<AchatFactureDTO>> searchByCommon(@RequestBody CommonSearchModel commonSearchModel) {
        logger.info("Searching achat facture by common: {}", commonSearchModel);
        try {
            return ResponseEntity.ok(achatFactureService.searchByCommon(commonSearchModel));
        } catch (Exception e) {
            logger.error("Error searching achat facture by common: {}", commonSearchModel, e);
            throw new RuntimeException("Error searching achat facture by common: " + commonSearchModel, e);
        }
    }

    @GetMapping("/imprimer/{id}")
    public ResponseEntity<?> imprimer(@PathVariable Long id) {
        logger.info("imprimer achat facture with ID {}", id);
        try {
            //Ajouter Imprimer dans tableau --> Fournisseur + ICE + NumFacture + Date Facture (1ere Ligne) + Tableau Produit + Tableau TVA (+ total pour les TVA)
            Map<String, Object> params = new HashMap<>();
            try {
                AchatFactureResponse achatFactureResponse = achatFactureService.findByIdAchatSimpleResponse(id);
                StringBuilder etatName = new StringBuilder("etatAchatFacture");
                List<AchatFactureImprimer> list = new ArrayList<>();

                if (null == achatFactureResponse || null == achatFactureResponse.achatFacture() || null == achatFactureResponse.achatFacture().getId()) {
                    byte[] bytes = JasperReportsUtil.anullerImpr("Aucune achat facture trouvé");
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

                list = achatFactureService.getAchatFactureImprimer(achatFactureResponse);
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
                logger.debug("Exception while trying to print achat facture : {}", e.getMessage());
                return ResponseEntity.internalServerError().build();
            }
        } catch (Exception e) {
            logger.error("Error printing achat facture with ID {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/imprimer/type/{type}")
    ResponseEntity<?> imprimerByType(@PathVariable("type") Integer type, @RequestBody CommonSearchModel commonSearchModel) {
        logger.info("imprimer achat facture by type: {}", type);
        try {
            PrintResponse printResponse = achatFactureService.imprimer(type, commonSearchModel);
            byte[] bytes = printResponse.getResponseBytes();
            if(null != bytes) {
                ByteArrayResource resource = new ByteArrayResource(bytes);
                String fileName = MessageFormat.format(printResponse.getEtatName() + "_{0}.{1}", LocalDateTime.now(), "pdf");
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, MessageFormat.format("attachment; filename=\"{0}\"", fileName))
                        .contentLength(resource.contentLength())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            logger.error("Error printing achat facture by type: {}", commonSearchModel, e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
