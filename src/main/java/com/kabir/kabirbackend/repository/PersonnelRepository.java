package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, Long>, JpaSpecificationExecutor<Personnel> {
    Optional<Personnel> findByEmail(String email);
    boolean existsByEmail(String email);
}