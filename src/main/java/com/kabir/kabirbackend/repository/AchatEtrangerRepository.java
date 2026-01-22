package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.AchatEtranger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AchatEtrangerRepository extends JpaRepository<AchatEtranger, Long>, JpaSpecificationExecutor<AchatEtranger> {

    @Query("SELECT MAX(a.numFacture) FROM AchatEtranger a WHERE YEAR(a.dateFacture) = :year")
    Optional<Integer> findMaxNumAchatEtrangerInYearDateFacture(@Param("year") int year);

    @Query("SELECT MAX(a.numFacture) FROM AchatEtranger a")
    Optional<Integer> findMaxNumAchatEtrangerInYearDateFacture();
}