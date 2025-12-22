package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Bulttinpai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BulttinpaiRepository extends JpaRepository<Bulttinpai, Long>, JpaSpecificationExecutor<Bulttinpai> {
}