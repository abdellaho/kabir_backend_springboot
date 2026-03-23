package com.kabir.kabirbackend.config.jobs;


import com.kabir.kabirbackend.repository.FournisseurRepository;
import com.kabir.kabirbackend.repository.PersonnelRepository;
import com.kabir.kabirbackend.repository.RepertoireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;

@Configuration
@EnableScheduling
public class OptimizeDB {

    private final Logger logger = LoggerFactory.getLogger(OptimizeDB.class);

    private final RepertoireRepository repertoireRepository;
    private final FournisseurRepository fournisseurRepository;
    private final PersonnelRepository personnelRepository;

    public OptimizeDB(
            RepertoireRepository repertoireRepository,
            FournisseurRepository fournisseurRepository,
            PersonnelRepository personnelRepository
    ) {
        this.repertoireRepository = repertoireRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.personnelRepository = personnelRepository;
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
    }
}
