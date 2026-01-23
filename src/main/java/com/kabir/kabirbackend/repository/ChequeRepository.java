package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Cheque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long>, JpaSpecificationExecutor<Cheque> {

    @Query("SELECT MAX(l.numCheque) FROM Cheque l")
    Optional<Integer> findMaxNumCheque();
}