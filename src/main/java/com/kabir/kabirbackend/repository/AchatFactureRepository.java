package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.AchatFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AchatFactureRepository extends JpaRepository<AchatFacture, Long>, JpaSpecificationExecutor<AchatFacture> {
}