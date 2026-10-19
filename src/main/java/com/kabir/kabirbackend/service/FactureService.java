package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.config.enums.ReportTypeEnum;
import com.kabir.kabirbackend.config.enums.StockOperation;
import com.kabir.kabirbackend.config.enums.TypeQteToUpdate;
import com.kabir.kabirbackend.config.requests.FacturePrintRequest;
import com.kabir.kabirbackend.config.requests.RequestStockQte;
import com.kabir.kabirbackend.config.responses.FactureResponse;
import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.config.util.JasperReportsUtil;
import com.kabir.kabirbackend.config.util.MontantEnLettreUtil;
import com.kabir.kabirbackend.config.util.StaticVariables;
import com.kabir.kabirbackend.dto.DetFactureDTO;
import com.kabir.kabirbackend.dto.EtablissementDTO;
import com.kabir.kabirbackend.dto.FactureDTO;
import com.kabir.kabirbackend.dto.StockLivraisonSumPrintDTO;
import com.kabir.kabirbackend.entities.*;
import com.kabir.kabirbackend.mapper.DetFactureMapper;
import com.kabir.kabirbackend.mapper.FactureMapper;
import com.kabir.kabirbackend.repository.*;
import com.kabir.kabirbackend.service.interfaces.IFactureService;
import com.kabir.kabirbackend.specifications.FactureSpecification;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FactureService implements IFactureService {

    private final Logger logger = LoggerFactory.getLogger(FactureService.class);
    private final FactureRepository factureRepository;
    private final DetFactureRepository detFactureRepository;
    private final FactureMapper factureMapper;
    private final DetFactureMapper detFactureMapper;
    private final StockService stockService;
    private final StockRepository stockRepository;
    private final PersonnelRepository personnelRepository;
    private final RepertoireRepository repertoireRepository;
    private final LivraisonRepository livraisonRepository;
    private final EtablissementService etablissementService;
    private final JasperReportsUtil jasperReportsUtil;
    

    public FactureService(FactureRepository factureRepository,
                          DetFactureRepository detFactureRepository,
                          FactureMapper factureMapper,
                          DetFactureMapper detFactureMapper,
                            StockRepository stockRepository,
                            PersonnelRepository personnelRepository,
                            RepertoireRepository repertoireRepository,
                            LivraisonRepository livraisonRepository,
                            StockService stockService,
                            EtablissementService etablissementService,
                            JasperReportsUtil jasperReportsUtil) {
        this.factureRepository = factureRepository;
        this.factureMapper = factureMapper;
        this.detFactureRepository = detFactureRepository;
        this.detFactureMapper = detFactureMapper;
        this.stockService = stockService;
        this.stockRepository = stockRepository;
        this.personnelRepository = personnelRepository;
        this.repertoireRepository = repertoireRepository;
        this.livraisonRepository = livraisonRepository;
        this.etablissementService = etablissementService;
        this.jasperReportsUtil = jasperReportsUtil;
    }

    @Override
    public List<FactureDTO> findAll() {
        logger.info("Finding all factures");
        try {
            List<Facture> factures = factureRepository.findAll();
            return factures.stream().map(factureMapper::toFactureDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all factures", e);
            throw new RuntimeException("Error finding all factures", e);
        }
    }

    @Override
    public FactureDTO findById(Long id) {
        logger.info("Finding facture by id: {}", id);
        try {
            Facture facture = factureRepository.findById(id).orElseThrow(() -> new RuntimeException("Facture not found"));
            return factureMapper.toFactureDTO(facture);
        } catch (Exception e) {
            logger.error("Error finding facture by id: {}", id, e);
            throw new RuntimeException("Error finding facture by id: " + id, e);
        }
    }

    @Override
    public FactureResponse findByIdFactureResponse(Long id) {
        logger.info("Finding facture response by id: {}", id);
        try {
            if(null != id) {
                FactureDTO factureDTO = factureRepository.findById(id).map(factureMapper::toFactureDTO).orElseThrow(() -> new RuntimeException("Facture not found"));
                List<DetFactureDTO>  list = detFactureRepository.findByFactureId(id).stream().map(detFactureMapper::toDTO).toList();
                return new FactureResponse(factureDTO, list);
            } else {
                throw new RuntimeException("Facture id cannot be null");
            }
        } catch (Exception e) {
            logger.error("Error finding facture response by id: {}", id, e);
            throw new RuntimeException("Error finding facture response by id: " + id, e);
        }
    }

    @Override
    public FactureDTO save(FactureResponse factureResponse) {
        logger.info("Saving facture response: {}", factureResponse);

        FactureDTO factureDTO = factureResponse.facture();
        boolean isSave = factureDTO.getId() == null;
        Personnel personnelOperation = null;
        Livraison livraison = null;

        try {
            if(null != factureDTO.getEmployeOperateurId() && factureDTO.getEmployeOperateurId() != 0) {
                personnelOperation = personnelRepository.findById(factureDTO.getEmployeOperateurId()).orElse(null);
            }
            if(null != factureDTO.getLivraisonId() && factureDTO.getLivraisonId() != 0) {
                livraison = livraisonRepository.findById(factureDTO.getLivraisonId()).orElse(null);
            }

            Optional<Repertoire> optionalRepertoire = repertoireRepository.findById(factureResponse.facture().getRepertoireId());
            Optional<Personnel> optionalPersonnel = factureResponse.facture().getPersonnelId() != null ? personnelRepository.findById(factureResponse.facture().getPersonnelId()) : Optional.empty();

            Facture facture = factureMapper.toFacture(factureDTO);

            facture.setEmployeOperateur(personnelOperation);
            facture.setRepertoire(optionalRepertoire.orElse(null));
            facture.setPersonnel(optionalPersonnel.orElse(null));
            facture.setLivraison(livraison);

            factureDTO = factureMapper.toFactureDTO(factureRepository.save(facture));

            enregistrerDetFacture(facture, isSave, factureResponse.detFactures());

            if(null != livraison && null != livraison.getId()) {
                logger.info("Setting livraison facturer to true for livraison id: {}", livraison.getId());
                livraison.setFacturer(true);
                livraisonRepository.save(livraison);
            }

            logger.info("Stock facture saved successfully: {}", factureDTO);
        } catch (Exception e) {
            logger.error("Failed to save facture: {}", e.getMessage());
        }

        return factureDTO;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting facture by id: {}", id);
        try {
            List<DetFacture> detFactureDTOOld = detFactureRepository.findByFactureId(id);
            logger.info("List of DetFacture to delete: {}", detFactureDTOOld.size());

            if(CollectionUtils.isNotEmpty(detFactureDTOOld)) {
                for(DetFacture detStockDepot : detFactureDTOOld) {
                    if(null != detStockDepot.getStock() && null != detStockDepot.getStock().getId()) {
                        stockService.updateQteStock(detStockDepot.getStock().getId(), TypeQteToUpdate.QTE_STOCK_FACTURER, new RequestStockQte(detStockDepot.getQteFacturer(), StockOperation.ADD_TO_STOCK.getValue(), null));

                        logger.info("Deleting detail facture by id: {}", detStockDepot.getId());
                        detFactureRepository.deleteById(detStockDepot.getId());
                    }
                }
            }

            updateLivraisonFacturer(id);

            factureRepository.deleteById(id);
            logger.info("Facture deleted successfully");
        } catch (Exception e) {
            logger.error("Failed to delete facture: {}", e.getMessage());
        }
    }

    public void updateLivraisonFacturer(Long id) {
        FactureDTO factureDTO = findById(id);
        if(null != factureDTO && null != factureDTO.getLivraisonId()) {
            Livraison livraison = livraisonRepository.findById(factureDTO.getLivraisonId()).orElse(null);

            if(null != livraison && null != livraison.getId()) {
                logger.info("Setting livraison facturer to false for livraison id: {}", livraison.getId());
                livraison.setFacturer(false);
                livraisonRepository.save(livraison);
            }
        }
    }

    @Override
    public List<FactureDTO> search(FactureDTO factureDTO) {
        logger.info("Searching factures: {}", factureDTO);
        return factureRepository.findAll(FactureSpecification.builder().factureDTO(factureDTO).build()).stream().map(factureMapper::toFactureDTO).toList();
    }

    @Override
    public int getLastNumFacture(FactureDTO factureDTO) {
        logger.info("Getting last num facture");
        return factureRepository.findMaxNumFactureInYearDateBL(factureDTO.getDateBF().getYear()).map(l -> l + 1).orElse(1);
    }

    public List<DetFacture> getDetStockDepotOldNotAnymore(List<DetFacture> detFactureDTOOld, List<DetFactureDTO> detFactureDTOs) {
        if(CollectionUtils.isEmpty(detFactureDTOOld)) {
            return new ArrayList<>();
        }

        return detFactureDTOOld.stream()
                .filter(detStockDepotOld -> detFactureDTOs.stream()
                        .noneMatch(detStockDepotDTO -> detStockDepotDTO.getId() != null && detStockDepotDTO.getId().equals(detStockDepotOld.getId())))
                .collect(Collectors.toList());
    }

    public void enregistrerDetFacture(Facture facture, boolean isSave, List<DetFactureDTO> detFactureDTOs) {
        List<DetFacture> detFactureDTOOld = new ArrayList<>();
        if(!isSave) {
            detFactureDTOOld = detFactureRepository.findByFactureId(facture.getId());
        }

        List<DetFacture> listToDelete = getDetStockDepotOldNotAnymore(detFactureDTOOld, detFactureDTOs);
        if(CollectionUtils.isNotEmpty(listToDelete)) {
            logger.info("Deleting det stock depot: {}", listToDelete.size());
            for(DetFacture detFacture : listToDelete) {
                if(null != detFacture.getStock() && null != detFacture.getStock().getId()) {
                    stockService.updateQteStock(detFacture.getStock().getId(), TypeQteToUpdate.QTE_STOCK_FACTURER, new RequestStockQte(detFacture.getQteFacturer(), 1, null));
                }

                detFactureRepository.delete(detFacture);
            }
        }

        List<DetFacture> listToSave = detFactureDTOs.stream()
                .map(detFactureDTO -> {
                    DetFacture detFacture = detFactureMapper.toEntity(detFactureDTO);

                    Optional<Stock> optionalStock =  stockRepository.findById(detFactureDTO.getStockId());
                    detFacture.setStock(optionalStock.orElse(null));

                    detFacture.setFacture(facture);

                    return detFacture;
                })
                .toList();
        updateStockAndSaveDetStockDepot(listToSave, detFactureDTOOld);
    }

    public void updateStockAndSaveDetStockDepot(List<DetFacture> listToSave, List<DetFacture> detFactureDTOOld) {
        if(CollectionUtils.isNotEmpty(listToSave)) {
            for(DetFacture detFacture : listToSave) {
                if(null == detFacture.getId()) {
                    stockService.updateQteStock(detFacture.getStock().getId(), TypeQteToUpdate.QTE_STOCK_FACTURER, new RequestStockQte(detFacture.getQteFacturer(),2, null));
                } else {
                    int qte = detFacture.getQteFacturer();
                    int operation = 2;
                    qte = updateQte(detFactureDTOOld, detFacture, qte);

                    if(qte != 0) {
                        if(qte < 0) {
                            qte = Math.abs(qte);
                            operation = 1;
                        }
                        stockService.updateQteStock(detFacture.getStock().getId(), TypeQteToUpdate.QTE_STOCK_FACTURER, new RequestStockQte(qte, operation, null));
                    }
                }

                detFactureRepository.save(detFacture);
            }
        }
    }

    public int updateQte(List<DetFacture> detFactureDTOOld, DetFacture detFacture, int qte) {
        if(CollectionUtils.isNotEmpty(detFactureDTOOld)) {
            Optional<DetFacture> optionalDetStockDepotOld = detFactureDTOOld.stream()
                    .filter(detStockDepotOld -> detStockDepotOld.getId().equals(detFacture.getId()))
                    .findFirst();

            if(optionalDetStockDepotOld.isPresent()) {
                qte -= optionalDetStockDepotOld.get().getQteFacturer();
            }
        }

        return qte;
    }

    public List<FactureDTO> searchByCommon(CommonSearchModel commonSearchModel) {
        logger.info("Searching facture by common: {}", commonSearchModel);
        return factureRepository.findAll(FactureSpecification.builder().build().searchByCommon(commonSearchModel)).stream().map(factureMapper::toFactureDTO).toList();
    }

    /*************************************************************************************************************************************/
    /*************************************************************  IMPRIMER *************************************************************/
    /*************************************************************************************************************************************/

    private static final String AUCUN_ELEMENT = "aucun element";

    /**
     * Common parameters of every facture print: {@code fichier} (base resource bundle name),
     * the empty "produit/total" placeholders used by BonFactr and the {@code dateDb}/{@code dateFn}
     * labels which are only filled when both dialog dates are provided (legacy GeneratePDF).
     */
    private Map<String, Object> buildPrintParams(LocalDate dialogDebut, LocalDate dialogFin) {
        Map<String, Object> params = new HashMap<>();
        params.put("fichier", StaticVariables.bundleFR.getBaseBundleName());
        params.put("titleText", "");
        params.put("produit", "");
        params.put("valeur", "");
        params.put("totalQteLivr", "");
        params.put("totalMontant", "");
        params.put("totalBenfice", "");

        String dateDb = "";
        String dateFn = "";
        if (null != dialogDebut && null != dialogFin) {
            dateDb = StaticVariables.bundleFR.getString("datedebut") + " : " + StaticVariables.dateFormatDayFirst.format(dialogDebut);
            dateFn = StaticVariables.bundleFR.getString("dateFin") + " : " + StaticVariables.dateFormatDayFirst.format(dialogFin);
        }
        params.put("dateDb", dateDb);
        params.put("dateFn", dateFn);

        return params;
    }

    public String getTypeReglement(int type) {
        return switch (type) {
            case 2 -> StaticVariables.bundleFR.getString("cheque");
            case 3 -> StaticVariables.bundleFR.getString("traite");
            case 4 -> StaticVariables.bundleFR.getString("virement");
            default -> StaticVariables.bundleFR.getString("espece");
        };
    }

    private LocalDate[] resolveDates(LocalDate dateDebut, LocalDate dateFin) {
        return resolveDates(dateDebut, dateFin, 730);
    }

    private LocalDate[] resolveDates(LocalDate dateDebut, LocalDate dateFin, int defaultDays) {
        if (dateDebut != null && dateFin != null) {
            return new LocalDate[]{dateDebut, dateFin};
        }
        return new LocalDate[]{LocalDate.now().minusDays(defaultDays), LocalDate.now()};
    }

    private double toDouble(Object o) {
        return StaticVariables.toDouble(o);
    }

    public static boolean isDateBetween(LocalDate start, LocalDate end, LocalDate date) {
        if (null == date) return false;
        return (date.isEqual(start) || date.isAfter(start)) && (date.isEqual(end) || date.isBefore(end));
    }

    /**
     * Resolves the connected operator when he/she is not an administrator
     * (legacy {@code utilisateurConnecte.getTypeUser() != 1}). Admins and unauthenticated
     * users return {@code null} so the date queries stay unfiltered.
     */
    private Long resolveOperateurId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (null == authentication || !authentication.isAuthenticated()
                    || authentication instanceof AnonymousAuthenticationToken) {
                return null;
            }
            String email = authentication.getName();
            if (StringUtils.isBlank(email) || "anonymousUser".equalsIgnoreCase(email)) {
                return null;
            }
            return personnelRepository.findByEmail(email)
                    .filter(personnel -> personnel.getTypePersonnel() != 1)
                    .map(Personnel::getId)
                    .orElse(null);
        } catch (Exception e) {
            logger.error("Error while resolving operateur id", e);
            return null;
        }
    }

    /** {@code findByFactureIdIn} must never receive an empty collection. */
    private List<DetFacture> findByFactureIds(Collection<Long> factureIds) {
        if (CollectionUtils.isEmpty(factureIds)) {
            return new ArrayList<>();
        }
        return detFactureRepository.findByFactureIdIn(factureIds);
    }

    /** Legacy: benepourcentage = ((sum(prixVente) - sum(pattc)) / sum(prixVente)) * 100. */
    private String computeBeneficePercentage(List<DetFacture> list) {
        double prixVente = 0.0;
        double pattc = 0.0;
        for (DetFacture detFacture : list) {
            if (detFacture.getPrixVente() > 0.0) {
                prixVente += detFacture.getPrixVente();
            }
            if (null != detFacture.getStock() && detFacture.getStock().getPattc() > 0.0) {
                pattc += detFacture.getStock().getPattc();
            }
        }
        if (prixVente <= 0.0) {
            return "";
        }
        return new DecimalFormat("0.##").format((((prixVente - pattc) / prixVente) * 100));
    }

    /** Legacy bilan/client payment sum: slot 1 + slot 2 + mantantBF fallback. */
    private double sumBilanPayment(List<Facture> list, int type) {
        double sum = 0.0;
        for (Facture facture : list) {
            if (facture.getTypeReglment() == type) {
                sum += facture.getMntReglement();
            }
            if (facture.getTypeReglment2() == type) {
                sum += facture.getMntReglement2();
            }
            if (facture.getTypeReglment() == type && facture.getMntReglement() == 0 && facture.getMntReglement2() == 0) {
                sum += facture.getMantantBF();
            }
        }
        return sum;
    }

    private double sumHt7(List<DetFacture> dets) {
        return dets.stream()
                .filter(det -> det.getTva7() > 0 && det.getTva20() == 0.0)
                .mapToDouble(DetFacture::getMontantProduitHT)
                .sum();
    }

    private double sumHt20(List<DetFacture> dets) {
        return dets.stream()
                .filter(det -> det.getTva20() > 0 && det.getTva7() == 0.0)
                .mapToDouble(DetFacture::getMontantProduitHT)
                .sum();
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] imprimerFacture(Long id) throws Exception {
        logger.info("Printing facture bon with id: {}", id);
        if (null == id || id == 0) {
            return JasperReportsUtil.anullerImpr(AUCUN_ELEMENT);
        }

        Facture facture = factureRepository.findById(id).orElse(null);
        if (null == facture) {
            return JasperReportsUtil.anullerImpr(AUCUN_ELEMENT);
        }

        List<DetFacture> listAllDetImpr = detFactureRepository.findByFactureIdOrderByStockDesignation(id);
        if (CollectionUtils.isEmpty(listAllDetImpr)) {
            return JasperReportsUtil.anullerImpr(AUCUN_ELEMENT);
        }

        boolean avecRemise = true;
        for (DetFacture detFacture : listAllDetImpr) {
            if (!detFacture.isAvecRemise()) {
                avecRemise = false;
                break;
            }
        }
        if (avecRemise) {
            for (DetFacture detFacture : listAllDetImpr) {
                detFacture.setRemiseFacture(0.0);
            }
        }

        Map<String, Object> parameters = new HashMap<>();
        StaticVariables.getInfoEtablissement(parameters, etablissementService.findAll(), true);
        parameters.put("fichier", StaticVariables.bundleFR.getBaseBundleName());
        parameters.put("MntTotalLettre", MontantEnLettreUtil.convertir(facture.getMantantBF()));

        Double sommeHT20 = detFactureRepository.sumTva20(id);
        Double sommeHT7 = detFactureRepository.sumTva7(id);
        parameters.put("sommeHT20", (null != sommeHT20 ? sommeHT20 : 0.0) + "");
        parameters.put("sommeHT7", (null != sommeHT7 ? sommeHT7 : 0.0) + "");

        Comparator<DetFactureDTO> byDesignation = Comparator.comparing(
                DetFactureDTO::getStockDesignation, Comparator.nullsFirst(Comparator.naturalOrder()));

        List<DetFactureDTO> avecMontant = new ArrayList<>();
        List<DetFactureDTO> sansMontant = new ArrayList<>();
        for (DetFacture detFacture : listAllDetImpr) {
            DetFactureDTO detFactureDTO = detFactureMapper.toDTO(detFacture);
            if (detFacture.getMontantProduit() == 0) {
                sansMontant.add(detFactureDTO);
            } else {
                avecMontant.add(detFactureDTO);
            }
        }
        avecMontant.sort(byDesignation);
        sansMontant.sort(byDesignation);

        List<DetFactureDTO> rows = new ArrayList<>(avecMontant);
        rows.addAll(sansMontant);

        return jasperReportsUtil.jasperReportInBytes(rows, parameters, "BonFactr", ReportTypeEnum.PDF, "");
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] imprimerEtat(FacturePrintRequest request) throws Exception {
        logger.info("Printing facture etat: {}", request);
        if (null == request || null == request.getType()) {
            return JasperReportsUtil.anullerImpr(AUCUN_ELEMENT);
        }

        return switch (request.getType()) {
            case 0 -> imprimerLibel(1, request.getDateDebut(), request.getDateFin());
            case 1 -> imprimerLibel(2, request.getDateDebut(), request.getDateFin());
            case 2 -> imprimerBilan(request.getDateDebut(), request.getDateFin());
            case 3 -> imprimerParProduit(request.getStockId(), request.getDateDebut(), request.getDateFin());
            case 4 -> imprimerParClient(request.getRepertoireId(), request.getDateDebut(), request.getDateFin());
            default -> JasperReportsUtil.anullerImpr(AUCUN_ELEMENT);
        };
    }

    /**
     * Legacy {@code imprimerLibel(Integer i)}: {@code i == 1} prints the "Consulter Cheque"
     * report (etatClienLibel2, order by dateReglement) and {@code i == 2} the "Consulter TVA
     * detaille" report (etatClientTvaDetaille), splitting the rows in a 7% fetch and a 20%
     * fetch, ordered by dateBF.
     */
    private byte[] imprimerLibel(Integer i, LocalDate dialogDebut, LocalDate dialogFin) throws Exception {
        List<FactureDTO> listImprim = new ArrayList<>();
        String etatPrint = i.equals(1) ? "etatClienLibel2" : "etatClientTvaDetaille";
        boolean orderByDateReglement = i.equals(1);

        LocalDate[] dates = resolveDates(dialogDebut, dialogFin);
        LocalDate dateDebut = dates[0];
        LocalDate dateFin = dates[1];

        if (i.equals(1)) {
            listImprim.addAll(factureRepository.getByDateReglementBetween(dateDebut, dateFin, false, true, orderByDateReglement)
                    .stream().map(factureMapper::toFactureDTO).toList());
        } else {
            List<FactureDTO> listImprimTVA7 = factureRepository.getByDateReglementBetween(dateDebut, dateFin, true, true, orderByDateReglement)
                    .stream().map(factureMapper::toFactureDTO).toList();
            listImprimTVA7.forEach(facture -> facture.setTypeTVA("7%"));
            List<FactureDTO> listImprimTVA20 = factureRepository.getByDateReglementBetween(dateDebut, dateFin, true, false, orderByDateReglement)
                    .stream().map(factureMapper::toFactureDTO).toList();
            listImprimTVA20.forEach(facture -> facture.setTypeTVA("20%"));
            listImprim.addAll(listImprimTVA7);
            listImprim.addAll(listImprimTVA20);
        }

        if (CollectionUtils.isEmpty(listImprim)) {
            return JasperReportsUtil.anullerImpr(AUCUN_ELEMENT);
        }

        Object[] stats = factureRepository.getReglementStats(dateDebut, dateFin);

        double espece = toDouble(stats[0]) + toDouble(stats[4]) + toDouble(stats[8]) + toDouble(stats[12]) + toDouble(stats[16]);
        double cheque = toDouble(stats[1]) + toDouble(stats[5]) + toDouble(stats[9]) + toDouble(stats[13]) + toDouble(stats[17]);
        double traite = toDouble(stats[2]) + toDouble(stats[6]) + toDouble(stats[10]) + toDouble(stats[14]) + toDouble(stats[18]);
        double virement = toDouble(stats[3]) + toDouble(stats[7]) + toDouble(stats[11]) + toDouble(stats[15]) + toDouble(stats[19]);

        Map<String, Object> parameters = buildPrintParams(dialogDebut, dialogFin);
        parameters.put("Espece", StaticVariables.convertDouble(espece) + "");
        parameters.put("Cheque", StaticVariables.convertDouble(cheque) + "");
        parameters.put("Traite", StaticVariables.convertDouble(traite) + "");
        parameters.put("Virement", StaticVariables.convertDouble(virement) + "");
        parameters.put("Prelevement", 0 + "");
        parameters.put("Carte", 0 + "");

        List<FactureDTO> listImprimTemp = new ArrayList<>();

        for (FactureDTO facture : listImprim) {
            facture.setCalculer(false);
            facture.setType(1);
            Double somme20 = 0.0, somme7 = 0.0;

            LocalDate dateReglement = facture.getDateReglement();
            LocalDate dateReglement2 = facture.getDateReglement2();
            LocalDate dateReglement3 = facture.getDateReglement3();
            LocalDate dateReglement4 = facture.getDateReglement4();

            boolean isDateReglement1Between = isDateBetween(dateDebut, dateFin, dateReglement);
            boolean isDateReglement2Between = isDateBetween(dateDebut, dateFin, dateReglement2);
            boolean isDateReglement3Between = isDateBetween(dateDebut, dateFin, dateReglement3);
            boolean isDateReglement4Between = isDateBetween(dateDebut, dateFin, dateReglement4);
            StringBuilder espaceVide = new StringBuilder("\n-------------");
            StringBuilder espaceVideReverse = new StringBuilder("-------------\n");
            if(i == 2) {
                if(isDateReglement1Between) {
                    facture.setCalculer(true);
                    somme20 = detFactureRepository.sumTva20(facture.getId());
                    somme7 = detFactureRepository.sumTva7(facture.getId());
                }

                facture.setMntHT2O(null != somme20 ? somme20 : 0.0);
                facture.setMntHT7(null != somme7 ? somme7 : 0.0);
            } else {
                facture.setCalculer(true);
                facture.setMntHT7(0);
                facture.setTva7(0);
                StringBuilder dateReglementOne = new StringBuilder();
                boolean isAdded = false;
                FactureDTO facture2 = null;
                FactureDTO facture3 = null;
                FactureDTO facture4 = null;

                dateReglementOne.append(StaticVariables.sdfDDMMYY.format(facture.getDateReglement()));

                if(isDateReglement1Between) {
                    StringBuilder espaceVide1 = new StringBuilder();
                    if(null != facture.getDateReglement2()) espaceVide1.append(espaceVide.toString());
                    if(null != facture.getDateReglement3()) espaceVide1.append(espaceVide.toString());
                    if(null != facture.getDateReglement4()) espaceVide1.append(espaceVide.toString());

                    facture.setDateReglementIn(true);
                    facture.setMntHT20One(facture.getMntReglement() - (facture.getMntReglement() * 0.2));
                    facture.setTva20One(facture.getMntReglement() * 0.2);
                    facture.setDateReglementOne(facture.getDateReglement());
                    facture.setMantantBFOne(facture.getMntReglement());
                    facture.setNumRemiseTmp(facture.getNumRemise());

                    facture.setNumChequeOne(facture.getNumCheque() + espaceVide1.toString());
                    facture.setNumRemiseOne(facture.getNumRemise() + espaceVide1.toString());
                    facture.setTypeReglementOne(getTypeReglement(facture.getTypeReglment()) + espaceVide1.toString());
                    facture.setMntReglementOne(StaticVariables.decimalFormat.format(facture.getMntReglement()) + espaceVide1.toString());

                    isAdded = true;
                }
                if(isDateReglement2Between) {
                    dateReglementOne
                            .append(dateReglementOne.isEmpty() ? "" : "\n")
                            .append(StaticVariables.sdfDDMMYY.format(facture.getDateReglement2()));

                    if(isAdded) {
                        try {
                            StringBuilder espaceVide1 = new StringBuilder();
                            if(null != facture.getDateReglement3()) espaceVide1.append(espaceVide.toString());
                            if(null != facture.getDateReglement4()) espaceVide1.append(espaceVide.toString());

                            facture2 = (FactureDTO) facture.clone();

                            facture2.setDateReglementIn(true);
                            facture2.setMntHT20One(facture2.getMntReglement2() - (facture2.getMntReglement2() * 0.2));
                            facture2.setTva20One(facture2.getMntReglement2() * 0.2);
                            facture2.setDateReglementOne(facture2.getDateReglement2());
                            facture2.setMantantBFOne(facture2.getMntReglement2());
                            facture2.setNumRemiseTmp(facture.getNumRemise2());

                            facture2.setNumChequeOne(espaceVideReverse.toString() + facture2.getNumCheque2() + espaceVide1.toString());
                            facture2.setNumRemiseOne(espaceVideReverse.toString() + facture2.getNumRemise2() + espaceVide1.toString());
                            facture2.setTypeReglementOne(espaceVideReverse.toString() + getTypeReglement(facture2.getTypeReglment2()) + espaceVide1.toString());
                            facture2.setMntReglementOne(espaceVideReverse.toString() + StaticVariables.decimalFormat.format(facture2.getMntReglement2()) + espaceVide1.toString());
                        } catch (Exception e) {
                            logger.error("Error while processing facture2", e);
                        }
                    } else {
                        facture.setDateReglementIn(true);
                        facture.setMntHT20One(facture.getMntReglement2() - (facture.getMntReglement2() * 0.2));
                        facture.setTva20One(facture.getMntReglement2() * 0.2);
                        facture.setDateReglementOne(facture.getDateReglement2());
                        facture.setMantantBFOne(facture.getMntReglement2());
                        facture.setNumRemiseTmp(facture.getNumRemise2());

                        facture.setNumChequeOne(facture.getNumChequeOne() + "\n" + facture.getNumCheque2());
                        facture.setNumRemiseOne(facture.getNumRemiseOne() + "\n" + facture.getNumRemise2());
                        facture.setTypeReglementOne(facture.getTypeReglementOne() + "\n" + getTypeReglement(facture.getTypeReglment2()));
                        facture.setMntReglementOne(facture.getMntReglementOne() + "\n" + StaticVariables.decimalFormat.format(facture.getMntReglement2()));

                        isAdded = true;
                    }
                } else {
                    if(null != facture.getDateReglement2()) {
                        dateReglementOne
                                .append(!dateReglementOne.isEmpty() ? "\n" : "")
                                .append(StaticVariables.sdfDDMMYY.format(facture.getDateReglement2()));
                    }
                }
                if(isDateReglement3Between) {
                    dateReglementOne
                            .append(!dateReglementOne.isEmpty() ? "\n" : "")
                            .append(StaticVariables.sdfDDMMYY.format(facture.getDateReglement3()));

                    if(isAdded) {
                        try {
                            StringBuilder espaceVide1 = new StringBuilder();
                            if(null != facture.getDateReglement4()) espaceVide1.append(espaceVide.toString());

                            facture3 = (FactureDTO) facture.clone();

                            facture3.setDateReglementIn(true);
                            facture3.setMntHT20One(facture3.getMntReglement3() - (facture3.getMntReglement3() * 0.2));
                            facture3.setTva20One(facture3.getMntReglement3() * 0.2);
                            facture3.setDateReglementOne(facture3.getDateReglement3());
                            facture3.setMantantBFOne(facture3.getMntReglement3());
                            facture3.setNumRemiseTmp(facture.getNumRemise3());

                            facture3.setNumChequeOne(espaceVideReverse.toString() + espaceVideReverse.toString() + facture3.getNumCheque3() + espaceVide1.toString());
                            facture3.setNumRemiseOne(espaceVideReverse.toString() + espaceVideReverse.toString() + facture3.getNumRemise3() + espaceVide1.toString());
                            facture3.setTypeReglementOne(espaceVideReverse.toString() + espaceVideReverse.toString() + getTypeReglement(facture3.getTypeReglment3()) + espaceVide1.toString());
                            facture3.setMntReglementOne(espaceVideReverse.toString() + espaceVideReverse.toString() + StaticVariables.decimalFormat.format(facture3.getMntReglement3()) + espaceVide1.toString());
                        } catch (Exception e) {
                            logger.error("Error while processing facture3", e);
                        }
                    } else {
                        facture.setDateReglementIn(true);
                        facture.setMntHT20One(facture.getMntReglement3() - (facture.getMntReglement3() * 0.2));
                        facture.setTva20One(facture.getMntReglement3() * 0.2);
                        facture.setDateReglementOne(facture.getDateReglement3());
                        facture.setMantantBFOne(facture.getMntReglement3());
                        facture.setNumRemiseTmp(facture.getNumRemise3());

                        facture.setNumChequeOne(facture.getNumChequeOne() + "\n" + facture.getNumCheque3());
                        facture.setNumRemiseOne(facture.getNumRemiseOne() + "\n" + facture.getNumRemise3());
                        facture.setTypeReglementOne(facture.getTypeReglementOne() + "\n" + getTypeReglement(facture.getTypeReglment3()));
                        facture.setMntReglementOne(facture.getMntReglementOne() + "\n" + StaticVariables.decimalFormat.format(facture.getMntReglement3()));

                        isAdded = true;
                    }
                } else {
                    if(null != facture.getDateReglement3()) {
                        dateReglementOne
                                .append(!dateReglementOne.isEmpty() ? "\n" : "")
                                .append(StaticVariables.sdfDDMMYY.format(facture.getDateReglement3()));
                    }
                }
                if(isDateReglement4Between) {
                    dateReglementOne
                            .append(!dateReglementOne.isEmpty() ? "\n" : "")
                            .append(StaticVariables.sdfDDMMYY.format(facture.getDateReglement4()));

                    if(isAdded) {
                        try {
                            StringBuilder espaceVide1 = new StringBuilder(espaceVideReverse.toString())
                                    .append(espaceVideReverse.toString())
                                    .append(espaceVideReverse.toString());
                            facture4 = (FactureDTO) facture.clone();

                            facture4.setDateReglementIn(true);
                            facture4.setMntHT20One(facture4.getMntReglement4() - (facture4.getMntReglement4() * 0.2));
                            facture4.setTva20One(facture4.getMntReglement4() * 0.2);
                            facture4.setDateReglementOne(facture4.getDateReglement4());
                            facture4.setMantantBFOne(facture4.getMntReglement4());
                            facture4.setNumRemiseTmp(facture.getNumRemise4());

                            facture4.setNumChequeOne(espaceVide1.toString() + facture4.getNumCheque4());
                            facture4.setNumRemiseOne(espaceVide1.toString() + facture4.getNumRemise4());
                            facture4.setTypeReglementOne(espaceVide1.toString() + getTypeReglement(facture4.getTypeReglment4()));
                            facture4.setMntReglementOne(espaceVide1.toString() + new DecimalFormat("#,##0.00").format(facture4.getMntReglement4()));
                        } catch (Exception e) {
                            logger.error("Error while processing facture4", e);
                        }
                    } else {
                        facture.setDateReglementIn(true);
                        facture.setMntHT20One(facture.getMntReglement4() - (facture.getMntReglement4() * 0.2));
                        facture.setTva20One(facture.getMntReglement4() * 0.2);
                        facture.setDateReglementOne(facture.getDateReglement4());
                        facture.setMantantBFOne(facture.getMntReglement4());
                        facture.setNumRemiseTmp(facture.getNumRemise4());

                        facture.setNumChequeOne(facture.getNumChequeOne() + "\n" + facture.getNumCheque4());
                        facture.setNumRemiseOne(facture.getNumRemiseOne() + "\n" + facture.getNumRemise4());
                        facture.setTypeReglementOne(facture.getTypeReglementOne() + "\n" + getTypeReglement(facture.getTypeReglment4()));
                        facture.setMntReglementOne(facture.getMntReglementOne() + "\n" + StaticVariables.decimalFormat.format(facture.getMntReglement4()));

                        isAdded = true;
                    }
                } else {
                    if(null != facture.getDateReglement4()) {
                        dateReglementOne
                                .append(!dateReglementOne.isEmpty() ? "\n" : "")
                                .append(StaticVariables.sdfDDMMYY.format(facture.getDateReglement4()));
                    }
                }

                facture.setDateReglementOneLettre(dateReglementOne.toString());

                if(null != facture2) {
                    facture2.setDateReglementOneLettre(dateReglementOne.toString());
                    listImprimTemp.add(facture2);
                }
                if(null != facture3) {
                    facture3.setDateReglementOneLettre(dateReglementOne.toString());
                    listImprimTemp.add(facture3);
                }
                if(null != facture4) {
                    facture4.setDateReglementOneLettre(dateReglementOne.toString());
                    listImprimTemp.add(facture4);
                }
            }
        }

        listImprim.addAll(listImprimTemp);
        listImprim.sort(Comparator.comparing(FactureDTO::getDateReglementOne, Comparator.nullsFirst(Comparator.naturalOrder())));

        Map<Long, FactureDTO> map = listImprim.stream().collect(Collectors.toMap(FactureDTO::getId, f -> f, (oldValue, newValue) -> newValue));
        double mntTotal = map.values().stream().mapToDouble(f -> f.isCalculer() ? f.getMantantBF() : 0).sum();

        parameters.put("mntTotal", StaticVariables.convertDouble(mntTotal) + "");

        if(i == 1) {
            TreeMap<String, Double> treeMap = listImprim.stream()
                    .filter(facture -> StringUtils.isNotBlank(facture.getNumRemise()))
                    .collect(Collectors.toMap(FactureDTO::getNumRemise, FactureDTO::getTotalNumRemise, (existing, replacement) -> existing, TreeMap::new));
            treeMap.putAll(listImprim.stream()
                    .filter(facture -> StringUtils.isNotBlank(facture.getNumRemise2()))
                    .collect(Collectors.toMap(FactureDTO::getNumRemise2, FactureDTO::getTotalNumRemise, (existing, replacement) -> existing)));
            treeMap.putAll(listImprim.stream()
                    .filter(facture -> StringUtils.isNotBlank(facture.getNumRemise3()))
                    .collect(Collectors.toMap(FactureDTO::getNumRemise3, FactureDTO::getTotalNumRemise, (existing, replacement) -> existing)));
            treeMap.putAll(listImprim.stream()
                    .filter(facture -> StringUtils.isNotBlank(facture.getNumRemise4()))
                    .collect(Collectors.toMap(FactureDTO::getNumRemise4, FactureDTO::getTotalNumRemise, (existing, replacement) -> existing)));
            treeMap.remove("");
            treeMap.entrySet().removeIf(entry -> "".equalsIgnoreCase(entry.getKey().trim()));

            if(!treeMap.isEmpty()) {
                for(Map.Entry<String, Double> entry : treeMap.entrySet()) {
                    Object[] sums = factureRepository.getMntReglementByNumRemise(entry.getKey());

                    double sumNumRemise = sums[0] != null ? (Double) sums[0] : 0;
                    double sumNumRemise2 = sums[1] != null ? (Double) sums[1] : 0;
                    double sumNumRemise3 = sums[2] != null ? (Double) sums[2] : 0;
                    double sumNumRemise4 = sums[3] != null ? (Double) sums[3] : 0;

                    entry.setValue(sumNumRemise + sumNumRemise2 + sumNumRemise3 + sumNumRemise4);
                }
            }

            listImprim.forEach(facture -> {
                if(StringUtils.isNotBlank(facture.getNumRemiseTmp())) {
                    boolean exist = treeMap.containsKey(facture.getNumRemiseTmp());
                    if(exist) facture.setTotalNumRemise(treeMap.get(facture.getNumRemiseTmp()));
                }
            });
        }

        return jasperReportsUtil.jasperReportInBytes(listImprim, parameters, etatPrint, ReportTypeEnum.PDF, "");
    }

    /**
     * Legacy {@code imprimerBilan()}: main rows over dateBF, extra "spec" rows over the
     * settlement year bounds and the After/Before/Now VAT-HT aggregates.
     */
    private byte[] imprimerBilan(LocalDate dialogDebut, LocalDate dialogFin) throws Exception {
        LocalDate[] dates = resolveDates(dialogDebut, dialogFin);
        LocalDate debut = dates[0];
        LocalDate fin = dates[1];

        int yearAfter = fin.getYear() + 1;
        int yearBefor = debut.getYear() - 1;
        int yearDebut = debut.getYear();
        int yearFin = fin.getYear();

        Long operateurId = resolveOperateurId();

        List<Facture> mainList = factureRepository.findBilanMainList(debut, fin, operateurId);
        if (CollectionUtils.isEmpty(mainList)) {
            return JasperReportsUtil.anullerImpr(AUCUN_ELEMENT);
        }

        List<Facture> specList = factureRepository.findBilanSpecList(
                        LocalDate.of(yearDebut, 1, 1), LocalDate.of(yearFin, 12, 31), operateurId)
                .stream()
                .filter(facture -> null != facture.getDateBF() && facture.getDateBF().getYear() <= yearBefor)
                .toList();

        List<Facture> reglementList = factureRepository.findBilanReglementList(debut, fin, operateurId);

        List<DetFacture> mainDets = findByFactureIds(mainList.stream().map(Facture::getId).toList());
        List<DetFacture> specDets = findByFactureIds(specList.stream().map(Facture::getId).toList());
        List<DetFacture> reglementDets = findByFactureIds(reglementList.stream().map(Facture::getId).toList());

        Set<Long> idsAfter = mainList.stream()
                .filter(facture -> null != facture.getDateReglement() && facture.getDateReglement().getYear() >= yearAfter)
                .map(Facture::getId)
                .collect(Collectors.toSet());
        Set<Long> idsNow = mainList.stream()
                .filter(facture -> isDateBetween(debut, fin, facture.getDateReglement()))
                .map(Facture::getId)
                .collect(Collectors.toSet());

        double tvaAfter = mainList.stream()
                .filter(facture -> null != facture.getDateReglement() && facture.getDateReglement().getYear() >= yearAfter)
                .mapToDouble(facture -> facture.getTva7Total() + facture.getTva20Total())
                .sum();
        double htAfter = mainDets.stream()
                .filter(det -> idsAfter.contains(det.getFacture().getId()))
                .mapToDouble(DetFacture::getMontantProduitHT)
                .sum();

        double tvaBefor = specList.stream().mapToDouble(facture -> facture.getTva7Total() + facture.getTva20Total()).sum();
        double htBefor = specDets.stream().mapToDouble(DetFacture::getMontantProduitHT).sum();

        double tvaNow = mainList.stream()
                .filter(facture -> isDateBetween(debut, fin, facture.getDateReglement()))
                .mapToDouble(facture -> facture.getTva7Total() + facture.getTva20Total())
                .sum();
        double htNow = mainDets.stream()
                .filter(det -> idsNow.contains(det.getFacture().getId()))
                .mapToDouble(DetFacture::getMontantProduitHT)
                .sum();

        double tva7DateFacture = mainList.stream().mapToDouble(Facture::getTva7Total).sum();
        double tva7DateRegl = reglementList.stream().mapToDouble(Facture::getTva7Total).sum();
        double tva20DateFacture = mainList.stream().mapToDouble(Facture::getTva20Total).sum();
        double tva20DateRegl = reglementList.stream().mapToDouble(Facture::getTva20Total).sum();
        double ht7DateFacture = sumHt7(mainDets);
        double ht7DateRegl = sumHt7(reglementDets);
        double ht20DateFacture = sumHt20(mainDets);
        double ht20DateRegl = sumHt20(reglementDets);

        double tvaAfterConv = StaticVariables.convertDouble(tvaAfter);
        double htAfterConv = StaticVariables.convertDouble(htAfter);
        double tvaBeforConv = StaticVariables.convertDouble(tvaBefor);
        double htBeforConv = StaticVariables.convertDouble(htBefor);
        double tvaNowConv = StaticVariables.convertDouble(tvaNow);
        double htNowConv = StaticVariables.convertDouble(htNow);

        Map<String, Object> parameters = buildPrintParams(dialogDebut, dialogFin);
        parameters.put("Espece", StaticVariables.convertDouble(sumBilanPayment(reglementList, 1)) + "");
        parameters.put("Cheque", StaticVariables.convertDouble(sumBilanPayment(reglementList, 2)) + "");
        parameters.put("Virement", StaticVariables.convertDouble(sumBilanPayment(reglementList, 4)) + "");
        parameters.put("Traite", StaticVariables.convertDouble(sumBilanPayment(reglementList, 3)) + "");
        parameters.put("yearBefor", yearBefor + "");
        parameters.put("yearAfter", yearAfter + "");

        parameters.put("TvaAfter", tvaAfterConv + "");
        parameters.put("HtAfter", htAfterConv + "");
        parameters.put("TtcAfter", (tvaAfterConv + htAfterConv) + "");
        parameters.put("TvaBefor", tvaBeforConv + "");
        parameters.put("HtBefor", htBeforConv + "");
        parameters.put("TtcBefor", (tvaBeforConv + htBeforConv) + "");
        parameters.put("TvaNow", tvaNowConv + "");
        parameters.put("HtNow", htNowConv + "");
        parameters.put("TtcNow", (tvaNowConv + htNowConv) + "");

        parameters.put("tva7DateFacture", StaticVariables.convertDouble(tva7DateFacture) + "");
        parameters.put("tva7DateRegl", StaticVariables.convertDouble(tva7DateRegl) + "");
        parameters.put("tva20DateFacture", StaticVariables.convertDouble(tva20DateFacture) + "");
        parameters.put("tva20DateRegl", StaticVariables.convertDouble(tva20DateRegl) + "");
        parameters.put("ht7DateFacture", StaticVariables.convertDouble(ht7DateFacture) + "");
        parameters.put("ht7DateRegl", StaticVariables.convertDouble(ht7DateRegl) + "");
        parameters.put("ht20DateFacture", StaticVariables.convertDouble(ht20DateFacture) + "");
        parameters.put("ht20DateRegl", StaticVariables.convertDouble(ht20DateRegl) + "");

        Map<Long, Double> mnt20ByFacture = new HashMap<>();
        Map<Long, Double> mnt7ByFacture = new HashMap<>();
        List<DetFacture> allDets = new ArrayList<>(mainDets);
        allDets.addAll(specDets);
        for (DetFacture det : allDets) {
            Long factureId = det.getFacture().getId();
            if (det.getTva20() > 0 && det.getTva7() == 0.0) {
                mnt20ByFacture.merge(factureId, det.getMontantProduitHT(), Double::sum);
            }
            if (det.getTva7() > 0 && det.getTva20() == 0.0) {
                mnt7ByFacture.merge(factureId, det.getMontantProduitHT(), Double::sum);
            }
        }

        List<FactureDTO> rows = new ArrayList<>();
        for (Facture facture : mainList) {
            rows.add(toBilanRow(facture, 1, mnt20ByFacture, mnt7ByFacture));
        }
        for (Facture facture : specList) {
            rows.add(toBilanRow(facture, 3, mnt20ByFacture, mnt7ByFacture));
        }
        rows.sort(Comparator.comparingInt(FactureDTO::getType));

        return jasperReportsUtil.jasperReportInBytes(rows, parameters, "etatFactureBilan", ReportTypeEnum.PDF, "");
    }

    private FactureDTO toBilanRow(Facture facture, int type, Map<Long, Double> mnt20ByFacture, Map<Long, Double> mnt7ByFacture) {
        FactureDTO factureDTO = factureMapper.toFactureDTO(facture);
        factureDTO.setType(type);
        factureDTO.setMntHT2O(mnt20ByFacture.getOrDefault(facture.getId(), 0.0));
        factureDTO.setMntHT7(mnt7ByFacture.getOrDefault(facture.getId(), 0.0));
        return factureDTO;
    }

    /**
     * Legacy {@code imprimerParProduit()}: a given {@code stockId} prints the matching detail
     * rows (listeProduitFacture), otherwise every matching row is aggregated per stock
     * (listeProduitFactureTous) and sorted by upper-cased stock designation.
     */
    private byte[] imprimerParProduit(Long stockId, LocalDate dialogDebut, LocalDate dialogFin) throws Exception {
        LocalDate[] dates = resolveDates(dialogDebut, dialogFin, 100);
        LocalDate dateDebut = dates[0];
        LocalDate dateFin = dates[1];

        Long operateurId = resolveOperateurId();
        Map<String, Object> parameters = buildPrintParams(dialogDebut, dialogFin);

        String etatPrint;
        List<?> dataSource;
        if (null != stockId && stockId != 0) {
            List<DetFacture> list = detFactureRepository.findProduitByStock(stockId, dateDebut, dateFin, operateurId);
            parameters.put("valeur", computeBeneficePercentage(list));
            dataSource = list.stream().map(detFactureMapper::toDTO).toList();
            etatPrint = "listeProduitFacture";
        } else {
            dataSource = buildProduitAllRows(dateDebut, dateFin, operateurId);
            parameters.put("valeur", "");
            etatPrint = "listeProduitFactureTous";
        }

        if (dataSource.isEmpty()) {
            return JasperReportsUtil.anullerImpr(AUCUN_ELEMENT);
        }
        return jasperReportsUtil.jasperReportInBytes(dataSource, parameters, etatPrint, ReportTypeEnum.PDF, "");
    }

    private List<StockLivraisonSumPrintDTO> buildProduitAllRows(LocalDate dateDebut, LocalDate dateFin, Long operateurId) {
        List<DetFacture> dets = detFactureRepository.findProduitAll(dateDebut, dateFin, operateurId);

        Map<Long, StockLivraisonSumPrintDTO> byStock = new LinkedHashMap<>();
        for (DetFacture det : dets) {
            Stock stock = det.getStock();
            if (null == stock || null == stock.getId()) {
                continue;
            }
            StockLivraisonSumPrintDTO row = byStock.get(stock.getId());
            if (null == row) {
                row = new StockLivraisonSumPrintDTO(stock, 0, 0.0, 0.0, 0);
                byStock.put(stock.getId(), row);
            }
            row.setQteLivre(row.getQteLivre() + det.getQteFacturer());
            row.setMontant(row.getMontant() + det.getMontantProduit());
            row.setBeneficeDH(row.getBeneficeDH() + det.getBeneficeDH());
        }

        List<StockLivraisonSumPrintDTO> rows = new ArrayList<>(byStock.values());
        rows.sort(Comparator.comparing((StockLivraisonSumPrintDTO row) ->
                null == row.getStock().getDesignation() ? "" : row.getStock().getDesignation().toUpperCase()));
        return rows;
    }

    /**
     * Legacy {@code imprimerParClient()}: every settlement slot in range produces a printed
     * row (clones when a previous slot already matched), ordered by type then settlement date.
     */
    private byte[] imprimerParClient(Long repertoireId, LocalDate dialogDebut, LocalDate dialogFin) throws Exception {
        LocalDate[] dates = resolveDates(dialogDebut, dialogFin);
        LocalDate dateDebut = dates[0];
        LocalDate dateFin = dates[1];

        Long clientId = (null != repertoireId && repertoireId != 0) ? repertoireId : null;
        Long operateurId = resolveOperateurId();

        List<Facture> listImprim = factureRepository.findParClientList(dateDebut, dateFin, clientId, operateurId);
        if (CollectionUtils.isEmpty(listImprim)) {
            return JasperReportsUtil.anullerImpr(AUCUN_ELEMENT);
        }

        Map<String, Object> parameters = buildPrintParams(dialogDebut, dialogFin);
        parameters.put("Espece", StaticVariables.convertDouble(sumBilanPayment(listImprim, 1)) + "");
        parameters.put("Cheque", StaticVariables.convertDouble(sumBilanPayment(listImprim, 2)) + "");
        parameters.put("Virement", StaticVariables.convertDouble(sumBilanPayment(listImprim, 4)) + "");
        parameters.put("Traite", StaticVariables.convertDouble(sumBilanPayment(listImprim, 3)) + "");
        parameters.put("Prelevement", 0 + "");
        parameters.put("Carte", 0 + "");

        List<FactureDTO> rows = new ArrayList<>();
        List<FactureDTO> listImprimTemp = new ArrayList<>();

        for (Facture facture : listImprim) {
            FactureDTO factureDTO = factureMapper.toFactureDTO(facture);
            factureDTO.setType(1);
            factureDTO.setCalculer(false);

            LocalDate dateReglement = factureDTO.getDateReglement();
            LocalDate dateReglement2 = factureDTO.getDateReglement2();
            LocalDate dateReglement3 = factureDTO.getDateReglement3();
            LocalDate dateReglement4 = factureDTO.getDateReglement4();

            boolean isAdded = false;

            if (isDateBetween(dateDebut, dateFin, dateReglement)) {
                applyClientReglement(factureDTO, 1, factureDTO.getMntReglement(), factureDTO.getTypeReglment(), dateReglement);
                factureDTO.setDateReglementIn(true);
                isAdded = true;
            }
            if (isDateBetween(dateDebut, dateFin, dateReglement2)) {
                if (isAdded) {
                    FactureDTO clone = factureDTO.clone();
                    applyClientReglement(clone, 2, clone.getMntReglement2(), clone.getTypeReglment2(), dateReglement2);
                    clone.setDateReglement2In(true);
                    listImprimTemp.add(clone);
                } else {
                    applyClientReglement(factureDTO, 2, factureDTO.getMntReglement2(), factureDTO.getTypeReglment2(), dateReglement2);
                    factureDTO.setDateReglement2In(true);
                    isAdded = true;
                }
            }
            if (isDateBetween(dateDebut, dateFin, dateReglement3)) {
                if (isAdded) {
                    FactureDTO clone = factureDTO.clone();
                    applyClientReglement(clone, 3, clone.getMntReglement3(), clone.getTypeReglment3(), dateReglement3);
                    clone.setDateReglement3In(true);
                    listImprimTemp.add(clone);
                } else {
                    applyClientReglement(factureDTO, 3, factureDTO.getMntReglement3(), factureDTO.getTypeReglment3(), dateReglement3);
                    factureDTO.setDateReglement3In(true);
                    isAdded = true;
                }
            }
            if (isDateBetween(dateDebut, dateFin, dateReglement4)) {
                if (isAdded) {
                    FactureDTO clone = factureDTO.clone();
                    applyClientReglement(clone, 4, clone.getMntReglement4(), clone.getTypeReglment4(), dateReglement4);
                    clone.setDateReglement4In(true);
                    listImprimTemp.add(clone);
                } else {
                    applyClientReglement(factureDTO, 4, factureDTO.getMntReglement4(), factureDTO.getTypeReglment4(), dateReglement4);
                    factureDTO.setDateReglement4In(true);
                    isAdded = true;
                }
            }

            rows.add(factureDTO);
        }

        rows.addAll(listImprimTemp);
        rows.sort(Comparator.comparingInt(FactureDTO::getType)
                .thenComparing(FactureDTO::getDateReglementOne, Comparator.nullsFirst(Comparator.naturalOrder())));

        return jasperReportsUtil.jasperReportInBytes(rows, parameters, "listeVenteAchatCleint2", ReportTypeEnum.PDF, "");
    }

    private void applyClientReglement(FactureDTO facture, int slot, double montant, int typeReglement, LocalDate dateReglement) {
        facture.setDateReglementOne(dateReglement);
        facture.setTypeReglementOne(getTypeReglement(typeReglement));
        double mntHT20 = montant / 1.2;
        double tva20 = montant - mntHT20;
        facture.setMntHT20One(mntHT20);
        facture.setTva20One(tva20);
        facture.setMantantBFOne(montant);
        switch (slot) {
            case 1 -> {
                facture.setMntHT20Reglement1(mntHT20);
                facture.setTva20Reglement1(tva20);
            }
            case 2 -> {
                facture.setMntHT20Reglement2(mntHT20);
                facture.setTva20Reglement2(tva20);
            }
            case 3 -> {
                facture.setMntHT20Reglement3(mntHT20);
                facture.setTva20Reglement3(tva20);
            }
            default -> {
                facture.setMntHT20Reglement4(mntHT20);
                facture.setTva20Reglement4(tva20);
            }
        }
    }
}
