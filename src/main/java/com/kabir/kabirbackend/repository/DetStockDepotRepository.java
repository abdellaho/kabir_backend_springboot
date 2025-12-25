package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.DetStockDepot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetStockDepotRepository extends JpaRepository<DetStockDepot, Long>, JpaSpecificationExecutor<DetStockDepot> {
    List<DetStockDepot> findAllByStockDepotId(Long stockDepotId);
}