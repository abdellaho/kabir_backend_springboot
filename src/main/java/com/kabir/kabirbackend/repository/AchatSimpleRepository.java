package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.AchatSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AchatSimpleRepository extends JpaRepository<AchatSimple, Long>, JpaSpecificationExecutor<AchatSimple> {
}