package com.kabir.kabirbackend.reports;

import com.kabir.kabirbackend.config.util.StaticVariables;
import com.kabir.kabirbackend.dto.LivraisonClientPrintDTO;
import com.kabir.kabirbackend.dto.LivraisonProduitCommissionPrintDTO;
import com.kabir.kabirbackend.dto.LivraisonProduitPrintDTO;
import com.kabir.kabirbackend.dto.LivraisonVillePrintDTO;
import com.kabir.kabirbackend.dto.NumChequePrintDTO;
import com.kabir.kabirbackend.dto.StockImprPrintDTO;
import com.kabir.kabirbackend.dto.StockLivraisonSumPrintDTO;
import com.kabir.kabirbackend.entities.DetLivraison;
import com.kabir.kabirbackend.entities.Livraison;
import com.kabir.kabirbackend.entities.Personnel;
import com.kabir.kabirbackend.entities.Repertoire;
import com.kabir.kabirbackend.entities.Stock;
import com.kabir.kabirbackend.entities.Ville;
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
 * Fills the 6 reports behind the livraison "Consulter" print buttons with in-memory
 * data (no database, no Spring context) so that the jrxml adaptation is proven:
 * <ul>
 *     <li>the rewritten fieldDescription paths (repertoire / personnel) resolve,</li>
 *     <li>{@code java.time.LocalDate} dateLivr fields fill and format,</li>
 *     <li>$P{REPORT_RESOURCE_BUNDLE} labels resolve through the {@code fichier} param.</li>
 * </ul>
 */
class LivraisonPrintReportsTest {

