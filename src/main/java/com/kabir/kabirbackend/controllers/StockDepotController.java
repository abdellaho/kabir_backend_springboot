package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.config.enums.ReportTypeEnum;
import com.kabir.kabirbackend.config.responses.StockDepotResponse;
import com.kabir.kabirbackend.config.util.JasperReportsUtil;
import com.kabir.kabirbackend.config.util.StaticVariables;
import com.kabir.kabirbackend.dto.DetStockDepotDTO;
import com.kabir.kabirbackend.dto.StockDepotDTO;
import com.kabir.kabirbackend.service.StockDepotService;
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
@RequestMapping("/stock-depot")
public class StockDepotController {

    private final Logger logger = LoggerFactory.getLogger(StockDepotController.class);

    private final StockDepotService stockDepotService;
    private final JasperReportsUtil jasperReportsUtil;

    public StockDepotController(StockDepotService stockDepotService, JasperReportsUtil jasperReportsUtil) {
        this.stockDepotService = stockDepotService;
        this.jasperReportsUtil = jasperReportsUtil;
    }

    /*
    getAll: `${BASE_URL}/stock-depot`,
        getById: (id: bigint) => `${BASE_URL}/stock-depot/${id}`,
        create: `${BASE_URL}/stock-depot`,
        update: (id: bigint) => `${BASE_URL}/stock-depot/${id}`,
        delete: (id: bigint) => `${BASE_URL}/stock-depot/${id}`,
     */

    @GetMapping
    public ResponseEntity<List<StockDepotDTO>> getAll() {
        logger.info("Finding all stock depots");
        try {
            return ResponseEntity.ok(stockDepotService.findAll());
        } catch (Exception e) {
            logger.error("Error finding all stock depots", e);
            throw new RuntimeException("Error finding all stock depots", e);
        }
    }

    @GetMapping("/details")
    public ResponseEntity<List<DetStockDepotDTO>> getAllDetails() {
        logger.info("Finding all det stock depots");
        try {
            return ResponseEntity.ok(stockDepotService.findAllDetails());
        } catch (Exception e) {
            logger.error("Error finding all det stock depots", e);
            throw new RuntimeException("Error finding all det stock depots", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDepotDTO> getById(@PathVariable Long id) {
        logger.info("Finding stock depot by id: {}", id);
        try {
            return ResponseEntity.ok(stockDepotService.findById(id));
        } catch (Exception e) {
            logger.error("Error finding stock depot by id: {}", id, e);
            throw new RuntimeException("Error finding stock depot by id: " + id, e);
        }
    }

    @GetMapping("/response/{id}")
    public ResponseEntity<StockDepotResponse> getByIdStockDepotResponse(@PathVariable Long id) {
        logger.info("Finding stock depot response by id: {}", id);
        try {
            return ResponseEntity.ok(stockDepotService.findByIdStockDepotResponse(id));
        } catch (Exception e) {
            logger.error("Error finding stock depot response by id: {}", id, e);
            throw new RuntimeException("Error finding stock depot response by id: " + id, e);
        }
    }

    @PostMapping
    public ResponseEntity<StockDepotResponse> create(@RequestBody StockDepotResponse stockDepotResponse) {
        logger.info("Creating stock depot response : {}", stockDepotResponse);
        try {
            return ResponseEntity.ok(stockDepotService.save(stockDepotResponse));
        } catch (Exception e) {
            logger.error("Error creating stock depot response : {}", stockDepotResponse, e);
            throw new RuntimeException("Error creating stock depot response : " + stockDepotResponse, e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StockDepotResponse> update(@PathVariable Long id, @RequestBody StockDepotResponse stockDepotResponse) {
        logger.info("Updating stock depot response : {}", stockDepotResponse);
        try {
            return ResponseEntity.ok(stockDepotService.save(stockDepotResponse));
        } catch (Exception e) {
            logger.error("Error updating stock depot response : {}", stockDepotResponse, e);
            throw new RuntimeException("Error updating stock depot response : " + stockDepotResponse, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting stock depot by id: {}", id);
        try {
            stockDepotService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting stock depot by id: {}", id, e);
            throw new RuntimeException("Error deleting stock depot by id: " + id, e);
        }
    }

    @PostMapping("/imprimer")
    public ResponseEntity<?> imprimer(@RequestBody DetStockDepotDTO detStockDepotDTO) {
        logger.info("imprimer stock depot with same date as this: {}", detStockDepotDTO);
        try {
            Map<String, Object> params = new HashMap<>();
            try {
                List<DetStockDepotDTO> stockDepotDTOList = stockDepotService.findAllDetails(detStockDepotDTO);
                StringBuilder etatName = new StringBuilder("etatAchatLivraison");

                if (CollectionUtils.isEmpty(stockDepotDTOList)) {
                    byte[] bytes = JasperReportsUtil.anullerImpr("Aucun Stock Dépot trouvé");
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

                params.put("fournisseur", "");
                params.put("fichier", StaticVariables.bundleFR.getBaseBundleName());
                params.put("dateBL", StaticVariables.dateFormatDayFirst.format(detStockDepotDTO.getStockDepotDateOperation()));
                params.put("normalUser", true + "");

                String logo = "";
                byte[] bytes = jasperReportsUtil.jasperReportInBytes(stockDepotDTOList, params, etatName.toString(), ReportTypeEnum.PDF, logo);
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
                logger.debug("Exception while trying to print absence : {}", e.getMessage());
                return ResponseEntity.internalServerError().build();
            }
        } catch (Exception e) {
            logger.error("Error printing : {}", detStockDepotDTO, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
