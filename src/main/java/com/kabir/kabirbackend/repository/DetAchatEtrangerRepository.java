package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.DetAchatEtranger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DetAchatEtrangerRepository extends JpaRepository<DetAchatEtranger, Long>, JpaSpecificationExecutor<DetAchatEtranger> {

    @Query("SELECT d FROM DetAchatEtranger d WHERE d.achatEtranger.id = :achatEtrangerId")
    List<DetAchatEtranger> findAllByAchatEtrangerId(@Param("achatEtrangerId") Long achatEtrangerId);
}