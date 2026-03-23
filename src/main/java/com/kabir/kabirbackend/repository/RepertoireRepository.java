package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Repertoire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface RepertoireRepository extends JpaRepository<Repertoire, Long>, JpaSpecificationExecutor<Repertoire> {

    boolean existsByDesignationIgnoreCaseAndIdNotAndBloquerFalse(String designation, Long id);
    @Query("""
        select case when count(r) > 0 then true else false end
        from Repertoire r
        where (r.tel1 = :tel or r.tel2 = :tel or r.tel3 = :tel)
            and r.id <> :id
            and r.bloquer = false
    """)
    boolean existsByTelAndIdNotAndBloquerFalse(@Param("tel") String tel, @Param("id") Long id);

    boolean existsByIceIgnoreCaseAndIdNotAndBloquerFalse(String ice, Long id);

    @Query(value = """
        SELECT
            EXISTS (SELECT 1 FROM Comptabilite WHERE repertoire.id = :id)
            OR EXISTS (SELECT 1 FROM Employe WHERE repertoire.id = :id)
            OR EXISTS (SELECT 1 FROM Comptabilite WHERE repertoire.id = :id)
            OR EXISTS (SELECT 1 FROM Livraison WHERE repertoire.id = :id)
            OR EXISTS (SELECT 1 FROM Facture WHERE repertoire.id = :id)
            OR EXISTS (SELECT 1 FROM Importation WHERE repertoireFour.id = :id)
        """)
    boolean isRepertoireUsed(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Repertoire r WHERE r.bloquer = true AND r.dateSuppression >= :date")
    int deleteBloquerOlderThan(@Param("date") LocalDate date);
}