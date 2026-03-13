package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.config.responses.ComptaResponse;
import com.kabir.kabirbackend.entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long>, JpaSpecificationExecutor<Facture> {

    @Query("SELECT MAX(l.numFacture) FROM Facture l WHERE YEAR(l.dateBF) = :year")
    Optional<Integer> findMaxNumFactureInYearDateBL(@Param("year") int year);

    @Query("""
        SELECT new com.kabir.kabirbackend.config.responses.ComptaResponse(SUM(f.tva7Total), SUM(f.tva20Total), SUM(f.tva20Total))
        FROM Facture f
        WHERE f.dateReglement BETWEEN :dateDebut AND :dateFin
    """)
    ComptaResponse getGlobalSums(@Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("""
    SELECT
        SUM(CASE WHEN f.typeReglment = 1 AND f.dateReglement BETWEEN :start AND :end THEN f.mntReglement ELSE 0 END),
        SUM(CASE WHEN f.typeReglment = 2 AND f.dateReglement BETWEEN :start AND :end THEN f.mntReglement ELSE 0 END),
        SUM(CASE WHEN f.typeReglment = 3 AND f.dateReglement BETWEEN :start AND :end THEN f.mntReglement ELSE 0 END),
        SUM(CASE WHEN f.typeReglment = 4 AND f.dateReglement BETWEEN :start AND :end THEN f.mntReglement ELSE 0 END),

        SUM(CASE WHEN f.typeReglment2 = 1 AND f.dateReglement2 BETWEEN :start AND :end THEN f.mntReglement2 ELSE 0 END),
        SUM(CASE WHEN f.typeReglment2 = 2 AND f.dateReglement2 BETWEEN :start AND :end THEN f.mntReglement2 ELSE 0 END),
        SUM(CASE WHEN f.typeReglment2 = 3 AND f.dateReglement2 BETWEEN :start AND :end THEN f.mntReglement2 ELSE 0 END),
        SUM(CASE WHEN f.typeReglment2 = 4 AND f.dateReglement2 BETWEEN :start AND :end THEN f.mntReglement2 ELSE 0 END),

        SUM(CASE WHEN f.typeReglment3 = 1 AND f.dateReglement3 BETWEEN :start AND :end THEN f.mntReglement3 ELSE 0 END),
        SUM(CASE WHEN f.typeReglment3 = 2 AND f.dateReglement3 BETWEEN :start AND :end THEN f.mntReglement3 ELSE 0 END),
        SUM(CASE WHEN f.typeReglment3 = 3 AND f.dateReglement3 BETWEEN :start AND :end THEN f.mntReglement3 ELSE 0 END),
        SUM(CASE WHEN f.typeReglment3 = 4 AND f.dateReglement3 BETWEEN :start AND :end THEN f.mntReglement3 ELSE 0 END),

        SUM(CASE WHEN f.typeReglment4 = 1 AND f.dateReglement4 BETWEEN :start AND :end THEN f.mntReglement4 ELSE 0 END),
        SUM(CASE WHEN f.typeReglment4 = 2 AND f.dateReglement4 BETWEEN :start AND :end THEN f.mntReglement4 ELSE 0 END),
        SUM(CASE WHEN f.typeReglment4 = 3 AND f.dateReglement4 BETWEEN :start AND :end THEN f.mntReglement4 ELSE 0 END),
        SUM(CASE WHEN f.typeReglment4 = 4 AND f.dateReglement4 BETWEEN :start AND :end THEN f.mntReglement4 ELSE 0 END),

        SUM(CASE WHEN f.typeReglment = 1 AND f.mntReglement = 0 AND f.mntReglement2 = 0 AND f.mntReglement3 = 0 AND f.mntReglement4 = 0 AND ((f.dateReglement BETWEEN :start AND :end) OR (f.dateReglement2 BETWEEN :start AND :end) OR (f.dateReglement3 BETWEEN :start AND :end) OR (f.dateReglement4 BETWEEN :start AND :end)) THEN f.mantantBF ELSE 0 END),
        SUM(CASE WHEN f.typeReglment = 2 AND f.mntReglement = 0 AND f.mntReglement2 = 0 AND f.mntReglement3 = 0 AND f.mntReglement4 = 0 AND ((f.dateReglement BETWEEN :start AND :end) OR (f.dateReglement2 BETWEEN :start AND :end) OR (f.dateReglement3 BETWEEN :start AND :end) OR (f.dateReglement4 BETWEEN :start AND :end)) THEN f.mantantBF ELSE 0 END),
        SUM(CASE WHEN f.typeReglment = 3 AND f.mntReglement = 0 AND f.mntReglement2 = 0 AND f.mntReglement3 = 0 AND f.mntReglement4 = 0 AND ((f.dateReglement BETWEEN :start AND :end) OR (f.dateReglement2 BETWEEN :start AND :end) OR (f.dateReglement3 BETWEEN :start AND :end) OR (f.dateReglement4 BETWEEN :start AND :end)) THEN f.mantantBF ELSE 0 END),
        SUM(CASE WHEN f.typeReglment = 4 AND f.mntReglement = 0 AND f.mntReglement2 = 0 AND f.mntReglement3 = 0 AND f.mntReglement4 = 0 AND ((f.dateReglement BETWEEN :start AND :end) OR (f.dateReglement2 BETWEEN :start AND :end) OR (f.dateReglement3 BETWEEN :start AND :end) OR (f.dateReglement4 BETWEEN :start AND :end)) THEN f.mantantBF ELSE 0 END)

    FROM Facture f
    """
    )
    Object[] getReglementStats(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("""
    SELECT f
    FROM Facture f
    WHERE ((f.dateReglement BETWEEN :dateDebut AND :dateFin)
        OR (f.dateReglement2 BETWEEN :dateDebut AND :dateFin)
        OR (f.dateReglement3 BETWEEN :dateDebut AND :dateFin)
        OR (f.dateReglement4 BETWEEN :dateDebut AND :dateFin))
    and ((:subQuery = true AND f.id IN (SELECT df.facture.id FROM DetFacture df WHERE df.facture.id = f.id AND ((:tva7 = true AND df.tva7 <> 0.0) OR (:tva7 = false AND df.tva20 <> 0.0)))) or (:subQuery = false))
    ORDER BY
        CASE WHEN :orderByDateReglement = true THEN f.dateReglement END ASC,
        CASE WHEN :orderByDateReglement = false THEN f.dateBF END ASC,
        f.repertoire.designation ASC
    """)
    List<Facture> getByDateReglementBetween(@Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin,@Param("subQuery") boolean subQuery, @Param("tva7") boolean tva7, @Param("orderByDateReglement") boolean orderByDateReglement);

    @Query("""
    SELECT
        SUM(CASE WHEN f.numRemise LIKE :numRemise THEN f.mntReglement ELSE 0 END),
        SUM(CASE WHEN f.numRemise LIKE :numRemise THEN f.mntReglement2 ELSE 0 END),
        SUM(CASE WHEN f.numRemise LIKE :numRemise THEN f.mntReglement3 ELSE 0 END),
        SUM(CASE WHEN f.numRemise LIKE :numRemise THEN f.mntReglement4 ELSE 0 END)

    FROM Facture f
    """
    )
    Object[] getMntReglementByNumRemise(@Param("numRemise") String numRemise);


}