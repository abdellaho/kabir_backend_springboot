package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.DetailBonSortie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailBonSortieRepository extends JpaRepository<DetailBonSortie, Long>, JpaSpecificationExecutor<DetailBonSortie> {

    @Query("SELECT d FROM DetailBonSortie d WHERE d.bonSortie.id = :id")
    List<DetailBonSortie> findAllByBonSortieId(@Param("id") Long id);
}