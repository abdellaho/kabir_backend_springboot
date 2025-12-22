package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Solde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SoldeRepository extends JpaRepository<Solde, Long>, JpaSpecificationExecutor<Solde> {
}