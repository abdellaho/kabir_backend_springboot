package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.DetailBonSortie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface DetailBonSortieRepository extends JpaRepository<DetailBonSortie, Long>, JpaSpecificationExecutor<DetailBonSortie> {

    @Query("SELECT d FROM DetailBonSortie d WHERE d.bonSortie.id = :id")
    List<DetailBonSortie> findAllByBonSortieId(@Param("id") Long id);

    /**
     * Detail rows of the "Consulter" etat for one commercial and date range,
     * optionally restricted to one product. The fetch joins avoid N+1 selects
     * while mapping the report datasource.
     */
    @Query("""
        select d from DetailBonSortie d
        join fetch d.bonSortie
        join fetch d.stock
        where d.bonSortie.commercial.id = :personnelId
          and d.bonSortie.dateOperation >= :start
          and d.bonSortie.dateOperation < :end
          and (:stockId is null or d.stock.id = :stockId)
        order by d.stock.designation, d.bonSortie.dateOperation
    """)
    List<DetailBonSortie> findPrintDetails(@Param("personnelId") Long personnelId,
                                           @Param("start") Instant start,
                                           @Param("end") Instant end,
                                           @Param("stockId") Long stockId);

    /**
     * Summary quantities per product from the bons de sortie.
     * Returns rows of {@code [stockId, stockDesignation, stockPvttc, sumQteSortie]}.
     */
    @Query("""
        select d.stock.id, d.stock.designation, d.stock.pvttc, sum(d.qteSortie)
        from DetailBonSortie d
        where d.bonSortie.commercial.id = :personnelId
          and d.bonSortie.dateOperation >= :start
          and d.bonSortie.dateOperation < :end
        group by d.stock.id, d.stock.designation, d.stock.pvttc
    """)
    List<Object[]> sumQteByStock(@Param("personnelId") Long personnelId,
                                 @Param("start") Instant start,
                                 @Param("end") Instant end);
}