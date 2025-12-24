package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.PlanComptable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PlancomptableRepository extends JpaRepository<PlanComptable, Long>, JpaSpecificationExecutor<PlanComptable> {
}