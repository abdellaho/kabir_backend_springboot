package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long>, JpaSpecificationExecutor<Stock> {

    @Query("""
        select sum(s.pattc * s.qteFacturer), sum(s.pattc * s.qteStock)
        from Stock s
        where s.archiver = false
    """)
    Object[] getSumPattcQteFacturerAndStock();

    @Query("""
        select sum(s.pattc * s.qteFacturer), sum(s.pattc * s.qteStock)
        from Stock s
        where s.archiver = true and (s.qteStockImport <> 0 or s.qteStock <> 0)
    """)
    Object[] getSumPattcQteFacturerAndStockArchive();

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
}