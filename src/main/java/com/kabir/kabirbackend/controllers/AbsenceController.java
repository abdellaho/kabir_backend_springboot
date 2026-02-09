package com.kabir.kabirbackend.controllers;

import com.kabir.kabirbackend.config.enums.ReportTypeEnum;
import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.config.util.JasperReportsUtil;
import com.kabir.kabirbackend.dto.AbsenceDTO;
import com.kabir.kabirbackend.service.AbsenceService;
import jakarta.validation.Valid;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/absence")
public class AbsenceController {

    private static final ResourceBundle bundleFR = ResourceBundle.getBundle("i18n/ApplicationResources", Locale.of("fr"));
    private static final DateFormat dateFormatDayFirst = new SimpleDateFormat("dd-MM-yyyy");
    private final Logger logger = LoggerFactory.getLogger(AbsenceController.class);

    /*
    getAll: `${BASE_URL}/absence`,
    getById: (id: bigint) => `${BASE_URL}/absence/${id}`,
    create: `${BASE_URL}/absence`,
    update: (id: bigint) => `${BASE_URL}/absence/${id}`,
    delete: (id: bigint) => `${BASE_URL}/absence/${id}`,
    exist: `${BASE_URL}/absence/exist`,
    search: `${BASE_URL}/absence/search`,

    id: bigint | null;
  dateAbsence: Date;
  matin: boolean;
  apresMidi: boolean;
  dateOperation: Date;
  personnelOperationId: bigint | null;
  personnelOperation?: Personnel | null;
  personnelId: bigint;
  personnel?: Personnel | null;
  dateAbsenceStr?: string;
     */

    private final AbsenceService absenceService;
    private final JasperReportsUtil jasperReportsUtil;

    public AbsenceController(AbsenceService absenceService, JasperReportsUtil jasperReportsUtil) {
        this.absenceService = absenceService;
        this.jasperReportsUtil = jasperReportsUtil;
    }

    @GetMapping
    public ResponseEntity<List<AbsenceDTO>> findAll() {
        logger.info("Finding all absences");
        try {
            return ResponseEntity.ok(absenceService.findAll());
        } catch (Exception e) {
            logger.error("Error finding all absences", e);
            throw new RuntimeException("Error finding all absences", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbsenceDTO> findById(@PathVariable Long id) {
        logger.info("Finding absence by id: {}", id);
        try {
            AbsenceDTO absenceDTO = absenceService.findById(id);
            if (absenceDTO == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(absenceDTO);
            }
        } catch (Exception e) {
            logger.error("Error finding absence by id: {}", id, e);
            throw new RuntimeException("Error finding absence by id: " + id, e);
        }
    }

    @PostMapping
    public ResponseEntity<AbsenceDTO> save(@Valid @RequestBody AbsenceDTO absenceDTO) {
        logger.info("Saving absence: {}", absenceDTO);
        try {
            AbsenceDTO updatedAbsenceDTO = absenceService.save(absenceDTO);
            return ResponseEntity.ok(updatedAbsenceDTO);
        } catch (Exception e) {
            logger.error("Error saving absence: {}", absenceDTO, e);
            throw new RuntimeException("Error saving absence: " + absenceDTO, e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AbsenceDTO> update(@PathVariable Long id, @Valid @RequestBody AbsenceDTO absenceDTO) {
        logger.info("Updating absence: {}, with ID {} : ", absenceDTO, id);
        try {
            AbsenceDTO updatedAbsenceDTO = absenceService.save(absenceDTO);
            return ResponseEntity.ok(updatedAbsenceDTO);
        } catch (Exception e) {
            logger.error("Error updating absence: {}, with ID: {}", absenceDTO, id, e);
            throw new RuntimeException("Error updating absence: " + absenceDTO, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting absence: {}", id);
        try {
            absenceService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting absence: {}", id, e);
            throw new RuntimeException("Error deleting absence: " + id, e);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<AbsenceDTO>> search(@RequestBody AbsenceDTO absenceDTO) {
        logger.info("Searching absence: {}", absenceDTO);
        try {
            return ResponseEntity.ok(absenceService.search(absenceDTO));
        } catch (Exception e) {
            logger.error("Error searching absence: {}", absenceDTO, e);
            throw new RuntimeException("Error searching absence: " + absenceDTO, e);
        }
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody AbsenceDTO absenceDTO) {
        logger.info("Searching absence if exist: {}", absenceDTO);
        try {
            return ResponseEntity.ok(CollectionUtils.isNotEmpty(absenceService.search(absenceDTO)));
        } catch (Exception e) {
            logger.error("Error searching absence if exist: {}", absenceDTO, e);
            throw new RuntimeException("Error searching absence if exist: " + absenceDTO, e);
        }
    }

    @PostMapping("/searchByCommon")
    public ResponseEntity<List<AbsenceDTO>> searchByCommon(@RequestBody CommonSearchModel commonSearchModel) {
        logger.info("Searching absence by common: {}", commonSearchModel);
        try {
            return ResponseEntity.ok(absenceService.searchByCommon(commonSearchModel));
        } catch (Exception e) {
            logger.error("Error searching absence by common: {}", commonSearchModel, e);
            throw new RuntimeException("Error searching absence by common: " + commonSearchModel, e);
        }
    }

    @PostMapping("/imprimer")
    public ResponseEntity<?> imprimer(@RequestBody CommonSearchModel commonSearchModel) {
        logger.info("imprimer with this search model: {}", commonSearchModel);
        try {
            Map<String, Object> params = new HashMap<>();
            try {
                List<AbsenceDTO> listAbsence = absenceService.searchByCommon(commonSearchModel);
                StringBuilder etatName = new StringBuilder("listAbsences");

                if (CollectionUtils.isEmpty(listAbsence)) {
                    byte[] bytes = JasperReportsUtil.anullerImpr("Aucun absence trouvé");
                    ByteArrayResource resource = null;
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

                params.put("fichier", bundleFR.getBaseBundleName());
                params.put("checked", getClass().getResourceAsStream("/images/checked.png"));
                params.put("notChecked", getClass().getResourceAsStream("/images/notChecked.png"));
                params.put("titre", "");

                Map<Long, List<AbsenceDTO>> map = listAbsence.stream().collect(Collectors.groupingBy(AbsenceDTO::getPersonnelId));
                List<AbsenceDTO> result = new ArrayList<>();

                List<AbsenceDTO> finalResult = new ArrayList<>();
                map.values().forEach(list -> {
                    double nbrJour = list.stream()
                            .mapToDouble(a -> (a.isMatin() ? 0.5 : 0) + (a.isApresMidi() ? 0.5 : 0))
                            .sum();

                    list.forEach(a -> a.setNbrJour(nbrJour));
                    finalResult.addAll(list);
                });

                if(null != commonSearchModel.getPersonnelId()) {
                    etatName.setLength(0);
                    etatName.append("listAbsencesOne");
                    result = finalResult.stream().sorted((o1, o2)-> o1.getDateAbsence().compareTo(o2.getDateAbsence())).collect(Collectors.toList());
                }

                String logo = "";
                byte[] bytes = jasperReportsUtil.jasperReportInBytes(result, params, etatName.toString(), ReportTypeEnum.PDF, logo);
                if (null != bytes) {
                    ByteArrayResource resource = new ByteArrayResource(bytes);
                    String fileName = MessageFormat.format(etatName.toString() + "_{0}.{1}", LocalDateTime.now(), "pdf");
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
            logger.error("Error printing : {}", commonSearchModel, e);
            throw new RuntimeException("Error printing absence by common: " + commonSearchModel, e);
        }
    }

}
