package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.DetBulttinPai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DetbulttinpaiRepository extends JpaRepository<DetBulttinPai, Long>, JpaSpecificationExecutor<DetBulttinPai> {
}