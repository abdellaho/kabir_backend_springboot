package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.DetFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetFactureRepository extends JpaRepository<DetFacture, Long>, JpaSpecificationExecutor<DetFacture> {
    @Query("SELECT df FROM DetFacture df WHERE df.facture.id = :factureId")
    List<DetFacture> findByFactureId(@Param("factureId") Long id);

    @Query("SELECT sum(d.montantProduitHT) from DetFacture d WHERE d.facture.id = :factureId AND d.tva20 > 0 AND d.tva7 = 0.0")
    Double sumTva20(@Param("factureId") Long factureId);

    @Query("SELECT sum(d.montantProduitHT) from DetFacture d WHERE d.facture.id = :factureId AND d.tva7 > 0 AND d.tva20 = 0.0")
    Double sumTva7(@Param("factureId") Long factureId);
}