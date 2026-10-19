package com.kabir.kabirbackend.reports;

import com.kabir.kabirbackend.config.util.StaticVariables;
import com.kabir.kabirbackend.dto.DetFactureDTO;
import com.kabir.kabirbackend.dto.FactureDTO;
import com.kabir.kabirbackend.dto.StockLivraisonSumPrintDTO;
import com.kabir.kabirbackend.entities.Stock;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Fills the 7 reports behind the facture "Consulter" print buttons with in-memory
 * data (no database, no Spring context) so that the jrxml adaptation is proven:
 * <ul>
 *     <li>the fieldDescription paths (repertoire / stock flattened by ModelMapper) resolve,</li>
 *     <li>{@code java.time.LocalDate} date fields fill and format,</li>
 *     <li>the declared parameters (including the bilan numeric ones) parse.</li>
 * </ul>
 */
class FacturePrintReportsTest {

    private static Map<String, Object> baseParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("fichier", StaticVariables.bundleFR.getBaseBundleName());
        params.put("titleText", "");
        params.put("dateDb", StaticVariables.bundleFR.getString("datedebut") + " : 01-09-2026");
        params.put("dateFn", StaticVariables.bundleFR.getString("dateFin") + " : 07-10-2026");
        params.put("produit", "");
        params.put("valeur", "");
        params.put("totalQteLivr", "");
        params.put("totalMontant", "");
        params.put("totalBenfice", "");
        return params;
    }

    private static byte[] fill(String reportName, List<?> rows, Map<String, Object> extraParams) throws Exception {
        try (InputStream reportStream = new ClassPathResource("reports/fr/" + reportName + ".jrxml").getInputStream()) {
            JasperReport report = JasperCompileManager.compileReport(reportStream);

            Map<String, Object> params = baseParams();
            if (null != extraParams) {
                params.putAll(extraParams);
            }

            JasperPrint print = JasperFillManager.fillReport(report, params, new JRBeanCollectionDataSource(rows));
            byte[] bytes = JasperExportManager.exportReportToPdf(print);

            assertNotNull(bytes, reportName + " produced no PDF");
            assertTrue(bytes.length > 1000, reportName + " PDF is unexpectedly small: " + bytes.length);
            return bytes;
        }
    }

    private static FactureDTO facture(Long id, String codeBF, LocalDate dateBF, LocalDate dateReglement) {
        FactureDTO dto = new FactureDTO();
        dto.setId(id);
        dto.setNumFacture(1);
        dto.setCodeBF(codeBF);
        dto.setNumeroLibil("L-1");
        dto.setDateBF(dateBF);
        dto.setDateReglement(dateReglement);
        dto.setDateReglement2(dateReglement.plusDays(5));
        dto.setDateReglement3(dateReglement.plusDays(10));
        dto.setDateReglement4(dateReglement.plusDays(15));
        dto.setTypeReglment(1);
        dto.setTypeReglment2(2);
        dto.setTypeReglment3(3);
        dto.setTypeReglment4(4);
        dto.setTypePaiement("Espece");
        dto.setMantantBF(1200.0);
        dto.setMantantBFBenefice(200.0);
        dto.setMantantBFHT(1000.0);
        dto.setTva7Total(50.0);
        dto.setTva20Total(100.0);
        dto.setTva7(50.0);
        dto.setTva20(100.0);
        dto.setMntHT2O(500.0);
        dto.setMntHT7(500.0);
        dto.setNumCheque("CHQ-1");
        dto.setNumCheque2("CHQ-2");
        dto.setNumCheque3("CHQ-3");
        dto.setNumCheque4("CHQ-4");
        dto.setNumRemise("REM-1");
        dto.setNumRemise2("REM-2");
        dto.setNumRemise3("REM-3");
        dto.setNumRemise4("REM-4");
        dto.setMntReglement(600.0);
        dto.setMntReglement2(300.0);
        dto.setMntReglement3(200.0);
        dto.setMntReglement4(100.0);
        dto.setType(1);
        dto.setCalculer(true);
        dto.setTypeTVA("20%");
        dto.setDateReglementIn(true);
        dto.setDateReglement2In(true);
        dto.setDateReglement3In(true);
        dto.setDateReglement4In(true);
        dto.setDateReglementOne(dateReglement);
        dto.setDateReglementOneLettre("01/09/26");
        dto.setMntHT20One(500.0);
        dto.setTva20One(100.0);
        dto.setMantantBFOne(600.0);
        dto.setNumChequeOne("CHQ-1");
        dto.setNumRemiseOne("REM-1");
        dto.setTypeReglementOne("Espece");
        dto.setMntReglementOne("600.00");
        dto.setNumRemiseTmp("REM-1");
        dto.setTotalNumRemise(600.0);
        dto.setRepertoireDesignation("Client Test");
        dto.setRepertoireIce("ICE123");
        return dto;
    }

    private static List<FactureDTO> twoFactures() {
        List<FactureDTO> list = new ArrayList<>();
        list.add(facture(1L, "FC-26-0001", LocalDate.of(2026, 9, 15), LocalDate.of(2026, 9, 20)));
        list.add(facture(2L, "FC-26-0002", LocalDate.of(2026, 9, 25), LocalDate.of(2026, 10, 1)));
        return list;
    }

    private static DetFactureDTO det(String designation, int qte, double prixVente, double montantProduit,
                                     double beneficeDH, double montantProduitHT, double tva7, double tva20) {
        DetFactureDTO dto = new DetFactureDTO();
        dto.setId(100L);
        dto.setQteFacturer(qte);
        dto.setPrixVente(prixVente);
        dto.setMontantProduit(montantProduit);
        dto.setBeneficeDH(beneficeDH);
        dto.setBenepourcentage(12.5);
        dto.setTva7(tva7);
        dto.setTva20(tva20);
        dto.setMontantProduitHT(montantProduitHT);
        dto.setDirectFacture(1);
        dto.setAvecRemise(false);
        dto.setRemiseFacture(0.0);
        dto.setStockDesignation(designation);
        dto.setStockBenifice(12.5);
        dto.setStockPvttc(prixVente);
        dto.setStockTva(tva20);
        dto.setFactureCodeBF("FC-26-0001");
        dto.setFactureDateBF(LocalDate.of(2026, 9, 15));
        dto.setFactureDateReglement(LocalDate.of(2026, 9, 20));
        dto.setFactureRepertoireDesignation("Client Test");
        dto.setFactureRepertoireTel1("0600000000");
        dto.setFactureRepertoireAdresse("Adresse Test");
        dto.setFactureRepertoireIce("ICE123");
        dto.setFactureTypePaiement("Espece");
        dto.setFactureMantantBFHT(1000.0);
        dto.setFactureTva7Total(tva7);
        dto.setFactureTva20Total(tva20);
        dto.setFactureFacturer100(false);
        return dto;
    }

    private static Stock stock(Long id, String designation) {
        Stock stock = new Stock();
        stock.setId(id);
        stock.setDesignation(designation);
        return stock;
    }

    private static Map<String, Object> bilanParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("Espece", "600.00");
        params.put("Cheque", "300.00");
        params.put("Virement", "100.00");
        params.put("Traite", "200.00");
        params.put("yearBefor", "2025");
        params.put("yearAfter", "2027");
        params.put("TvaAfter", "100.00");
        params.put("HtAfter", "1000.00");
        params.put("TtcAfter", "1100.00");
        params.put("TvaBefor", "100.00");
        params.put("HtBefor", "1000.00");
        params.put("TtcBefor", "1100.00");
        params.put("TvaNow", "100.00");
        params.put("HtNow", "1000.00");
        params.put("TtcNow", "1100.00");
        params.put("tva7DateFacture", "50.00");
        params.put("tva7DateRegl", "50.00");
        params.put("tva20DateFacture", "100.00");
        params.put("tva20DateRegl", "100.00");
        params.put("ht7DateFacture", "500.00");
        params.put("ht7DateRegl", "500.00");
        params.put("ht20DateFacture", "500.00");
        params.put("ht20DateRegl", "500.00");
        return params;
    }

    @Test
    void bonFactrFills() throws Exception {
        Map<String, Object> extra = new HashMap<>();
        extra.put("nameParamex", "Kabir");
        extra.put("adresseParamex", "Casablanca");
        extra.put("inforParamex", "RC 123 - PATENTE 456");
        extra.put("iceParamex", "ICE 123");
        extra.put("gsmTel", "TEL : 0600000000");
        extra.put("MntTotalLettre", "mille deux cents dirhams");
        extra.put("sommeHT20", "1000.00");
        extra.put("sommeHT7", "500.00");

        List<DetFactureDTO> rows = new ArrayList<>();
        rows.add(det("Produit A", 4, 212.5, 850.0, 120.0, 1000.0, 0.0, 100.0));
        rows.add(det("Produit B", 2, 100.0, 200.0, 40.0, 400.0, 50.0, 0.0));
        fill("BonFactr", rows, extra);
    }

    @Test
    void etatClienLibel2Fills() throws Exception {
        fill("etatClienLibel2", twoFactures(), null);
    }

    @Test
    void etatClientTvaDetailleFills() throws Exception {
        fill("etatClientTvaDetaille", twoFactures(), null);
    }

    @Test
    void etatFactureBilanFills() throws Exception {
        fill("etatFactureBilan", twoFactures(), bilanParams());
    }

    @Test
    void listeVenteAchatCleint2Fills() throws Exception {
        fill("listeVenteAchatCleint2", twoFactures(), null);
    }

    @Test
    void listeProduitFactureFills() throws Exception {
        List<DetFactureDTO> rows = new ArrayList<>();
        rows.add(det("Produit A", 4, 212.5, 850.0, 120.0, 1000.0, 0.0, 100.0));
        rows.add(det("Produit B", 5, 150.0, 750.0, 90.0, 600.0, 50.0, 0.0));
        fill("listeProduitFacture", rows, null);
    }

    @Test
    void listeProduitFactureTousFills() throws Exception {
        List<StockLivraisonSumPrintDTO> rows = new ArrayList<>();
        rows.add(new StockLivraisonSumPrintDTO(stock(7L, "Produit A"), 12, 3400.0, 900.0, 0));
        rows.add(new StockLivraisonSumPrintDTO(stock(8L, "Produit B"), 5, 750.0, 150.0, 0));
        rows.add(new StockLivraisonSumPrintDTO(stock(9L, "Produit C"), 3, 0.0, 0.0, 1));
        fill("listeProduitFactureTous", rows, null);
    }
}
