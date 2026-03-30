package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.config.util.StockSumProjection;
import com.kabir.kabirbackend.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long>, JpaSpecificationExecutor<Stock> {

    @Query("""
        select sum(s.pattc * s.qteFacturer) as sumQteFact, sum(s.pattc * s.qteStock) as sumQteStock
        from Stock s
        where s.archiver = false
    """)
    StockSumProjection getSumPattcQteFacturerAndStock();

    @Query("""
        select sum(s.pattc * s.qteFacturer) as sumQteFact, sum(s.pattc * s.qteStock) as sumQteStock
        from Stock s
        where s.archiver = true and (s.qteStockImport <> 0 or s.qteStock <> 0)
    """)
    StockSumProjection getSumPattcQteFacturerAndStockArchive();

    @Query("""
        select s
        from Stock s
        where s.archiver = false and (:fournisseurId is null or s.fournisseur.id = :fournisseurId)
        order by (case when :fournisseurId is null then s.designation else s.fournisseur.designation end) asc,
                 s.designation asc
    """)
    List<Stock> listStockNonArchiveArchive(@Param("fournisseurId") Long fournisseurId);

    @Query("""
        select s
        from Stock s
        where s.archiver = false and s.prixVentMin2 > 0
        order by s.designation asc
    """)
    List<Stock> listStockPrixCommercialPositive();

    @Query("""
        select s
        from Stock s
        where s.archiver = true and (s.qteStock <> 0 or s.qteStockImport <> 0)
        order by s.designation asc
    """)
    List<Stock> listStockArchive();

    @Query(value = """
        SELECT
            EXISTS (SELECT 1 FROM DetAchatEtranger WHERE stock.id = :id)
            OR EXISTS (SELECT 1 FROM DetAchatFacture WHERE stock.id = :id)
            OR EXISTS (SELECT 1 FROM DetAchatLivraison WHERE stock.id = :id)
            OR EXISTS (SELECT 1 FROM DetAchatSimple WHERE stock.id = :id)
            OR EXISTS (SELECT 1 FROM DetailBonSortie WHERE stock.id = :id)
            OR EXISTS (SELECT 1 FROM DetBulletinPai WHERE produit.id = :id)
            OR EXISTS (SELECT 1 FROM DetFacture WHERE stock.id = :id)
            OR EXISTS (SELECT 1 FROM DetImportation WHERE stock.id = :id)
            OR EXISTS (SELECT 1 FROM DetLivraison WHERE stock.id = :id)
            OR EXISTS (SELECT 1 FROM DetStockDepot WHERE stock.id = :id)
        """)
    boolean isUsed(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Stock r WHERE r.supprimer = true AND r.dateSuppression <= :date")
    void deleteBloquerOlderThan(@Param("date") LocalDate date);
}