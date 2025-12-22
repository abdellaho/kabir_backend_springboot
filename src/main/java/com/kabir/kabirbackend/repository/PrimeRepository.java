package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Prime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimeRepository extends JpaRepository<Prime, Long>, JpaSpecificationExecutor<Prime> {
}