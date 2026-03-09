package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long>, JpaSpecificationExecutor<Fournisseur> {

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
}