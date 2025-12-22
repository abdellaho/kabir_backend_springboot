package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Repertoire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RepertoireRepository extends JpaRepository<Repertoire, Long>, JpaSpecificationExecutor<Repertoire> {
}