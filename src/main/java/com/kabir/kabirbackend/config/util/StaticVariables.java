package com.kabir.kabirbackend.config.util;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class StaticVariables {
    public static final ResourceBundle bundleFR = ResourceBundle.getBundle("i18n/ApplicationResources", Locale.of("fr"));
    public static final DateTimeFormatter dateFormatDayFirst = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static double convertDouble(double montant) {
        DecimalFormat format = new DecimalFormat("#.00");
        String tt = format.format(montant);
        String mnt = tt.replace(",", ".");
        montant = Double.parseDouble(mnt);
        return montant;
    }
}
