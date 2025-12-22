package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Stockdepot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StockdepotRepository extends JpaRepository<Stockdepot, Long>, JpaSpecificationExecutor<Stockdepot> {
}