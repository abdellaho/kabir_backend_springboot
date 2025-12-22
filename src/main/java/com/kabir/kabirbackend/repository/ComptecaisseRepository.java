package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Comptecaisse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ComptecaisseRepository extends JpaRepository<Comptecaisse, Long>, JpaSpecificationExecutor<Comptecaisse> {
}