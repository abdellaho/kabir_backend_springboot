package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.DetBulletinLivraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetBulletinLivraisonRepository extends JpaRepository<DetBulletinLivraison, Long>, JpaSpecificationExecutor<DetBulletinLivraison> {

    @Query("SELECT d FROM DetBulletinLivraison d WHERE d.bulletinPai.id = :bulletinLivraisonId")
    List<DetBulletinLivraison> findByBulletinPaiId(@Param("bulletinLivraisonId") Long id);
}