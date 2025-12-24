package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.config.requests.RequestStockQte;
import com.kabir.kabirbackend.dto.StockDTO;
import com.kabir.kabirbackend.service.StockService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
class StockController {

    private final Logger logger = LoggerFactory.getLogger(StockController.class);
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    /*
    getAll: `${BASE_URL}/stock`,
    getById: (id: bigint) => `${BASE_URL}/stock/${id}`,
    create: `${BASE_URL}/stock`,
    update: (id: bigint) => `${BASE_URL}/stock/${id}`,
    delete: (id: bigint) => `${BASE_URL}/stock/${id}`,
    exist: `${BASE_URL}/stock/exist`,
    search: `${BASE_URL}/stock/search`,
    updateQteStock: (id: bigint) => `${BASE_URL}/stock/${id}/update-qte-stock`,
    updateQteStockImport: (id: bigint) => `${BASE_URL}/stock/${id}/update-qte-stock-import`,
    updateQteStockFacturer: (id: bigint) => `${BASE_URL}/stock/${id}/update-qte-stock-facturer`,

    id: bigint | null;
  designation: string;
  sysDate: Date;
  dateSuppression: Date | null;
  pahtGrossiste: number;
  prixCommercial: number;
  tva: number;
  pattc: number;
  pvttc: number;
  pvaht: number;
  benifice: number;
  qteStock: number;
  qtePVMin1: number;
  qtePVMin2: number;
  qtePVMin3: number;
  qtePVMin4: number;
  qteFacturer: number;
  prixVentMin1: number;
  prixVentMin2: number;
  prixVentMin3: number;
  prixVentMin4: number;
  remiseMax1: number;
  remiseMax2: number;
  remiseMax3: number;
  remiseMax4: number;
  prixImport: number;
  commission: number;
  archiver: boolean;
  supprimer: boolean;
  qteStockImport: number;
  montant1: number;
  montant2: number;
  montant3: number;
  prime1: number;
  prime2: number;
  prime3: number;
  fournisseurId: bigint;
  fournisseur?: Fournisseur | null;
     */

    @GetMapping
    public ResponseEntity<List<StockDTO>> getAll() {
        logger.info("Fetching all stocks");
        try {
            List<StockDTO> stocks = stockService.findAll();
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            logger.error("Error fetching all stocks: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDTO> getById(@PathVariable Long id) {
        logger.info("Fetching stock with id: {}", id);
        try {
            StockDTO stock = stockService.findById(id);
            return ResponseEntity.ok(stock);
        } catch (Exception e) {
            logger.error("Error fetching stock with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<StockDTO> save(@RequestBody StockDTO stockDTO) {
        logger.info("Saving stock: {}", stockDTO);
        try {
            StockDTO savedStock = stockService.save(stockDTO);
            return ResponseEntity.ok(savedStock);
        } catch (Exception e) {
            logger.error("Error saving stock: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StockDTO> create(@PathVariable Long id, @RequestBody StockDTO stockDTO) {
        logger.info("Creating stock: {}", stockDTO);
        try {
            StockDTO createdStock = stockService.save(stockDTO);
            return ResponseEntity.ok(createdStock);
        } catch (Exception e) {
            logger.error("Error creating stock: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StockDTO> delete(@PathVariable Long id) {
        logger.info("Deleting stock with id: {}", id);
        try {
            stockService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting stock with id: {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody StockDTO stockDTO) {
        logger.info("Checking if stock exists: {}", stockDTO);
        try {
            List<StockDTO> stocks = stockService.search(stockDTO);
            return ResponseEntity.ok(CollectionUtils.isNotEmpty(stocks));
        } catch (Exception e) {
            logger.error("Error checking if stock exists: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<StockDTO>> search(@RequestBody StockDTO stockDTO) {
        logger.info("Searching stocks: {}", stockDTO);
        try {
            List<StockDTO> stocks = stockService.searchBySupprimerOrArchiver(stockDTO);
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            logger.error("Error searching stocks: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}/update-qte-stock")
    public ResponseEntity<Void> updateQteStock(@PathVariable Long id, @RequestBody RequestStockQte requestStockQte) {
        logger.info("Updating stock qte stock: {}", requestStockQte);
        try {
            stockService.updateQteStock(id, requestStockQte);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error updating stock qte stock: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}/update-qte-stock-import")
    public ResponseEntity<StockDTO> updateQteStockImport(@PathVariable Long id, @RequestBody RequestStockQte requestStockQte) {
        logger.info("Updating stock qte stock import: {}", requestStockQte);
        try {
            stockService.updateQteStockImport(id, requestStockQte);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error updating stock qte stock import: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}/update-qte-stock-facturer")
    public ResponseEntity<Void> updateQteStockFacturer(@PathVariable Long id, @RequestBody RequestStockQte requestStockQte) {
        logger.info("Updating stock qte stock facturer: {}", requestStockQte);
        try {
            stockService.updateQteStockFacturer(id, requestStockQte);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error updating stock qte stock facturer: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

}
