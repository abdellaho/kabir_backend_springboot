package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.DetLivraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DetLivraisonRepository extends JpaRepository<DetLivraison, Long>, JpaSpecificationExecutor<DetLivraison> {
    List<DetLivraison> findByLivraisonId(Long idLivraison);
    List<DetLivraison> findByLivraisonIdAndMontantProduitGreaterThanOrderByStockDesignation(Long livraisonId, double montant);
    List<DetLivraison> findByLivraisonIdAndMontantProduitLessThanEqualOrderByStockDesignation(Long livraisonId, double montant);

    /**
     * Print of the "Consulter Produit" (one product) etat: the delivered rows of one
     * product in the requested date range, ordered like the legacy query
     * {@code order by livraison.dateBl desc}.
     */
    @Query("""
        select d from DetLivraison d
        where d.stock.id = :stockId
          and d.livraison.dateBl between :dateDebut and :dateFin
        order by d.livraison.dateBl desc
    """)
    List<DetLivraison> findByStockIdAndDateBlBetween(@Param("stockId") Long stockId,
                                                     @Param("dateDebut") LocalDate dateDebut,
                                                     @Param("dateFin") LocalDate dateFin);

    /**
     * Aggregation per product of the paid rows (montantProduit <> 0) delivered in the
     * range, optionally restricted to one commercial.
     * Returns rows of {@code [stockId, sumQteLivrer, sumMontantProduit, sumBeneficeDH]}.
     * Pass {@code personnelId = null} to aggregate for every commercial.
     */
    @Query("""
        select d.stock.id, sum(d.qteLivrer), sum(d.montantProduit), sum(d.beneficeDH)
        from DetLivraison d
        where d.livraison.dateBl between :dateDebut and :dateFin
          and (:personnelId is null or d.livraison.personnel.id = :personnelId)
          and d.montantProduit <> 0
        group by d.stock.id
    """)
    List<Object[]> sumMontantProduitByStock(@Param("dateDebut") LocalDate dateDebut,
                                            @Param("dateFin") LocalDate dateFin,
                                            @Param("personnelId") Long personnelId);

    /**
     * Same aggregation for the free rows (montantProduit = 0): only the quantity is
     * used by the reports, the amount and the benefice are printed as zero like in the
     * legacy code.
     */
    @Query("""
        select d.stock.id, sum(d.qteLivrer), sum(d.montantProduit), sum(d.beneficeDH)
        from DetLivraison d
        where d.livraison.dateBl between :dateDebut and :dateFin
          and (:personnelId is null or d.livraison.personnel.id = :personnelId)
          and d.montantProduit = 0
        group by d.stock.id
    """)
    List<Object[]> sumFreeQteByStock(@Param("dateDebut") LocalDate dateDebut,
                                     @Param("dateFin") LocalDate dateFin,
                                     @Param("personnelId") Long personnelId);

    /**
     * Delivered rows of one client in a date range (legacy
     * {@code imprimerProduitsClient} query
     * {@code livraison.repertoireByClient = idRepertoireClientImpr2}).
     */
    @Query("""
        select d from DetLivraison d
        where d.livraison.repertoire.id = :repertoireId
          and d.livraison.dateBl between :dateDebut and :dateFin
    """)
    List<DetLivraison> findByRepertoireIdAndDateBlBetween(@Param("repertoireId") Long repertoireId,
                                                          @Param("dateDebut") LocalDate dateDebut,
                                                          @Param("dateFin") LocalDate dateFin);

    /**
     * Legacy {@code imprimerParNumCheque} pre-filter: distinct stock ids having a
     * delivered row whose {@code numCheque} exactly matches the (trimmed, lower cased)
     * input - legacy {@code lower(livraison.numCheque) like '...'}.
     */
    @Query("""
        select distinct d.stock.id from DetLivraison d
        where lower(d.livraison.numCheque) = :numCheque
    """)
    List<Long> findDistinctStockIdsByNumCheque(@Param("numCheque") String numCheque);

    /**
     * Legacy {@code imprimerParNumCheque} aggregation per stock: quantity and amount
     * summed over the rows matching the loose row filter
     * {@code numCheque like '%X%' or numCheque2 like 'X'}. Rows:
     * {@code [stockId, stockDesignation, sumQteLivrer, sumMontantProduit]}.
     */
    @Query("""
        select d.stock.id, d.stock.designation, sum(d.qteLivrer), sum(d.montantProduit)
        from DetLivraison d
        where d.stock.id in :stockIds
          and (d.livraison.numCheque like concat('%', :numCheque, '%')
               or d.livraison.numCheque2 like :numCheque)
        group by d.stock.id, d.stock.designation
    """)
    List<Object[]> sumQteAndMontantByNumCheque(@Param("stockIds") List<Long> stockIds,
                                               @Param("numCheque") String numCheque);

    /**
     * Detail rows of the bon de sortie "Consulter" etat for one commercial and
     * date range, optionally restricted to one product.
     */
    @Query("""
        select d from DetLivraison d
        join fetch d.livraison
        join fetch d.stock
        where d.livraison.personnel.id = :personnelId
          and d.livraison.dateBl between :dateDebut and :dateFin
          and (:stockId is null or d.stock.id = :stockId)
        order by d.stock.designation, d.livraison.dateBl
    """)
    List<DetLivraison> findPrintDetails(@Param("personnelId") Long personnelId,
                                        @Param("dateDebut") LocalDate dateDebut,
                                        @Param("dateFin") LocalDate dateFin,
                                        @Param("stockId") Long stockId);

    /**
     * Summary quantities per product from the livraisons.
     * Returns rows of {@code [stockId, stockDesignation, stockPvttc, sumQteLivrer]}.
     */
    @Query("""
        select d.stock.id, d.stock.designation, d.stock.pvttc, sum(d.qteLivrer)
        from DetLivraison d
        where d.livraison.personnel.id = :personnelId
          and d.livraison.dateBl between :dateDebut and :dateFin
        group by d.stock.id, d.stock.designation, d.stock.pvttc
    """)
    List<Object[]> sumQteByStock(@Param("personnelId") Long personnelId,
                                 @Param("dateDebut") LocalDate dateDebut,
                                 @Param("dateFin") LocalDate dateFin);
}