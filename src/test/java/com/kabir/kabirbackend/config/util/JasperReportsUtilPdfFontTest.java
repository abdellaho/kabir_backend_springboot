package com.kabir.kabirbackend.config.util;

import com.kabir.kabirbackend.config.enums.ReportTypeEnum;
import com.kabir.kabirbackend.dto.BonSortiePrintDTO;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Plain JUnit check that the default PDF font style applied by
 * {@link JasperReportsUtil} reaches the Arabic {@code messageBonSortie} text in
 * {@code BonSortie.jrxml} and embeds KacstLetter in the exported PDF.
 * No Spring context and no database are involved.
 */
class JasperReportsUtilPdfFontTest {

    private Locale originalLocale;

    @BeforeEach
    void useFrenchLocale() {
        originalLocale = Locale.getDefault();
        Locale.setDefault(Locale.FRENCH);
    }

    @AfterEach
    void restoreLocale() {
        Locale.setDefault(originalLocale);
    }

    private static JasperReportsUtil util() throws Exception {
        JasperReportsUtil util = new JasperReportsUtil();
        Field resourceLoader = JasperReportsUtil.class.getDeclaredField("resourceLoader");
        resourceLoader.setAccessible(true);
        resourceLoader.set(util, new DefaultResourceLoader());
        return util;
    }

    private static Date date(int day) {
        return Date.from(LocalDate.of(2026, 11, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private static List<BonSortiePrintDTO> rows() {
        return List.of(new BonSortiePrintDTO(
                "Commercial Test", date(5), "S0001", 2, 12.5, 1.0, "Produit A", false, 12.5, 11.0));
    }

    private static Map<String, Object> parameters() {
        Map<String, Object> params = new HashMap<>();
        params.put("nameParamex", "Kabir");
        params.put("adresseParamex", "Casablanca");
        params.put("iceParamex", "ICE : 123");
        params.put("gsmTel", "TEL : 0600000000");
        params.put("fichier", StaticVariables.bundleFR.getBaseBundleName());
        params.put("dateDb", StaticVariables.bundleFR.getString("du") + " : 01-11-2026");
        params.put("dateFn", StaticVariables.bundleFR.getString("au") + " : 21-11-2026");
        params.put("repertoire", "Commercial Test");
        params.put("produit", "Produit Test");
        return params;
    }

    @Test
    void defaultPdfFontAppliesToArabicTextAndIsEmbeddedInPdf() throws Exception {
        JasperReportsUtil util = util();
        String arabicMessage = StaticVariables.bundleFR.getString("messageBonSortie");

        JasperPrint jasperPrint;
        try (InputStream reportStream = getClass().getResourceAsStream("/reports/fr/BonSortie.jrxml")) {
            assertNotNull(reportStream, "BonSortie.jrxml not found on the test classpath");
            JasperReport report = JasperCompileManager.compileReport(reportStream);
            jasperPrint = JasperFillManager.fillReport(report, parameters(), new JRBeanCollectionDataSource(rows()));
        }

        util.applyDefaultPdfFont(jasperPrint);

        JRPrintText arabicText = null;
        for (JRPrintPage page : jasperPrint.getPages()) {
            for (Object element : page.getElements()) {
                if (element instanceof JRPrintText text && arabicMessage.equals(text.getFullText())) {
                    arabicText = text;
                }
            }
        }
        assertNotNull(arabicText, "Arabic messageBonSortie text not found in the filled report");
        assertTrue(arabicText.getPdfFontName().endsWith("KacstLetter.ttf"),
                "Unexpected PDF font: " + arabicText.getPdfFontName());
        assertEquals("Identity-H", arabicText.getPdfEncoding());
        assertTrue(arabicText.isPdfEmbedded());

        // Duplicate-safe: a second application must not throw or add another style.
        util.applyDefaultPdfFont(jasperPrint);
    }

    @Test
    void exportedPdfContainsEmbeddedFont() throws Exception {
        byte[] pdf = util().jasperReportInBytes(
                rows(), parameters(), "BonSortie", ReportTypeEnum.PDF, null);

        assertNotNull(pdf);
        assertTrue(pdf.length > 0, "Exported PDF is empty");
        String pdfAsString = new String(pdf, StandardCharsets.ISO_8859_1);
        assertTrue(pdfAsString.contains("/FontFile2"), "Exported PDF has no embedded TrueType font");
    }
}
