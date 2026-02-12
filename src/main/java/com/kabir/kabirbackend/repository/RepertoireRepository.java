package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Repertoire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RepertoireRepository extends JpaRepository<Repertoire, Long>, JpaSpecificationExecutor<Repertoire> {

    boolean existsByDesignationIgnoreCaseAndIdNot(String designation, Long id);
    boolean existsByTel1AndIdNot(String tel1, Long id);
    boolean existsByTel2AndIdNot(String tel2, Long id);
    boolean existsByTel3AndIdNot(String tel3, Long id);
    boolean existsByIceIgnoreCaseAndIdNot(String ice, Long id);
}