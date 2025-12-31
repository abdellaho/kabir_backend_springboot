package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long>, JpaSpecificationExecutor<Facture> {

    @Query("SELECT MAX(l.numFacture) FROM Facture l WHERE YEAR(l.dateBF) = :year")
    Optional<Integer> findMaxNumFactureInYearDateBL(@Param("year") int year);
}