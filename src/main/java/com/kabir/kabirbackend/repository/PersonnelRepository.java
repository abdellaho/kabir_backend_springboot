package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, Long>, JpaSpecificationExecutor<Personnel> {
    Optional<Personnel> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query(value = """
        SELECT
            EXISTS (SELECT 1 FROM Absence WHERE (personnel.id = :id or personnelOperation.id = :id))
            OR EXISTS (SELECT 1 FROM Livraison WHERE personnel.id = :id)
            OR EXISTS (SELECT 1 FROM Facture WHERE personnel.id = :id)
            OR EXISTS (SELECT 1 FROM AchatEtranger WHERE operateur.id = :id)
            OR EXISTS (SELECT 1 FROM BonSortie WHERE personnel.id = :id)
            OR EXISTS (SELECT 1 FROM BulletinPai WHERE (commercial.id = :id or operateur.id = :id))
            OR EXISTS (SELECT 1 FROM Cheque WHERE operateur.id = :id)
            OR EXISTS (SELECT 1 FROM Repertoire WHERE personnel.id = :id)
        """)
    boolean isPersonnelUsed(Long id);

    boolean existsByDesignationIgnoreCaseAndIdNotAndSupprimerFalse(String trim, Long id);
    boolean existsByCinIgnoreCaseAndIdNotAndSupprimerFalse(String trim, Long id);
    boolean existsByEmailIgnoreCaseAndIdNotAndSupprimerFalse(String trim, Long id);

    @Query("""
        select case when count(r) > 0 then true else false end
        from Personnel r
        where (r.tel1 = :tel or r.tel2 = :tel)
            and r.id <> :id
            and r.supprimer = false
    """)
    boolean existsByTelAndIdNotAndSupprimerFalse(@Param("tel") String tel, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Personnel r WHERE r.supprimer = true AND r.dateSuppression <= :date")
    void deleteBloquerOlderThan(@Param("date") LocalDate date);
}