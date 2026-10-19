package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.DetFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface DetFactureRepository extends JpaRepository<DetFacture, Long>, JpaSpecificationExecutor<DetFacture> {
    @Query("SELECT df FROM DetFacture df WHERE df.facture.id = :factureId")
    List<DetFacture> findByFactureId(@Param("factureId") Long id);

    @Query("SELECT df FROM DetFacture df WHERE df.facture.id IN :factureIds")
    List<DetFacture> findByFactureIdIn(@Param("factureIds") Collection<Long> factureIds);

    @Query("SELECT df FROM DetFacture df WHERE df.facture.id = :factureId ORDER BY df.stock.designation")
    List<DetFacture> findByFactureIdOrderByStockDesignation(@Param("factureId") Long id);

    @Query("SELECT sum(d.montantProduitHT) from DetFacture d WHERE d.facture.id = :factureId AND d.tva20 > 0 AND d.tva7 = 0.0")
    Double sumTva20(@Param("factureId") Long factureId);

    @Query("SELECT sum(d.montantProduitHT) from DetFacture d WHERE d.facture.id = :factureId AND d.tva7 > 0 AND d.tva20 = 0.0")
    Double sumTva7(@Param("factureId") Long factureId);

    /**
     * "Consulter Produit" (one stock): the matching rows of one product in the range,
     * ordered like the legacy query {@code order by facture.dateReglement desc}.
     * Legacy paid-row filter:
     * {@code montantProduit > 0 or (montantProduit=0 and directFacture=1) or
     * (montantProduit=0 and stock.avecRemise=1 and remiseFacture=100)}.
     * The new {@code Stock} has no {@code avecRemise}; the line-level
     * {@code DetFacture.avecRemise} is used instead.
     */
    @Query("""
        SELECT df FROM DetFacture df
        WHERE df.stock.id = :stockId
          AND df.facture.dateReglement BETWEEN :dateDebut AND :dateFin
          AND (:operateurId IS NULL OR df.facture.employeOperateur.id = :operateurId)
          AND (df.montantProduit > 0
               OR (df.montantProduit = 0 AND df.directFacture = 1)
               OR (df.montantProduit = 0 AND df.avecRemise = true AND df.remiseFacture = 100))
        ORDER BY df.facture.dateReglement DESC
    """)
    List<DetFacture> findProduitByStock(@Param("stockId") Long stockId,
                                        @Param("dateDebut") LocalDate dateDebut,
                                        @Param("dateFin") LocalDate dateFin,
                                        @Param("operateurId") Long operateurId);

    /**
     * "Consulter Produit" (all stocks): every matching row of the range, aggregated per
     * stock in memory.
     */
    @Query("""
        SELECT df FROM DetFacture df
        WHERE df.facture.dateReglement BETWEEN :dateDebut AND :dateFin
          AND (:operateurId IS NULL OR df.facture.employeOperateur.id = :operateurId)
          AND (df.montantProduit > 0
               OR (df.montantProduit = 0 AND df.directFacture = 1)
               OR (df.montantProduit = 0 AND df.avecRemise = true AND df.remiseFacture = 100))
    """)
    List<DetFacture> findProduitAll(@Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin, @Param("operateurId") Long operateurId);
}