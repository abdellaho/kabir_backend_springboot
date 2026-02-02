package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Prime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrimeRepository extends JpaRepository<Prime, Long>, JpaSpecificationExecutor<Prime> {

    @Query("SELECT p FROM Prime p WHERE p.montant = (SELECT MAX(p2.montant) FROM Prime p2 WHERE p2.montant <= :montant)")
    List<Prime> findByMontantGreaterThanEqual(@Param("montant") Double montant);
}