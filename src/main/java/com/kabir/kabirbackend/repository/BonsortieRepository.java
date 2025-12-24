package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.BonSortie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BonsortieRepository extends JpaRepository<BonSortie, Long>, JpaSpecificationExecutor<BonSortie> {
}