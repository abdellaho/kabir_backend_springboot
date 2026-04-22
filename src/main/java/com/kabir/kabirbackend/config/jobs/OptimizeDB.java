package com.kabir.kabirbackend.config.jobs;


import com.kabir.kabirbackend.entities.Etablissement;
import com.kabir.kabirbackend.repository.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class OptimizeDB implements SchedulingConfigurer {

    private final Logger logger = LoggerFactory.getLogger(OptimizeDB.class);

    @Value("${backup.db.name}")
    private String dbName;

    @Value("${backup.db.host}")
    private String dbHost;

    @Value("${backup.db.port}")
    private String dbPort;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private final RepertoireRepository repertoireRepository;
    private final FournisseurRepository fournisseurRepository;
    private final PersonnelRepository personnelRepository;
    private final StockRepository stockRepository;
    private final EtablissementRepository etablissementRepository;
    private final EmailService emailService;

    public OptimizeDB(
            RepertoireRepository repertoireRepository,
            FournisseurRepository fournisseurRepository,
            PersonnelRepository personnelRepository,
            StockRepository stockRepository,
            EtablissementRepository etablissementRepository,
            EmailService emailService
    ) {
        this.repertoireRepository = repertoireRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.personnelRepository = personnelRepository;
        this.stockRepository = stockRepository;
        this.etablissementRepository = etablissementRepository;
        this.emailService = emailService;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(this::generateBackupDataBase,
            triggerContext -> {
                String cron = cronConfig();
                logger.info("Next cron execution with expression: {}", cron);

                CronTrigger trigger = new CronTrigger(cron);
                return trigger.nextExecution(triggerContext);
            }
        );
    }

    @Scheduled(cron = "0 0 14 * * ?")
    public void optimizeDB() {
        LocalDate date = LocalDate.now().minusDays(10);
        logger.info("Optimizing database with data older than {}", date);
        logger.info("Deleting in repertoire table rows that have dateSuppression older than {}", date);
        this.repertoireRepository.deleteBloquerOlderThan(date);

        logger.info("Deleting in fournisseur table rows that have dateSuppression older than {}", date);
        this.fournisseurRepository.deleteBloquerOlderThan(date);

        logger.info("Deleting in personnel table rows that have dateSuppression older than {}", date);
        this.personnelRepository.deleteBloquerOlderThan(date);

        logger.info("Deleting in stock table rows that have dateSuppression older than {}", date);
        this.stockRepository.deleteBloquerOlderThan(date);
    }

    public void generateBackupDataBase() {
        List<Etablissement> list = etablissementRepository.findAll();
        if(CollectionUtils.isNotEmpty(list)) {
            Etablissement etablissement = list.getFirst();
            if(StringUtils.isNotEmpty(etablissement.getLienBackupDB())
                    && StringUtils.isNotEmpty(etablissement.getLienDbDump())
            ) {
                try {
                    ProcessBuilder processBuilder;

                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HHmmss");
                    Date date = new Date();
                    String filepath = "backup-" + dbName + "-(" + dateFormat.format(date) + ").sql";

                    String outputFile = etablissement.getLienBackupDB() + File.separator + filepath;
                    String mysqldumpPath = etablissement.getLienDbDump() + File.separator + "mysqldump";

                    // Build command as LIST (important, no string concatenation)
                    List<String> command = new ArrayList<>();
                    command.add(mysqldumpPath);
                    command.add("-h");
                    command.add(dbHost);
                    command.add("--port");
                    command.add(dbPort);
                    command.add("-u");
                    command.add(username);

                    if (StringUtils.isNotBlank(password)) {
                        command.add("--password=" + password);
                    }

                    command.add(dbName);

                    // Create process builder
                    processBuilder = new ProcessBuilder(command);

                    // Redirect output to file
                    File file = new File(outputFile);
                    processBuilder.redirectOutput(file);

                    // Optional: capture errors
                    processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);

                    // Start process
                    Process process = processBuilder.start();
                    int processComplete = process.waitFor();

                    if (processComplete == 0) {
                        try {
                            if(null != etablissement.getHostMail() && !etablissement.getHostMail().isEmpty()
                                    && null != etablissement.getFromMail() && !etablissement.getFromMail().isEmpty()
                                    && null != etablissement.getUserMail() && !etablissement.getUserMail().isEmpty()
                                    && null != etablissement.getPaswordMail() && !etablissement.getPaswordMail().isEmpty()
                                    && etablissement.getPort() > 0) {
                                String bodyText = "<meta http-equiv='Content-Type' content='text/html; charset= utf-8 '>"
                                        + " Mr vous avez recu une nouvelle sauvegarde de base de donnee";

                                emailService.sendEmail(etablissement, etablissement.getFromMail(), etablissement.getFromMail(), "Sauvegarde Base Donnee", bodyText, true, file);
                            }
                        } catch (Exception e) {
                            logger.error(String.valueOf(e), e.getCause());
                        }
                        logger.info("Opération effectue avec succès");
                    } else {
                        logger.info("Opération effectue avec succès");
                        System.out.println("Opération effectue avec succès");
                    }
                } catch (Exception e) {
                    logger.error(String.valueOf(e), e.getCause());
                }
            }
        }
    }

    private String cronConfig() {
        String cronTabExpression = "0 0 14 * * ?";//Seconds Minutes Hours DayOfMonth Month DayOfWeek Year

        List<Etablissement> list = etablissementRepository.findAll();
        if(CollectionUtils.isNotEmpty(list)) {
            Etablissement etablissement = list.getFirst();
            if(null != etablissement && null != etablissement.getId() && etablissement.getId() != 0) {
                if(null != etablissement.getDateTime()) {
                    LocalDateTime localDateTime = etablissement.getDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    if(etablissement.getTypeExec() == 0) {
                        cronTabExpression = "0 " + Integer.parseInt(localDateTime.getMinute() + "") +" "+ Integer.parseInt(localDateTime.getHour() + "") + " * * *";
                    }else if(etablissement.getTypeExec() == 1) {
                        String jours = "";
                        if(etablissement.isDimanche()) {
                            jours += "SUN";
                        }
                        if(etablissement.isLundi()) {
                            if(jours.isEmpty()) {
                                jours += "MON";
                            }else {
                                jours += ",MON";
                            }
                        }
                        if(etablissement.isMardi()) {
                            if(jours.isEmpty()) {
                                jours += "TUE";
                            }else {
                                jours += ",TUE";
                            }
                        }
                        if(etablissement.isMercredi()) {
                            if(jours.isEmpty()) {
                                jours += "WED";
                            }else {
                                jours += ",WED";
                            }
                        }
                        if(etablissement.isJeudi()) {
                            if(jours.isEmpty()) {
                                jours += "THU";
                            }else {
                                jours += ",THU";
                            }
                        }
                        if(etablissement.isVendredi()) {
                            if(jours.isEmpty()) {
                                jours += "FRI";
                            }else {
                                jours += ",FRI";
                            }
                        }
                        if(etablissement.isSamedi()) {
                            if(jours.isEmpty()) {
                                jours += "SAT ";
                            }else {
                                jours += ",SAT ";
                            }
                        }
                        cronTabExpression = "0 " + Integer.parseInt(localDateTime.getMinute() + "") + " " + Integer.parseInt(localDateTime.getHour() + "") + " * * " + jours;
                    }else if(etablissement.getTypeExec() == 2) {
                        cronTabExpression = "0 " + Integer.parseInt(localDateTime.getMinute() + "") + " " + Integer.parseInt(localDateTime.getHour() + "") + " " + etablissement.getNumJour() + " * *";
                    }
                }
            }
        }

        return cronTabExpression;
    }
}
