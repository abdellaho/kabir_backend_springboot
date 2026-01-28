package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Compta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComptaRepository extends JpaRepository<Compta, Long>, JpaSpecificationExecutor<Compta> {

    @Query("SELECT c FROM Compta c WHERE c.dateFin = (SELECT MAX(cc.dateFin) FROM Compta cc)")
    Optional<Compta> getLastCompta();
}