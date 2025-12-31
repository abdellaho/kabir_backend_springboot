package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.BonSortie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BonSortieRepository extends JpaRepository<BonSortie, Long>, JpaSpecificationExecutor<BonSortie> {

    @Query("SELECT MAX(bs.numSortie) FROM BonSortie bs WHERE YEAR(bs.dateOperation) = :year")
    Optional<Integer> findMaxNumBonSortieInYearDateBL(int year);
}