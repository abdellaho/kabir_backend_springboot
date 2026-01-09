package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.DetAchatFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetAchatFactureRepository extends JpaRepository<DetAchatFacture, Long>, JpaSpecificationExecutor<DetAchatFacture> {

    @Query("SELECT d FROM DetAchatFacture d WHERE d.achatFacture.id = :achatFactureId")
    List<DetAchatFacture> findAllByAchatFactureId(@Param("achatFactureId") Long achatFactureId);
}