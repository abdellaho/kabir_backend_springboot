package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Droit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DroitRepository extends JpaRepository<Droit, Long>, JpaSpecificationExecutor<Droit> {
}