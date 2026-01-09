package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.AchatFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AchatFactureRepository extends JpaRepository<AchatFacture, Long>, JpaSpecificationExecutor<AchatFacture> {

    @Query("SELECT MAX(l.numAchat) FROM AchatFacture l WHERE YEAR(l.dateAF) = :year")
    Optional<Integer> findMaxNumAchatFactureInYearDateAF(@Param("year") int year);

}