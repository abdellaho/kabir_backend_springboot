package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Paramcommiss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ParamcommissRepository extends JpaRepository<Paramcommiss, Long>, JpaSpecificationExecutor<Paramcommiss> {
}