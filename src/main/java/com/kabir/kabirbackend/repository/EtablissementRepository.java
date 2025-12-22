package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Etablissement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, Long>, JpaSpecificationExecutor<Etablissement> {
}