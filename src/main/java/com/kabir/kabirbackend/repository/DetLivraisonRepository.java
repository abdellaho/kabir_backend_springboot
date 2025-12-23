package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.DetLivraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetLivraisonRepository extends JpaRepository<DetLivraison, Long>, JpaSpecificationExecutor<DetLivraison> {
    List<DetLivraison> findByLivraisonId(Long idLivraison);
}