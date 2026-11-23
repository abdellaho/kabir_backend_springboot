package com.kabir.kabirbackend.reports;

import com.kabir.kabirbackend.config.util.StaticVariables;
import com.kabir.kabirbackend.dto.BonSortieDetailPrintDTO;
import com.kabir.kabirbackend.dto.BonSortiePrintDTO;
import com.kabir.kabirbackend.dto.BonSortieSummaryPrintDTO;
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
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Fills the four jrxml templates used by the bon de sortie print flows with
 * in-memory datasource rows (no database, no Spring context): the row print
 * ({@code BonSortie}) and the three "Consulter" etats ({@code etatBonSortie},
 * {@code etatBonSortieDetail}, {@code etatBonSortieDetailAll}).
 */
class BonSortiePrintReportsTest {

    private static Map<String, Object> baseParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("fichier", StaticVariables.bundleFR.getBaseBundleName());
        params.put("dateDb", StaticVariables.bundleFR.getString("du") + " : 01-11-2026");
        params.put("dateFn", StaticVariables.bundleFR.getString("au") + " : 21-11-2026");
        params.put("repertoire", "Commercial Test");
        params.put("produit", "Produit Test");
        params.put("nameParamex", "Kabir");
        params.put("adresseParamex", "Casablanca");
        params.put("iceParamex", "ICE : 123");
        params.put("gsmTel", "TEL : 0600000000");
        return params;
    }

    private static Date date(int day) {
        return Date.from(LocalDate.of(2026, 11, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private static void fill(String reportName, List<?> rows) throws Exception {
        fill(reportName, rows, Map.of());
    }

    private static void fill(String reportName, List<?> rows, Map<String, Object> extraParams) throws Exception {
        try (InputStream reportStream = new ClassPathResource("reports/fr/" + reportName + ".jrxml").getInputStream()) {
            JasperReport report = JasperCompileManager.compileReport(reportStream);

            Map<String, Object> params = baseParams();
            params.putAll(extraParams);

            JasperPrint print = JasperFillManager.fillReport(report, params, new JRBeanCollectionDataSource(rows));
            byte[] bytes = JasperExportManager.exportReportToPdf(print);

            assertNotNull(bytes, reportName + " produced no PDF");
            assertTrue(bytes.length > 1000, reportName + " PDF is unexpectedly small: " + bytes.length);
        }
    }

    @Test
    void bonSortieRowPrintFills() throws Exception {
        List<BonSortiePrintDTO> rows = List.of(
                new BonSortiePrintDTO("Commercial Test", date(5), "S0001", 2, 12.5, 1.0, "Produit A", false, 12.5, 11.0),
                new BonSortiePrintDTO("Commercial Test", date(7), "S0001", 3, 9.0, 0.5, "Produit B", false, 9.0, 8.0)
        );

        fill("BonSortie", rows, Map.of("produit", ""));
    }

    @Test
    void etatBonSortieFills() throws Exception {
        List<BonSortieSummaryPrintDTO> rows = List.of(
                new BonSortieSummaryPrintDTO("Produit A", 12.5, 3L, 2L),
                new BonSortieSummaryPrintDTO("Produit B", 9.0, 5L, 0L)
        );

        fill("etatBonSortie", rows, Map.of("produit", ""));
    }

    @Test
    void etatBonSortieDetailFills() throws Exception {
        List<BonSortieDetailPrintDTO> rows = List.of(
                new BonSortieDetailPrintDTO(0, date(5), "S0001", 2, "", ""),
                new BonSortieDetailPrintDTO(1, date(6), "BL001", 1, "Client A", "")
        );

        fill("etatBonSortieDetail", rows);
    }

    @Test
    void etatBonSortieDetailAllFills() throws Exception {
        // Product-first order, as produced by the service for the all-products mode.
        List<BonSortieDetailPrintDTO> rows = List.of(
                new BonSortieDetailPrintDTO(0, date(5), "S0001", 2, "", "Produit A"),
                new BonSortieDetailPrintDTO(1, date(6), "BL001", 1, "Client A", "Produit A"),
                new BonSortieDetailPrintDTO(0, date(7), "S0002", 4, "", "Produit B")
        );

        fill("etatBonSortieDetailAll", rows);
    }
}
