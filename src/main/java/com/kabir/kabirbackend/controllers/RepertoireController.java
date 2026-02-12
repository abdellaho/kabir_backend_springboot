package com.kabir.kabirbackend.controllers;


import com.kabir.kabirbackend.config.responses.RepertoireValidationResponse;
import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.RepertoireDTO;
import com.kabir.kabirbackend.service.RepertoireService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/repertoire")
public class RepertoireController {

    private final Logger logger = LoggerFactory.getLogger(RepertoireController.class);
    private final RepertoireService repertoireService;

    public RepertoireController(RepertoireService repertoireService) {
        this.repertoireService = repertoireService;
    }

    /*
    getAll: `${BASE_URL}/repertoire`,
    getById: (id: bigint) => `${BASE_URL}/repertoire/${id}`,
    create: `${BASE_URL}/repertoire`,
    update: (id: bigint) => `${BASE_URL}/repertoire/${id}`,
    delete: (id: bigint) => `${BASE_URL}/repertoire/${id}`,
    exist: `${BASE_URL}/repertoire/exist`,
    search: `${BASE_URL}/repertoire/search`,
    searchPersonnel: `${BASE_URL}/repertoire/search-personnel`,
     */

    @GetMapping
    public ResponseEntity<List<RepertoireDTO>> findAll() {
        logger.info("Finding all repertoires");
        try {
            return ResponseEntity.ok(repertoireService.findAll());
        } catch (Exception e) {
            logger.error("Error finding all repertoires", e);
            throw new RuntimeException("Error finding all repertoires", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepertoireDTO> findById(@PathVariable Long id) {
        logger.info("Finding repertoire by id: {}", id);
        try {
            RepertoireDTO repertoireDTO = repertoireService.findById(id);
            if (repertoireDTO == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(repertoireDTO);
            }
        } catch (Exception e) {
            logger.error("Error finding repertoire by id: {}", id, e);
            throw new RuntimeException("Error finding repertoire by id: " + id, e);
        }
    }

    @PostMapping
    public ResponseEntity<RepertoireDTO> save(@Valid @RequestBody RepertoireDTO repertoireDTO) {
        logger.info("Saving repertoire: {}", repertoireDTO);
        try {
            RepertoireDTO updatedRepertoireDTO = repertoireService.save(repertoireDTO);
            return ResponseEntity.ok(updatedRepertoireDTO);
        } catch (Exception e) {
            logger.error("Error saving repertoire: {}", repertoireDTO, e);
            throw new RuntimeException("Error saving repertoire: " + repertoireDTO, e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RepertoireDTO> update(@PathVariable Long id, @Valid @RequestBody RepertoireDTO repertoireDTO) {
        logger.info("Updating repertoire: {}, with ID {} : ", repertoireDTO, id);
        try {
            RepertoireDTO updatedRepertoireDTO = repertoireService.save(repertoireDTO);
            return ResponseEntity.ok(updatedRepertoireDTO);
        } catch (Exception e) {
            logger.error("Error updating repertoire: {}, with ID: {}", repertoireDTO, id, e);
            throw new RuntimeException("Error updating repertoire: " + repertoireDTO, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting repertoire: {}", id);
        try {
            repertoireService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting repertoire: {}", id, e);
            throw new RuntimeException("Error deleting repertoire: " + id, e);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<RepertoireDTO>> search(@RequestBody RepertoireDTO repertoireDTO) {
        logger.info("Searching repertoire: {}", repertoireDTO);
        try {
            return ResponseEntity.ok(repertoireService.search(repertoireDTO));
        } catch (Exception e) {
            logger.error("Error searching repertoire: {}", repertoireDTO, e);
            throw new RuntimeException("Error searching repertoire: " + repertoireDTO, e);
        }
    }

    @PostMapping("/search/clients")
    public ResponseEntity<List<RepertoireDTO>> searchOnlyClients(@RequestBody RepertoireDTO repertoireDTO) {
        logger.info("Searching repertoire only clients: {}", repertoireDTO);
        try {
            return ResponseEntity.ok(repertoireService.searchClients(repertoireDTO));
        } catch (Exception e) {
            logger.error("Error searching repertoire only clients: {}", repertoireDTO, e);
            throw new RuntimeException("Error searching repertoire only clients: " + repertoireDTO, e);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<RepertoireValidationResponse> exist(@RequestBody RepertoireDTO repertoireDTO) {
        logger.info("Searching repertoire if exist: {}", repertoireDTO);
        try {
            return ResponseEntity.ok(repertoireService.existingTest(repertoireDTO));
        } catch (Exception e) {
            logger.error("Error searching repertoire if exist: {}", repertoireDTO, e);
            throw new RuntimeException("Error searching repertoire if exist: " + repertoireDTO, e);
        }
    }

    @PostMapping("/search-personnel")
    public ResponseEntity<List<RepertoireDTO>> searchPersonnel(@RequestBody RepertoireDTO repertoireDTO) {
        logger.info("Searching repertoire by personnel: {}", repertoireDTO);
        try {
            return ResponseEntity.ok(repertoireService.search(repertoireDTO));
        } catch (Exception e) {
            logger.error("Error searching repertoire by personnel: {}", repertoireDTO, e);
            throw new RuntimeException("Error searching repertoire by personnel: " + repertoireDTO, e);
        }
    }

    @PatchMapping("/{id}/update-nbr-operation/{type}")
    public ResponseEntity<RepertoireDTO> updateNbrOperation(@PathVariable Long id, @PathVariable Integer type) {
        logger.info("Updating nbr-operation of fournisseur: {}, with ID {}: ", (type == 1 ? "increment" : "decrement"), id);
        try {
            RepertoireDTO repertoireDTO = repertoireService.updateNbrOperation(id, type);
            return ResponseEntity.ok(repertoireDTO);
        } catch (Exception e) {
            logger.error("Error updating nbr-operation offournisseur: {}, with ID {}: ", e.getMessage(), id);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/imprimer")
    public ResponseEntity<?> imprimer(@RequestBody CommonSearchModel commonSearchModel) {
        logger.info("Printing repertoire with the following criteria: {}", commonSearchModel);
        try {
            StringBuilder etatName = getStringBuilder(commonSearchModel);
            
            byte[] bytes = repertoireService.imprimer(commonSearchModel, etatName.toString());
            if(null != bytes) {
                ByteArrayResource resource = new ByteArrayResource(bytes);
                String fileName = MessageFormat.format(etatName + "_{0}.{1}", LocalDateTime.now(), "pdf");
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, MessageFormat.format("attachment; filename=\"{0}\"", fileName))
                        .contentLength(resource.contentLength())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            logger.error("Error printing repertoire: {}", commonSearchModel, e);
            throw new RuntimeException("Error printing repertoire: " + commonSearchModel, e);
        }
    }

    @PostMapping("/imprimer/client-adresse")
    public ResponseEntity<?> imprimerClientAdresse(@RequestBody CommonSearchModel commonSearchModel) {
        logger.info("Printing repertoire with adresse with the following criteria: {}", commonSearchModel);
        try {
            StringBuilder etatName = getStringBuilder(commonSearchModel);

            byte[] bytes = repertoireService.printClientAndAdresse(commonSearchModel, etatName.toString());
            if(null != bytes) {
                ByteArrayResource resource = new ByteArrayResource(bytes);
                String fileName = MessageFormat.format(etatName + "_{0}.{1}", LocalDateTime.now(), "pdf");
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, MessageFormat.format("attachment; filename=\"{0}\"", fileName))
                        .contentLength(resource.contentLength())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            logger.error("Error printing repertoire: {}", commonSearchModel, e);
            throw new RuntimeException("Error printing repertoire: " + commonSearchModel, e);
        }
    }

    @PostMapping("/imprimer/transport")
    public ResponseEntity<?> imprimerTransport(@RequestBody CommonSearchModel commonSearchModel) {
        logger.info("Printing repertoire type transport with the following criteria: {}", commonSearchModel);
        try {
            StringBuilder etatName = getStringBuilder(commonSearchModel);

            byte[] bytes = repertoireService.printTransport(commonSearchModel, etatName.toString());
            if(null != bytes) {
                ByteArrayResource resource = new ByteArrayResource(bytes);
                String fileName = MessageFormat.format(etatName + "_{0}.{1}", LocalDateTime.now(), "pdf");
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, MessageFormat.format("attachment; filename=\"{0}\"", fileName))
                        .contentLength(resource.contentLength())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            logger.error("Error printing repertoire: {}", commonSearchModel, e);
            throw new RuntimeException("Error printing repertoire: " + commonSearchModel, e);
        }
    }

    private static StringBuilder getStringBuilder(CommonSearchModel commonSearchModel) {
        StringBuilder etatName  =  new StringBuilder("etatRepertoireClientPharmacie");

        if(commonSearchModel.getTypeRepertoire() == 2) {
            if(commonSearchModel.getTypeImprimRepertoire() == 10) {
                etatName.setLength(0);
                etatName.append("etatTousRepertoireClientAdresse");
            }
        } else if(commonSearchModel.getTypeRepertoire() == 3) {
            etatName.setLength(0);
            etatName.append("etatRepertoireTransport");
        }
        return etatName;
    }
}
