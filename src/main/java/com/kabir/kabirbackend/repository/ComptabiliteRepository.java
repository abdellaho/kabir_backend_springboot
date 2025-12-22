package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Comptabilite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ComptabiliteRepository extends JpaRepository<Comptabilite, Long>, JpaSpecificationExecutor<Comptabilite> {
}