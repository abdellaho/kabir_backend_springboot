package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Detfacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DetfactureRepository extends JpaRepository<Detfacture, Long>, JpaSpecificationExecutor<Detfacture> {
}