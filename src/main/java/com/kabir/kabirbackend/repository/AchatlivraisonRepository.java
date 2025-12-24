package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.AchatLivraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AchatlivraisonRepository extends JpaRepository<AchatLivraison, Long>, JpaSpecificationExecutor<AchatLivraison> {
}