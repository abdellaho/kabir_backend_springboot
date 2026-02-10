package com.kabir.kabirbackend.config.util;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class StaticVariables {
    public static final ResourceBundle bundleFR = ResourceBundle.getBundle("i18n/ApplicationResources", Locale.of("fr"));
    public static final DateTimeFormatter dateFormatDayFirst = DateTimeFormatter.ofPattern("dd-MM-yyyy");
}
