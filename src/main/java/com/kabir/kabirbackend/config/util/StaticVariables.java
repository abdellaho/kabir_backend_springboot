package com.kabir.kabirbackend.config.util;

import com.kabir.kabirbackend.dto.EtablissementDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class StaticVariables {
    public static final ResourceBundle bundleFR = ResourceBundle.getBundle("i18n/ApplicationResources", Locale.of("fr"));
    public static final DateTimeFormatter dateFormatDayFirst = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter sdfDDMMYY = DateTimeFormatter.ofPattern("dd/MM/yy");
    public static final DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

    public static double convertDouble(double montant) {
        DecimalFormat format = new DecimalFormat("#.00");
        String tt = format.format(montant);
        String mnt = tt.replace(",", ".");
        montant = Double.parseDouble(mnt);
        return montant;
    }

    public static Map<String, Object> getInfoEtablissement(Map<String, Object> parameters, List<EtablissementDTO> listEtablissement, boolean avecICE) {
        StringBuilder nameParamex = new StringBuilder();
        StringBuilder adresseParamex = new StringBuilder();
        StringBuilder inforParamex = new StringBuilder();
        StringBuilder iceParamex = new StringBuilder();
        StringBuilder gsmTel = new StringBuilder();

        if(CollectionUtils.isNotEmpty(listEtablissement)) {
            EtablissementDTO etablissement = listEtablissement.getFirst();

            nameParamex.append(etablissement.getNom());
            adresseParamex.append(etablissement.getAdresse());

            inforParamex
                    .append(StringUtils.isNotBlank(etablissement.getRaisonSocial()) ? " R.C : " : "")
                    .append(StringUtils.isNotBlank(etablissement.getRaisonSocial()) ? etablissement.getRaisonSocial() : "")
                    .append(StringUtils.isNotBlank(etablissement.getPatente()) ? " - PATENTE : " : "")
                    .append(StringUtils.isNotBlank(etablissement.getPatente()) ? etablissement.getPatente() : "")
                    .append(StringUtils.isNotBlank(etablissement.getIfe()) ? " - I.F : " : "")
                    .append(StringUtils.isNotBlank(etablissement.getIfe()) ? etablissement.getIfe() : "");

            iceParamex
                    .append(StringUtils.isNotBlank(etablissement.getIce()) ? "ICE : " : "")
                    .append(StringUtils.isNotBlank(etablissement.getIce()) ? etablissement.getIce() : "");
            gsmTel
                    .append(StringUtils.isNotBlank(etablissement.getTel1()) ? "TEL : " : "")
                    .append(StringUtils.isNotBlank(etablissement.getTel1()) ? etablissement.getTel1() : "")
                    .append((StringUtils.isNotBlank(etablissement.getGsm()) && StringUtils.isNotBlank(etablissement.getGsm())) ? " / " : "")
                    .append(StringUtils.isNotBlank(etablissement.getGsm()) ? " GSM : " : "")
                    .append(StringUtils.isNotBlank(etablissement.getGsm()) ? etablissement.getGsm() : "");

            if(avecICE) {
                inforParamex
                        .append(!iceParamex.isEmpty() ? " " : "")
                        .append(iceParamex);
            } else {
                inforParamex
                        .append(StringUtils.isNotBlank(etablissement.getCnss()) ? " -  CNSS : " : "")
                        .append(StringUtils.isNotBlank(etablissement.getCnss()) ? etablissement.getCnss() : "")
                        .append(" - Capitale : ")
                        .append(etablissement.getCapitale())
                        .append(" DH");
            }
        }

        parameters.put("nameParamex", nameParamex.toString());
        parameters.put("adresseParamex", adresseParamex.toString());
        parameters.put("inforParamex", inforParamex.toString());
        parameters.put("iceParamex", iceParamex.toString());
        parameters.put("gsmTel", gsmTel.toString());

        return parameters;
    }
}
