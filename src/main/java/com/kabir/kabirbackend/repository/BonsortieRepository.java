package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Bonsortie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BonsortieRepository extends JpaRepository<Bonsortie, Long>, JpaSpecificationExecutor<Bonsortie> {
}