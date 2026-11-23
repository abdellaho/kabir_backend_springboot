package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.config.enums.StockOperation;
import com.kabir.kabirbackend.config.enums.ReportTypeEnum;
import com.kabir.kabirbackend.config.enums.TypeOperation;
import com.kabir.kabirbackend.config.enums.TypeQteToUpdate;
import com.kabir.kabirbackend.config.requests.LivraisonPrintRequest;
import com.kabir.kabirbackend.config.requests.RequestStockQte;
import com.kabir.kabirbackend.config.responses.LivraisonResponse;
import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.config.util.JasperReportsUtil;
import com.kabir.kabirbackend.config.util.StaticVariables;
import com.kabir.kabirbackend.dto.DetLivraisonDTO;
import com.kabir.kabirbackend.dto.LivraisonClientPrintDTO;
import com.kabir.kabirbackend.dto.LivraisonDTO;
import com.kabir.kabirbackend.dto.LivraisonProduitCommissionPrintDTO;
import com.kabir.kabirbackend.dto.LivraisonProduitPrintDTO;
import com.kabir.kabirbackend.dto.LivraisonVillePrintDTO;
import com.kabir.kabirbackend.dto.NumChequePrintDTO;
import com.kabir.kabirbackend.dto.StockImprPrintDTO;
import com.kabir.kabirbackend.dto.StockLivraisonSumPrintDTO;
import com.kabir.kabirbackend.entities.*;
import com.kabir.kabirbackend.mapper.DetLivraisonMapper;
import com.kabir.kabirbackend.mapper.LivraisonMapper;
import com.kabir.kabirbackend.repository.*;
import com.kabir.kabirbackend.service.interfaces.ILivraisonService;
import com.kabir.kabirbackend.specifications.LivraisonSpecification;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LivraisonService implements ILivraisonService {
    private static final Logger logger = LoggerFactory.getLogger(LivraisonService.class);

    private final LivraisonRepository livraisonRepository;
    private final DetLivraisonRepository detLivraisonRepository;
    private final RepertoireRepository repertoireRepository;
    private final PersonnelRepository personnelRepository;
    private final StockRepository stockRepository;
    private final StockService stockService;
    private final LivraisonMapper livraisonMapper;
    private final DetLivraisonMapper detLivraisonMapper;
    private final JasperReportsUtil jasperReportsUtil;

    public LivraisonService(LivraisonRepository livraisonRepository, 
                            LivraisonMapper livraisonMapper, 
                            RepertoireRepository repertoireRepository, 
                            DetLivraisonRepository detLivraisonRepository,
                            DetLivraisonMapper detLivraisonMapper,
                            StockRepository stockRepository,
                            PersonnelRepository personnelRepository, 
                            StockService stockService,
                            JasperReportsUtil jasperReportsUtil) {
        this.jasperReportsUtil = jasperReportsUtil;
        this.livraisonRepository = livraisonRepository;
        this.repertoireRepository = repertoireRepository;
        this.personnelRepository = personnelRepository;
        this.detLivraisonRepository = detLivraisonRepository;
        this.stockRepository = stockRepository;
        this.stockService = stockService;
        this.livraisonMapper = livraisonMapper;
        this.detLivraisonMapper = detLivraisonMapper;
    }

    @Override
    @Transactional
    public LivraisonDTO save(LivraisonResponse livraisonResponse) {
        logger.info("Saving livraison response: {}", livraisonResponse);

        LivraisonDTO livraisonDTO = livraisonResponse.livraison();
        boolean isSave = livraisonDTO.getId() == null;
        Repertoire oldRepertoire = null;
        Personnel personnelOperation = null;
        String repertoireObservation = livraisonDTO.getRepertoireObservation();
        String repertoireAdresse = livraisonDTO.getRepertoireAdresse();
        String repertoireTel1 = livraisonDTO.getRepertoireTel1();
        String repertoireTel2 = livraisonDTO.getRepertoireTel2();
        String repertoireDesignation = livraisonDTO.getRepertoireDesignation();

        try {
            if(!isSave && null != livraisonDTO.getRepertoireIdOld()) {
                oldRepertoire = repertoireRepository.findById(livraisonDTO.getRepertoireIdOld()).orElse(null);
            }
            if(null != livraisonDTO.getEmployeOperateurId() && livraisonDTO.getEmployeOperateurId() != 0) {
                personnelOperation = personnelRepository.findById(livraisonDTO.getEmployeOperateurId()).orElse(null);
            }

            if (null == livraisonResponse.livraison().getRepertoireId()) {
                throw new IllegalArgumentException("repertoireId is required");
            }
            if (null == livraisonResponse.livraison().getPersonnelId()) {
                throw new IllegalArgumentException("personnelId is required");
            }

            Optional<Repertoire> optionalRepertoire = repertoireRepository.findById(livraisonResponse.livraison().getRepertoireId());
            Optional<Personnel> optionalPersonnel = personnelRepository.findById(livraisonResponse.livraison().getPersonnelId());

            Livraison livraison = livraisonMapper.toLivraison(livraisonDTO);

            livraison.setEmployeOperateur(personnelOperation);
            livraison.setRepertoire(optionalRepertoire.orElse(null));
            livraison.setPersonnel(optionalPersonnel.orElse(null));

            livraisonDTO = livraisonMapper.toLivraisonDTO(livraisonRepository.save(livraison));

            enregistrerDetLivraison(livraison, isSave, livraisonResponse.detLivraisons());

            logger.info("Livraison repertoire observation: {}", repertoireObservation);
            Repertoire repertoireToUpdate = livraison.getRepertoire();

            repertoireToUpdate.setObservation(repertoireObservation);
            if (StringUtils.isNotBlank(repertoireAdresse)) {
                repertoireToUpdate.setAdresse(repertoireAdresse);
            }
            if (StringUtils.isNotBlank(repertoireTel1)) {
                repertoireToUpdate.setTel1(repertoireTel1);
            }
            if (StringUtils.isNotBlank(repertoireTel2)) {
                repertoireToUpdate.setTel2(repertoireTel2);
            }
            if (StringUtils.isNotBlank(repertoireDesignation)) {
                repertoireToUpdate.setDesignation(repertoireDesignation);
            }

            updateRepertoire(oldRepertoire, repertoireToUpdate);

            logger.info("Stock livraison saved successfully: {}", livraisonDTO);
        } catch (Exception e) {
            logger.error("Failed to save livraison", e);
            throw new RuntimeException("Failed to save livraison", e);
        }

        return livraisonDTO;
    }

    @Override
    public LivraisonDTO findById(Long id) {
        logger.info("Finding livraison by id: {}", id);
        try {
            return livraisonMapper.toLivraisonDTO(livraisonRepository.findById(id).orElse(null));
        } catch (Exception e) {
            logger.error("Error finding livraison by id: {}", id, e);
            throw new RuntimeException("Error finding livraison by id: " + id, e);
        }
    }

    @Override
    public LivraisonResponse findByIdWithDetLivraison(Long id) {
        logger.info("Finding livraison response by id: {}", id);
        try {
            LivraisonDTO livraisonDTO = livraisonMapper.toLivraisonDTO(livraisonRepository.findById(id).orElse(null));
            if(null != livraisonDTO && null != livraisonDTO.getId()) {
                List<DetLivraisonDTO> list = detLivraisonRepository.findByLivraisonId(id).stream()
                        .map(detLivraisonMapper::toDetLivraisonDTO)
                        .toList();

                return new LivraisonResponse(livraisonDTO, list);
            } else {
                logger.info("Livraison not found with ID: {}", id);
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to find livraison response by id: {}", id);
            return null;
        }
    }

    @Override
    public List<LivraisonDTO> findAll() {
        logger.info("Finding all livraisons");
        try {
            List<Livraison> livraisons = livraisonRepository.findAll();
            return livraisons.stream().map(livraisonMapper::toLivraisonDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all livraisons", e);
            throw new RuntimeException("Error finding all livraisons", e);
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting livraison by id: {}", id);
        try {
            Livraison livraison = livraisonRepository.findById(id).orElse(null);
            List<DetLivraison> detLivraisonDTOOld = detLivraisonRepository.findByLivraisonId(id);
            logger.info("List of DetLivraison to delete: {}", detLivraisonDTOOld.size());

            boolean isPersonnelIsCommercialExterne = doesPersonnelIsCommercialExterne(livraison);

            if(CollectionUtils.isNotEmpty(detLivraisonDTOOld)) {
                for(DetLivraison detStockDepot : detLivraisonDTOOld) {
                    if(null != detStockDepot.getStock() && null != detStockDepot.getStock().getId()) {
                        if(!isPersonnelIsCommercialExterne) {
                            stockService.updateQteStock(detStockDepot.getStock().getId(), TypeQteToUpdate.QTE_STOCK_IMPORT, new RequestStockQte(detStockDepot.getQteLivrer(),  StockOperation.ADD_TO_STOCK.getValue(), null));
                        }

                        logger.info("Deleting detail livraison by id: {}", detStockDepot.getId());
                        detLivraisonRepository.deleteById(detStockDepot.getId());
                    }
                }
            }

            livraisonRepository.deleteById(id);
            updateRepertoire(livraison.getRepertoire(), null);
            logger.info("Livraison deleted successfully");
        } catch (Exception e) {
            logger.error("Failed to delete livraison: {}", e.getMessage());
        }
    }

    @Override
    public List<LivraisonDTO> search(LivraisonDTO livraisonDTO) {
        return livraisonRepository.findAll(LivraisonSpecification.builder().livraisonDTO(livraisonDTO).build()).stream().map(livraisonMapper::toLivraisonDTO).toList();
    }

    @Override
    public int getLastNumLivraison(LivraisonDTO livraisonDTO) {
        return livraisonRepository.findMaxNumLivraisonInYearDateBL(livraisonDTO.getDateBl().getYear()).map(l -> l + 1).orElse(1);
    }

    public void deleteStockInDetLivraison(List<DetLivraisonDTO> detLivraisons) {
        if(CollectionUtils.isNotEmpty(detLivraisons)) {
            for (DetLivraisonDTO detLivraisonDTO : detLivraisons) {
                if(null != detLivraisonDTO.getStockId() && detLivraisonDTO.getQteLivrer() != 0) {
                    stockService.updateQteStock(detLivraisonDTO.getStockId(), TypeQteToUpdate.QTE_STOCK, new RequestStockQte(detLivraisonDTO.getQteLivrer(), 2, null));
                }
            }
        }
    }

    public List<DetLivraison> getDetStockDepotOldNotAnymore(List<DetLivraison> detLivraisonDTOOld, List<DetLivraisonDTO> detLivraisonDTOs) {
        if(CollectionUtils.isEmpty(detLivraisonDTOOld)) {
            return new ArrayList<>();
        }

        return detLivraisonDTOOld.stream()
                .filter(detStockDepotOld -> detLivraisonDTOs.stream()
                        .noneMatch(detLivraisonDTO -> detLivraisonDTO.getId() != null && detLivraisonDTO.getId().equals(detStockDepotOld.getId())))
                .collect(Collectors.toList());
    }

    public boolean doesPersonnelIsCommercialExterne(Livraison livraison) {
        return null != livraison && null != livraison.getPersonnel() && livraison.getPersonnel().getTypePersonnel() == 6;
    }

    public void enregistrerDetLivraison(Livraison livraison, boolean isSave, List<DetLivraisonDTO> detLivraisonDTOs) {
        boolean isPersonnelIsCommercialExterne = doesPersonnelIsCommercialExterne(livraison);
        boolean isPersonnelIsCommercialExterneOld = false;

        List<DetLivraison> detLivraisonDTOOld = new ArrayList<>();
        Livraison livraisonOld = null;
        if(!isSave) {
            detLivraisonDTOOld = detLivraisonRepository.findByLivraisonId(livraison.getId());
            livraisonOld = livraisonRepository.getReferenceById(livraison.getId());
            isPersonnelIsCommercialExterneOld = doesPersonnelIsCommercialExterne(livraisonOld);
        }

        List<DetLivraison> listToDelete = getDetStockDepotOldNotAnymore(detLivraisonDTOOld, detLivraisonDTOs);
        if(CollectionUtils.isNotEmpty(listToDelete)) {
            logger.info("Deleting det stock depot: {}", listToDelete.size());

            for(DetLivraison detLivraison : listToDelete) {
                if(!isPersonnelIsCommercialExterneOld) {
                    if(null != detLivraison.getStock() && null != detLivraison.getStock().getId()) {
                        stockService.updateQteStock(detLivraison.getStock().getId(), TypeQteToUpdate.QTE_STOCK_IMPORT, new RequestStockQte(detLivraison.getQteLivrer(), StockOperation.ADD_TO_STOCK.getValue(), null));
                    }
                }
                detLivraisonRepository.delete(detLivraison);
            }
        }

        List<DetLivraison> listToSave = detLivraisonDTOs.stream()
                .map(detLivraisonDTO -> {
                    DetLivraison detLivraison = detLivraisonMapper.toDetLivraison(detLivraisonDTO);

                    Optional<Stock> optionalStock =  stockRepository.findById(detLivraisonDTO.getStockId());
                    detLivraison.setStock(optionalStock.orElse(null));

                    detLivraison.setLivraison(livraison);

                    return detLivraison;
                })
                .toList();
        updateStockAndSaveDetStockDepot(listToSave, detLivraisonDTOOld, isPersonnelIsCommercialExterne);
    }

    public void updateStockAndSaveDetStockDepot(List<DetLivraison> listToSave, List<DetLivraison> detLivraisonDTOOld, boolean isPersonnelIsCommercialExterne) {
        if(CollectionUtils.isNotEmpty(listToSave)) {
            for(DetLivraison detLivraison : listToSave) {
                if(!isPersonnelIsCommercialExterne) {
                    if (null == detLivraison.getId()) {
                        stockService.updateQteStock(detLivraison.getStock().getId(), TypeQteToUpdate.QTE_STOCK_IMPORT, new RequestStockQte(detLivraison.getQteLivrer(), StockOperation.DELETE_FROM_STOCK.getValue(), null));
                    } else {
                        int qte = detLivraison.getQteLivrer();
                        int operation = 1;//Supprimer from qte stock
                        qte = updateQte(detLivraisonDTOOld, detLivraison, qte);

                        if (qte != 0) {
                            if (qte < 0) {
                                qte = Math.abs(qte);
                                operation = 2;//Ajouter from qte stock
                            }
                            stockService.updateQteStock(detLivraison.getStock().getId(), TypeQteToUpdate.QTE_STOCK_IMPORT, new RequestStockQte(qte, operation, null));
                        }
                    }
                }

                detLivraisonRepository.save(detLivraison);
            }
        }
    }

    public int updateQte(List<DetLivraison> detLivraisonDTOOld, DetLivraison detLivraison, int qte) {
        if(CollectionUtils.isNotEmpty(detLivraisonDTOOld)) {
            Optional<DetLivraison> optionalDetStockDepotOld = detLivraisonDTOOld.stream()
                    .filter(detStockDepotOld -> detStockDepotOld.getId().equals(detLivraison.getId()))
                    .findFirst();

            if(optionalDetStockDepotOld.isPresent()) {
                qte -= optionalDetStockDepotOld.get().getQteLivrer();
            }
        }

        return qte;
    }

    @Override
    public List<DetLivraison> findByLivraisonIdAndMontantProduitGreaterThanOrderByStockDesignation(Long livraisonId, double montant) {
        return detLivraisonRepository.findByLivraisonIdAndMontantProduitGreaterThanOrderByStockDesignation(livraisonId, montant);
    }

    @Override
    public List<DetLivraison> findByLivraisonIdAndMontantProduitLessThanEqualOrderByStockDesignation(Long livraisonId, double montant) {
        return detLivraisonRepository.findByLivraisonIdAndMontantProduitLessThanEqualOrderByStockDesignation(livraisonId, montant);
    }

    public void updateRepertoire(Repertoire oldRepertoire, Repertoire repertoire) {
        logger.info("Updating repertoire Old: {}, New: {}", oldRepertoire, repertoire);
        if(null != oldRepertoire && null != oldRepertoire.getId()) {
            if(null != repertoire && null != repertoire.getId()) {
                logger.info("Updating repertoire Old: {}, New: {}", oldRepertoire, repertoire);
                if(oldRepertoire.getId().equals(repertoire.getId())) {
                    logger.info("Updating repertoire but just the observation: {}", oldRepertoire);
                    updateRepertoireInformation(repertoire, false, TypeOperation.UPDATE, true);
                } else {
                    logger.info("Updating repertoire but just the number of operations: {}", oldRepertoire);
                    updateRepertoireInformation(oldRepertoire, true, TypeOperation.REMOVE, false);
                    logger.info("Updating repertoire (the number of operations and observation): {}", repertoire);
                    updateRepertoireInformation(repertoire, true, TypeOperation.ADD, true);
                }
            } else {
                logger.info("Updating repertoire minus one of the number of operations: {}", oldRepertoire);
                updateRepertoireInformation(oldRepertoire, true, TypeOperation.REMOVE, false);
            }
        } else {
            logger.info("Updating repertoire plus one of the number of operations: {}", repertoire);
            updateRepertoireInformation(repertoire, true, TypeOperation.ADD, true);
        }
    }

    public void updateRepertoireInformation(Repertoire repertoire, boolean updateNBrOperation, TypeOperation typeOperation, boolean updateObservation) {
        if(updateNBrOperation || updateObservation) {
            if(null != repertoire && null != repertoire.getId()) {
                Repertoire repertoireToUpdate = repertoireRepository.findById(repertoire.getId()).orElse(null);
                if(null != repertoireToUpdate && null != repertoireToUpdate.getId()) {
                    if(updateNBrOperation) {
                        if(typeOperation == TypeOperation.ADD) {
                            repertoireToUpdate.setNbrOperationClient(repertoireToUpdate.getNbrOperationClient() + 1);
                        } else {
                            repertoireToUpdate.setNbrOperationClient(repertoireToUpdate.getNbrOperationClient() - 1);
                            if(repertoireToUpdate.getNbrOperationClient() < 0) repertoireToUpdate.setNbrOperationClient(0);
                        }
                    }

                    if(updateObservation) {
                        repertoireToUpdate.setObservation(repertoire.getObservation());
                        repertoireToUpdate.setDesignation(repertoire.getDesignation());
                        repertoireToUpdate.setTel1(repertoire.getTel1());
                        repertoireToUpdate.setTel2(repertoire.getTel2());
                        repertoireToUpdate.setAdresse(repertoire.getAdresse());
                    }

                    repertoireRepository.save(repertoireToUpdate);
                }
            }
        }
    }

    @Override
    public List<LivraisonDTO> searchByCommon(CommonSearchModel commonSearchModel) {
        logger.info("Searching livraison by common: {}", commonSearchModel);
        return livraisonRepository.findAll(LivraisonSpecification.searchByCommon(commonSearchModel)).stream().map(livraisonMapper::toLivraisonDTO).toList();
    }

    /***************************************** Impression des etats "Consulter" ********************************************/

    @Override
    public byte[] imprimerEtat(LivraisonPrintRequest request) throws Exception {
        logger.info("Printing livraison etat: {}", request);

        if (null == request) {
            return JasperReportsUtil.anullerImpr("aucun element");
        }

        if (request.getType() == 0) {
            return imprimerEtatCommercial(request);
        } else if (request.getType() == 1) {
            return imprimerEtatProduit(request);
        } else if (request.getType() == 2) {
            return imprimerEtatVille(request);
        } else if (request.getType() == 4) {
            return imprimerEtatReglerNonRegler(request);
        } else if (request.getType() == 5) {
            return imprimerEtatCommercialJournalier(request);
        } else if (request.getType() == 6) {
            return imprimerEtatProduitsClient(request);
        }
        return imprimerEtatClient(request);
    }

    /**
     * Legacy fallback: when one of the two dates is missing the whole date filter is
     * dropped and the last 100 days are printed (LivraisonActionBeans: dateBl > today - 100).
     */
    private LocalDate[] resolveDateRange(LivraisonPrintRequest request) {
        if (null == request.getDateDebut() || null == request.getDateFin()) {
            return new LocalDate[]{ LocalDate.now().minusDays(100), LocalDate.now() };
        }
        return new LocalDate[]{ request.getDateDebut(), request.getDateFin() };
    }

    /** dateDb / dateFn / fichier / titleText, exactly like the legacy GeneratePDF parameters. */
    private Map<String, Object> buildPrintParams(LivraisonPrintRequest request, LocalDate dateDebut, LocalDate dateFin) {
        Map<String, Object> params = new HashMap<>();
        params.put("fichier", StaticVariables.bundleFR.getBaseBundleName());
        params.put("titleText", "");

        String dateDb = "";
        String dateFn = "";
        if (null != request.getDateDebut() && null != request.getDateFin()) {
            dateDb = StaticVariables.bundleFR.getString("datedebut") + " : " + StaticVariables.dateFormatDayFirst.format(dateDebut);
            dateFn = StaticVariables.bundleFR.getString("dateFin") + " : " + StaticVariables.dateFormatDayFirst.format(dateFin);
        }
        params.put("dateDb", dateDb);
        params.put("dateFn", dateFn);

        return params;
    }

    private CommonSearchModel buildSearch(LivraisonPrintRequest request, LocalDate dateDebut, LocalDate dateFin, Long repertoireId, Long personnelId) {
        return CommonSearchModel.builder()
                .searchByDate(true)
                .dateDebut(dateDebut)
                .dateFin(dateFin)
                .repertoireId(null != repertoireId && repertoireId != 0 ? repertoireId : null)
                .personnelId(null != personnelId && personnelId != 0 ? personnelId : null)
                .build();
    }

    private byte[] imprimerEtatClient(LivraisonPrintRequest request) throws Exception {
        LocalDate[] range = resolveDateRange(request);
        Long repertoireId = request.getRepertoireId();
        boolean oneClient = null != repertoireId && repertoireId != 0;

        List<Livraison> livraisons = new ArrayList<>(livraisonRepository.findAll(
                LivraisonSpecification.searchByCommon(buildSearch(request, range[0], range[1], repertoireId, null))));

        Map<String, Object> params = buildPrintParams(request, range[0], range[1]);

        String etatName;
        List<?> dataSource;
        if (oneClient) {
            // legacy: order by client designation, mantantBLBenefice desc
            livraisons.sort(Comparator.comparingDouble(Livraison::getMantantBLBenefice).reversed());
            dataSource = livraisons;
            etatName = "listeJournaliereCleintOne";
        } else {
            dataSource = buildClientPrintRows(livraisons);
            etatName = "listeJournaliereCleint";
        }

        if (dataSource.isEmpty()) {
            return JasperReportsUtil.anullerImpr("aucun element");
        }
        return jasperReportsUtil.jasperReportInBytes(dataSource, params, etatName, ReportTypeEnum.PDF, "");
    }

    /**
     * Groups the livraisons per client and attaches the benefice total of that client to
     * every row (legacy {@code ClientLivraison} rows). Replaces the legacy N+1
     * (one query per client + one sum per client) by a single fetch grouped in memory.
     * Order: client benefice total desc, then client designation asc, then the client's
     * livraisons by mantantBLBenefice desc (legacy {@code ClientLivraison.ID_COMPARATOR}).
     */
    private List<LivraisonClientPrintDTO> buildClientPrintRows(List<Livraison> livraisons) {
        Map<Long, List<Livraison>> livraisonsByClient = new LinkedHashMap<>();
        Map<Long, Double> sumBeneficeByClient = new HashMap<>();
        Map<Long, String> designationByClient = new HashMap<>();

        for (Livraison livraison : livraisons) {
            if (null == livraison.getRepertoire() || null == livraison.getRepertoire().getId()) {
                continue;
            }
            Long clientId = livraison.getRepertoire().getId();
            livraisonsByClient.computeIfAbsent(clientId, key -> new ArrayList<>()).add(livraison);
            sumBeneficeByClient.merge(clientId, livraison.getMantantBLBenefice(), Double::sum);
            designationByClient.put(clientId, null == livraison.getRepertoire().getDesignation() ? "" : livraison.getRepertoire().getDesignation());
        }

        List<Long> clientIds = new ArrayList<>(livraisonsByClient.keySet());
        clientIds.sort((first, second) -> {
            int byBenefice = Double.compare(sumBeneficeByClient.getOrDefault(second, 0d), sumBeneficeByClient.getOrDefault(first, 0d));
            return byBenefice != 0 ? byBenefice : designationByClient.get(first).compareTo(designationByClient.get(second));
        });

        List<LivraisonClientPrintDTO> rows = new ArrayList<>();
        for (Long clientId : clientIds) {
            List<Livraison> clientLivraisons = livraisonsByClient.get(clientId);
            clientLivraisons.sort(Comparator.comparingDouble(Livraison::getMantantBLBenefice).reversed());
            for (Livraison livraison : clientLivraisons) {
                rows.add(new LivraisonClientPrintDTO(livraison, sumBeneficeByClient.getOrDefault(clientId, 0d)));
            }
        }
        return rows;
    }

    /**
     * Consulter Villes (legacy {@code imprimerVille}, type 2): livraisons grouped per
     * client, clients ordered by {@code ville.nomVille}, and inside one client the
     * livraisons ordered by {@code mantantBLBenefice desc}. The client benefice total
     * is attached to every row.
     * <p>
     * The legacy user/operator restrictions ({@code isLivraisonLimite}, the
     * {@code employeOperateur} filter) are dropped like the other Spring etats.
     */
    private byte[] imprimerEtatVille(LivraisonPrintRequest request) throws Exception {
        LocalDate[] range = resolveDateRange(request);
        Long villeId = request.getVilleId();

        List<Livraison> livraisons = new ArrayList<>(livraisonRepository.findAll(
                LivraisonSpecification.searchByCommon(buildSearch(request, range[0], range[1], null, null))));

        if (null != villeId && villeId != 0) {
            livraisons.removeIf(livraison -> null == livraison.getRepertoire() || null == livraison.getRepertoire().getVille()
                    || !villeId.equals(livraison.getRepertoire().getVille().getId()));
        }

        Map<String, Object> params = buildPrintParams(request, range[0], range[1]);
        params.put("titleText", "");

        List<LivraisonVillePrintDTO> dataSource = buildVillePrintRows(livraisons);
        if (dataSource.isEmpty()) {
            return JasperReportsUtil.anullerImpr("aucun element");
        }
        return jasperReportsUtil.jasperReportInBytes(dataSource, params, "listeJournaliereCleintVille", ReportTypeEnum.PDF, "");
    }

    /**
     * Rows of {@code listeJournaliereCleintVille.jrxml}: one livraison per row, carrying
     * its client repertoire (which exposes {@code ville.nomVille}) and the client
     * benefice total. Clients are ordered by ville name, mirroring the legacy
     * {@code imprimerVille()} nested queries.
     */
    private List<LivraisonVillePrintDTO> buildVillePrintRows(List<Livraison> livraisons) {
        Map<Long, List<Livraison>> livraisonsByClient = new LinkedHashMap<>();
        Map<Long, Repertoire> repertoireByClient = new HashMap<>();
        Map<Long, Double> sumBeneficeByClient = new HashMap<>();
        Map<Long, String> villeByClient = new HashMap<>();

        for (Livraison livraison : livraisons) {
            if (null == livraison.getRepertoire() || null == livraison.getRepertoire().getId()) {
                continue;
            }
            Long clientId = livraison.getRepertoire().getId();
            livraisonsByClient.computeIfAbsent(clientId, key -> new ArrayList<>()).add(livraison);
            repertoireByClient.put(clientId, livraison.getRepertoire());
            sumBeneficeByClient.merge(clientId, livraison.getMantantBLBenefice(), Double::sum);
            villeByClient.put(clientId, null == livraison.getRepertoire().getVille() || null == livraison.getRepertoire().getVille().getNomVille()
                    ? "" : livraison.getRepertoire().getVille().getNomVille());
        }

        List<Long> clientIds = new ArrayList<>(livraisonsByClient.keySet());
        clientIds.sort(Comparator.comparing(villeByClient::get));

        List<LivraisonVillePrintDTO> rows = new ArrayList<>();
        for (Long clientId : clientIds) {
            List<Livraison> clientLivraisons = livraisonsByClient.get(clientId);
            clientLivraisons.sort(Comparator.comparingDouble(Livraison::getMantantBLBenefice).reversed());
            for (Livraison livraison : clientLivraisons) {
                rows.add(new LivraisonVillePrintDTO(livraison, sumBeneficeByClient.getOrDefault(clientId, 0d), repertoireByClient.get(clientId)));
            }
        }
        return rows;
    }

    /**
     * Consulter Client + Regle / Non Regle (legacy {@code imprimerRegelerNonRegler},
     * type 4). A null or {@code 2} selector produces the legacy "aucun element" PDF.
     * Rows are ordered by {@code dateBl, mantantBLBenefice desc}.
     */
    private byte[] imprimerEtatReglerNonRegler(LivraisonPrintRequest request) throws Exception {
        Integer reglerNonRegler = request.getReglerNonRegler();
        if (null == reglerNonRegler || reglerNonRegler == 2) {
            return JasperReportsUtil.anullerImpr("aucun element");
        }

        LocalDate[] range = resolveDateRange(request);
        List<Livraison> livraisons = new ArrayList<>(livraisonRepository.findAll(
                LivraisonSpecification.searchByCommon(buildSearch(request, range[0], range[1], null, null))));
        livraisons.removeIf(livraison -> livraison.getReglerNonRegler() != reglerNonRegler);
        livraisons.sort(Comparator.comparing(Livraison::getDateBl, Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(Comparator.comparingDouble(Livraison::getMantantBLBenefice).reversed()));

        Map<String, Object> params = buildPrintParams(request, range[0], range[1]);
        params.put("titleText", StaticVariables.bundleFR.getString(reglerNonRegler == 0 ? "NonRegler" : "Regler"));

        if (livraisons.isEmpty()) {
            return JasperReportsUtil.anullerImpr("aucun element");
        }
        return jasperReportsUtil.jasperReportInBytes(livraisons, params, "listeEtatLivrRegNoReg", ReportTypeEnum.PDF, "");
    }

    /**
     * Consulter (journalier commercial, legacy {@code imprimerCommercial}, type 5).
     * Rows are ordered by {@code personnel.designation, mantantBLPourcent desc}. The
     * legacy {@code isLivraisonLimite}/{@code normalUser} restrictions are dropped like
     * the other Spring etats.
     */
    private byte[] imprimerEtatCommercialJournalier(LivraisonPrintRequest request) throws Exception {
        LocalDate[] range = resolveDateRange(request);
        List<Livraison> livraisons = new ArrayList<>(livraisonRepository.findAll(
                LivraisonSpecification.searchByCommon(buildSearch(request, range[0], range[1], null, null))));
        livraisons.sort(Comparator.comparing((Livraison livraison) -> null == livraison.getPersonnel() || null == livraison.getPersonnel().getDesignation()
                        ? "" : livraison.getPersonnel().getDesignation())
                .thenComparing(Comparator.comparingDouble(Livraison::getMantantBLPourcent).reversed()));

        Map<String, Object> params = buildPrintParams(request, range[0], range[1]);
        params.put("titleText", "");

        if (livraisons.isEmpty()) {
            return JasperReportsUtil.anullerImpr("aucun element");
        }
        return jasperReportsUtil.jasperReportInBytes(livraisons, params, "listeJournaliereCommercial", ReportTypeEnum.PDF, "");
    }

    /**
     * Consulter Client + Produit (legacy {@code imprimerProduitsClient}, type 6): the
     * products delivered to one client in the range (with their total quantity) side by
     * side with the non delivered products of the same client.
     * <p>
     * A null or {@code 0} client produces the legacy "Client obligatoire" PDF.
     */
    private byte[] imprimerEtatProduitsClient(LivraisonPrintRequest request) throws Exception {
        Long repertoireClientId = request.getRepertoireClientId2();
        if (null == repertoireClientId || repertoireClientId == 0) {
            return JasperReportsUtil.anullerImpr(StaticVariables.bundleFR.getString("Client") + " " + StaticVariables.bundleFR.getString("obligatoire"));
        }

        LocalDate[] range = resolveDateRange(request);
        List<DetLivraison> detLivraisons = detLivraisonRepository.findByRepertoireIdAndDateBlBetween(repertoireClientId, range[0], range[1]);

        Map<Long, StockImprPrintDTO> rowsByStock = new LinkedHashMap<>();
        for (DetLivraison detLivraison : detLivraisons) {
            if (null == detLivraison.getStock() || null == detLivraison.getStock().getId()) {
                continue;
            }
            StockImprPrintDTO row = rowsByStock.computeIfAbsent(detLivraison.getStock().getId(),
                    key -> new StockImprPrintDTO(key, detLivraison.getStock().getDesignation(), 0L));
            row.setQteLivrer(row.getQteLivrer() + detLivraison.getQteLivrer());
        }

        if (rowsByStock.isEmpty()) {
            return JasperReportsUtil.anullerImpr("aucun element");
        }

        List<StockImprPrintDTO> dataSource = new ArrayList<>(rowsByStock.values());
        dataSource.sort(Comparator.comparing(StockImprPrintDTO::getDesignation, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER)));

        // non delivered (non archived) products of that client, ordered by designation
        List<String> nonDelivered = new ArrayList<>();
        Set<String> seenDesignations = new HashSet<>();
        for (Stock stock : stockRepository.listStockNonArchiveArchive(null)) {
            if (rowsByStock.containsKey(stock.getId())) {
                continue;
            }
            if (null != stock.getDesignation() && seenDesignations.add(stock.getDesignation())) {
                nonDelivered.add(stock.getDesignation());
            }
        }

        // pad the delivered rows so both columns line up (legacy blank StockImpr rows)
        if (nonDelivered.size() > dataSource.size()) {
            int diff = nonDelivered.size() - dataSource.size();
            for (int i = 0; i < diff; i++) {
                dataSource.add(new StockImprPrintDTO(1L, "", 0L));
            }
        }
        for (int i = 0; i < dataSource.size() && i < nonDelivered.size(); i++) {
            dataSource.get(i).setDesignation2(nonDelivered.get(i));
        }

        Map<String, Object> params = buildPrintParams(request, range[0], range[1]);
        params.put("titleText", "");
        LocalDate dernierDateValue = livraisonRepository.findMaxDateBlByRepertoireId(repertoireClientId);
        params.put("dernierDate", null != dernierDateValue ? StaticVariables.dateFormatDayFirst.format(dernierDateValue) : "");
        params.put("stockNonPresent", "");
        params.put("clientName", repertoireRepository.findById(repertoireClientId).map(Repertoire::getDesignation).orElse(""));

        return jasperReportsUtil.jasperReportInBytes(dataSource, params, "etatLivraisonProduitsParClient", ReportTypeEnum.PDF, "");
    }

    /**
     * Consulter par numero de cheque (legacy {@code imprimerParNumCheque}). A blank
     * input produces the legacy "aucun element" PDF. Mirrors the legacy asymmetry:
     * pre-filter = exact, trimmed, lower-case on {@code numCheque}; aggregate filter =
     * {@code numCheque like '%X%' or numCheque2 like 'X'} with the raw input.
     */
    public byte[] imprimerParNumCheque(String numChequeLibel) {
        logger.info("Printing livraison etat by numCheque: {}", numChequeLibel);

        if (StringUtils.isBlank(numChequeLibel)) {
            return JasperReportsUtil.anullerImpr("aucun element");
        }

        List<Long> stockIds = detLivraisonRepository.findDistinctStockIdsByNumCheque(numChequeLibel.trim().toLowerCase());
        if (CollectionUtils.isEmpty(stockIds)) {
            return JasperReportsUtil.anullerImpr("aucun element");
        }

        List<NumChequePrintDTO> dataSource = new ArrayList<>();
        for (Object[] row : detLivraisonRepository.sumQteAndMontantByNumCheque(stockIds, numChequeLibel)) {
            dataSource.add(new NumChequePrintDTO(toLong(row[0]), (String) row[1], toNumber(row[2]).longValue(), toNumber(row[3]).doubleValue()));
        }
        if (dataSource.isEmpty()) {
            return JasperReportsUtil.anullerImpr("aucun element");
        }
        dataSource.sort(Comparator.comparing(NumChequePrintDTO::getDesignation, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER)));

        Map<String, Object> params = new HashMap<>();
        params.put("fichier", StaticVariables.bundleFR.getBaseBundleName());
        params.put("numCheque", numChequeLibel);

        try {
            return jasperReportsUtil.jasperReportInBytes(dataSource, params, "etatNumCheque", ReportTypeEnum.PDF, "");
        } catch (Exception e) {
            logger.error("Error printing livraison etat by numCheque: {}", e.getMessage());
            return JasperReportsUtil.anullerImpr("aucun element");
        }
    }

    /**
     * Narrow "Livrer" update (legacy {@code livrerNonLivrer}): only
     * {@code livrernonlivrer} is touched, unlike {@link #save(LivraisonResponse)} which
     * deletes the detLivraisons when the list is empty.
     */
    public LivraisonDTO livrer(Long id, int livrernonlivrer) {
        logger.info("Livrer livraison id: {}, value: {}", id, livrernonlivrer);

        Livraison livraison = livraisonRepository.findById(id).orElse(null);
        if (null == livraison) {
            return null;
        }
        livraison.setLivrernonlivrer(livrernonlivrer);
        return livraisonMapper.toLivraisonDTO(livraisonRepository.save(livraison));
    }

    private byte[] imprimerEtatCommercial(LivraisonPrintRequest request) throws Exception {
        Long personnelId = request.getPersonnelId();
        if (null == personnelId || personnelId == 0) {
            return JasperReportsUtil.anullerImpr(StaticVariables.bundleFR.getString("commercial") + " " + StaticVariables.bundleFR.getString("obligatoire"));
        }

        LocalDate[] range = resolveDateRange(request);
        Map<String, Object> params = buildPrintParams(request, range[0], range[1]);
        params.put("commercialName", personnelRepository.findById(personnelId).map(Personnel::getDesignation).orElse(""));

        String etatName;
        List<?> dataSource;
        switch (request.getByBlOrProduit()) {
            case 1 -> {
                dataSource = buildCommercialProduitRows(range[0], range[1], personnelId);
                etatName = "etatCommercialDetailProduit";
            }
            case 2 -> {
                List<Livraison> livraisons = new ArrayList<>(livraisonRepository.findAll(
                        LivraisonSpecification.searchByCommon(buildSearch(request, range[0], range[1], null, personnelId))));
                // legacy: order by repertoireByClient.typeRepertoire desc, then dateBl
                livraisons.sort(Comparator.comparingInt((Livraison l) -> l.getRepertoire().getTypeRepertoire())
                        .reversed()
                        .thenComparing(Livraison::getDateBl, Comparator.nullsFirst(Comparator.naturalOrder())));
                dataSource = livraisons;
                etatName = "etatCommercialDetailByTypeRepertoire";
            }
            case 3 -> {
                dataSource = buildCommercialCommissionRows(range[0], range[1], personnelId);
                etatName = "etatCommercialDetailProduitByCommission";
            }
            default -> {
                List<Livraison> livraisons = new ArrayList<>(livraisonRepository.findAll(
                        LivraisonSpecification.searchByCommon(buildSearch(request, range[0], range[1], null, personnelId))));
                // legacy: order by dateBl
                livraisons.sort(Comparator.comparing(Livraison::getDateBl, Comparator.nullsFirst(Comparator.naturalOrder())));
                dataSource = livraisons;
                etatName = "etatCommercialDetail";
            }
        }

        if (dataSource.isEmpty()) {
            return JasperReportsUtil.anullerImpr("aucun element");
        }
        return jasperReportsUtil.jasperReportInBytes(dataSource, params, etatName, ReportTypeEnum.PDF, "");
    }

    /**
     * Consulter Commercial, mode "par Produit": paid products aggregated by product and
     * sorted by montant desc, then the free products sorted by designation
     * (legacy imprimerCommercialDetail with byBlOrProduit == 1).
     */
    private List<LivraisonProduitPrintDTO> buildCommercialProduitRows(LocalDate dateDebut, LocalDate dateFin, Long personnelId) {
        List<Object[]> paidRows = detLivraisonRepository.sumMontantProduitByStock(dateDebut, dateFin, personnelId);
        List<Object[]> freeRows = detLivraisonRepository.sumFreeQteByStock(dateDebut, dateFin, personnelId);

        Map<Long, Stock> stocks = findStocks(paidRows, freeRows);

        List<LivraisonProduitPrintDTO> paid = new ArrayList<>();
        for (Object[] row : paidRows) {
            Stock stock = stocks.get(toLong(row[0]));
            if (null != stock) {
                paid.add(new LivraisonProduitPrintDTO(stock.getDesignation(), toNumber(row[1]).longValue(), toNumber(row[2]).doubleValue(), 0));
            }
        }
        paid.sort(Comparator.comparing(LivraisonProduitPrintDTO::getMontantProduit).reversed());

        List<LivraisonProduitPrintDTO> free = new ArrayList<>();
        for (Object[] row : freeRows) {
            Stock stock = stocks.get(toLong(row[0]));
            if (null != stock) {
                free.add(new LivraisonProduitPrintDTO(stock.getDesignation(), toNumber(row[1]).longValue(), 0.0, 1));
            }
        }
        free.sort(Comparator.comparing(LivraisonProduitPrintDTO::getDesignation, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER)));

        paid.addAll(free);
        return paid;
    }

    /**
     * Consulter Commercial, mode "par Commission": one row per delivered product with
     * its paid and free quantities merged, then P.U = montantProduit / qteLivrer,
     * Taux = stock.commission and Commission = Taux * 0.01 * montantProduit
     * (legacy {@code LivraisonActionBeans.imprimerCommercialDetail} with
     * byBlOrProduit == 3, adapted to the new single stock commission rate).
     */
    private List<LivraisonProduitCommissionPrintDTO> buildCommercialCommissionRows(LocalDate dateDebut, LocalDate dateFin, Long personnelId) {
        List<Object[]> paidRows = detLivraisonRepository.sumMontantProduitByStock(dateDebut, dateFin, personnelId);
        List<Object[]> freeRows = detLivraisonRepository.sumFreeQteByStock(dateDebut, dateFin, personnelId);

        Map<Long, Stock> stocks = findStocks(paidRows, freeRows);

        // union of paid and free stock ids: quantity = paid + free, amount = paid only
        Map<Long, long[]> qteByStock = new LinkedHashMap<>();
        Map<Long, Double> montantByStock = new HashMap<>();
        for (Object[] row : paidRows) {
            Long stockId = toLong(row[0]);
            if (null != stockId) {
                qteByStock.computeIfAbsent(stockId, key -> new long[1])[0] += toNumber(row[1]).longValue();
                montantByStock.merge(stockId, toNumber(row[2]).doubleValue(), Double::sum);
            }
        }
        for (Object[] row : freeRows) {
            Long stockId = toLong(row[0]);
            if (null != stockId) {
                qteByStock.computeIfAbsent(stockId, key -> new long[1])[0] += toNumber(row[1]).longValue();
            }
        }

        List<LivraisonProduitCommissionPrintDTO> rows = new ArrayList<>();
        for (Map.Entry<Long, long[]> entry : qteByStock.entrySet()) {
            Stock stock = stocks.get(entry.getKey());
            if (null == stock) {
                continue;
            }
            long qte = entry.getValue()[0];
            double montant = montantByStock.getOrDefault(entry.getKey(), 0d);
            double prixUnitaire = qte != 0 ? montant / qte : 0;
            double taux = stock.getCommission();
            double commission = taux * 0.01 * montant;
            rows.add(new LivraisonProduitCommissionPrintDTO(stock.getDesignation(), qte, montant, 0, prixUnitaire, taux, commission));
        }
        rows.sort(Comparator.comparing(LivraisonProduitCommissionPrintDTO::getMontantProduit).reversed());
        return rows;
    }

    private byte[] imprimerEtatProduit(LivraisonPrintRequest request) throws Exception {
        LocalDate[] range = resolveDateRange(request);
        Map<String, Object> params = buildPrintParams(request, range[0], range[1]);

        Long stockId = request.getStockId();
        String etatName;
        List<?> dataSource;
        if (null != stockId && stockId != 0) {
            List<DetLivraison> list = detLivraisonRepository.findByStockIdAndDateBlBetween(stockId, range[0], range[1]);
            params.put("valeur", computeBeneficePercentage(list));
            dataSource = list;
            etatName = "listeProduitLivrs";
        } else {
            dataSource = buildProduitAllRows(range[0], range[1]);
            params.put("valeur", "");
            etatName = "listeProduitLivrsTous";
        }

        if (dataSource.isEmpty()) {
            return JasperReportsUtil.anullerImpr("aucun element");
        }
        return jasperReportsUtil.jasperReportInBytes(dataSource, params, etatName, ReportTypeEnum.PDF, "");
    }

    /**
     * Consulter Produit, all products: paid products aggregated per product (legacy
     * {@code FournisseurProduit.ID_COMPARATORProdui}: benefice desc then designation asc),
     * then the free products sorted by quantity * pattc desc (legacy imprimerProduit).
     * Rows with a zero delivered quantity are skipped, like in the legacy code.
     */
    private List<StockLivraisonSumPrintDTO> buildProduitAllRows(LocalDate dateDebut, LocalDate dateFin) {
        List<Object[]> paidRows = detLivraisonRepository.sumMontantProduitByStock(dateDebut, dateFin, null);
        List<Object[]> freeRows = detLivraisonRepository.sumFreeQteByStock(dateDebut, dateFin, null);

        Map<Long, Stock> stocks = findStocks(paidRows, freeRows);

        List<StockLivraisonSumPrintDTO> paid = new ArrayList<>();
        for (Object[] row : paidRows) {
            Stock stock = stocks.get(toLong(row[0]));
            int qteLivre = toNumber(row[1]).intValue();
            if (null != stock && qteLivre != 0) {
                paid.add(new StockLivraisonSumPrintDTO(stock, qteLivre, toNumber(row[2]).doubleValue(), toNumber(row[3]).doubleValue(), 0));
            }
        }
        paid.sort(Comparator.comparingDouble(StockLivraisonSumPrintDTO::getBeneficeDH).reversed()
                .thenComparing(dto -> null == dto.getStock().getDesignation() ? "" : dto.getStock().getDesignation()));

        List<StockLivraisonSumPrintDTO> free = new ArrayList<>();
        for (Object[] row : freeRows) {
            Stock stock = stocks.get(toLong(row[0]));
            int qteLivre = toNumber(row[1]).intValue();
            if (null != stock && qteLivre != 0) {
                free.add(new StockLivraisonSumPrintDTO(stock, qteLivre, 0.0, 0.0, 1));
            }
        }
        free.sort(Comparator.comparingDouble((StockLivraisonSumPrintDTO dto) -> dto.getQteLivre() * dto.getStock().getPattc()).reversed());

        paid.addAll(free);
        return paid;
    }

    /** Legacy: benepourcentage = ((sum(prixVente) - sum(pattc)) / sum(prixVente)) * 100. */
    private String computeBeneficePercentage(List<DetLivraison> list) {
        double prixVente = 0.0;
        double pattc = 0.0;
        for (DetLivraison detLivraison : list) {
            if (detLivraison.getPrixVente() > 0.0) {
                prixVente += detLivraison.getPrixVente();
            }
            if (null != detLivraison.getStock() && detLivraison.getStock().getPattc() > 0.0) {
                pattc += detLivraison.getStock().getPattc();
            }
        }
        if (prixVente <= 0.0) {
            return "";
        }
        double benepourcentage = (((prixVente - pattc) / prixVente) * 100);
        return new DecimalFormat("0.##").format(benepourcentage);
    }

    /** One {@code findAllById} for both aggregation results - no N+1. */
    private Map<Long, Stock> findStocks(List<Object[]> paidRows, List<Object[]> freeRows) {
        Set<Long> stockIds = new HashSet<>();
        for (Object[] row : paidRows) {
            Long stockId = toLong(row[0]);
            if (null != stockId) {
                stockIds.add(stockId);
            }
        }
        for (Object[] row : freeRows) {
            Long stockId = toLong(row[0]);
            if (null != stockId) {
                stockIds.add(stockId);
            }
        }

        Map<Long, Stock> stocks = new HashMap<>();
        if (!stockIds.isEmpty()) {
            stockRepository.findAllById(stockIds).forEach(stock -> stocks.put(stock.getId(), stock));
        }
        return stocks;
    }

    private Long toLong(Object value) {
        return value instanceof Number number ? number.longValue() : null;
    }

    private Number toNumber(Object value) {
        return value instanceof Number number ? number : 0d;
    }
}
