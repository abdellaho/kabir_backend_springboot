package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.DetAchatLivraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DetachatlivraisonRepository extends JpaRepository<DetAchatLivraison, Long>, JpaSpecificationExecutor<DetAchatLivraison> {
}