package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.config.enums.ReportTypeEnum;
import com.kabir.kabirbackend.config.enums.StockOperation;
import com.kabir.kabirbackend.config.enums.TypeQteToUpdate;
import com.kabir.kabirbackend.config.imprimer.AchatFactureImport;
import com.kabir.kabirbackend.config.imprimer.AchatFactureImprimer;
import com.kabir.kabirbackend.config.imprimer.FournisseurProduit;
import com.kabir.kabirbackend.config.imprimer.RepertoireInfo;
import com.kabir.kabirbackend.config.requests.PrintResponse;
import com.kabir.kabirbackend.config.requests.RequestStockQte;
import com.kabir.kabirbackend.config.responses.AchatFactureResponse;
import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.config.util.JasperReportsUtil;
import com.kabir.kabirbackend.config.util.StaticVariables;
import com.kabir.kabirbackend.dto.*;
import com.kabir.kabirbackend.entities.*;
import com.kabir.kabirbackend.mapper.AchatFactureMapper;
import com.kabir.kabirbackend.mapper.DetAchatFactureMapper;
import com.kabir.kabirbackend.mapper.DetAchatFactureTVAMapper;
import com.kabir.kabirbackend.repository.*;
import com.kabir.kabirbackend.service.interfaces.IAchatFactureService;
import com.kabir.kabirbackend.specifications.AchatFactureSpecification;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AchatFactureService implements IAchatFactureService {

    private final Logger logger = LoggerFactory.getLogger(AchatFactureService.class);
    private final AchatFactureRepository achatFactureRepository;
    private final AchatFactureMapper achatFactureMapper;
    private final DetAchatFactureRepository detAchatFactureRepository;
    private final DetAchatFactureMapper detAchatFactureMapper;
    private final StockService stockService;
    private final StockRepository stockRepository;
    private final FournisseurRepository fournisseurRepository;
    private final DetAchatFactureTVARepository detAchatFactureTVARepository;
    private final DetAchatFactureTVAMapper detAchatFactureTVAMapper;
    private final FactureRepository factureRepository;
    private final JasperReportsUtil jasperReportsUtil;

    public AchatFactureService(AchatFactureRepository achatFactureRepository,
                               AchatFactureMapper achatFactureMapper,
                               DetAchatFactureRepository detAchatFactureRepository, 
                               DetAchatFactureMapper detAchatFactureMapper, 
                               StockService stockService, 
                               StockRepository stockRepository,
                               FournisseurRepository fournisseurRepository,
                               DetAchatFactureTVARepository detAchatFactureTVARepository,
                               DetAchatFactureTVAMapper detAchatFactureTVAMapper,
                               FactureRepository factureRepository,
                               JasperReportsUtil jasperReportsUtil
                               ) {
        this.achatFactureRepository = achatFactureRepository;
        this.achatFactureMapper = achatFactureMapper;
        this.detAchatFactureRepository = detAchatFactureRepository;
        this.detAchatFactureMapper = detAchatFactureMapper;
        this.stockService = stockService;
        this.stockRepository = stockRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.detAchatFactureTVARepository = detAchatFactureTVARepository;
        this.detAchatFactureTVAMapper = detAchatFactureTVAMapper;
        this.factureRepository = factureRepository;
        this.jasperReportsUtil = jasperReportsUtil;
    }

    @Override
    public AchatFactureDTO save(AchatFactureResponse achatFactureResponse) {
        logger.info("Saving achat facture: {}", achatFactureResponse);
        AchatFactureDTO achatFactureDTO = achatFactureResponse.achatFacture();
        boolean isSave = achatFactureDTO.getId() == null;

        try {
            Optional<Fournisseur> optionalFournisseur = null != achatFactureDTO.getFournisseurId() ? fournisseurRepository.findById(achatFactureDTO.getFournisseurId()) : Optional.empty();

            AchatFacture achatFacture = achatFactureMapper.toAchatFacture(achatFactureDTO);
            achatFacture.setFournisseur(optionalFournisseur.orElse(null));
            achatFactureDTO = achatFactureMapper.toAchatFactureDTO(achatFactureRepository.save(achatFacture));

            enregistrerDetAchatFacture(achatFacture, isSave, achatFactureResponse.detAchatFactures());
            enregistrerDetAchatFactureTVA(achatFacture, isSave, achatFactureResponse.detAchatFactureTVA());

            logger.info("Achat facture saved successfully: {}", achatFactureDTO);
        } catch (Exception e) {
            logger.error("Failed to save achat facture: {}", e.getMessage());
        }

        return achatFactureDTO;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting achat facture by id: {}", id);
        try {
            List<DetAchatFacture> detAchatFactureDTOOld = detAchatFactureRepository.findAllByAchatFactureId(id, Sort.by("id").descending());
            List<DetAchatFactureTVA> detAchatFactureTVAOld = detAchatFactureTVARepository.findAllByAchatFactureId(id);
            logger.info("List of det Achat Facture to delete: {}", detAchatFactureDTOOld.size());

            if(CollectionUtils.isNotEmpty(detAchatFactureDTOOld)) {
                for(DetAchatFacture detAchatFacture : detAchatFactureDTOOld) {
                    if(null != detAchatFacture.getStock() && null != detAchatFacture.getStock().getId()) {
                        stockService.updateQteStock(detAchatFacture.getStock().getId(), TypeQteToUpdate.QTE_STOCK_FACTURER, new RequestStockQte(detAchatFacture.getQteAcheter(), 2, detAchatFacture.getUniteGratuit()));

                        logger.info("Deleting detail achat facture by id: {}", detAchatFacture.getId());
                        detAchatFactureRepository.deleteById(detAchatFacture.getId());
                    }
                }
            }

            if(CollectionUtils.isNotEmpty(detAchatFactureTVAOld)) {
                logger.info("List of det Achat Facture TVA to delete: {}", detAchatFactureTVAOld.size());
                detAchatFactureTVARepository.deleteAll(detAchatFactureTVAOld);
            }

            achatFactureRepository.deleteById(id);
            logger.info("Achat facture deleted successfully");
        } catch (Exception e) {
            logger.error("Failed to delete achat facture: {}", e.getMessage());
        }
    }

    @Override
    public AchatFactureResponse findByIdAchatSimpleResponse(Long id) {
        logger.info("Finding achat facture response by id: {}", id);
        try {
            AchatFactureDTO achatFactureDTO = achatFactureMapper.toAchatFactureDTO(achatFactureRepository.findById(id).orElse(null));
            if(null != achatFactureDTO && null != achatFactureDTO.getId()) {
                List<DetAchatFactureDTO> list = detAchatFactureRepository.findAllByAchatFactureId(id, Sort.by("id").ascending()).stream()
                        .map(detAchatFactureMapper::toDTO)
                        .toList();

                List<DetAchatFactureTVADTO> listTVA = detAchatFactureTVARepository.findAllByAchatFactureId(id).stream()
                        .map(detAchatFactureTVAMapper::toDTO)
                        .toList();

                return new AchatFactureResponse(achatFactureDTO, list, listTVA);
            } else {
                logger.info("Achat facture not found with ID: {}", id);
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to find achat facture response by id: {}", id);
            return null;
        }
    }

    @Override
    public List<AchatFactureDTO> findAll() {
        logger.info("Finding all achatFactures");
        try {
            List<AchatFacture> achatFactures = achatFactureRepository.findAll(Sort.by(Sort.Direction.DESC, "dateReglement"));
            return achatFactures.stream().map(achatFactureMapper::toAchatFactureDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all achatFactures", e);
            throw new RuntimeException("Error finding all achatFactures", e);
        }
    }

    @Override
    public AchatFactureDTO findById(Long id) {
        logger.info("Finding achatFacture by id: {}", id);
        try {
            AchatFacture achatFacture = achatFactureRepository.findById(id).orElseThrow(() -> new RuntimeException("AchatFacture not found"));
            return achatFactureMapper.toAchatFactureDTO(achatFacture);
        } catch (Exception e) {
            logger.error("Error finding achatFacture by id: {}", id, e);
            throw new RuntimeException("Error finding achatFacture by id: " + id, e);
        }
    }

    @Override
    public List<AchatFactureDTO> search(AchatFactureDTO achatFactureDTO) {
        logger.info("Searching achatFacture: {}", achatFactureDTO);
        try {
            List<AchatFacture> achatFactures = achatFactureRepository.findAll(AchatFactureSpecification.builder().achatFactureDTO(achatFactureDTO).build());
            return achatFactures.stream().map(achatFactureMapper::toAchatFactureDTO).toList();
        } catch (Exception e) {
            logger.error("Error searching achatFacture: {}", achatFactureDTO, e);
            throw new RuntimeException("Error searching achatFacture: " + achatFactureDTO, e);
        }
    }

    public void enregistrerDetAchatFactureTVA(AchatFacture achatFacture, boolean isSave, List<DetAchatFactureTVADTO> detAchatFactureTVADTOs) {
        List<DetAchatFactureTVA> detAchatFactureOld = new ArrayList<>();
        if(!isSave) {
            detAchatFactureOld = detAchatFactureTVARepository.findAllByAchatFactureId(achatFacture.getId());
        }

        List<DetAchatFactureTVA> listToDelete = getDetAchatFactureTVAOldNotAnymore(detAchatFactureOld, detAchatFactureTVADTOs);
        if(CollectionUtils.isNotEmpty(listToDelete)) {
            logger.info("Deleting det achat facture TVA : {}", listToDelete.size());
            detAchatFactureTVARepository.deleteAll(listToDelete);
        }

        List<DetAchatFactureTVA> listToSave = detAchatFactureTVADTOs.stream()
                .map(detAchatFactureDTO -> {
                    DetAchatFactureTVA detAchatFacture = detAchatFactureTVAMapper.toEntity(detAchatFactureDTO);
                    detAchatFacture.setAchatFacture(achatFacture);

                    return detAchatFacture;
                })
                .toList();
        logger.info("Saving det achat facture TVA : {}", listToSave.size());
        detAchatFactureTVARepository.saveAll(listToSave);
    }

    public void enregistrerDetAchatFacture(AchatFacture achatFacture, boolean isSave, List<DetAchatFactureDTO> detAchatFactureDTOs) {
        List<DetAchatFacture> detAchatFactureOld = new ArrayList<>();
        if(!isSave) {
            detAchatFactureOld = detAchatFactureRepository.findAllByAchatFactureId(achatFacture.getId(), Sort.by("id").ascending());
        }

        List<DetAchatFacture> listToDelete = getDetStockDepotOldNotAnymore(detAchatFactureOld, detAchatFactureDTOs);
        if(CollectionUtils.isNotEmpty(listToDelete)) {
            logger.info("Deleting det achat facture: {}", listToDelete.size());
            for(DetAchatFacture detAchatFacture : listToDelete) {
                if(null != detAchatFacture.getStock() && null != detAchatFacture.getStock().getId()) {
                    stockService.updateQteStock(detAchatFacture.getStock().getId(), TypeQteToUpdate.QTE_STOCK_FACTURER, new RequestStockQte(detAchatFacture.getQteAcheter(), StockOperation.ADD_TO_STOCK.getValue(), detAchatFacture.getUniteGratuit()));
                }

                detAchatFactureRepository.delete(detAchatFacture);
            }
        }

        List<DetAchatFacture> listToSave = detAchatFactureDTOs.stream()
                .map(detAchatFactureDTO -> {
                    DetAchatFacture detAchatFacture = detAchatFactureMapper.toEntity(detAchatFactureDTO);

                    Optional<Stock> optionalStock =  stockRepository.findById(detAchatFactureDTO.getStockId());
                    detAchatFacture.setStock(optionalStock.orElse(null));

                    detAchatFacture.setAchatFacture(achatFacture);

                    return detAchatFacture;
                })
                .toList();
        updateStockAndSaveDetStockDepot(listToSave, detAchatFactureOld);
    }

    public void updateStockAndSaveDetStockDepot(List<DetAchatFacture> listToSave, List<DetAchatFacture> detAchatFactureDTOOld) {
        if(CollectionUtils.isNotEmpty(listToSave)) {
            for(DetAchatFacture detAchatFacture : listToSave) {
                if(null == detAchatFacture.getId()) {
                    stockService.updateQteStock(detAchatFacture.getStock().getId(), TypeQteToUpdate.QTE_STOCK_FACTURER, new RequestStockQte(detAchatFacture.getQteAcheter(),StockOperation.DELETE_FROM_STOCK.getValue(), detAchatFacture.getUniteGratuit()));
                } else {
                    int qte = detAchatFacture.getQteAcheter();
                    int operation = StockOperation.ADD_TO_STOCK.getValue();
                    qte = updateQte(detAchatFactureDTOOld, detAchatFacture, qte);

                    if(qte != 0) {
                        if(qte < 0) {
                            qte = Math.abs(qte);
                            operation = StockOperation.DELETE_FROM_STOCK.getValue();
                        }

                        stockService.updateQteStock(detAchatFacture.getStock().getId(), TypeQteToUpdate.QTE_STOCK_FACTURER, new RequestStockQte(qte, operation, detAchatFacture.getUniteGratuit()));
                    }
                }

                detAchatFactureRepository.save(detAchatFacture);
            }
        }
    }

    public int updateQte(List<DetAchatFacture> detAchatFactureDTOOld, DetAchatFacture detStockDepot, int qte) {
        if(CollectionUtils.isNotEmpty(detAchatFactureDTOOld)) {
            Optional<DetAchatFacture> optionalDetStockDepotOld = detAchatFactureDTOOld.stream()
                    .filter(detStockDepotOld -> detStockDepotOld.getId().equals(detStockDepot.getId()))
                    .findFirst();

            if(optionalDetStockDepotOld.isPresent()) {
                qte -= optionalDetStockDepotOld.get().getQteAcheter();
            }
        }

        return qte;
    }

    public List<DetAchatFactureTVA> getDetAchatFactureTVAOldNotAnymore(List<DetAchatFactureTVA> detAchatFactureTVADTOOld, List<DetAchatFactureTVADTO> detAchatFactureTVADTOs) {
        if(CollectionUtils.isEmpty(detAchatFactureTVADTOOld)) {
            return new ArrayList<>();
        }

        return detAchatFactureTVADTOOld.stream()
                .filter(detStockDepotOld -> detAchatFactureTVADTOs.stream()
                        .noneMatch(detAchatFactureDTO -> detAchatFactureDTO.getId() != null && detAchatFactureDTO.getId().equals(detStockDepotOld.getId())))
                .collect(Collectors.toList());
    }

    public List<DetAchatFacture> getDetStockDepotOldNotAnymore(List<DetAchatFacture> detAchatFactureDTOOld, List<DetAchatFactureDTO> detAchatSimpleDTOs) {
        if(CollectionUtils.isEmpty(detAchatFactureDTOOld)) {
            return new ArrayList<>();
        }

        return detAchatFactureDTOOld.stream()
                .filter(detStockDepotOld -> detAchatSimpleDTOs.stream()
                        .noneMatch(detAchatFactureDTO -> detAchatFactureDTO.getId() != null && detAchatFactureDTO.getId().equals(detStockDepotOld.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public Integer getLastNumAchatFacture(AchatFactureDTO achatFactureDTO) {
        return achatFactureRepository.findMaxNumAchatFactureInYearDateAF(achatFactureDTO.getDateAF().getYear()).map(l -> l + 1).orElse(1);
    }

    @Override
    public boolean exist(AchatFactureDTO achatFactureDTO) {
        List<AchatFacture> list = achatFactureRepository.findAll(AchatFactureSpecification.builder().achatFactureDTO(achatFactureDTO).build());
        return CollectionUtils.isNotEmpty(list);
    }

    @Override
    public List<AchatFactureDTO> searchByCommon(CommonSearchModel commonSearchModel) {
        return achatFactureRepository.findAll(AchatFactureSpecification.searchByCommon(commonSearchModel)).stream()
                .map(achatFactureMapper::toAchatFactureDTO)
                .toList();
    }

    public List<AchatFactureImprimer> getAchatFactureImprimer(AchatFactureResponse achatFactureResponse) {
        List<AchatFactureImprimer> list = new ArrayList<>();

        list.add(new AchatFactureImprimer(1, achatFactureResponse.achatFacture(), null, null));

        if(CollectionUtils.isNotEmpty(achatFactureResponse.detAchatFactures())) {
            for(DetAchatFactureDTO detAchatFactureDTO : achatFactureResponse.detAchatFactures()) {
                list.add(new AchatFactureImprimer(2, achatFactureResponse.achatFacture(), null, detAchatFactureDTO));
            }
        }

        if(CollectionUtils.isNotEmpty(achatFactureResponse.detAchatFactures())) {
            for(DetAchatFactureTVADTO detAchatFactureTVADTO : achatFactureResponse.detAchatFactureTVA()) {
                list.add(new AchatFactureImprimer(3, achatFactureResponse.achatFacture(), detAchatFactureTVADTO, null));
            }
        }

        return list;
    }

    public PrintResponse imprimer(Integer i, CommonSearchModel commonSearchModel) throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();
        PrintResponse printResponse = new PrintResponse();
        commonSearchModel.setSearchByDate(true);

        double listImprimEspece = 0.0;
        double listImprimCheque = 0.0;
        double listImprimCarte = 0.0;
        double listImprimPrelevement = 0.0;
        double listImprimTraite = 0.0;
        double listImprimVirement = 0.0;
        double listImprimEspeceFacture = 0.0;
        double listImprimEspeceFacture2 = 0.0;

        List<AchatFactureDTO> listImprim = new ArrayList<>();
        List<DetAchatFactureDTO> listDetachatfactureImpr = new ArrayList<>();

        String dateDb = "";
        String dateFn = "";

        if(null == commonSearchModel.getDateDebut() && null == commonSearchModel.getDateFin()) {
            commonSearchModel.setDateFin(LocalDate.now());
            commonSearchModel.setDateDebut(commonSearchModel.getDateFin().minusYears(1));
        }

        String dat1 = StaticVariables.sdfDDMMYY.format(commonSearchModel.getDateDebut());
        String dat2 = StaticVariables.sdfDDMMYY.format(commonSearchModel.getDateFin());

        dateDb = StaticVariables.bundleFR.getString("datedebut") + " : " + StaticVariables.sdfDDMMYY.format(commonSearchModel.getDateDebut());
        dateFn = StaticVariables.bundleFR.getString("dateFin") + " : " + StaticVariables.sdfDDMMYY.format(commonSearchModel.getDateFin());

        /*if(i != 0 && i != 2) {
            if (utilisateurConnecte.getTypeUser() != 1) {
                queryDetAchFCt += " and achatFacture.employeOperateur =" + utilisateurConnecte.getId();
                query += " and employeOperateur =" + utilisateurConnecte.getId();
                queryBilan += " and employeOperateur =" + utilisateurConnecte.getId();
            }
        }*/

        if(i == 0 || i == 2) {
            printResponse.setEtatName("listeVenteAchatFactureImport");

            List<AchatFactureImport> list = new ArrayList<>();
            List<AchatFactureDTO> listImprimTVA = new ArrayList<>();
            List<Importation> listImprotationsImp = new ArrayList<>();

            if(i != 0) {
                if (commonSearchModel.getFournisseurId() != 0) {
                    commonSearchModel.setFournisseurId(null);
                }
            }

            listImprim = achatFactureRepository.findAll(AchatFactureSpecification.searchToPrint(commonSearchModel), Sort.by("dateReglement").ascending().and(Sort.by("repertoire.designation").ascending()))
                    .stream()
                    .map(achatFactureMapper::toAchatFactureDTO)
                    .toList();

            /*if(i == 2) {
                for(int j = 0; j < 5; j++) {
                    String queryTemp = query;

                    if(j == 0) queryTemp += " and montantTVA7 <> 0.0 ";
                    if(j == 1) queryTemp += " and montantTVA10 <> 0.0 ";
                    if(j == 2) queryTemp += " and montantTVA13 <> 0.0 ";
                    if(j == 3) queryTemp += " and montantTVA14 <> 0.0 ";
                    if(j == 4) queryTemp += " and montantTVA20 <> 0.0 ";

                    List<AchatFactureDTO> listTemp = achatFactureRepository.findAll(AchatFactureSpecification.searchToPrint(commonSearchModel), Sort.by("dateReglement").ascending().and(Sort.by("repertoire.designation").ascending()))
                            .stream()
                            .map(achatFactureMapper::toAchatFactureDTO)
                            .toList();

                    if(CollectionUtils.isNotEmpty(listTemp)) {
                        if(j == 0) listTemp.forEach(achatFacture -> achatFacture.setTypeTVA("7%"));
                        if(j == 1) listTemp.forEach(achatFacture -> achatFacture.setTypeTVA("10%"));
                        if(j == 2) listTemp.forEach(achatFacture -> achatFacture.setTypeTVA("13%"));
                        if(j == 3) listTemp.forEach(achatFacture -> achatFacture.setTypeTVA("14%"));
                        if(j == 4) listTemp.forEach(achatFacture -> achatFacture.setTypeTVA("20%"));

                        listImprimTVA.addAll(listTemp);
                    }
                }
            }*/

            if (commonSearchModel.getFournisseurId() != 0) {
                if (CollectionUtils.isNotEmpty(listImprim) || CollectionUtils.isNotEmpty(listImprimTVA)) {
                    printResponse.setEtatName("listeVenteAchatFacture");
                    if(i == 2) printResponse.setEtatName("listeVenteAchatFactureImportTVA");

                    listImprimEspece = achatFactureRepository.getSumMantantTotTTC(commonSearchModel.getDateDebut(), commonSearchModel.getDateFin(), 1);
                    listImprimCheque = achatFactureRepository.getSumMantantTotTTC(commonSearchModel.getDateDebut(), commonSearchModel.getDateFin(), 2);
                    listImprimCarte = achatFactureRepository.getSumMantantTotTTC(commonSearchModel.getDateDebut(), commonSearchModel.getDateFin(), 6);
                    listImprimPrelevement = achatFactureRepository.getSumMantantTotTTC(commonSearchModel.getDateDebut(), commonSearchModel.getDateFin(), 5);
                    listImprimTraite = achatFactureRepository.getSumMantantTotTTC(commonSearchModel.getDateDebut(), commonSearchModel.getDateFin(), 3);
                    listImprimVirement = achatFactureRepository.getSumMantantTotTTC(commonSearchModel.getDateDebut(), commonSearchModel.getDateFin(), 4);

                    parameters.put("dateDb", dateDb);
                    parameters.put("dateFn", dateFn);

                    parameters.put("Espece", listImprimEspece + "");
                    parameters.put("Cheque", listImprimCheque + "");

                    parameters.put("Virement", listImprimVirement + "");
                    parameters.put("Traite", listImprimTraite + "");

                    parameters.put("Prelevement", listImprimPrelevement + "");
                    parameters.put("Carte", listImprimCarte + "");

                    parameters.put("fichier", StaticVariables.bundleFR.getBaseBundleName());

                    if(i == 0) {
                        byte[] bytes = jasperReportsUtil.jasperReportInBytes(listImprim, parameters, printResponse.getEtatName(), ReportTypeEnum.PDF, "");
                        printResponse.setResponseBytes(bytes);
                    } else {
                        double mntTotalTTC = listImprim.stream().mapToDouble(AchatFactureDTO::getMantantTotTTC).sum();
                        double mntTotalHT = listImprim.stream().mapToDouble(AchatFactureDTO::getMantantTotHT).sum();
                        double mntTotalSupp = 0;//listImprim.stream().mapToDouble(AchatFactureDTO::get).sum();

                        parameters.put("mntTotalTTC", mntTotalTTC + "");
                        parameters.put("mntTotalHT", (mntTotalHT + mntTotalSupp) + "");

                        list.addAll(listImprimTVA.stream().map(achatFactureModif -> new AchatFactureImport(0, achatFactureModif, null, null)).toList());

                        byte[] bytes = jasperReportsUtil.jasperReportInBytes(list, parameters, printResponse.getEtatName(), ReportTypeEnum.PDF, "");
                        printResponse.setResponseBytes(bytes);
                    }
                } else {
                    byte[] bytes = JasperReportsUtil.anullerImpr(StaticVariables.bundleFR.getString("aucuneResultatTrouve"));
                    printResponse.setResponseBytes(bytes);
                }
            }else {
                List<RepertoireInfo> listRepertoireInfos = new ArrayList<RepertoireInfo>();
                Map<Long, RepertoireInfo> hashMap = new HashMap<Long, RepertoireInfo>();

                if(i == 2) printResponse.setEtatName("listeVenteAchatFactureImportTVA");

                if (CollectionUtils.isNotEmpty(listImprim) || CollectionUtils.isNotEmpty(listImprotationsImp)) {
                    if (CollectionUtils.isNotEmpty(listImprim)) {
                        for (AchatFactureDTO achatFacture : listImprim) {
                            FournisseurDTO fournisseurDTO = new FournisseurDTO();
                            fournisseurDTO.setId(achatFacture.getFournisseurId());
                            fournisseurDTO.setDesignation(achatFacture.getFournisseurDesignation());
                            fournisseurDTO.setIce(achatFacture.getFournisseurIce());
                            hashMap.put(achatFacture.getFournisseurId(), new RepertoireInfo(fournisseurDTO, 0.0, 0.0, 0.0));
                        }

                        if(i == 0) list.addAll(listImprim.stream().map(achatFactureModif -> new AchatFactureImport(0, achatFactureModif, null, null)).toList());
                        else list.addAll(listImprimTVA.stream().map(achatFactureModif -> new AchatFactureImport(0, achatFactureModif, null, null)).toList());

                        listImprimEspece = achatFactureRepository.getSumMantantTotTTC(commonSearchModel.getDateDebut(), commonSearchModel.getDateFin(), 1);
                        listImprimCheque = achatFactureRepository.getSumMantantTotTTC(commonSearchModel.getDateDebut(), commonSearchModel.getDateFin(), 2);
                        listImprimCarte = achatFactureRepository.getSumMantantTotTTC(commonSearchModel.getDateDebut(), commonSearchModel.getDateFin(), 6);
                        listImprimPrelevement = achatFactureRepository.getSumMantantTotTTC(commonSearchModel.getDateDebut(), commonSearchModel.getDateFin(), 5);
                        listImprimTraite = achatFactureRepository.getSumMantantTotTTC(commonSearchModel.getDateDebut(), commonSearchModel.getDateFin(), 3);
                        listImprimVirement = achatFactureRepository.getSumMantantTotTTC(commonSearchModel.getDateDebut(), commonSearchModel.getDateFin(), 4);
                        listImprimEspeceFacture = factureRepository.getSumMntReglement(commonSearchModel.getDateDebut(), commonSearchModel.getDateFin(), 1);
                        listImprimEspeceFacture2 = factureRepository.getSumMntReglement2(commonSearchModel.getDateDebut(), commonSearchModel.getDateFin(), 1);

                        if(i == 2) {
                            double mntTotalTTC = listImprim.stream().mapToDouble(AchatFactureDTO::getMantantTotTTC).sum();
                            double mntTotalHT = listImprim.stream().mapToDouble(AchatFactureDTO::getMantantTotHT).sum();
                            double mntTotalSupp = 0;//listImprim.stream().mapToDouble(Achatfacture::getMontantDroitSupplementaire).sum();

                            parameters.put("mntTotalTTC", mntTotalTTC + "");
                            parameters.put("mntTotalHT", (mntTotalHT + mntTotalSupp) + "");
                        }
                    }

                    if(i == 0) {
                        if(CollectionUtils.isNotEmpty(listImprotationsImp)) {
                            list.addAll(listImprotationsImp.stream().map(importation -> new AchatFactureImport(1, null, importation, null)).toList());
                        }

                        if(!hashMap.isEmpty()) {
                            for (Map.Entry<Long, RepertoireInfo> entry : hashMap.entrySet()) {
                                RepertoireInfo repertoireInfo = entry.getValue();
                                double mntTva7 = 0;//listImprim.stream().filter(achatFacture -> achatFacture.getFournisseurId().equals(entry.getKey())).mapToDouble(AchatFactureDTO::getMontantTVA7).sum();
                                double mntTva10 = 0;//listImprim.stream().filter(achatFacture -> achatFacture.getFournisseurId().equals(entry.getKey())).mapToDouble(AchatFactureDTO::getMontantTVA10).sum();
                                double mntTva13 = 0;//listImprim.stream().filter(achatFacture -> achatFacture.getFournisseurId().equals(entry.getKey())).mapToDouble(AchatFactureDTO::getMontantTVA13).sum();
                                double mntTva14 = 0;//listImprim.stream().filter(achatFacture -> achatFacture.getFournisseurId().equals(entry.getKey())).mapToDouble(AchatFactureDTO::getMontantTVA14).sum();
                                double mntTva20 = 0;//listImprim.stream().filter(achatFacture -> achatFacture.getFournisseurId().equals(entry.getKey())).mapToDouble(AchatFactureDTO::getMontantTVA20).sum();

                                double mntHT = listImprim.stream().filter(achatFacture -> achatFacture.getFournisseurId().equals(entry.getKey())).mapToDouble(AchatFactureDTO::getMantantTotHT).sum();
                                double mntDroitSuppl = 0;//listImprim.stream().filter(achatFacture -> achatFacture.getFournisseurId().equals(entry.getKey())).mapToDouble(Achatfacture::getMontantDroitSupplementaire).sum();

                                repertoireInfo.setMontantTva(mntTva7 + mntTva10 + mntTva13 + mntTva14 + mntTva20);
                                repertoireInfo.setMontantHT(mntHT + mntDroitSuppl);
                                repertoireInfo.setMontantTTC(listImprim.stream().filter(achatFacture -> achatFacture.getFournisseurId().equals(entry.getKey())).mapToDouble(AchatFactureDTO::getMantantTotTTC).sum());

                                listRepertoireInfos.add(repertoireInfo);
                            }

                            list.addAll(listRepertoireInfos.stream().map(achatFactureModif -> new AchatFactureImport(2, null, null, achatFactureModif)).toList());
                        }
                    }

                    parameters.put("dateDb", dateDb);
                    parameters.put("dateFn", dateFn);

                    parameters.put("Espece", listImprimEspece + "");
                    parameters.put("Cheque", listImprimCheque + "");

                    parameters.put("Virement", listImprimVirement + "");
                    parameters.put("Traite", listImprimTraite + "");

                    parameters.put("Prelevement", listImprimPrelevement + "");
                    parameters.put("Carte", listImprimCarte + "");

                    parameters.put("EspeceFacture", (listImprimEspeceFacture + listImprimEspeceFacture2) + "");
                    parameters.put("Caisse", (listImprimEspeceFacture + listImprimEspeceFacture2 - listImprimEspece) + "");

                    parameters.put("fichier", StaticVariables.bundleFR.getBaseBundleName());
                    byte[] bytes = jasperReportsUtil.jasperReportInBytes(list, parameters, printResponse.getEtatName(), ReportTypeEnum.PDF, "");
                    printResponse.setResponseBytes(bytes);
                } else {
                    byte[] bytes = JasperReportsUtil.anullerImpr(StaticVariables.bundleFR.getString("aucuneResultatTrouve"));
                    printResponse.setResponseBytes(bytes);
                }
            }
        }else {
            if(i == 1) {//Bilan
                if (commonSearchModel.getStockId() != 0) {
                    printResponse.setEtatName("listeAchatFactureProduit");

                    listDetachatfactureImpr = detAchatFactureRepository
                            .findAllByDates(commonSearchModel.getDateDebut(), commonSearchModel.getDateFin(), commonSearchModel.getStockId(), Sort.by(Sort.Direction.DESC, "achatFacture.dateAF"))
                            .stream().map(detAchatFactureMapper::toDTO).toList();

                    if (CollectionUtils.isNotEmpty(listDetachatfactureImpr)) {
                        parameters.put("dateDb", dateDb);
                        parameters.put("dateFn", dateFn);

                        parameters.put("Espece", listImprimEspece + "");
                        parameters.put("Cheque", listImprimCheque + "");

                        parameters.put("Virement", listImprimVirement + "");
                        parameters.put("Traite", listImprimTraite + "");

                        parameters.put("Prelevement", listImprimPrelevement + "");
                        parameters.put("Carte", listImprimCarte + "");

                        parameters.put("fichier", StaticVariables.bundleFR.getBaseBundleName());

                        byte[] bytes = jasperReportsUtil.jasperReportInBytes(listDetachatfactureImpr, parameters, printResponse.getEtatName(), ReportTypeEnum.PDF, "");
                        printResponse.setResponseBytes(bytes);
                    } else {
                        byte[] bytes = JasperReportsUtil.anullerImpr(StaticVariables.bundleFR.getString("aucuneResultatTrouve"));
                        printResponse.setResponseBytes(bytes);
                    }
                }else {
                    printResponse.setEtatName("listeProduitAchatFacture");
                    List<FournisseurProduit> listFournisseurProduit = new ArrayList<FournisseurProduit>();
                    Map<Long,FournisseurProduit> map = new LinkedHashMap<Long, FournisseurProduit>();

                    CommonSearchModel commonSearchModelTemp = CommonSearchModel
                            .builder()
                            .searchByDate(true)
                            .dateDebut(commonSearchModel.getDateDebut())
                            .dateFin(commonSearchModel.getDateFin())
                            .build();
                    List<AchatFactureDTO> listAchatFacture = achatFactureRepository.findAll(AchatFactureSpecification.searchByCommon(commonSearchModelTemp))
                            .stream()
                            .map(achatFactureMapper::toAchatFactureDTO).toList();
                    for (AchatFactureDTO achatFacture : listAchatFacture) {
                        List<DetAchatFactureDTO> listAllDetImpr = detAchatFactureRepository.findAllByAchatFactureId(achatFacture.getId(), Sort.by("stock.designation").ascending())
                                .stream()
                                .map(detAchatFactureMapper::toDTO).toList();
                        if(CollectionUtils.isNotEmpty(listAllDetImpr)) {
                            for (DetAchatFactureDTO detAchatFacture : listAllDetImpr) {
                                if(map.containsKey(detAchatFacture.getStockId())) {
                                    FournisseurProduit fournisseurProduitOld = map.get(detAchatFacture.getStockId());
                                    if(null != fournisseurProduitOld) {
                                        fournisseurProduitOld.setMontant(fournisseurProduitOld.getMontant() + detAchatFacture.getMantantTTC());
                                        fournisseurProduitOld.setQteLivre(fournisseurProduitOld.getQteLivre() + detAchatFacture.getQteAcheter());
                                        fournisseurProduitOld.setBeneficeDH(fournisseurProduitOld.getBeneficeDH() + detAchatFacture.getBeneficeDH());

                                        map.put(detAchatFacture.getStockId(),fournisseurProduitOld);
                                    }
                                }else {
                                    StockDTO stockDTO = new StockDTO();
                                    stockDTO.setId(detAchatFacture.getStockId());
                                    stockDTO.setDesignation(detAchatFacture.getStockDesignation());
                                    stockDTO.setPvttc(detAchatFacture.getStockPvttc());
                                    stockDTO.setQteStock(detAchatFacture.getStockQteStock());
                                    stockDTO.setQteStockImport(detAchatFacture.getStockQteStock());

                                    FournisseurProduit fournisseurProduit = new FournisseurProduit();
                                    fournisseurProduit.setStock(stockDTO);
                                    fournisseurProduit.setQteLivre(detAchatFacture.getQteAcheter());
                                    fournisseurProduit.setMontant(detAchatFacture.getMantantTTC());
                                    fournisseurProduit.setBeneficeDH(detAchatFacture.getBeneficeDH());

                                    map.put(detAchatFacture.getStockId(), fournisseurProduit);
                                }
                            }
                        }
                    }

                    if(!map.isEmpty()) {
                        listFournisseurProduit.addAll(map.values());
                        listFournisseurProduit.sort(FournisseurProduit.parOrdreAlphabetiqueStock);
                    }

                    if (CollectionUtils.isNotEmpty(listFournisseurProduit)) {
                        parameters.put("dateDb", dateDb);
                        parameters.put("dateFn", dateFn);
                        parameters.put("valeur", 0 + "");

                        parameters.put("fichier", StaticVariables.bundleFR.getBaseBundleName());
                        parameters.put("titleText", "");

                        byte[] bytes = jasperReportsUtil.jasperReportInBytes(listFournisseurProduit, parameters, printResponse.getEtatName(), ReportTypeEnum.PDF, "");
                        printResponse.setResponseBytes(bytes);
                    } else {
                        byte[] bytes = JasperReportsUtil.anullerImpr(StaticVariables.bundleFR.getString("aucuneResultatTrouve"));
                        printResponse.setResponseBytes(bytes);
                    }
                }
            }else {//Fournisseur
                printResponse.setEtatName("listeVenteAchatFactureImportBilan");

                CommonSearchModel commonSearchModelTemp = CommonSearchModel
                        .builder()
                        .searchByDate(true)
                        .dateDebut(commonSearchModel.getDateDebut())
                        .dateFin(commonSearchModel.getDateFin())
                        .build();
                listImprim = achatFactureRepository.findAll(AchatFactureSpecification.searchByCommon(commonSearchModelTemp), Sort.by("dateAF").ascending().and(Sort.by("repertoire.designation").ascending()))
                        .stream()
                        .map(achatFactureMapper::toAchatFactureDTO)
                        .toList();

                List<RepertoireInfo> listRepertoireInfos = new ArrayList<RepertoireInfo>();
                Map<Long, RepertoireInfo> hashMap = new HashMap<Long, RepertoireInfo>();
                if (CollectionUtils.isNotEmpty(listImprim)) {
                        for (AchatFactureDTO achatFacture : listImprim) {
                            FournisseurDTO fournisseurDTO = new FournisseurDTO();
                            fournisseurDTO.setId(achatFacture.getFournisseurId());
                            fournisseurDTO.setDesignation(achatFacture.getFournisseurDesignation());
                            fournisseurDTO.setIce(achatFacture.getFournisseurIce());

                            hashMap.put(achatFacture.getFournisseurId(), new RepertoireInfo(fournisseurDTO, 0.0, 0.0, 0.0));
                        }

                    List<AchatFactureImport> list = new ArrayList<AchatFactureImport>(listImprim.stream().map(achatFactureModif -> new AchatFactureImport(0, achatFactureModif, null, null)).toList());

                    if(!hashMap.isEmpty()) {
                        for (Map.Entry<Long, RepertoireInfo> entry : hashMap.entrySet()) {
                            RepertoireInfo repertoireInfo = entry.getValue();
                            double mntTva7 = 0;//listImprim.stream().filter(achatFacture -> achatFacture.getFournisseurId().equals(entry.getKey())).mapToDouble(AchatFactureDTO::getMontantTVA7).sum();
                            double mntTva10 = 0;//listImprim.stream().filter(achatFacture -> achatFacture.getFournisseurId().equals(entry.getKey())).mapToDouble(AchatFactureDTO::getMontantTVA10).sum();
                            double mntTva13 = 0;//listImprim.stream().filter(achatFacture -> achatFacture.getFournisseurId().equals(entry.getKey())).mapToDouble(AchatFactureDTO::getMontantTVA13).sum();
                            double mntTva14 = 0;//listImprim.stream().filter(achatFacture -> achatFacture.getFournisseurId().equals(entry.getKey())).mapToDouble(AchatFactureDTO::getMontantTVA14).sum();
                            double mntTva20 = 0;//listImprim.stream().filter(achatFacture -> achatFacture.getFournisseurId().equals(entry.getKey())).mapToDouble(AchatFactureDTO::getMontantTVA20).sum();

                            double mntHT = listImprim.stream().filter(achatFacture -> achatFacture.getFournisseurId().equals(entry.getKey())).mapToDouble(AchatFactureDTO::getMantantTotHT).sum();
                            double mntDroitSuppl = 0;//listImprim.stream().filter(achatFacture -> achatFacture.getFournisseurId().equals(entry.getKey())).mapToDouble(AchatFactureDTO::getMontantDroitSupplementaire).sum();

                            repertoireInfo.setMontantTva(mntTva7 + mntTva10 + mntTva13 + mntTva14 + mntTva20);
                            repertoireInfo.setMontantHT(mntHT + mntDroitSuppl);
                            repertoireInfo.setMontantTTC(listImprim.stream().filter(achatFacture -> achatFacture.getFournisseurId().equals(entry.getKey())).mapToDouble(AchatFactureDTO::getMantantTotTTC).sum());

                            listRepertoireInfos.add(repertoireInfo);
                        }

                        list.addAll(listRepertoireInfos.stream().map(achatFactureModif -> new AchatFactureImport(2, null, null, achatFactureModif)).collect(Collectors.toList()));
                    }

                    parameters.put("dateDb", dateDb);
                    parameters.put("dateFn", dateFn);

                    parameters.put("Espece", listImprimEspece + "");
                    parameters.put("Cheque", listImprimCheque + "");

                    parameters.put("Virement", listImprimVirement + "");
                    parameters.put("Traite", listImprimTraite + "");

                    parameters.put("Prelevement", listImprimPrelevement + "");
                    parameters.put("Carte", listImprimCarte + "");

                    parameters.put("EspeceFacture", (listImprimEspeceFacture + listImprimEspeceFacture2) + "");
                    parameters.put("Caisse", (listImprimEspeceFacture + listImprimEspeceFacture2 - listImprimEspece) + "");

                    parameters.put("fichier", StaticVariables.bundleFR.getBaseBundleName());

                    byte[] bytes = jasperReportsUtil.jasperReportInBytes(list, parameters, printResponse.getEtatName(), ReportTypeEnum.PDF, "");
                    printResponse.setResponseBytes(bytes);
                } else {
                    byte[] bytes = JasperReportsUtil.anullerImpr(StaticVariables.bundleFR.getString("aucuneResultatTrouve"));
                    printResponse.setResponseBytes(bytes);
                }
            }
        }

        return printResponse;
    }
}
