package com.kabir.kabirbackend.config.util;


import java.io.File;
import java.text.MessageFormat;

public class Constant {

    public static final String SLASH = File.separator;
    public static final String FOLDER_PATH_REPORT_DYNAMIC_FR = MessageFormat.format("reports{0}fr", SLASH);;
    public static final String FOLDER_PATH_REPORT_DYNAMIC_AR = MessageFormat.format("reports{0}ar", SLASH);
    public static final String EXPORT_TYPE_PARAMETER_IS_MISSING = "Parameter: exportType is missing";
    public static final String EXTENSION_JRXML = ".jrxml";

}
