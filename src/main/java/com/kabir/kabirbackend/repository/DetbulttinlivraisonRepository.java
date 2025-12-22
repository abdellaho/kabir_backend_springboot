package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Detbulttinlivraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DetbulttinlivraisonRepository extends JpaRepository<Detbulttinlivraison, Long>, JpaSpecificationExecutor<Detbulttinlivraison> {
}