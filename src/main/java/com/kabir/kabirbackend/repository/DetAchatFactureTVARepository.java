package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.DetAchatFactureTVA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetAchatFactureTVARepository extends JpaRepository<DetAchatFactureTVA, Long>, JpaSpecificationExecutor<DetAchatFactureTVA> {

    @Query("SELECT d FROM DetAchatFactureTVA d WHERE d.achatFacture.id = :achatFactureId")
    List<DetAchatFactureTVA> findAllByAchatFactureId(@Param("achatFactureId") Long achatFactureId);

}