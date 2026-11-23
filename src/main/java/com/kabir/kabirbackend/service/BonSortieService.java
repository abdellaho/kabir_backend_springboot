package com.kabir.kabirbackend.service;

import com.kabir.kabirbackend.config.enums.ReportTypeEnum;
import com.kabir.kabirbackend.config.enums.TypeQteToUpdate;
import com.kabir.kabirbackend.config.requests.BonSortiePrintRequest;
import com.kabir.kabirbackend.config.requests.RequestStockQte;
import com.kabir.kabirbackend.config.responses.BonSortieResponse;
import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.config.util.JasperReportsUtil;
import com.kabir.kabirbackend.config.util.StaticVariables;
import com.kabir.kabirbackend.dto.BonSortieDTO;
import com.kabir.kabirbackend.dto.BonSortieDetailPrintDTO;
import com.kabir.kabirbackend.dto.BonSortiePrintDTO;
import com.kabir.kabirbackend.dto.BonSortieSummaryPrintDTO;
import com.kabir.kabirbackend.dto.DetailBonSortieDTO;
import com.kabir.kabirbackend.entities.*;
import com.kabir.kabirbackend.mapper.BonSortieMapper;
import com.kabir.kabirbackend.mapper.DetailBonSortieMapper;
import com.kabir.kabirbackend.repository.*;
import com.kabir.kabirbackend.service.interfaces.IBonSortieService;
import com.kabir.kabirbackend.specifications.BonSortieSpecification;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class BonSortieService implements IBonSortieService {

    private final Logger logger = LoggerFactory.getLogger(BonSortieService.class);

    private final BonSortieRepository bonSortieRepository;
    private final DetailBonSortieRepository detailBonSortieRepository;
    private final DetLivraisonRepository detLivraisonRepository;
    private final BonSortieMapper bonSortieMapper;
    private final DetailBonSortieMapper detailBonSortieMapper;
    private final StockService stockService;
    private final StockRepository stockRepository;
    private final RepertoireRepository repertoireRepository;
    private final PersonnelRepository personnelRepository;
    private final EtablissementService etablissementService;
    private final JasperReportsUtil jasperReportsUtil;

    private static final String AUCUN_ELEMENT = "aucun element";

    public BonSortieService(
            BonSortieRepository bonSortieRepository,
            DetailBonSortieRepository detailBonSortieRepository,
            DetLivraisonRepository detLivraisonRepository,
            BonSortieMapper bonSortieMapper,
            DetailBonSortieMapper detailBonSortieMapper,
            StockService stockService,
            StockRepository stockRepository,
            RepertoireRepository repertoireRepository,
            PersonnelRepository personnelRepository,
            EtablissementService etablissementService,
            JasperReportsUtil jasperReportsUtil
    ) {
        this.bonSortieRepository = bonSortieRepository;
        this.bonSortieMapper = bonSortieMapper;
        this.detailBonSortieMapper = detailBonSortieMapper;
        this.stockService = stockService;
        this.stockRepository = stockRepository;
        this.detailBonSortieRepository = detailBonSortieRepository;
        this.detLivraisonRepository = detLivraisonRepository;
        this.repertoireRepository = repertoireRepository;
        this.personnelRepository = personnelRepository;
        this.etablissementService = etablissementService;
        this.jasperReportsUtil = jasperReportsUtil;
    }

    @Override
    public BonSortieDTO save(BonSortieResponse bonSortieResponse) {
        logger.info("Saving bon sortie Response: {}", bonSortieResponse);
        BonSortieDTO bonSortieDTO = bonSortieResponse.bonSortie();
        boolean isSave = bonSortieDTO.getId() == null;
        try {
            Optional<Personnel> optionalRepertoire = personnelRepository.findById(bonSortieResponse.bonSortie().getCommercialId());
            Optional<Personnel> optionalPersonnel = personnelRepository.findById(bonSortieResponse.bonSortie().getPersonnelId());

            BonSortie bonSortie = bonSortieMapper.toEntity(bonSortieDTO);
            bonSortie.setCommercial(optionalRepertoire.orElse(null));
            bonSortie.setPersonnel(optionalPersonnel.orElse(null));
            bonSortieDTO = bonSortieMapper.toDTO(bonSortieRepository.save(bonSortie));

            enregistrerDetBonSortie(bonSortie, isSave, bonSortieResponse.detailBonSorties());

            logger.info("Bon sortie saved successfully: {}", bonSortieDTO);
        } catch (Exception e) {
            logger.error("Failed to save bon sortie: {}", e.getMessage());
        }
        return bonSortieDTO;
    }

    @Override
    public BonSortieDTO findById(Long id) {
        logger.info("Finding bon sortie by id: {}", id);
        BonSortieDTO bonSortieDTO = bonSortieMapper.toDTO(bonSortieRepository.findById(id).orElse(null));
        logger.info("Bon sortie found: {}", bonSortieDTO);
        return bonSortieDTO;
    }

    @Override
    public BonSortieResponse findByIdBonSortieResponse(Long id) {
        logger.info("Finding bon Sortie response by id: {}", id);
        try {
            BonSortieDTO bonSortieDTO = bonSortieMapper.toDTO(bonSortieRepository.findById(id).orElse(null));
            if(null != bonSortieDTO && null != bonSortieDTO.getId()) {
                List<DetailBonSortieDTO> list = detailBonSortieRepository.findAllByBonSortieId(id).stream()
                    .map(detailBonSortieMapper::toDTO)
                    .toList();

                return new BonSortieResponse(bonSortieDTO, list);
            } else {
                logger.info("Bon sortie not found with ID: {}", id);
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to find bon sortie response by id: {}", id);
            return null;
        }
    }

    @Override
    public List<BonSortieDTO> findAll() {
        logger.info("Finding all bon sorties");
        List<BonSortieDTO> stockDepotDTOs = bonSortieRepository.findAll(Sort.by(Sort.Direction.DESC, "dateOperation")).stream()
                .map(bonSortieMapper::toDTO)
                .collect(Collectors.toList());
        logger.info("Bon sorties found: {}", stockDepotDTOs.size());
        return stockDepotDTOs;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting bon sortie by id: {}", id);
        try {
            List<DetailBonSortie> detailBonSortieDTOOld = detailBonSortieRepository.findAllByBonSortieId(id);
            logger.info("List of Detail Bon Sortie to delete: {}", detailBonSortieDTOOld.size());

            if(CollectionUtils.isNotEmpty(detailBonSortieDTOOld)) {
                for(DetailBonSortie detStockDepot : detailBonSortieDTOOld) {
                    if(null != detStockDepot.getStock() && null != detStockDepot.getStock().getId()) {
                        stockService.updateQteStock(detStockDepot.getStock().getId(), TypeQteToUpdate.QTE_STOCK_SORTIE, new RequestStockQte(detStockDepot.getQteSortie(), 1, null));

                        logger.info("Deleting detail bon sortie by id: {}", detStockDepot.getId());
                        detailBonSortieRepository.deleteById(detStockDepot.getId());
                    }
                }
            }

            bonSortieRepository.deleteById(id);
            logger.info("Bon sortie deleted successfully");
        } catch (Exception e) {
            logger.error("Failed to delete bon sortie: {}", e.getMessage());
        }
    }

    @Override
    public List<BonSortieDTO> search(BonSortieDTO bonSortieDTO) {
        return this.findAll();
    }


    public List<DetailBonSortie> getDetStockDepotOldNotAnymore(List<DetailBonSortie> detailBonSortieDTOOld, List<DetailBonSortieDTO> detStockDepotDTOs) {
        if(CollectionUtils.isEmpty(detailBonSortieDTOOld)) {
            return new ArrayList<>();
        }

        return detailBonSortieDTOOld.stream()
                .filter(detStockDepotOld -> detStockDepotDTOs.stream()
                        .noneMatch(detailBonSortieDTO -> detailBonSortieDTO.getId() != null && detailBonSortieDTO.getId().equals(detStockDepotOld.getId())))
                .collect(Collectors.toList());
    }

    public void enregistrerDetBonSortie(BonSortie bonSortie, boolean isSave, List<DetailBonSortieDTO> detStockDepotDTOs) {
        List<DetailBonSortie> detailBonSortieDTOOld = new ArrayList<>();
        if(!isSave) {
            detailBonSortieDTOOld = detailBonSortieRepository.findAllByBonSortieId(bonSortie.getId());
        }

        List<DetailBonSortie> listToDelete = getDetStockDepotOldNotAnymore(detailBonSortieDTOOld, detStockDepotDTOs);
        if(CollectionUtils.isNotEmpty(listToDelete)) {
            logger.info("Deleting detail bon sortie: {}", listToDelete.size());
            for(DetailBonSortie detStockDepot : listToDelete) {
                if(null != detStockDepot.getStock() && null != detStockDepot.getStock().getId()) {
                    stockService.updateQteStock(detStockDepot.getStock().getId(), TypeQteToUpdate.QTE_STOCK_SORTIE, new RequestStockQte(detStockDepot.getQteSortie(), 1, null));
                }

                detailBonSortieRepository.delete(detStockDepot);
            }
        }

        List<DetailBonSortie> listToSave = detStockDepotDTOs.stream()
                .map(detailBonSortieDTO -> {
                    DetailBonSortie detailBonSortie = detailBonSortieMapper.toEntity(detailBonSortieDTO);

                    Optional<Stock> optionalStock =  stockRepository.findById(detailBonSortieDTO.getStockId());
                    detailBonSortie.setStock(optionalStock.orElse(null));

                    detailBonSortie.setBonSortie(bonSortie);

                    return detailBonSortie;
                })
                .toList();
        updateStockAndSaveDetStockDepot(listToSave, detailBonSortieDTOOld);
    }

    public void updateStockAndSaveDetStockDepot(List<DetailBonSortie> listToSave, List<DetailBonSortie> detailBonSortieDTOOld) {
        if(CollectionUtils.isNotEmpty(listToSave)) {
            for(DetailBonSortie detStockDepot : listToSave) {
                if(null == detStockDepot.getId()) {
                    stockService.updateQteStock(detStockDepot.getStock().getId(), TypeQteToUpdate.QTE_STOCK_SORTIE, new RequestStockQte(detStockDepot.getQteSortie(),2, null));
                } else {
                    int qte = detStockDepot.getQteSortie();
                    int operation = 2;
                    qte = updateQte(detailBonSortieDTOOld, detStockDepot, qte);

                    if(qte != 0) {
                        if(qte < 0) {
                            qte = Math.abs(qte);
                            operation = 1;
                        }
                        stockService.updateQteStock(detStockDepot.getStock().getId(), TypeQteToUpdate.QTE_STOCK_SORTIE, new RequestStockQte(qte, operation, null));
                    }
                }

                detailBonSortieRepository.save(detStockDepot);
            }
        }
    }

    public int updateQte(List<DetailBonSortie> detailBonSortieDTOOld, DetailBonSortie detStockDepot, int qte) {
        if(CollectionUtils.isNotEmpty(detailBonSortieDTOOld)) {
            Optional<DetailBonSortie> optionalDetStockDepotOld = detailBonSortieDTOOld.stream()
                    .filter(detStockDepotOld -> detStockDepotOld.getId().equals(detStockDepot.getId()))
                    .findFirst();

            if(optionalDetStockDepotOld.isPresent()) {
                qte -= optionalDetStockDepotOld.get().getQteSortie();
            }
        }

        return qte;
    }

    @Override
    public int getLastNumBonSortie(BonSortieDTO bonSortieDTO) {
        logger.info("Getting last num bon sortie");
        //LocalDate localDate = bonSortieDTO.getDateOperation().atZone(ZoneId.systemDefault()).toLocalDate();
        return bonSortieRepository.findMaxNumBonSortie().map(l -> l + 1).orElse(1);
    }

    @Override
    public List<BonSortieDTO> searchByCommon(CommonSearchModel commonSearchModel) {
        logger.info("Searching bon sortie by common: {}", commonSearchModel);
        return bonSortieRepository.findAll(BonSortieSpecification.builder().build().searchByCommon(commonSearchModel)).stream()
                .map(bonSortieMapper::toDTO)
                .toList();
    }

    /**
     * Legacy {@code BonSortieActionBeans.imprimerBonSortie()}: prints the details of one
     * bon de sortie (ordered by {@code stock.designation}) with the {@code BonSortie.jrxml}
     * template.
     */
    @Override
    @Transactional(readOnly = true)
    public byte[] imprimerBonSortie(Long id) throws Exception {
        logger.info("Printing bon sortie with id: {}", id);
        if (null == id || id == 0) {
            return JasperReportsUtil.anullerImpr(AUCUN_ELEMENT);
        }

        BonSortie bonSortie = bonSortieRepository.findById(id).orElse(null);
        if (null == bonSortie) {
            return JasperReportsUtil.anullerImpr(AUCUN_ELEMENT);
        }

        List<DetailBonSortie> listDetailBonSortie = detailBonSortieRepository.findAllByBonSortieId(id);
        if (CollectionUtils.isEmpty(listDetailBonSortie)) {
            return JasperReportsUtil.anullerImpr(AUCUN_ELEMENT);
        }

        listDetailBonSortie.sort(Comparator.comparing(
                detail -> null != detail.getStock() ? detail.getStock().getDesignation() : null,
                Comparator.nullsFirst(Comparator.naturalOrder())));

        String client = null != bonSortie.getCommercial() ? bonSortie.getCommercial().getDesignation() : "";
        Date dateLivr = null != bonSortie.getDateOperation() ? Date.from(bonSortie.getDateOperation()) : null;

        List<BonSortiePrintDTO> rows = new ArrayList<>();
        for (DetailBonSortie detail : listDetailBonSortie) {
            Stock stock = detail.getStock();
            rows.add(new BonSortiePrintDTO(
                    client,
                    dateLivr,
                    bonSortie.getCodeSortie(),
                    detail.getQteSortie(),
                    detail.getMntProduit(),
                    null != stock ? stock.getBenifice() : 0.0,
                    null != stock ? stock.getDesignation() : "",
                    false,
                    null != stock ? stock.getPrixVentMin1() : 0.0,
                    null != stock ? stock.getPrixVentMin2() : 0.0
            ));
        }

        Map<String, Object> parameters = new HashMap<>();
        StaticVariables.getInfoEtablissement(parameters, etablissementService.findAll(), true);
        parameters.put("fichier", StaticVariables.bundleFR.getBaseBundleName());

        return jasperReportsUtil.jasperReportInBytes(rows, parameters, "BonSortie", ReportTypeEnum.PDF, "");
    }

    /***************************************** Impression des etats "Consulter" ********************************************/

    /**
     * Port of {@code BonSortieActionBeans.imprimer()}: summary per product
     * (stockId 0), all products detailed (stockId -1) or one product detailed
     * (stockId > 0), for one mandatory commercial.
     */
    @Override
    @Transactional(readOnly = true)
    public byte[] imprimerEtat(BonSortiePrintRequest request) throws Exception {
        logger.info("Printing bon sortie etat: {}", request);

        if (null == request || null == request.getPersonnelId() || request.getPersonnelId() <= 0) {
            return JasperReportsUtil.anullerImpr("commercial obligatoire");
        }
        Personnel commercial = personnelRepository.findById(request.getPersonnelId()).orElse(null);
        if (null == commercial) {
            return JasperReportsUtil.anullerImpr("commercial obligatoire");
        }

        boolean fallbackDates = null == request.getDateDebut() || null == request.getDateFin();
        LocalDate today = LocalDate.now();
        LocalDate dateDebut = fallbackDates ? today.minusDays(365) : request.getDateDebut();
        LocalDate dateFin = fallbackDates ? today : request.getDateFin();
        Instant start = dateDebut.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endExclusive = dateFin.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        // Legacy wording/format differs between supplied dates (du/au, dd-MM-yyyy)
        // and the automatic one-year fallback (datedebut/dateFin, yyyy-MM-dd).
        String dateLabelDebut = fallbackDates ? "datedebut" : "du";
        String dateLabelFin = fallbackDates ? "dateFin" : "au";
        String dateValueDebut = fallbackDates ? dateDebut.toString() : StaticVariables.dateFormatDayFirst.format(dateDebut);
        String dateValueFin = fallbackDates ? dateFin.toString() : StaticVariables.dateFormatDayFirst.format(dateFin);
        String dateDb = StaticVariables.bundleFR.getString(dateLabelDebut) + " : " + dateValueDebut;
        String dateFn = StaticVariables.bundleFR.getString(dateLabelFin) + " : " + dateValueFin;

        Long stockId = request.getStockId();
        if (null != stockId && stockId == -1) {
            return imprimerEtatDetail(commercial, start, endExclusive, dateDebut, dateFin, dateDb, dateFn, null, true);
        }
        if (null != stockId && stockId > 0) {
            Stock stock = stockRepository.findById(stockId).orElse(null);
            return imprimerEtatDetail(commercial, start, endExclusive, dateDebut, dateFin, dateDb, dateFn, stock, false);
        }
        return imprimerEtatSummary(commercial, start, endExclusive, dateDebut, dateFin, dateDb, dateFn);
    }

    private byte[] imprimerEtatSummary(Personnel commercial, Instant start, Instant endExclusive,
                                       LocalDate dateDebut, LocalDate dateFin, String dateDb, String dateFn) throws Exception {
        Map<Long, BonSortieSummaryPrintDTO> byStock = new LinkedHashMap<>();
        for (Object[] row : detailBonSortieRepository.sumQteByStock(commercial.getId(), start, endExclusive)) {
            long stockId = toLong(row[0]);
            byStock.put(stockId, new BonSortieSummaryPrintDTO(
                    (String) row[1], StaticVariables.toDouble(row[2]), toLong(row[3]), 0L));
        }
        for (Object[] row : detLivraisonRepository.sumQteByStock(commercial.getId(), dateDebut, dateFin)) {
            long stockId = toLong(row[0]);
            BonSortieSummaryPrintDTO dto = byStock.get(stockId);
            if (null == dto) {
                dto = new BonSortieSummaryPrintDTO((String) row[1], StaticVariables.toDouble(row[2]), 0L, 0L);
                byStock.put(stockId, dto);
            }
            dto.setQteVente(toLong(row[3]));
        }

        List<BonSortieSummaryPrintDTO> rows = new ArrayList<>(byStock.values());
        rows.sort(Comparator.comparing(BonSortieSummaryPrintDTO::getStock,
                Comparator.nullsFirst(Comparator.naturalOrder())));
        if (rows.isEmpty()) {
            return JasperReportsUtil.anullerImpr(AUCUN_ELEMENT);
        }

        return jasperReportsUtil.jasperReportInBytes(rows,
                buildEtatParameters(commercial, dateDb, dateFn, ""), "etatBonSortie", ReportTypeEnum.PDF, "");
    }

    private byte[] imprimerEtatDetail(Personnel commercial, Instant start, Instant endExclusive,
                                      LocalDate dateDebut, LocalDate dateFin, String dateDb, String dateFn,
                                      Stock selectedStock, boolean allProducts) throws Exception {
        Long stockFilter = allProducts || null == selectedStock ? null : selectedStock.getId();
        List<BonSortieDetailPrintDTO> rows = new ArrayList<>();

        for (DetailBonSortie detail : detailBonSortieRepository.findPrintDetails(commercial.getId(), start, endExclusive, stockFilter)) {
            rows.add(new BonSortieDetailPrintDTO(
                    0,
                    null != detail.getBonSortie().getDateOperation() ? Date.from(detail.getBonSortie().getDateOperation()) : null,
                    detail.getBonSortie().getCodeSortie(),
                    detail.getQteSortie(),
                    "",
                    allProducts && null != detail.getStock() ? detail.getStock().getDesignation() : ""));
        }
        for (DetLivraison detail : detLivraisonRepository.findPrintDetails(commercial.getId(), dateDebut, dateFin, stockFilter)) {
            Livraison livraison = detail.getLivraison();
            rows.add(new BonSortieDetailPrintDTO(
                    1,
                    null != livraison.getDateBl() ? Date.from(livraison.getDateBl().atStartOfDay(ZoneId.systemDefault()).toInstant()) : null,
                    livraison.getCodeBl(),
                    detail.getQteLivrer(),
                    null != livraison.getRepertoire() ? livraison.getRepertoire().getDesignation() : "",
                    allProducts && null != detail.getStock() ? detail.getStock().getDesignation() : ""));
        }

        rows = groupDetailRows(rows);
        sortDetailRows(rows, allProducts);
        if (rows.isEmpty()) {
            return JasperReportsUtil.anullerImpr(AUCUN_ELEMENT);
        }

        String produit = null != selectedStock ? selectedStock.getDesignation() : "";
        String report = allProducts ? "etatBonSortieDetailAll" : "etatBonSortieDetail";
        return jasperReportsUtil.jasperReportInBytes(rows,
                buildEtatParameters(commercial, dateDb, dateFn, produit), report, ReportTypeEnum.PDF, "");
    }

    /**
     * Both detail reports group on {@code type}, so type rows must stay contiguous
     * (the all-products report also groups on {@code produit}, hence product first).
     */
    static void sortDetailRows(List<BonSortieDetailPrintDTO> rows, boolean allProducts) {
        Comparator<BonSortieDetailPrintDTO> typeThenDate = Comparator
                .comparing(BonSortieDetailPrintDTO::getType)
                .thenComparing(BonSortieDetailPrintDTO::getDate, Comparator.nullsFirst(Comparator.naturalOrder()));
        if (allProducts) {
            rows.sort(Comparator.comparing(BonSortieDetailPrintDTO::getProduit,
                    Comparator.nullsFirst(Comparator.naturalOrder())).thenComparing(typeThenDate));
        } else {
            rows.sort(typeThenDate);
        }
    }

    /** Legacy grouping by document number, extended with type/product for the all-products report. */
    private List<BonSortieDetailPrintDTO> groupDetailRows(List<BonSortieDetailPrintDTO> rows) {
        Map<String, BonSortieDetailPrintDTO> grouped = new LinkedHashMap<>();
        for (BonSortieDetailPrintDTO row : rows) {
            String key = row.getType() + "|"
                    + (null != row.getNum() ? row.getNum() : "") + "|"
                    + (null != row.getProduit() ? row.getProduit() : "");
            BonSortieDetailPrintDTO existing = grouped.get(key);
            if (null == existing) {
                grouped.put(key, row);
            } else {
                existing.setQte(existing.getQte() + row.getQte());
            }
        }
        return new ArrayList<>(grouped.values());
    }

    private Map<String, Object> buildEtatParameters(Personnel commercial, String dateDb, String dateFn, String produit) {
        Map<String, Object> parameters = new HashMap<>();
        StaticVariables.getInfoEtablissement(parameters, etablissementService.findAll(), true);
        parameters.put("fichier", StaticVariables.bundleFR.getBaseBundleName());
        parameters.put("dateDb", dateDb);
        parameters.put("dateFn", dateFn);
        parameters.put("repertoire", commercial.getDesignation());
        parameters.put("produit", produit);
        return parameters;
    }

    private static long toLong(Object value) {
        return null == value ? 0L : ((Number) value).longValue();
    }

}
