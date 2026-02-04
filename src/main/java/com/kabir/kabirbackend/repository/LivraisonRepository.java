package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.dto.DetBulletinLivraisonDTO;
import com.kabir.kabirbackend.entities.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LivraisonRepository extends JpaRepository<Livraison, Long>, JpaSpecificationExecutor<Livraison> {
    @Query("SELECT MAX(l.numLivraison) FROM Livraison l WHERE YEAR(l.dateBl) = :year")
    Optional<Integer> findMaxNumLivraisonInYearDateBL(@Param("year") int year);

    @Query("SELECT new com.kabir.kabirbackend.dto.DetBulletinLivraisonDTO(l.id, l.codeBl, l.dateBl, l.mantantBL, l.mantantBLReel, l.reglerNonRegler, l.repertoire.designation, l.mantantBLBenefice) FROM Livraison l WHERE l.dateBl between :start and :end and l.personnel.id = :repId")
    List<DetBulletinLivraisonDTO> findByDateBlBetweenAndPersonnelId(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("repId") Long repId);
}