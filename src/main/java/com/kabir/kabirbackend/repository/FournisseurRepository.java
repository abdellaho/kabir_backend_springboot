package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long>, JpaSpecificationExecutor<Fournisseur> {


    @Query("""
        select case when count(r) > 0 then true else false end
        from Fournisseur r
        where (r.tel1 = :tel or r.tel2 = :tel)
            and r.id <> :id
            and r.supprimer = false
    """)
    boolean existsByTelAndIdNotAndSupprimerFalse(@Param("tel") String tel, @Param("id") Long id);
    boolean existsByDesignationIgnoreCaseAndIdNotAndSupprimerFalse(String designation, Long id);
    boolean existsByIceIgnoreCaseAndIdNotAndSupprimerFalse(String ice, Long id);

    @Query(value = """
        SELECT
            EXISTS (SELECT 1 FROM Stock WHERE fournisseur.id = :id)
            OR EXISTS (SELECT 1 FROM AchatSimple WHERE fournisseur.id = :id)
            OR EXISTS (SELECT 1 FROM AchatEtranger WHERE fournisseur.id = :id)
            OR EXISTS (SELECT 1 FROM AchatFacture WHERE fournisseur.id = :id)
            OR EXISTS (SELECT 1 FROM AchatSimple WHERE fournisseur.id = :id)
            OR EXISTS (SELECT 1 FROM Cheque WHERE fournisseur.id = :id)
        """)
    boolean isFournisseurUsed(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Fournisseur r WHERE r.supprimer = true AND r.dateSuppression >= :date")
    int deleteBloquerOlderThan(@Param("date") LocalDate date);
}