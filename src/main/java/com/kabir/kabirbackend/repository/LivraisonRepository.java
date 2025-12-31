package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivraisonRepository extends JpaRepository<Livraison, Long>, JpaSpecificationExecutor<Livraison> {
    @Query("SELECT MAX(l.numLivraison) FROM Livraison l WHERE YEAR(l.dateBl) = :year")
    Optional<Integer> findMaxNumLivraisonInYearDateBL(@Param("year") int year);
}