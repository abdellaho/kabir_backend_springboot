package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.AchatFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AchatFactureRepository extends JpaRepository<AchatFacture, Long>, JpaSpecificationExecutor<AchatFacture> {

    @Query("SELECT MAX(l.numAchat) FROM AchatFacture l WHERE YEAR(l.dateAF) = :year")
    Optional<Integer> findMaxNumAchatFactureInYearDateAF(@Param("year") int year);

    @Query("SELECT SUM(l.mantantTotHTVA) FROM AchatFacture l WHERE l.dateReglement BETWEEN :dateDebut AND :dateFin")
    Double getSumMantantTotHTVA(@Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT SUM(l.mantantTotTTC) FROM AchatFacture l WHERE l.dateReglement BETWEEN :dateDebut AND :dateFin AND (:typeReglement IS NULL OR l.typeReglment = :typeReglement) AND (:operateurId IS NULL OR l.operateur.id = :operateurId)")
    Double getSumMantantTotTTC(@Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin, @Param("typeReglement") Integer typeReglement, @Param("operateurId") Long operateurId);

    @Query("SELECT af FROM AchatFacture af WHERE af.id in (SELECT daf.achatFacture.id FROM DetAchatFacture daf where daf.achatFacture.dateAF BETWEEN :dateDebut AND :dateFin AND (:operateurId IS NULL OR daf.achatFacture.operateur.id = :operateurId))")
    List<AchatFacture> findAllByDates(@Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin, @Param("operateurId") Long operateurId);

}