    private static Map<String, Object> baseParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("fichier", StaticVariables.bundleFR.getBaseBundleName());
        params.put("titleText", "");
        params.put("dateDb", StaticVariables.bundleFR.getString("datedebut") + " : 01-09-2026");
        params.put("dateFn", StaticVariables.bundleFR.getString("dateFin") + " : 07-10-2026");
        params.put("commercialName", "Commercial Test");
        params.put("produit", "");
        params.put("valeur", "12.5");
        params.put("totalQteLivr", "");
        params.put("totalMontant", "");
        params.put("totalBenfice", "");
        return params;
    }

    private static byte[] fill(String reportName, List<?> rows) throws Exception {
        return fill(reportName, rows, Map.of());
    }

    private static byte[] fill(String reportName, List<?> rows, Map<String, Object> extraParams) throws Exception {
        try (InputStream reportStream = new ClassPathResource("reports/fr/" + reportName + ".jrxml").getInputStream()) {
            JasperReport report = JasperCompileManager.compileReport(reportStream);

            Map<String, Object> params = baseParams();
            params.putAll(extraParams);

            JasperPrint print = JasperFillManager.fillReport(report, params, new JRBeanCollectionDataSource(rows));
            byte[] bytes = JasperExportManager.exportReportToPdf(print);

            assertNotNull(bytes, reportName + " produced no PDF");
            assertTrue(bytes.length > 1000, reportName + " PDF is unexpectedly small: " + bytes.length);
            return bytes;
        }
    }

    private static Repertoire client(Long id, String designation) {
        Repertoire repertoire = new Repertoire();
        repertoire.setId(id);
        repertoire.setDesignation(designation);
        return repertoire;
    }

    private static Repertoire clientWithVille(Long id, String designation, Long villeId, String nomVille) {
        Repertoire repertoire = client(id, designation);
        Ville ville = new Ville();
        ville.setId(villeId);
        ville.setNomVille(nomVille);
        repertoire.setVille(ville);
        return repertoire;
    }

    private static Repertoire clientWithType(Long id, String designation, int typeRepertoire) {
        Repertoire repertoire = client(id, designation);
        repertoire.setTypeRepertoire(typeRepertoire);
        return repertoire;
    }

    private static Personnel commercial(Long id, String designation) {
        Personnel personnel = new Personnel();
        personnel.setId(id);
        personnel.setDesignation(designation);
        return personnel;
    }

    private static Stock stock(Long id, String designation, double pattc, double benifice) {
        Stock stock = new Stock();
        stock.setId(id);
        stock.setDesignation(designation);
        stock.setPattc(pattc);
        stock.setBenifice(benifice);
        return stock;
    }

    private static Livraison livraison(String codeBl, LocalDate dateBl, double montant, double benefice) {
        Livraison livraison = new Livraison();
        livraison.setId(10L);
        livraison.setCodeBl(codeBl);
        livraison.setDateBl(dateBl);
        livraison.setMantantBL(montant);
        livraison.setMantantBLBenefice(benefice);
        livraison.setRepertoire(client(1L, "Client Test"));
        livraison.setPersonnel(commercial(2L, "Commercial Test"));
        return livraison;
    }

    private static List<Livraison> twoLivraisons() {
        List<Livraison> list = new ArrayList<>();
        list.add(livraison("26-0001", LocalDate.of(2026, 9, 15), 1500.0, 300.0));
        list.add(livraison("26-0002", LocalDate.of(2026, 9, 20), 900.0, 120.0));
        return list;
    }

    @Test
    void listeJournaliereCleintFills() throws Exception {
        List<LivraisonClientPrintDTO> rows = new ArrayList<>();
        for (Livraison livraison : twoLivraisons()) {
            rows.add(new LivraisonClientPrintDTO(livraison, 420.0));
        }
        fill("listeJournaliereCleint", rows);
    }

    @Test
    void listeJournaliereCleintOneFills() throws Exception {
        fill("listeJournaliereCleintOne", twoLivraisons());
    }

    @Test
    void etatCommercialDetailFills() throws Exception {
        fill("etatCommercialDetail", twoLivraisons());
    }

    @Test
    void etatCommercialDetailProduitFills() throws Exception {
        List<LivraisonProduitPrintDTO> rows = new ArrayList<>();
        rows.add(new LivraisonProduitPrintDTO("Produit A", 12L, 3400.0, 0));
        rows.add(new LivraisonProduitPrintDTO("Produit B", 5L, 750.0, 0));
        rows.add(new LivraisonProduitPrintDTO("Produit C", 3L, 0.0, 1));
        fill("etatCommercialDetailProduit", rows);
    }

    @Test
    void etatCommercialDetailByTypeRepertoireFills() throws Exception {
        List<Livraison> rows = new ArrayList<>();
        Livraison revendeur = livraison("26-0002", LocalDate.of(2026, 9, 20), 900.0, 120.0);
        revendeur.setRepertoire(clientWithType(3L, "Revendeur Test", 2));
        Livraison pharmacie = livraison("26-0001", LocalDate.of(2026, 9, 15), 1500.0, 300.0);
        pharmacie.setRepertoire(clientWithType(1L, "Pharmacie Test", 0));
        rows.add(revendeur);
        rows.add(pharmacie);
        fill("etatCommercialDetailByTypeRepertoire", rows);
    }

    @Test
    void etatCommercialDetailProduitByCommissionFills() throws Exception {
        List<LivraisonProduitCommissionPrintDTO> rows = new ArrayList<>();
        rows.add(new LivraisonProduitCommissionPrintDTO("Produit A", 12L, 3400.0, 0, 283.33, 2.5, 85.0));
        rows.add(new LivraisonProduitCommissionPrintDTO("Produit B", 5L, 750.0, 0, 150.0, 1.0, 7.5));
        fill("etatCommercialDetailProduitByCommission", rows);
    }

    @Test
    void listeProduitLivrsFills() throws Exception {
        List<DetLivraison> rows = new ArrayList<>();
        for (Livraison livraison : twoLivraisons()) {
            DetLivraison detLivraison = new DetLivraison();
            detLivraison.setId(100L);
            detLivraison.setLivraison(livraison);
            detLivraison.setStock(stock(7L, "Produit A", 55.0, 12.5));
            detLivraison.setQteLivrer(4);
            detLivraison.setMontantProduit(850.0);
            detLivraison.setBeneficeDH(120.0);
            detLivraison.setPrixVente(212.5);
            rows.add(detLivraison);
        }
        fill("listeProduitLivrs", rows);
    }

    @Test
    void listeProduitLivrsTousFills() throws Exception {
        List<StockLivraisonSumPrintDTO> rows = new ArrayList<>();
        rows.add(new StockLivraisonSumPrintDTO(stock(7L, "Produit A", 55.0, 12.5), 12, 3400.0, 900.0, 0));
        rows.add(new StockLivraisonSumPrintDTO(stock(8L, "Produit B", 30.0, 8.0), 5, 750.0, 150.0, 0));
        rows.add(new StockLivraisonSumPrintDTO(stock(9L, "Produit C", 12.0, 0.0), 3, 0.0, 0.0, 1));
        fill("listeProduitLivrsTous", rows);
    }

    @Test
    void listeJournaliereCleintVilleFills() throws Exception {
        List<LivraisonVillePrintDTO> rows = new ArrayList<>();
        rows.add(new LivraisonVillePrintDTO(livraison("26-0001", LocalDate.of(2026, 9, 15), 1500.0, 300.0), 420.0, clientWithVille(1L, "Client Test", 5L, "Casablanca")));
        rows.add(new LivraisonVillePrintDTO(livraison("26-0002", LocalDate.of(2026, 9, 20), 900.0, 120.0), 420.0, clientWithVille(1L, "Client Test", 5L, "Casablanca")));
        fill("listeJournaliereCleintVille", rows);
    }

    @Test
    void listeEtatLivrRegNoRegFills() throws Exception {
        fill("listeEtatLivrRegNoReg", twoLivraisons());
    }

    @Test
    void listeJournaliereCommercialFills() throws Exception {
        fill("listeJournaliereCommercial", twoLivraisons());
    }

    @Test
    void etatLivraisonProduitsParClientFills() throws Exception {
        List<StockImprPrintDTO> rows = new ArrayList<>();
        StockImprPrintDTO first = new StockImprPrintDTO(7L, "Produit A", 12L);
        first.setDesignation2("Produit C");
        StockImprPrintDTO second = new StockImprPrintDTO(8L, "Produit B", 5L);
        second.setDesignation2("Produit D");
        rows.add(first);
        rows.add(second);

        Map<String, Object> extra = new HashMap<>();
        extra.put("dernierDate", "20-09-2026");
        extra.put("stockNonPresent", "");
        extra.put("clientName", "Client Test");
        fill("etatLivraisonProduitsParClient", rows, extra);
    }

    @Test
    void etatNumChequeFills() throws Exception {
        List<NumChequePrintDTO> rows = new ArrayList<>();
        rows.add(new NumChequePrintDTO(7L, "Produit A", 12L, 3400.0));
        rows.add(new NumChequePrintDTO(8L, "Produit B", 5L, 750.0));

        Map<String, Object> extra = new HashMap<>();
        extra.put("numCheque", "123456");
        fill("etatNumCheque", rows, extra);
    }
}
