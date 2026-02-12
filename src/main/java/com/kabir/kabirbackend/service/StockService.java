package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.config.enums.ReportTypeEnum;
import com.kabir.kabirbackend.config.enums.TypeQteToUpdate;
import com.kabir.kabirbackend.config.requests.PrintRequest;
import com.kabir.kabirbackend.config.requests.PrintResponse;
import com.kabir.kabirbackend.config.requests.RequestStockQte;
import com.kabir.kabirbackend.config.util.JasperReportsUtil;
import com.kabir.kabirbackend.config.util.StaticVariables;
import com.kabir.kabirbackend.config.util.StockSumProjection;
import com.kabir.kabirbackend.dto.StockDTO;
import com.kabir.kabirbackend.entities.Fournisseur;
import com.kabir.kabirbackend.entities.Stock;
import com.kabir.kabirbackend.mapper.StockMapper;
import com.kabir.kabirbackend.repository.FournisseurRepository;
import com.kabir.kabirbackend.repository.StockRepository;
import com.kabir.kabirbackend.service.interfaces.IStockService;
import com.kabir.kabirbackend.specifications.StockSpecification;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StockService implements IStockService {
    private final Logger logger = LoggerFactory.getLogger(StockService.class);
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    private final FournisseurRepository fournisseurRepository;
    private final JasperReportsUtil jasperReportsUtil;

    public StockService(StockRepository stockRepository, StockMapper stockMapper, FournisseurRepository fournisseurRepository, JasperReportsUtil jasperReportsUtil) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
        this.fournisseurRepository = fournisseurRepository;
        this.jasperReportsUtil = jasperReportsUtil;
    }

    @Override
    public StockDTO save(StockDTO stockDTO) {
        logger.info("Saving stock: {}", stockDTO);
        try {
            Optional<Fournisseur> optionalFournisseur = fournisseurRepository.findById(stockDTO.getFournisseurId());
            Stock stock = stockMapper.toStock(stockDTO);
            stock.setFournisseur(optionalFournisseur.orElse(null));

            return stockMapper.toStockDTO(stockRepository.save(stock));
        } catch (Exception e) {
            logger.error("Error saving stock", e);
            throw new RuntimeException("Error saving stock", e);
        }
    }

    @Override
    public List<StockDTO> findAll() {
        logger.info("Finding all stocks");
        try {
            List<Stock> stocks = stockRepository.findAll();
            return stocks.stream().map(stockMapper::toStockDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all stocks", e);
            throw new RuntimeException("Error finding all stocks", e);
        }
    }

    @Override
    public StockDTO findById(Long id) {
        logger.info("Finding stock DTO by id: {}", id);
        try {
            Stock stock = stockRepository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
            return stockMapper.toStockDTO(stock);
        } catch (Exception e) {
            logger.error("Error finding stock by id", e);
            throw new RuntimeException("Error finding stock by id", e);
        }
    }

    @Override
    public Stock findByIdStock(Long id) {
        logger.info("Finding stock by id: {}", id);
        try {
            return stockRepository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
        } catch (Exception e) {
            logger.error("Error finding stock by id", e);
            throw new RuntimeException("Error finding stock by id", e);
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting stock: {}", id);
        try {
            stockRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting stock", e);
            throw new RuntimeException("Error deleting stock", e);
        }
    }

    @Override
    public List<StockDTO> search(StockDTO stockDTO) {
        logger.info("Searching stocks: {}", stockDTO);
        return stockRepository.findAll(StockSpecification.builder().stockDTO(stockDTO).build()).stream().map(stockMapper::toStockDTO).toList();
    }

    @Override
    public List<StockDTO> searchBySupprimerOrArchiver(StockDTO stockDTO) {
        return stockRepository.findAll(StockSpecification.searchBySupprimerOrArchiver(stockDTO)).stream().map(stockMapper::toStockDTO).toList();
    }

    @Override
    public void updateQteStock(Long id, TypeQteToUpdate typeQteToUpdate, RequestStockQte requestStockQte) {
        logger.info("Updating stock qte stock: {}", requestStockQte);
        try {
            if (null != id) {
                StockDTO stockDTO = findById(id);
                if(null != stockDTO && null != stockDTO.getId()) {
                    if (typeQteToUpdate == TypeQteToUpdate.QTE_STOCK) {
                        logger.info("Updating stock qte stock: {}", requestStockQte.qte());
                    } else if (typeQteToUpdate == TypeQteToUpdate.QTE_STOCK_IMPORT) {
                        logger.info("Updating stock qte stock import: {}", requestStockQte.qte());
                    } else if (typeQteToUpdate == TypeQteToUpdate.QTE_STOCK_FACTURER) {
                        logger.info("Updating stock qte stock facturer: {}", requestStockQte.qte());
                    } else if (typeQteToUpdate == TypeQteToUpdate.QTE_STOCK_SORTIE) {
                        logger.info("Updating stock qte stock sortie: {}", requestStockQte.qte());
                    }

                    updateStock(typeQteToUpdate, stockDTO, requestStockQte);
                } else {
                    throw new RuntimeException("Stock not found");
                }
            } else {
                throw new RuntimeException("Id is null");
            }
        } catch (Exception e) {
            logger.error("Error updating stock qte stock", e);
            throw new RuntimeException("Error updating stock qte stock", e);
        }
    }

    @Override
    public PrintResponse imprimer(PrintRequest printRequest) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("fichier", StaticVariables.bundleFR.getBaseBundleName());

        PrintResponse printResponse = new PrintResponse();
        logger.info("Request to print");

        if(printRequest.getType() != 1) {
            List<StockDTO> listStock = stockRepository.findAll(StockSpecification.getListStockBasedOnIds(printRequest.getIds())).stream().map(stockMapper::toStockDTO).toList();
            if (CollectionUtils.isNotEmpty(listStock)) {
                StockSumProjection stockSumProjection = stockRepository.getSumPattcQteFacturerAndStock();

                double sumQteFact = stockSumProjection.getSumQteFact() != null ? stockSumProjection.getSumQteFact() : 0.0;
                double sumQteStock = stockSumProjection.getSumQteStock() != null ? stockSumProjection.getSumQteStock() : 0.0;

                if (printRequest.getType() == 1) {
                    printResponse.setEtatName("etatTousProduit");

                    parameters.put("sumAllFct", StaticVariables.convertDouble(sumQteFact) + "");
                    parameters.put("sumAllStc", StaticVariables.convertDouble(sumQteStock) + "");

                    listStock = listStock.stream()
                            .sorted(Comparator.comparing(StockDTO::getFournisseurId))
                            .toList();
                } else if (printRequest.getType() == 2) {
                    printResponse.setEtatName("etatProduitStock");
                    parameters.put("sumAllStc", StaticVariables.convertDouble(sumQteStock) + "");
                } else if (printRequest.getType() == 3) {
                    printResponse.setEtatName("etatProduitStockFacturer");
                    parameters.put("sumAllFct", StaticVariables.convertDouble(sumQteFact) + "");
                } else if (printRequest.getType() == 4 || printRequest.getType() == 9 || printRequest.getType() == 11) {
                    printResponse.setEtatName("etatProduitPrixVente");
                    if(printRequest.getType() == 9) printResponse.setEtatName("etatProduitPrixVenteCommercialRemise");
                    if(printRequest.getType() == 11) printResponse.setEtatName("etatProduitPrixVenteRemise");
                } else if (printRequest.getType() == 5) {
                    printResponse.setEtatName("etatProduitFourni");
                    if (null != printRequest.getFournisseurId() && printRequest.getFournisseurId() != 0) {
                        printResponse.setEtatName("etatProduitFourniOne");
                        listStock = stockRepository.listStockNonArchiveArchive(printRequest.getFournisseurId()).stream().map(stockMapper::toStockDTO).toList();
                    } else {
                        listStock = stockRepository.listStockNonArchiveArchive(null).stream().map(stockMapper::toStockDTO).toList();
                    }
                }else if (printRequest.getType() == 6) {
                    printResponse.setEtatName("etatProduitPrixVenteSimple");
                }else if (printRequest.getType() == 7) {
                    printResponse.setEtatName("etatProduitPrixRevendeur");
                }else if (printRequest.getType() == 8 || printRequest.getType() == 10) {
                    printResponse.setEtatName("etatProduitPrixNet");
                    if(printRequest.getType() == 10) printResponse.setEtatName("etatProduitPromotion");
                } else if (printRequest.getType() == 12) {
                    printResponse.setEtatName("etatProduitPrixCommercial");
                    listStock = stockRepository.listStockPrixCommercialPositive().stream().map(stockMapper::toStockDTO).toList();
                } else if(printRequest.getType() == 13) {
                    printResponse.setEtatName("etatProduitPrixRevendeurCommission");
                } else if(printRequest.getType() == 14) {
                    printResponse.setEtatName("etatProduitPrixPharmacieRemise");
                }

                byte[] bytes = jasperReportsUtil.jasperReportInBytes(listStock, parameters, printResponse.getEtatName(), ReportTypeEnum.PDF, "");
                printResponse.setResponseBytes(bytes);

            } else {
                byte[] bytes = JasperReportsUtil.anullerImpr(StaticVariables.bundleFR.getString("aucuneResultatTrouve"));
                printResponse.setResponseBytes(bytes);
            }
        }else {
            List<StockDTO> listStock = new ArrayList<>(stockRepository.findAll(StockSpecification.getListStockBasedOnIds(null)).stream().map(stockMapper::toStockDTO).toList());

            StockSumProjection stockSumProjection = stockRepository.getSumPattcQteFacturerAndStock();
            StockSumProjection stockSumProjectionArchive = stockRepository.getSumPattcQteFacturerAndStockArchive();

            double sumQteFact = stockSumProjection.getSumQteFact() != null ? stockSumProjection.getSumQteFact() : 0.0;
            double sumQteStock = stockSumProjection.getSumQteStock() != null ? stockSumProjection.getSumQteStock() : 0.0;
            double sumQteFactArch = stockSumProjectionArchive.getSumQteFact() != null ? stockSumProjection.getSumQteFact() : 0.0;
            double sumQteStockArch = stockSumProjectionArchive.getSumQteStock() != null ? stockSumProjection.getSumQteStock() : 0.0;

            List<StockDTO> listStockArch = stockRepository.listStockArchive().stream().map(stockMapper::toStockDTO).toList();

            printResponse.setEtatName("etatTousProduit");

            parameters.put("sumAllFct", StaticVariables.convertDouble(sumQteFact) + StaticVariables.convertDouble(sumQteFactArch) + "");
            parameters.put("sumAllStc", StaticVariables.convertDouble(sumQteStock) + StaticVariables.convertDouble(sumQteStockArch) + "");

            listStock.addAll(listStockArch);

            if (CollectionUtils.isNotEmpty(listStock)) {
                List<StockDTO> listGroup = listStock.stream()
                        .sorted(Comparator.comparing(StockDTO::getFournisseurId))
                        .toList();

                listGroup.forEach(stock -> {
                    if(stock.getPrixVentMin4() != 0) {
                        stock.setPrixVenteMinTemp(stock.getPrixVentMin4());
                    } else if(stock.getPrixVentMin3() != 0) {
                        stock.setPrixVenteMinTemp(stock.getPrixVentMin3());
                    } else if(stock.getPrixVentMin2() != 0) {
                        stock.setPrixVenteMinTemp(stock.getPrixVentMin2());
                    }else if(stock.getPrixVentMin1() != 0) {
                        stock.setPrixVenteMinTemp(stock.getPrixVentMin1());
                    } else {
                        stock.setPrixVenteMinTemp(stock.getPvttc());
                    }
                });

                byte[] bytes = jasperReportsUtil.jasperReportInBytes(listStock, parameters, printResponse.getEtatName(), ReportTypeEnum.PDF, "");
                printResponse.setResponseBytes(bytes);

            } else {
                byte[] bytes = JasperReportsUtil.anullerImpr(StaticVariables.bundleFR.getString("aucuneResultatTrouve"));
                printResponse.setResponseBytes(bytes);
            }
        }

        return printResponse;
    }

    public void updateStock(TypeQteToUpdate typeQteToUpdate, StockDTO stockDTO, RequestStockQte requestStockQte) {
        int operator = requestStockQte.typeOperation();
        int qte = requestStockQte.qte();
        int uniteGrat = (null != requestStockQte.uniteGratuite()) ? requestStockQte.uniteGratuite() : 0;

        switch (typeQteToUpdate) {
            case QTE_STOCK:
                stockDTO.setQteStock(operator == 1 ? stockDTO.getQteStock() - qte : stockDTO.getQteStock() + qte);
                break;
            case QTE_STOCK_IMPORT:
                stockDTO.setQteStock(operator == 1 ? stockDTO.getQteStock() - (qte + uniteGrat) : stockDTO.getQteStock() + (qte + uniteGrat));
                stockDTO.setQteStockImport(operator == 1 ? stockDTO.getQteStockImport() + qte : stockDTO.getQteStockImport() - qte);
                break;
            case QTE_STOCK_FACTURER:
                stockDTO.setQteFacturer(operator == 1 ? stockDTO.getQteFacturer() - qte : stockDTO.getQteFacturer() + qte);
                break;
            case QTE_STOCK_SORTIE:
                stockDTO.setQteSortie(operator == 1 ? stockDTO.getQteSortie() - qte : stockDTO.getQteSortie() + qte);
                break;
        }

        save(stockDTO);
    }
}
