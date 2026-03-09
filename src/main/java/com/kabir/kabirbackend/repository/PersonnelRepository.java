package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}