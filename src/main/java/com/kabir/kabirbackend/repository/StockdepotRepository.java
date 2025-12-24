package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.StockDepot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StockdepotRepository extends JpaRepository<StockDepot, Long>, JpaSpecificationExecutor<StockDepot> {
}