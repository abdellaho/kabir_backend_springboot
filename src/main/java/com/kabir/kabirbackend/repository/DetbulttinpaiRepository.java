package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Detbulttinpai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DetbulttinpaiRepository extends JpaRepository<Detbulttinpai, Long>, JpaSpecificationExecutor<Detbulttinpai> {
}