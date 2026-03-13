package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.config.enums.StockOperation;
import com.kabir.kabirbackend.config.enums.TypeQteToUpdate;
import com.kabir.kabirbackend.config.requests.RequestStockQte;
import com.kabir.kabirbackend.config.responses.FactureResponse;
import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.config.util.StaticVariables;
import com.kabir.kabirbackend.dto.DetFactureDTO;
import com.kabir.kabirbackend.dto.FactureDTO;
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
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    

    public FactureService(FactureRepository factureRepository,
                          DetFactureRepository detFactureRepository,
                          FactureMapper factureMapper,
                          DetFactureMapper detFactureMapper,
                            StockRepository stockRepository,
                            PersonnelRepository personnelRepository,
                            RepertoireRepository repertoireRepository,
                            LivraisonRepository livraisonRepository,
                            StockService stockService) {
        this.factureRepository = factureRepository;
        this.factureMapper = factureMapper;
        this.detFactureRepository = detFactureRepository;
        this.detFactureMapper = detFactureMapper;
        this.stockService = stockService;
        this.stockRepository = stockRepository;
        this.personnelRepository = personnelRepository;
        this.repertoireRepository = repertoireRepository;
        this.livraisonRepository = livraisonRepository;
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
            Optional<Personnel> optionalPersonnel = personnelRepository.findById(factureResponse.facture().getPersonnelId());

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

    private Map<String,String> buildParameters(double espece, double cheque, double traite, double virement, LocalDate start, LocalDate end){
        Map<String,String> p = new HashMap<>();

        p.put("Espece", String.valueOf(espece));
        p.put("Cheque", String.valueOf(cheque));
        p.put("Traite", String.valueOf(traite));
        p.put("Virement", String.valueOf(virement));

        return p;
    }

    private LocalDate[] resolveDates(LocalDate dateDebut, LocalDate dateFin) {
        if (dateDebut != null && dateFin != null) {
            return new LocalDate[]{dateDebut, dateFin};
        }

        return new LocalDate[]{ LocalDate.now().minusYears(2), LocalDate.now() };
    }

    private double toDouble(Object o){
        return o == null ? 0d : ((Number)o).doubleValue();
    }

    public static boolean isDateBetween(LocalDate start, LocalDate end, LocalDate date) {
        if(null == date) return false;
        return (date.isEqual(start) || date.isAfter(start)) && (date.isEqual(end) || date.isBefore(end));
    }

    public void imprimerLibel(Integer i, LocalDate dateDebut, LocalDate dateFin) {
        List<FactureDTO> listImprim = new ArrayList<>();
        String etatPrint =  i.equals(1) ? "etatClienLibel2.jrxml" : "etatClientTvaDetaille.jrxml";
        boolean orderByDateReglement = i.equals(1);

        LocalDate[] dates = resolveDates(dateDebut, dateFin);
        dateDebut = dates[0];
        dateFin = dates[1];

        if(i.equals(1)) {
            listImprim.addAll(factureRepository.getByDateReglementBetween(dateDebut, dateFin, false, true, orderByDateReglement).stream().map(factureMapper::toFactureDTO).toList());
        } else {
            listImprim.addAll(factureRepository.getByDateReglementBetween(dateDebut, dateFin, true, true, orderByDateReglement).stream().map(factureMapper::toFactureDTO).toList());
            listImprim.addAll(factureRepository.getByDateReglementBetween(dateDebut, dateFin, true, false, orderByDateReglement).stream().map(factureMapper::toFactureDTO).toList());
        }

        if(CollectionUtils.isNotEmpty(listImprim)) {
            Object[] stats = factureRepository.getReglementStats(dateDebut, dateFin);

            double espece = toDouble(stats[0]) + toDouble(stats[4]) + toDouble(stats[8]) + toDouble(stats[12]) + toDouble(stats[16]);
            double cheque = toDouble(stats[1]) + toDouble(stats[5]) + toDouble(stats[9]) + toDouble(stats[13]) + toDouble(stats[17]);
            double traite = toDouble(stats[2]) + toDouble(stats[6]) + toDouble(stats[10]) + toDouble(stats[14]) + toDouble(stats[18]);
            double virement = toDouble(stats[3]) + toDouble(stats[7]) + toDouble(stats[11]) + toDouble(stats[15]) + toDouble(stats[19]);

            Map<String, String> parameters = buildParameters(espece, cheque, traite, virement, dateDebut, dateFin);

            parameters.put("dateDb", StaticVariables.dateFormatDayFirst.format(dateDebut));
            parameters.put("dateFn", StaticVariables.dateFormatDayFirst.format(dateFin));
            parameters.put("Prelevement", 0 + "");
            parameters.put("Carte", 0 + "");
            parameters.put("fichier", StaticVariables.bundleFR.getBaseBundleName());

            List<FactureDTO> listImprimTemp = new ArrayList<>();

            for (FactureDTO facture : listImprim) {
                facture.setCalculer(false);
                facture.setType(1);
                double somme20 = 0.0, somme7 = 0.0;

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
                        somme20 = detfactureService.findQuerySumDouble("select sum(montantProduitHT) from Detfacture where facture = " + facture.getId() + " and tva20 > 0 and tva7 = 0.0");
                        somme7 = detfactureService.findQuerySumDouble("select sum(montantProduitHT) from Detfacture where facture = " + facture.getId() + " and tva7 > 0 and tva20 = 0.0");
                    }

                    facture.setMntHT2O(somme20);
                    facture.setMntHT7(somme7);
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
                    /*********************************************************************************************/
                    if(isDateReglement2Between) {
                        dateReglementOne
                                .append(dateReglementOne.isEmpty() ? "" : "\n")
                                .append(StaticVariables.sdfDDMMYY.format(facture.getDateReglement2()));

                        if(isAdded) {
                            try {
                                StringBuilder espaceVide1 = new StringBuilder();
                                if(null != facture.getDateReglement3()) espaceVide1.append(espaceVide.toString());
                                if(null != facture.getDateReglement4()) espaceVide1.append(espaceVide.toString());

                                facture2 = (Facture) facture.clone();

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

                                isAdded = true;
                            } catch (Exception e) {
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
                                    .append(dateReglementOne.length() > 0 ? "\n" : "")
                                    .append(StaticVariables.sdfDDMMYY.format(facture.getDateReglement2()));
                        }
                    }
                    /*********************************************************************************************/
                    if(isDateReglement3Between) {
                        dateReglementOne
                                .append(dateReglementOne.length() > 0 ? "\n" : "")
                                .append(StaticVariables.sdfDDMMYY.format(facture.getDateReglement3()));

                        if(isAdded) {
                            try {
                                StringBuilder espaceVide1 = new StringBuilder();
                                if(null != facture.getDateReglement4()) espaceVide1.append(espaceVide.toString());

                                facture3 = (Facture) facture.clone();

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
                                    .append(dateReglementOne.length() > 0 ? "\n" : "")
                                    .append(StaticVariables.sdfDDMMYY.format(facture.getDateReglement3()));
                        }
                    }
                    /*********************************************************************************************/
                    if(isDateReglement4Between) {
                        dateReglementOne
                                .append(dateReglementOne.length() > 0 ? "\n" : "")
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
                                    .append(dateReglementOne.length() > 0 ? "\n" : "")
                                    .append(StaticVariables.sdfDDMMYY.format(facture.getDateReglement4()));
                        }
                    }
                    /*********************************************************************************************/

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
            listImprim.sort(Comparator.comparing(FactureDTO::getDateReglementOne));

            Map<Long, FactureDTO> map = listImprim.stream().collect(Collectors.toMap(FactureDTO::getId, f -> f, (oldValue, newValue) -> newValue));
            double mntTotal = map.values().stream().mapToDouble(f -> f.isCalculer() ? f.getMantantBF() : 0).sum();

            parameters.put("mntTotal", methodCommun.convertrDouble(mntTotal) + "");

            if(i == 1) {
                TreeMap<String, Double> treeMap = listImprim.stream()
                        .filter(facture -> StringUtils.isNotBlank(facture.getNumRemise()))
                        .collect(Collectors.toMap(FactureDTO::getNumRemise, FactureDTO::getTotalNumRemise, (existing, replacement) -> existing != null ? existing : replacement, TreeMap::new));
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
                        getMntReglementByNumRemise

                        double sumNumRemise = factureService.findQuerySum("select sum(mntReglement) from Facture where numRemise like '" + entry.getKey() + "'");
                        double sumNumRemise2 = factureService.findQuerySum("select sum(mntReglement2) from Facture where numRemise2 like '" + entry.getKey() + "'");
                        double sumNumRemise3 = factureService.findQuerySum("select sum(mntReglement3) from Facture where numRemise3 like '" + entry.getKey() + "'");
                        double sumNumRemise4 = factureService.findQuerySum("select sum(mntReglement4) from Facture where numRemise4 like '" + entry.getKey() + "'");

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
            GeneratePDF.Imprimer(etatPrint, etatPrint.replace(".jrxml", ".pdf"), listImprim, parameters, "");
        } else {
            GeneratePDF.anullerImpr("aucun element");
        }
    }

    private void imprimerParProduit() {
        String etatPrint = "";
        List<Detfacture> listImprim = new ArrayList<Detfacture>();
        List<FournisseurProduit> listFournisseurProduit = new ArrayList<FournisseurProduit>();

        String queryFacture="";
        String query = "";
        String dateDb = "";
        String dateFn = "";
        String titleText = "";

        if (dateDebutImpr != null && dateFinImpr != null) {
            String dat1 = sdf.format(dateDebutImpr);
            String dat2 = sdf.format(dateFinImpr);
            dateDb = Messages.getString(local.getLangue(), "datedebut", null) + " : " + sdf1.format(dateDebutImpr);
            dateFn = Messages.getString(local.getLangue(), "dateFin", null) + " : " + sdf1.format(dateFinImpr);

            query += " and facture.dateReglement between '" + dat1 + "' and '" + dat2 + "' ";
            queryFacture+= " and dateReglement between '" + dat1 + "' and '" + dat2 + "' ";
        } else {
            DateFormat newannee1 = new SimpleDateFormat("yyyy-MM-dd");
            String newann1 = newannee1.format(new Date());
            Date dateOperation = null;
            String addjour = methodCommun.addDays(newann1, -100);
            try {
                dateOperation = newannee1.parse(addjour);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String dat1 = sdf.format(dateOperation);

            query += " and facture.dateReglement > '" + dat1 + "'";
            queryFacture += " and dateReglement > '" + dat1 + "'";

            if (utilisateurConnecte.getTypeUser() != 1) {
                query += " and facture.employeOperateur =" + utilisateurConnecte.getId();
                queryFacture += " and employeOperateur =" + utilisateurConnecte.getId();
            }
        }

        if (idStockImpr != 0) {
            query += " and (montantProduit > 0 or (montantProduit=0 and directFacture=1) or (montantProduit=0 and stock.avecRemise=1 and remiseFacture=100))";
            listImprim = detfactureService.findByQuery(" where 1=1 AND stock=" + idStockImpr + query + " order by facture.dateReglement desc");
            etatPrint = "listeProduitFacture.jrxml";
        } else {
            Map<Long,FournisseurProduit> map=new LinkedHashMap<Long, FournisseurProduit>();
            List<Facture> listFacture=factureService.findByQuery(" where id in (select facture from Detfacture where 1=1 "+queryFacture+")");
            for (Facture facture : listFacture) {
                List<Detfacture> listAllDetImpr = detfactureService.findByQuery(" where facture=" + facture.getId()
                        + " and (montantProduit > 0 or (montantProduit=0 and directFacture=1) or (montantProduit=0 and stock.avecRemise=1 and remiseFacture=100)) order by stock.designation");
                if(org.apache.commons.collections.CollectionUtils.isNotEmpty(listAllDetImpr)) {
                    for (Detfacture detfacture : listAllDetImpr) {
                        if(map.containsKey(detfacture.getStock().getId())) {
                            FournisseurProduit fournisseurProduitOld = map.get(detfacture.getStock().getId());
                            if(fournisseurProduitOld!=null) {
                                fournisseurProduitOld.setMontant(fournisseurProduitOld.getMontant()+detfacture.getMontantProduit());
                                fournisseurProduitOld.setQteLivre(fournisseurProduitOld.getQteLivre()+detfacture.getQteFacturer());
                                fournisseurProduitOld.setBeneficeDH(fournisseurProduitOld.getBeneficeDH()+detfacture.getBeneficeDH());

                                map.put(detfacture.getStock().getId(),fournisseurProduitOld);
                            }
                        }else {
                            FournisseurProduit fournisseurProduit = new FournisseurProduit();
                            fournisseurProduit.setStock(detfacture.getStock());
                            fournisseurProduit.setQteLivre(detfacture.getQteFacturer());
                            fournisseurProduit.setMontant(detfacture.getMontantProduit());
                            fournisseurProduit.setBeneficeDH(detfacture.getBeneficeDH());

                            map.put(detfacture.getStock().getId(), fournisseurProduit);
                        }
                    }
                }
            }

            if(map.size()>0) {
                listFournisseurProduit.addAll(map.values());
                Collections.sort(listFournisseurProduit, FournisseurProduit.parOrdreAlphabetiqueStock);
            }
            etatPrint = "listeProduitFactureTous.jrxml";
        }

        if (listImprim.size() > 0 || listFournisseurProduit.size() > 0) {
            Map<String, String> parameters = new HashMap<String, String>();

            parameters.put("dateDb", dateDb);
            parameters.put("dateFn", dateFn);

            double prv = 0.0;
            double benepourcentage = 0.0;
            double pracht = 0.0;
            for (Detfacture detfacture : listImprim) {
                if (detfacture.getPrixVente() > 0.0) {
                    prv += detfacture.getPrixVente();
                }
                if (detfacture.getStock().getPattc() > 0.0) {
                    pracht += detfacture.getStock().getPattc();
                }
            }
            java.text.DecimalFormat df = new java.text.DecimalFormat("0.##");
            benepourcentage = (((prv - pracht) / prv) * 100);
            parameters.put("valeur", df.format(benepourcentage) + "");

            parameters.put("fichier", local.getLangue());
            parameters.put("titleText", titleText);
            if (idStockImpr != 0) {
                GeneratePDF.Imprimer(etatPrint, etatPrint.replace("jrxml", "pdf"), listImprim, parameters, "");
            } else {
                GeneratePDF.Imprimer(etatPrint, etatPrint.replace("jrxml", "pdf"), listFournisseurProduit, parameters,"");
            }
        } else {
            GeneratePDF.anullerImpr("aucun element");
        }
    }

    public String getTypeReglement(int type) {
        switch (type) {
            case 1:
                return Messages.getString(local.getLangue(), "espece", null);

            case 2:
                return Messages.getString(local.getLangue(), "cheque", null);

            case 3:
                return Messages.getString(local.getLangue(), "traite", null);

            case 4:
                return Messages.getString(local.getLangue(), "virement", null);

            default:
                return Messages.getString(local.getLangue(), "espece", null);
        }
    }

    private void imprimerParClient() {
        String etatPrint = "listeVenteAchatCleint2.jrxml";

        double listImprimEspece = 0.0;
        double listImprimCheque = 0.0;
        double listImprimTraite = 0.0;
        double listImprimVirement = 0.0;

        StringBuilder query = new StringBuilder("");
        StringBuilder dateDb = new StringBuilder("");
        StringBuilder dateFn = new StringBuilder("");

        LocalDate localDateDebutSearch = LocalDate.now();
        LocalDate localDateFinSearch = LocalDate.now();

        if (dateDebutImpr != null && dateFinImpr != null) {
            String dat1 = sdf.format(dateDebutImpr);
            String dat2 = sdf.format(dateFinImpr);

            localDateDebutSearch = methodCommun.convertToLocalDateViaInstant(dateDebutImpr);
            localDateFinSearch = methodCommun.convertToLocalDateViaInstant(dateFinImpr);

            dateDb.append(Messages.getString(local.getLangue(), "datedebut", null) + " : " + sdf1.format(dateDebutImpr));
            dateFn.append(Messages.getString(local.getLangue(), "dateFin", null) + " : " + sdf1.format(dateFinImpr));
            query.append(" and ((dateReglement between '" + dat1 + "' and '" + dat2 + "')")
                    .append(" or (dateReglement2 between '" + dat1 + "' and '" + dat2 + "')")
                    .append(" or (dateReglement3 between '" + dat1 + "' and '" + dat2 + "')")
                    .append(" or (dateReglement4 between '" + dat1 + "' and '" + dat2 + "')")
                    .append(")");
        } else {
            DateFormat newannee1 = new SimpleDateFormat("yyyy-MM-dd");
            String newann1 = newannee1.format(new Date());
            Date dateOperation = null;
            String addjour = methodCommun.addDays(newann1, -730);
            try {
                dateOperation = newannee1.parse(addjour);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            localDateDebutSearch = methodCommun.convertToLocalDateViaInstant(dateOperation);

            String dat1 = sdf.format(dateOperation);
            String dat2 = sdf.format(new Date());

            query.append(" and ((dateReglement between '" + dat1 + "' and '" + dat2 + "')")
                    .append(" or (dateReglement2 between '" + dat1 + "' and '" + dat2 + "')")
                    .append(" or (dateReglement3 between '" + dat1 + "' and '" + dat2 + "')")
                    .append(" or (dateReglement4 between '" + dat1 + "' and '" + dat2 + "')")
                    .append(")");
        }
        if (idRepertoireClientImpr != 0) {
            query.append(" and repertoireByClient=" + idRepertoireClientImpr);
        }
        if (utilisateurConnecte.getTypeUser() != 1) {
            query.append(" and employeOperateur =" + utilisateurConnecte.getId());
        }

        List<Facture> listImprim = factureService.findByQuery(" where 1=1 " + query.toString() + " order by dateReglement,repertoireByClient.designation");

        /***********************************************  Espece  *************************************************/
        listImprimEspece = detfactureService.findQuerySumDouble("select sum(mntReglement) from Facture where typeReglment=1 " + query.toString());
        double doubleEspece = methodCommun.convertrDouble(listImprimEspece);

        double listImprimEspece2 = detfactureService.findQuerySumDouble("select sum(mntReglement2) from Facture where typeReglment2=1 " + query.toString());
        double doubleEspece2 = methodCommun.convertrDouble(listImprimEspece2);

        double listImprimEspece3 = detfactureService.findQuerySumDouble("select sum(mantantBF) from Facture where typeReglment=1 and mntReglement=0 and mntReglement2=0 " + query.toString());
        double doubleEspece3 = methodCommun.convertrDouble(listImprimEspece3);

        /***********************************************  Cheque  *************************************************/
        listImprimCheque = detfactureService.findQuerySumDouble("select sum(mntReglement) from Facture where typeReglment=2 " + query.toString());
        double doubleCheque = methodCommun.convertrDouble(listImprimCheque);

        double listImprimCheque2 = detfactureService.findQuerySumDouble("select sum(mntReglement2) from Facture where typeReglment2=2 " + query.toString());
        double doubleCheque2 = methodCommun.convertrDouble(listImprimCheque2);

        double listImprimCheque3 = detfactureService.findQuerySumDouble("select sum(mantantBF) from Facture where typeReglment=2 and mntReglement=0 and mntReglement2=0 " + query.toString());
        double doubleCheque3 = methodCommun.convertrDouble(listImprimCheque3);

        /***********************************************  Traite  ***************************************************/
        listImprimTraite = detfactureService.findQuerySumDouble("select sum(mntReglement) from Facture where typeReglment=3 " + query.toString());
        double doubleTraite = methodCommun.convertrDouble(listImprimTraite);

        double listImprimTraite2 = detfactureService.findQuerySumDouble("select sum(mntReglement2) from Facture where typeReglment2=3 " + query.toString());
        double doubleTraite2 = methodCommun.convertrDouble(listImprimTraite2);

        double listImprimTraite3 = detfactureService.findQuerySumDouble("select sum(mantantBF) from Facture where typeReglment=3 and mntReglement=0 and mntReglement2=0 " + query.toString());
        double doubleTraite3 = methodCommun.convertrDouble(listImprimTraite3);

        /***********************************************  Virement  ***************************************************/
        listImprimVirement = detfactureService.findQuerySumDouble("select sum(mntReglement) from Facture where typeReglment=4 " + query.toString());
        double doubleVirement = methodCommun.convertrDouble(listImprimVirement);

        double listImprimVirement2 = detfactureService.findQuerySumDouble("select sum(mntReglement2) from Facture where typeReglment2=4 " + query.toString());
        double doubleVirement2 = methodCommun.convertrDouble(listImprimVirement2);

        double listImprimVirement3 = detfactureService.findQuerySumDouble("select sum(mantantBF) from Facture where typeReglment=4 and mntReglement=0 and mntReglement2=0 " + query.toString());
        double doubleVirement3 = methodCommun.convertrDouble(listImprimVirement3);

        List<Facture> listImprimTemp = new ArrayList<Facture>();

        if (listImprim.size() > 0) {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("dateDb", dateDb.toString());
            parameters.put("dateFn", dateFn.toString());

            parameters.put("Espece", (doubleEspece + doubleEspece2 + doubleEspece3) + "");
            parameters.put("Cheque", (doubleCheque + doubleCheque2 + doubleCheque3) + "");

            parameters.put("Virement", (doubleVirement + doubleVirement2 + doubleVirement3) + "");
            parameters.put("Traite", (doubleTraite + doubleTraite2 + doubleTraite3) + "");

            parameters.put("Prelevement", 0 + "");
            parameters.put("Carte", 0 + "");

            for (Facture facture : listImprim) {
                facture.setType(1);
                facture.setCalculer(false);

                //double somme20 = 0.0, somme7 = 0.0;

                LocalDate dateReglement = methodCommun.convertToLocalDateViaInstant(facture.getDateReglement());
                LocalDate dateReglement2 = null != facture.getDateReglement2() ? methodCommun.convertToLocalDateViaInstant(facture.getDateReglement2()) : null;
                LocalDate dateReglement3 = null != facture.getDateReglement3() ? methodCommun.convertToLocalDateViaInstant(facture.getDateReglement3()) : null;
                LocalDate dateReglement4 = null != facture.getDateReglement4() ? methodCommun.convertToLocalDateViaInstant(facture.getDateReglement4()) : null;

                boolean dateReg1IsInRange = methodCommun.isDateInRange(dateReglement, localDateDebutSearch, localDateFinSearch);
                boolean dateReg2IsInRange = false;
                boolean dateReg3IsInRange = false;
                boolean dateReg4IsInRange = false;

                StringBuilder typeReglementOne = new StringBuilder("");
                boolean isAlreadyAddded = false;
                if(dateReg1IsInRange) {
                    typeReglementOne.append(getTypeReglement(facture.getTypeReglment()));
                    facture.setDateReglementIn(true);
                    facture.setMntHT20Reglement1(facture.getMntReglement() / 1.2);
                    facture.setTva20Reglement1(facture.getMntReglement() - facture.getMntHT20Reglement1());
                    facture.setDateReglementOne(facture.getDateReglement());

                    facture.setTypeReglementOne(getTypeReglement(facture.getTypeReglment()));
                    facture.setMntHT20One(facture.getMntReglement() / 1.2);
                    facture.setMantantBFOne(facture.getMntReglement());
                    facture.setTva20One(facture.getMntReglement() - (facture.getMntReglement() / 1.2));

                    isAlreadyAddded = true;
                }

                if(null != dateReglement2) {
                    dateReg2IsInRange = methodCommun.isDateInRange(dateReglement2, localDateDebutSearch, localDateFinSearch);
                    if(dateReg2IsInRange) {
                        try {
                            if(isAlreadyAddded) {
                                Facture facture2 = (Facture) facture.clone();

                                facture2.setDateReglementIn(true);
                                facture2.setMntHT20Reglement1(facture2.getMntReglement2() / 1.2);
                                facture2.setTva20Reglement1(facture2.getMntReglement2() - facture2.getMntHT20Reglement2());
                                facture2.setDateReglementOne(facture2.getDateReglement2());

                                facture2.setTypeReglementOne(getTypeReglement(facture2.getTypeReglment2()));
                                facture2.setMntHT20One(facture2.getMntReglement2() / 1.2);
                                facture2.setMantantBFOne(facture2.getMntReglement2());
                                facture2.setTva20One(facture2.getMntReglement2() - (facture2.getMntReglement2() / 1.2));

                                listImprimTemp.add(facture2);
                            } else {
                                facture.setDateReglementIn(true);
                                facture.setMntHT20Reglement1(facture.getMntReglement2() / 1.2);
                                facture.setTva20Reglement1(facture.getMntReglement2() - facture.getMntHT20Reglement2());
                                facture.setDateReglementOne(facture.getDateReglement2());

                                facture.setTypeReglementOne(getTypeReglement(facture.getTypeReglment2()));
                                facture.setMntHT20One(facture.getMntReglement2() / 1.2);
                                facture.setMantantBFOne(facture.getMntReglement2());
                                facture.setTva20One(facture.getMntReglement2() - (facture.getMntReglement2() / 1.2));

                                isAlreadyAddded = true;
                            }
                        } catch (Exception e) {
                        }
                    }
                }

                if(null != dateReglement3) {
                    dateReg3IsInRange = methodCommun.isDateInRange(dateReglement3, localDateDebutSearch, localDateFinSearch);
                    if(dateReg3IsInRange) {
                        try {
                            if(isAlreadyAddded) {
                                Facture facture2 = (Facture) facture.clone();

                                facture2.setDateReglement3In(true);
                                facture2.setMntHT20Reglement3(facture2.getMntReglement3() / 1.2);
                                facture2.setTva20Reglement3(facture2.getMntReglement3() - facture2.getMntHT20Reglement3());
                                facture2.setDateReglementOne(facture2.getDateReglement3());

                                facture2.setTypeReglementOne(getTypeReglement(facture2.getTypeReglment3()));
                                facture2.setMntHT20One(facture2.getMntReglement3() / 1.2);
                                facture2.setMantantBFOne(facture2.getMntReglement3());
                                facture2.setTva20One(facture2.getMntReglement3() - (facture2.getMntReglement3() / 1.2));

                                listImprimTemp.add(facture2);
                            } else {
                                facture.setDateReglement3In(true);
                                facture.setMntHT20Reglement3(facture.getMntReglement3() / 1.2);
                                facture.setTva20Reglement3(facture.getMntReglement3() - facture.getMntHT20Reglement3());
                                facture.setDateReglementOne(facture.getDateReglement3());

                                facture.setTypeReglementOne(getTypeReglement(facture.getTypeReglment3()));
                                facture.setMntHT20One(facture.getMntReglement3() / 1.2);
                                facture.setMantantBFOne(facture.getMntReglement3());
                                facture.setTva20One(facture.getMntReglement3() - (facture.getMntReglement3() / 1.2));

                                isAlreadyAddded = true;
                            }
                        } catch (Exception e) {
                        }
                    }
                }

                if(null != dateReglement4) {
                    dateReg4IsInRange = methodCommun.isDateInRange(dateReglement4, localDateDebutSearch, localDateFinSearch);
                    if(dateReg4IsInRange) {
                        try {
                            if(isAlreadyAddded) {
                                Facture facture2 = (Facture) facture.clone();
								/*typeReglementOne
									.append(typeReglementOne.length() > 0 ? "\n" : "")
									.append(getTypeReglement(facture2.getTypeReglment4()));*/
                                facture2.setDateReglement4In(true);
                                facture2.setMntHT20Reglement4(facture2.getMntReglement4() / 1.2);
                                facture2.setTva20Reglement4(facture2.getMntReglement4() - facture2.getMntHT20Reglement4());

                                facture2.setDateReglementOne(facture2.getDateReglement4());
                                facture2.setTypeReglementOne(getTypeReglement(facture2.getTypeReglment4()));
                                facture2.setMntHT20One(facture2.getMntReglement4() / 1.2);
                                facture2.setMantantBFOne(facture2.getMntReglement4());
                                facture2.setTva20One(facture2.getMntReglement4() - (facture2.getMntReglement4() / 1.2));

                                listImprimTemp.add(facture2);
                            } else {
                                facture.setDateReglement4In(true);
                                facture.setMntHT20Reglement4(facture.getMntReglement4() / 1.2);
                                facture.setTva20Reglement4(facture.getMntReglement4() - facture.getMntHT20Reglement4());

                                facture.setDateReglementOne(facture.getDateReglement4());
                                facture.setTypeReglementOne(getTypeReglement(facture.getTypeReglment4()));
                                facture.setMntHT20One(facture.getMntReglement4() / 1.2);
                                facture.setMantantBFOne(facture.getMntReglement4());
                                facture.setTva20One(facture.getMntReglement4() - (facture.getMntReglement4() / 1.2));
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }

            listImprim.addAll(listImprimTemp);
            List<Facture> listImprim2 = listImprim.stream()
                    .sorted(Comparator.comparing(Facture::getDateReglementOne))
                    .sorted(Comparator.comparingInt(Facture::getType))
                    .collect(Collectors.toList());

            parameters.put("fichier", local.getLangue());
            GeneratePDF.Imprimer(etatPrint, etatPrint.replace(".jrxml", ".pdf"), listImprim2, parameters, "");
        } else {
            GeneratePDF.anullerImpr("aucun element");
        }
    }
}
