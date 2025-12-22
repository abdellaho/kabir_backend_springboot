package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Detimportation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DetimportationRepository extends JpaRepository<Detimportation, Long>, JpaSpecificationExecutor<Detimportation> {
}