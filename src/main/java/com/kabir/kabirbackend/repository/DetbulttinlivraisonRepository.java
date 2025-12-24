package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.DetBulttinLivraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DetbulttinlivraisonRepository extends JpaRepository<DetBulttinLivraison, Long>, JpaSpecificationExecutor<DetBulttinLivraison> {
}