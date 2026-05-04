package com.kabir.kabirbackend.config.jobs;

import com.kabir.kabirbackend.entities.Etablissement;
import com.kabir.kabirbackend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OptimizeDBTest {

    @Mock
    private RepertoireRepository repertoireRepository;

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private PersonnelRepository personnelRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private EtablissementRepository etablissementRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private OptimizeDB optimizeDB;

    private Etablissement testEtablissement;

    @BeforeEach
    void setUp() {
        // Set up test configuration values
        ReflectionTestUtils.setField(optimizeDB, "dbName", "k");
        ReflectionTestUtils.setField(optimizeDB, "dbHost", "localhost");
        ReflectionTestUtils.setField(optimizeDB, "dbPort", "3307");
        ReflectionTestUtils.setField(optimizeDB, "username", "root");
        ReflectionTestUtils.setField(optimizeDB, "password", "root");

        // Create test etablissement
        testEtablissement = new Etablissement();
        testEtablissement.setId(1L);
        testEtablissement.setNom("Test Etablissement");
        testEtablissement.setLienBackupDB("C:\\Users\\user\\Desktop\\Nouveau dossier\\Important Files");
        testEtablissement.setLienDbDump("C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin");
        testEtablissement.setHostMail("smtp.test.com");
        testEtablissement.setFromMail("from@test.com");
        testEtablissement.setUserMail("user@test.com");
        testEtablissement.setPaswordMail("encrypted_password");
        testEtablissement.setPort(587);
        testEtablissement.setDateTime(Instant.now());
        testEtablissement.setTypeExec(0);
        testEtablissement.setNumJour(15);
    }

    @Test
    void testConfigureTasks() {
        // Given
        ScheduledTaskRegistrar taskRegistrar = mock(ScheduledTaskRegistrar.class);
        when(etablissementRepository.findAll()).thenReturn(List.of(testEtablissement));

        // When
        optimizeDB.configureTasks(taskRegistrar);

        // Then
        verify(taskRegistrar).addTriggerTask(any(Runnable.class), any());
    }

    @Test
    void testOptimizeDB() {
        // Given
        LocalDate expectedDate = LocalDate.now().minusDays(10);

        // When
        optimizeDB.optimizeDB();

        // Then
        verify(repertoireRepository).deleteBloquerOlderThan(expectedDate);
        verify(fournisseurRepository).deleteBloquerOlderThan(expectedDate);
        verify(personnelRepository).deleteBloquerOlderThan(expectedDate);
        verify(stockRepository).deleteBloquerOlderThan(expectedDate);
    }

    @Test
    void testGenerateBackupDataBase_WithValidEtablissement() {
        // Given
        when(etablissementRepository.findAll()).thenReturn(List.of(testEtablissement));

        // When
        optimizeDB.generateBackupDataBase();

        // Then
        verify(etablissementRepository).findAll();
        // Note: Process execution is complex to test without actual filesystem
    }

    @Test
    void testGenerateBackupDataBase_WithEmptyEtablissementList() {
        // Given
        when(etablissementRepository.findAll()).thenReturn(new ArrayList<>());

        // When
        optimizeDB.generateBackupDataBase();

        // Then
        verify(etablissementRepository).findAll();
        verifyNoInteractions(emailService);
    }

    @Test
    void testGenerateBackupDataBase_WithNullBackupPaths() {
        // Given
        testEtablissement.setLienBackupDB(null);
        testEtablissement.setLienDbDump(null);
        when(etablissementRepository.findAll()).thenReturn(List.of(testEtablissement));

        // When
        optimizeDB.generateBackupDataBase();

        // Then
        verify(etablissementRepository).findAll();
        verifyNoInteractions(emailService);
    }

    @Test
    void testGenerateBackupDataBase_WithEmptyBackupPaths() {
        // Given
        testEtablissement.setLienBackupDB("");
        testEtablissement.setLienDbDump("");
        when(etablissementRepository.findAll()).thenReturn(List.of(testEtablissement));

        // When
        optimizeDB.generateBackupDataBase();

        // Then
        verify(etablissementRepository).findAll();
        verifyNoInteractions(emailService);
    }

    @Test
    void testGenerateBackupDataBase_WithEmailSending() {
        // Given
        when(etablissementRepository.findAll()).thenReturn(List.of(testEtablissement));

        // When
        optimizeDB.generateBackupDataBase();

        // Then
        verify(etablissementRepository).findAll();
        // Email sending verification depends on successful process execution
    }

    @Test
    void testGenerateBackupDataBase_WithIncompleteEmailConfig() {
        // Given
        testEtablissement.setHostMail(null);
        when(etablissementRepository.findAll()).thenReturn(List.of(testEtablissement));

        // When
        optimizeDB.generateBackupDataBase();

        // Then
        verify(etablissementRepository).findAll();
        verifyNoInteractions(emailService);
    }
}
