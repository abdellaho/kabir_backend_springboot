package com.kabir.kabirbackend.config.util;

import com.kabir.kabirbackend.config.enums.ReportTypeEnum;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;


@Component
public class JasperReportsUtil {

    public byte[] exportJasperReportBytes(JasperPrint jasperPrint, ReportTypeEnum reportType) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        switch (reportType) {
            case CSV:
                // Export to CSV
                JRCsvExporter csvExporter = new JRCsvExporter();
                csvExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                csvExporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream));
                csvExporter.exportReport();
                break;
            case XLSX:
                // Export to XLSX
                JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                xlsxExporter.exportReport();
                break;
            case HTML:
                // Export to HTML
                HtmlExporter htmlExporter = new HtmlExporter();
                htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(outputStream));
                htmlExporter.exportReport();
                break;
            case XML:
                // Export to XML
                JRXmlExporter xmlExporter = new JRXmlExporter();
                xmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                xmlExporter.setExporterOutput(new SimpleXmlExporterOutput(outputStream));
                xmlExporter.exportReport();
                break;
            case DOC:
                // Export to DOCX (RTF format)
                JRRtfExporter docxExporter = new JRRtfExporter();
                docxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                docxExporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream));
                docxExporter.exportReport();
                break;
            default:
                // Export to PDF by default
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                break;
        }
        return outputStream.toByteArray();
    }

    public static byte[] anullerImpr(String message) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();
            Font font = new Font(Font.HELVETICA, 16, Font.BOLD);
            Paragraph p = new Paragraph(message, font);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            document.close();

            return baos.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] jasperReportInBytes(List<?> dataSource, Map<String, Object> parameters, String fileName, ReportTypeEnum reportTypeEnum , String logo) throws Exception {
        if(StringUtils.isNotEmpty(logo)) {
            InputStream logoStream = getClass().getResourceAsStream(logo);
            parameters.put("lienimage" , logoStream);
        }


        String template = MessageFormat.format("{0}{1}{2}{3}", Constant.FOLDER_PATH_REPORT_DYNAMIC_FR, Constant.SLASH, fileName, Constant.EXTENSION_JRXML);
        //2.Create DataSource
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(dataSource);
        //3.Compile .jrmxl template, stored in JasperReport object
        String path = ResourceUtils.getFile(MessageFormat.format("classpath:{0}", template)).getAbsolutePath();
        JasperReport jasperReport = JasperCompileManager.compileReport(path);
        //4.Fill Report - by passing complied .jrxml object, paramters, datasource
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanCollectionDataSource);
        //5.Export Report - by using JasperExportManager
        return exportJasperReportBytes(jasperPrint, reportTypeEnum);
    }

}

