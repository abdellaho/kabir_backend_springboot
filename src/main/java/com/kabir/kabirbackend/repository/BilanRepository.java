package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Bilan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BilanRepository extends JpaRepository<Bilan, Long>, JpaSpecificationExecutor<Bilan> {
}